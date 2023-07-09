package com.game.playparcels.ui.QueueTab;

import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.game.playparcels.ModelClasses.QueueListModelClass;
import com.game.playparcels.ModelClasses.QueueReturnModelClass;
import com.game.playparcels.ModelClasses.ReOrderModelClass;
import com.game.playparcels.R;
import com.game.playparcels.SevicesApi.ApiClient;
import com.game.playparcels.SevicesApi.ApiInterface;
import com.game.playparcels.ui.GamesTab.RinR.NestedRecyclerLinearLayoutManager;
import com.preference.PowerPreference;


import java.util.ArrayList;
import java.util.List;

import me.rishabhkhanna.recyclerswipedrag.OnDragListener;
import me.rishabhkhanna.recyclerswipedrag.OnSwipeListener;
import me.rishabhkhanna.recyclerswipedrag.RecyclerHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class QueueRecyclerAdapter extends RecyclerView.Adapter<QueueRecyclerAdapter.MyHolder> {
    Context context;
    List<QueueListModelClass.Category> categories;
    private LayoutInflater inflater;
    public View.OnClickListener clickListener;
    ChildQueueAdapter childAdapter;
    Vibrator v;
    QueueFragment fragment;
    Call<ReOrderModelClass> call;

    public QueueRecyclerAdapter(Context context, List<QueueListModelClass.Category> categories, View.OnClickListener clickListener, QueueFragment fragment) {
        this.context = context;
        this.categories = categories;
        this.clickListener = clickListener;
        inflater = LayoutInflater.from(context);
        this.fragment = fragment;
    }


    @NonNull
    @Override
    public QueueRecyclerAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        return new QueueRecyclerAdapter.MyHolder(inflater.inflate(R.layout.category_recycler_queue, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull QueueRecyclerAdapter.MyHolder holder, final int position) {

        holder.populartxt.setText(categories.get(position).status);
        holder.question.setOnClickListener(clickListener);

        List<QueueListModelClass.Order> p = categories.get(position).orders;
        String str = categories.get(position).status;


        LinearLayoutManager layout = new NestedRecyclerLinearLayoutManager(context);
        layout.setOrientation(RecyclerView.VERTICAL);
        holder.childlist.setLayoutManager(layout);
        childAdapter = new ChildQueueAdapter(context, p, clickListener, str, fragment);
        holder.childlist.setAdapter(childAdapter);
        childAdapter.notifyDataSetChanged();


        if (categories.get(position).status.equals("Processing")) {
            //      Library addition from here
            RecyclerHelper touchHelper = new RecyclerHelper<>((ArrayList<QueueListModelClass.Order>) p, childAdapter);
            touchHelper.setRecyclerItemDragEnabled(true).setOnDragItemListener(new OnDragListener() {
                @Override
                public void onDragItemListener(int fromPosition, int toPosition) {
                    Log.d("poipoi", "onDragItemListener: " + fromPosition + " " + toPosition);
//                    Toast.makeText(context, "Item moved from " + fromPosition + " to " + toPosition, Toast.LENGTH_SHORT).show();


                    int ind = categories.get(position).orders.size();
                    ArrayList<Integer> arraylisttt = new ArrayList<Integer>();
                    for (int i = 0; i < ind; i++) {
                        arraylisttt.add(i, i);
                    }

                    Log.d(TAG, "onDragItemListener: arraylisttt " + arraylisttt);

                    int temp = toPosition;
                    arraylisttt.set(toPosition, fromPosition);
                    arraylisttt.set(fromPosition, temp);

                    String finall = arraylisttt.toString();
                    finall = finall.substring(1, finall.length() - 1);
                    Log.d(TAG, "onDragItemListener: arraylistttfinal" + arraylisttt + " " + finall);

                    updatePriority(finall);


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        v.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));
                    } else {
                        //deprecated in API 26
                        v.vibrate(100);
                    }
                }
            });
            touchHelper.setRecyclerItemSwipeEnabled(false).setOnSwipeItemListener(new OnSwipeListener() {
                @Override
                public void onSwipeItemListener() {
                    Log.d("poipoi", "onSwipeItemListener: callback after swiping recycler view item");
                }
            });
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(touchHelper);
            itemTouchHelper.attachToRecyclerView(holder.childlist);


        }

    }

    private void updatePriority(String finall) {
        int userid = PowerPreference.getDefaultFile().getInt("userid");
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        call = apiInterface.reOrderProduct(userid, finall);
        call.enqueue(new Callback<ReOrderModelClass>() {
            @Override
            public void onResponse(Call<ReOrderModelClass> call, Response<ReOrderModelClass> response) {

                ReOrderModelClass reOrderModelClass = response.body();


                if (call.isExecuted()) {
                    assert reOrderModelClass != null;
                    fragment.showToast(reOrderModelClass.message);
                    childAdapter.notifyDataSetChanged();
                }
                //  Toast.makeText(context, ""+reOrderModelClass.message, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<ReOrderModelClass> call, Throwable t) {
                // PowerPreference.getDefaultFile().putBoolean("loggedin",false);
                //viewDialog.hideDialog();
                //  Toast.makeText(context, ""+t, Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount:categories " + categories.size());
        return categories.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        TextView populartxt;
        ImageView question;
        RecyclerView childlist;

        MyHolder(@NonNull View itemView) {
            super(itemView);
            populartxt = itemView.findViewById(R.id.populartxt);
            childlist = itemView.findViewById(R.id.childlist);
            question = itemView.findViewById(R.id.question);

        }
    }
}
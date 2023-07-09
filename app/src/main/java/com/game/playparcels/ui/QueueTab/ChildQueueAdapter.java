package com.game.playparcels.ui.QueueTab;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.game.playparcels.ModelClasses.QueueListModelClass;
import com.game.playparcels.ModelClasses.QueueReturnModelClass;
import com.game.playparcels.R;
import com.game.playparcels.SevicesApi.ApiClient;
import com.game.playparcels.SevicesApi.ApiInterface;
import com.preference.PowerPreference;
import com.squareup.picasso.Picasso;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChildQueueAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<QueueListModelClass.Order> posts;
    Context context;
    public View.OnClickListener clickListener;
    String status;
    QueueFragment fragment;
    Handler handler = new Handler();

    public ChildQueueAdapter(Context context, List<QueueListModelClass.Order> posts, View.OnClickListener clickListener, String status, QueueFragment fragment) {
        this.posts = posts;
        this.context = context;
        this.clickListener = clickListener;
        this.status = status;
        this.fragment = fragment;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img,iconimage;
        TextView commentname,commentdate,availibility;
        LinearLayout swipe_layout;
        TextView returnbtn,returnbtnphoto,preturn,texttttt;
        ProgressBar spinner;

        public ViewHolder(View itemView) {
            super(itemView);

            img= itemView.findViewById(R.id.imagee);
            commentname= itemView.findViewById(R.id.title);
            commentdate = itemView.findViewById(R.id.releasedate);
            availibility = itemView.findViewById(R.id.availabletxt);
            iconimage = itemView.findViewById(R.id.iconimage);

            returnbtn=itemView.findViewById(R.id.returnbtn);
            returnbtnphoto=itemView.findViewById(R.id.returnbtnphoto);
            swipe_layout=itemView.findViewById(R.id.swipe_layout);

            preturn=itemView.findViewById(R.id.preturn);
            spinner= itemView.findViewById(R.id.spinner);
            texttttt=itemView.findViewById(R.id.texttttt);

        }

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.nested_queue_childlist, parent, false);

        ChildQueueAdapter.ViewHolder cavh = new ChildQueueAdapter.ViewHolder(itemLayoutView);
        return cavh;
    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder vh = (ViewHolder) holder;
        vh.texttttt.setText(String.valueOf(holder.getAdapterPosition()+1));
        if(holder.getAdapterPosition() == 0) {
            vh.texttttt.setTextColor(Color.WHITE);
            vh.iconimage.setImageResource(R.drawable.ic_baseline_dehaze_24);
        }else {
            vh.texttttt.setTextColor(Color.GRAY);
            vh.iconimage.setImageResource(R.drawable.ic_baseline_dehaze_24_new);
        }

        vh.commentdate.setText(posts.get(position).orderdate);
        vh.commentname.setText(posts.get(position).productName);
        vh.availibility.setText(posts.get(position).availibility);

        if (posts.get(position).availibility.equals("High Availibility")){
            vh.availibility.setTextColor(Color.parseColor("#adff2f"));
//            holder.circlebtn.setColorFilter(context.getResources().getColor(R.color.green));
        }
        else if (posts.get(position).availibility.equals("Low Availibility")){
            vh.availibility.setTextColor(Color.parseColor("#FF0000"));
        }
        else if(posts.get(position).availibility.equals("Mid Availibility")){
            vh.availibility.setTextColor(Color.parseColor("#FFBF00"));
        }

        vh.returnbtnphoto.setOnClickListener(clickListener);
        vh.returnbtnphoto.setTag(posts.get(position).orderId);

        vh.swipe_layout.setOnClickListener(clickListener);

        if (posts.get(position).orderStatus.equals("processing-return")){
            vh.preturn.setVisibility(View.VISIBLE);
        }
        if (status.equals("Processing")){
            vh.returnbtnphoto.setVisibility(View.GONE);
            vh.returnbtn.setBackgroundColor(context.getResources().getColor(R.color.red));
        }


        /*if (posts.get(position).categoryId==29){
//            Picasso.with(context).load(R.drawable.switchicon).into(vh.iconimage);
            Picasso.with(context).load(posts.get(position).imgUrl).placeholder(R.drawable.switchnin).into(vh.img);
        }else if (posts.get(position).categoryId==26){
//            Picasso.with(context).load(R.drawable.xboxicon).into(vh.iconimage);
            Picasso.with(context).load(posts.get(position).imgUrl).placeholder(R.drawable.xboxone).into(vh.img);
        }else if (posts.get(position).categoryId==27){
//            Picasso.with(context).load(R.drawable.ps4icon).into(vh.iconimage);
            Picasso.with(context).load(posts.get(position).imgUrl).placeholder(R.drawable.psfour).into(vh.img);
        }else*/
            Picasso.with(context).load(posts.get(position).imgUrl).placeholder(R.drawable.gamelog).into(vh.img);

        vh.returnbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YoYo.with(Techniques.FadeOut)
                        .duration(400)
                        .repeat(0)
                        .playOn(vh.returnbtn);

                handler.postDelayed(new Runnable() {
                    public void run() {
                        vh.returnbtn.setVisibility(View.GONE);
                        vh.spinner.setVisibility(View.VISIBLE);
                        YoYo.with(Techniques.FadeIn)
                                .duration(400)
                                .repeat(0)
                                .playOn(vh.spinner);

                    }
                },100);

              int  userid = PowerPreference.getDefaultFile().getInt("userid");
                if (posts.get(position).orderStatus.equals("pending")){
                    vh.preturn.setVisibility(View.VISIBLE);
                    vh.spinner.setVisibility(View.GONE);
                }
                removeQueueProductapi(userid,posts.get(position).orderId);
            }
        });
    }

    @Override
    public int getItemCount() {
        int sizee = posts.size();
       /* if(posts.size()>=15){
            sizee =15;
        }*/
        return sizee;
    }

    private void removeQueueProductapi(final Integer useridd, Integer orderid) {
        ApiInterface apiInterface = ApiClient.getRetrofitInstance().create(ApiInterface.class);
        Call<QueueReturnModelClass> call  = apiInterface.returnProduct(orderid,useridd);
        call.enqueue(new Callback<QueueReturnModelClass>() {
            @Override
            public void onResponse(Call<QueueReturnModelClass> call, Response<QueueReturnModelClass> response) {
                    QueueReturnModelClass queueReturnModelClass = response.body();
                    if(queueReturnModelClass != null) {
                        if (queueReturnModelClass.status) {
                            //  Toast.makeText(context, "order successfully removed", Toast.LENGTH_SHORT).show();
                            Toasty.success(context, queueReturnModelClass.message, Toast.LENGTH_LONG).show();
                            (fragment).getQueueapii(useridd);
                        } else {
                            //   Toast.makeText(context, "" + queueReturnModelClass.message, Toast.LENGTH_SHORT).show();
                            fragment.showToast(queueReturnModelClass.message);
                        }
                    }else {
                        Toasty.success(context, queueReturnModelClass.message, Toast.LENGTH_LONG).show();
                    }
            }

            @Override
            public void onFailure(Call<QueueReturnModelClass> call, Throwable t) {
                fragment.showToast(t.getMessage());
            }
        });

    }
}

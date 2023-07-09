package com.game.playparcels.SingleCategory;

import android.annotation.SuppressLint;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.game.playparcels.Activity.MainActivity;
import com.game.playparcels.ModelClasses.RelatedGamesModelClass;
import com.game.playparcels.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.preference.PowerPreference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

class FullCategoryRecycle extends RecyclerView.Adapter<FullCategoryRecycle.MyHolder> {

    Context context;
    List<RelatedGamesModelClass.Post> posts;

    private LayoutInflater inflater;
    public View.OnClickListener clickListener;
    Integer idd;

    public FullCategoryRecycle(Context context, List<RelatedGamesModelClass.Post> posts, View.OnClickListener clickListener) {

        this.context=context;
        this.posts = posts;
        this.clickListener = clickListener;
        inflater = LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FullCategoryRecycle.MyHolder vv = new FullCategoryRecycle.MyHolder(inflater.inflate(R.layout.category_postrecycler_newxml, parent, false));
        return vv;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull FullCategoryRecycle.MyHolder holder, int position) {

        try {
            holder.titlee.setText(posts.get(position).title);

            holder.addtoqueue.setOnClickListener(clickListener);
            idd = posts.get(position).iD;
            holder.addtoqueue.setTag(idd);

            holder.arrivedDate.setText(posts.get(position).date);
            Picasso.with(context).load(posts.get(position).imageId).placeholder(R.drawable.gamelog).into(holder.image);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        holder.setIsRecyclable(true);

    }


    @Override
    public int getItemCount() {
        if (posts.size()>PowerPreference.getDefaultFile().getInt("itemcount")) {
            return PowerPreference.getDefaultFile().getInt("itemcount");/* posts.size();*/
        }
        else {
            return posts.size();
        }
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView titlee;
        TextView arrivedDate;
        TextView addtoqueue;
        TextView availabletxt;
        RelativeLayout img1, img11;
        RelativeLayout.LayoutParams layoutParams,layoutParamss;

        MyHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
            titlee = itemView.findViewById(R.id.titlee);
            addtoqueue = itemView.findViewById(R.id.addtoqueue);
            availabletxt = itemView.findViewById(R.id.availabletxt);
            arrivedDate = itemView.findViewById(R.id.arrivedDate);
            img1 = itemView.findViewById(R.id.img1);
            img11 = itemView.findViewById(R.id.img11);

            Log.d("lkjhkljhkjh", "idd: " +idd);
            layoutParams = new RelativeLayout.LayoutParams(430, 700);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("data", posts.get(getPosition())); /*.get(getPosition()*/
                    bundle.putString("adapter", "fullrelated");
                    bundle.putInt("idd",idd);
                    GameSingleFragment fragment1 = GameSingleFragment.newInstance();
                    fragment1.setArguments(bundle);
                    FragmentTransaction ft1 = ((YouTubeBaseActivity) context).getFragmentManager().beginTransaction().addToBackStack( "related" );
                    ft1.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
                    ft1.replace(R.id.container, fragment1);
                    ft1.commit();
                }
            });

        }
    }
}

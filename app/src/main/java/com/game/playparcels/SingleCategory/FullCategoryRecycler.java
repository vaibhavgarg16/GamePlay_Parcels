package com.game.playparcels.SingleCategory;

import android.annotation.SuppressLint;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Color;
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
import com.game.playparcels.ModelClasses.HomeGameList.PostsItem;
import com.game.playparcels.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.preference.PowerPreference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

class FullCategoryRecycler extends RecyclerView.Adapter<FullCategoryRecycler.MyHolder> {

    Context context;
    ArrayList<PostsItem> posts;

    private LayoutInflater inflater;
    public View.OnClickListener clickListener;
    Integer idd;

    public FullCategoryRecycler(Context context, ArrayList<PostsItem> posts, View.OnClickListener clickListener) {
        this.context = context;
        this.posts = posts;
        this.clickListener = clickListener;
        inflater = LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new MyHolder(inflater.inflate(R.layout.category_postrecycler_newxml, parent, false));
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        //Log.d("TAG", "onBindViewHolder:postTitle "+posts.get(position).postTitle);

        PostsItem postsItem = posts.get(position);
        holder.titlee.setText(postsItem.getPostTitle());
        try {
            holder.availabletxt.setText(postsItem.getAvailability());
            Log.d("TAG", "onBindViewHolder: " + postsItem.getAvailability());

            if (postsItem.getAvailability().equals("High Availibility")) {
                holder.availabletxt.setTextColor(Color.parseColor("#adff2f"));
            } else if (postsItem.getAvailability().equals("Low Availibility")) {
                holder.availabletxt.setTextColor(Color.parseColor("#FF0000"));
            } else if (postsItem.getAvailability().equals("Mid Availibility")) {
                holder.availabletxt.setTextColor(Color.parseColor("#FFBF00"));
            }


            holder.addtoqueue.setOnClickListener(clickListener);
            idd = postsItem.getID();
            holder.addtoqueue.setTag(idd);

            holder.arrivedDate.setText(postsItem.getPostDate());

            Picasso.with(context).load(postsItem.getPostImageUrl()).placeholder(R.drawable.gamelog).into(holder.image);
        } catch (IndexOutOfBoundsException e) {
            Log.d("TAG", "onBindViewHolder: " + e);
        }
        holder.setIsRecyclable(true);

    }


    @Override
    public int getItemCount() {
        if (posts.size() > PowerPreference.getDefaultFile().getInt("itemcount"))
            return PowerPreference.getDefaultFile().getInt("itemcount");/* posts.size();*/
        else
            return posts.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        ImageView image, available;
        TextView titlee;
        TextView arrivedDate;
        TextView addtoqueue;
        TextView availabletxt;


        RelativeLayout img1, img11;

        RelativeLayout.LayoutParams layoutParams;

        MyHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
            titlee = itemView.findViewById(R.id.titlee);
            available = itemView.findViewById(R.id.available);
            addtoqueue = itemView.findViewById(R.id.addtoqueue);
            availabletxt = itemView.findViewById(R.id.availabletxt);
            arrivedDate = itemView.findViewById(R.id.arrivedDate);
            img1 = itemView.findViewById(R.id.img1);
            img11 = itemView.findViewById(R.id.img11);

            Log.d("lkjhkljhkjh", "idd: " + idd);
            layoutParams = new RelativeLayout.LayoutParams(430, 700);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("data", posts.get(getPosition())); /*.get(getPosition()*/
                    bundle.putString("adapter", "full");
                    bundle.putInt("idd", idd);
                    GameSingleFragment fragment1 = GameSingleFragment.newInstance();
                    //put bundle in fragment
                    fragment1.setArguments(bundle);
                    //simple context wont work
                    FragmentTransaction ft1 = ((YouTubeBaseActivity) context).getFragmentManager().beginTransaction().addToBackStack("tag");
                    ft1.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
                    ft1.replace(R.id.container, fragment1);
                    ft1.commit();
                }
            });

        }
    }
}

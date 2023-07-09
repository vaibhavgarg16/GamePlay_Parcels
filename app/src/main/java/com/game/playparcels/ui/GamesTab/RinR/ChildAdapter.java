package com.game.playparcels.ui.GamesTab.RinR;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.game.playparcels.ModelClasses.HomeGameList.PostsItem;
import com.game.playparcels.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;
import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class ChildAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<PostsItem> posts;
    Context context;
    public View.OnClickListener clickListener;
    int idd;


    int tagKey = "YourSimpleKey".hashCode();
    int tagKey1 = "YourSimpleKey1".hashCode();
    int tagKey2 = "YourSimpleKey2".hashCode();

    public ChildAdapter(Context context, ArrayList<PostsItem> posts, View.OnClickListener clickListener/*, String idd*/) {
        this.posts = posts;
        this.context = context;
        this.clickListener = clickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView commentname;
        TextView commentdate;
        LinearLayout holderid;


        public ViewHolder(View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.imagee);
            commentname = itemView.findViewById(R.id.title);
            commentdate = itemView.findViewById(R.id.releasedate);
            holderid = itemView.findViewById(R.id.holderid);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.nested_recycler_childlist, parent, false);

        ChildAdapter.ViewHolder cavh = new ChildAdapter.ViewHolder(itemLayoutView);
        return cavh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder vh = (ViewHolder) holder;

        PostsItem postsItem = posts.get(position);

        idd = postsItem.getCategoryId();
        vh.commentdate.setText(postsItem.getPostDate());
        vh.commentname.setText(postsItem.getPostTitle());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(430 , 630);

        if (idd == 640) {
            Picasso.with(context).load(postsItem.getPostImageUrl()).placeholder(R.drawable.xboxseries).into(vh.img);
        } else if (idd == 26 || idd == 636 || idd == 90 || idd == 25 || idd == 440 || idd == 641) {
            Picasso.with(context).load(postsItem.getPostImageUrl()).placeholder(R.drawable.xboxone).into(vh.img);
        } else if (idd==29||idd==441||idd==28||idd== 432||idd==622||idd== 623 ||idd==624 ||idd== 607 ||idd== 608||idd== 609 ||idd==605||idd== 606 ||idd==626||idd== 625) {
            vh.img.setLayoutParams(layoutParams);
            Picasso.with(context).load(postsItem.getPostImageUrl()).placeholder(R.drawable.switchnin).into(vh.img);
        } else if (idd==642|| idd == 639 || idd ==645|| idd ==644|| idd ==643|| idd ==647|| idd ==646|| idd ==650|| idd ==648|| idd ==649|| idd ==652|| idd ==651|| idd ==663) {
            Picasso.with(context).load(postsItem.getPostImageUrl()).placeholder(R.drawable.psfive).into(vh.img);
        } else {
            Picasso.with(context).load(postsItem.getPostImageUrl()).placeholder(R.drawable.psfour).into(vh.img);
        }

        vh.holderid.setOnClickListener(clickListener);
        vh.holderid.setTag(tagKey, posts.get(position));
        vh.holderid.setTag(tagKey1, idd);

    }

    @Override
    public int getItemCount() {
        int sizee = (posts != null) ? posts.size() : 0;
        if (sizee >= 8) {
            sizee = 8;
        }
        return sizee;
    }
}

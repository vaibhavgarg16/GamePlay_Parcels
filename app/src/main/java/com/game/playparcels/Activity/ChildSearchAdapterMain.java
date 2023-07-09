package com.game.playparcels.Activity;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.game.playparcels.ModelClasses.SearchGamesModelClass;
import com.game.playparcels.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ChildSearchAdapterMain extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<SearchGamesModelClass.Post> posts;
    Context context;
    View.OnClickListener clickListener;
    int idd;
    Boolean isSearch;

    public ChildSearchAdapterMain(Context context, ArrayList<SearchGamesModelClass.Post> posts, View.OnClickListener clickListener, int idd, Boolean isSearch) {
        this.posts = posts;
        this.context = context;
        this.clickListener = clickListener;
        this.idd = idd;
        this.isSearch = isSearch;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        ImageView img;
        TextView commentname, type;
        TextView commentdate;
        RelativeLayout holderchildid;

        public ViewHolder(View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.imagee);
            commentname = itemView.findViewById(R.id.title);
            commentdate = itemView.findViewById(R.id.releasedate);
            holderchildid = itemView.findViewById(R.id.holderchildid);
            type = itemView.findViewById(R.id.type);

        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


            View itemLayoutView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.nested_recycler_childlist_main_image, parent, false);
            ChildSearchAdapterMain.ViewHolder cavh = new ChildSearchAdapterMain.ViewHolder(itemLayoutView);
            return cavh;



    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ChildSearchAdapterMain.ViewHolder vh = (ChildSearchAdapterMain.ViewHolder) holder;

        vh.commentdate.setText(posts.get(position).postDate);
        vh.commentname.setText(posts.get(position).postTitle);
        vh.holderchildid.setOnClickListener(clickListener);
        vh.holderchildid.setTag(posts.get(position));
        Picasso.with(context).load(posts.get(position).postImageUrl).placeholder(R.drawable.gamelog).into(vh.img);

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

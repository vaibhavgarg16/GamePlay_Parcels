package com.game.playparcels.Activity;

import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.game.playparcels.ModelClasses.SearchGamesModelClass;
import com.game.playparcels.R;
import com.game.playparcels.SingleCategory.GameSingleFragment;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ChildSearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<SearchGamesModelClass.Post> posts;
    Context context;
    public View.OnClickListener clickListener;
    int idd;
    LinearLayout.LayoutParams layoutParamsss = new LinearLayout.LayoutParams(430 ,500);

    public ChildSearchAdapter(Context context, ArrayList<SearchGamesModelClass.Post> posts, View.OnClickListener clickListener, int idd) {
        this.posts = posts;
        this.context = context;
        this.clickListener = clickListener;
        this.idd = idd;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        ImageView img;
        TextView commentname;
        TextView commentdate;

        public ViewHolder(View itemView) {
            super(itemView);

            img= itemView.findViewById(R.id.imagee);
            commentname= itemView.findViewById(R.id.title);
            commentdate = itemView.findViewById(R.id.releasedate);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Bundle bundle = new Bundle();
                    Log.d("poipoi", "onClick: "+posts+"------------");
                    Log.d("poipoi", "onClick: "+posts.get(getPosition()));
                    bundle.putSerializable("data",posts.get(getPosition())); /*.get(getPosition()*/
                    bundle.putString("adapter","childrelated");
                    bundle.putInt("idd",idd);
                    GameSingleFragment fragment1 = GameSingleFragment.newInstance();
                    //put bundle in fragment
                    fragment1.setArguments(bundle);
                    //simple context wont work
                    FragmentTransaction ft1 = ((YouTubeBaseActivity)context).getFragmentManager().beginTransaction().addToBackStack( "tag" );
                    ft1.replace(R.id.container,fragment1);
                    ft1.commit();

                }
            });

        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.nested_recycler_childlist, parent, false);

        ChildSearchAdapter.ViewHolder cavh = new ChildSearchAdapter.ViewHolder(itemLayoutView);
        return cavh;
    }




    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {


        final ChildSearchAdapter.ViewHolder vh = (ChildSearchAdapter.ViewHolder) holder;

        vh.commentdate.setText(posts.get(position).postDate);
        vh.commentname.setText(posts.get(position).postTitle);
        Picasso.with(context).load(posts.get(position).postImageUrl).placeholder(R.drawable.gamelog).into(vh.img);
    }

    @Override
    public int getItemCount() {
        int sizee = (posts!=null) ? posts.size() : 0;
        if(sizee>=8){
            sizee =8;
        }
        return sizee;
    }
}

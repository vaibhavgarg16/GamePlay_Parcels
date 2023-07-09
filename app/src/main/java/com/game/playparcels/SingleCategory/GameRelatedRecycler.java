package com.game.playparcels.SingleCategory;

import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.game.playparcels.Activity.MainActivity;
import com.game.playparcels.ModelClasses.HomeGameListClass;
import com.game.playparcels.ModelClasses.RelatedGamesModelClass;
import com.game.playparcels.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

class GameRelatedRecycler extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<RelatedGamesModelClass.Post> posts;
    Context context;
    public View.OnClickListener clickListener;
    int idd;

    public GameRelatedRecycler(Context context, List<RelatedGamesModelClass.Post> posts, GameSingleFragment clickListener, int idd) {
        this.posts = posts;
        this.context = context;
        this.clickListener =  clickListener;
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

            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {

                    ((MainActivity) context).stopVideo();

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("data",posts.get(getPosition())); /*.get(getPosition()*/
                    bundle.putString("adapter","related");
                    bundle.putInt("idd",idd);
                    GameSingleFragment fragment1 = GameSingleFragment.newInstance();
                    //put bundle in fragment
                    fragment1.setArguments(bundle);
                    //simple context wont work
                    FragmentTransaction ft1 = ((YouTubeBaseActivity)context).getFragmentManager().beginTransaction().addToBackStack( "tag" );
                    ft1.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
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

        GameRelatedRecycler.ViewHolder cavh = new GameRelatedRecycler.ViewHolder(itemLayoutView);
        return cavh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final GameRelatedRecycler.ViewHolder vh = (GameRelatedRecycler.ViewHolder) holder;

        vh.commentdate.setText(posts.get(position).date);
        vh.commentname.setText(posts.get(position).title);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(430 , 630);
        if (idd == 640) {
            Picasso.with(context).load(posts.get(position).imageId).placeholder(R.drawable.xboxseries).into(vh.img);
        } else if (idd == 26 || idd == 636 || idd == 90 || idd == 25 || idd == 440 || idd == 641) {
            Picasso.with(context).load(posts.get(position).imageId).placeholder(R.drawable.xboxone).into(vh.img);
        } else if (idd==29||idd==441||idd==28||idd== 432||idd==622||idd== 623 ||idd==624 ||idd== 607 ||idd== 608||idd== 609 ||idd==605||idd== 606 ||idd==626||idd== 625) {
            vh.img.setLayoutParams(layoutParams);
            Picasso.with(context).load(posts.get(position).imageId).placeholder(R.drawable.switchnin).into(vh.img);
        } else if (idd==642|| idd == 639 || idd ==645|| idd ==644|| idd ==643|| idd ==647|| idd ==646|| idd ==650|| idd ==648|| idd ==649|| idd ==652|| idd ==651|| idd ==663) {
            Picasso.with(context).load(posts.get(position).imageId).placeholder(R.drawable.psfive).into(vh.img);
        } else {
            Picasso.with(context).load(posts.get(position).imageId).placeholder(R.drawable.psfour).into(vh.img);
        }

       /* LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(430 ,700);
       */
    }

    @Override
    public int getItemCount() {
        int sizee = posts.size();
        if(posts.size()>=8){
            sizee =8;
        }
        return sizee;
    }
}

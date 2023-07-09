package com.game.playparcels.ui.GamesTab;

import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.game.playparcels.ModelClasses.HomeGameList.CategoriesItem;
import com.game.playparcels.ModelClasses.HomeGameList.PostsItem;
import com.game.playparcels.R;
import com.game.playparcels.SingleCategory.FullCategoryFragment;
import com.game.playparcels.ui.GamesTab.RinR.ChildAdapter;
import com.game.playparcels.ui.GamesTab.RinR.NestedRecyclerLinearLayoutManager;
import com.google.android.youtube.player.YouTubeBaseActivity;

import java.util.ArrayList;
import java.util.Collections;

public class GameCategoryRecycler extends RecyclerView.Adapter<GameCategoryRecycler.MyHolder> {

    Context context;
//    List<HomeGameListClass.Category> categories;
    ArrayList<CategoriesItem> categories;
    LayoutInflater inflater;
    public View.OnClickListener clickListener;

    public GameCategoryRecycler(Context context/*, List<HomeGameListClass.Category> categories*/, ArrayList<CategoriesItem> categories,View.OnClickListener clickListener) {
        this.context = context;
        this.categories = categories;
        this.clickListener = clickListener;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public GameCategoryRecycler.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GameCategoryRecycler.MyHolder(inflater.inflate(R.layout.category_recycler, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull GameCategoryRecycler.MyHolder holder, final int position) {



        CategoriesItem categoriesItem = categories.get(position);
        holder.populartxt.setText(categoriesItem.getName());
        ArrayList<PostsItem> p = categoriesItem.getPosts();
        Collections.shuffle(p);


        holder.childlist.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        ChildAdapter childAdapter = new ChildAdapter(context,p,clickListener);
        holder.childlist.setAdapter(childAdapter);
        holder.childlist.setHasFixedSize(true);
        holder.childlist.setItemViewCacheSize(5);
        holder.childlist.setDrawingCacheEnabled(true);
        holder.childlist.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        childAdapter.notifyDataSetChanged();

        // Horizontal
     //   ElasticityHelper.setUpOverScroll( holder.childlist, ORIENTATION.HORIZONTAL);

        holder.populartxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("data",categories.get(position)); /*.get(getPosition()*/
                bundle.putString("adapter","categories");
                FullCategoryFragment fragment1 = FullCategoryFragment.newInstance();
                fragment1.setArguments(bundle);
                FragmentTransaction ft1 = ((YouTubeBaseActivity)context).getFragmentManager().beginTransaction().addToBackStack( "tag" );
                ft1.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
                ft1.replace(R.id.container,fragment1);
                ft1.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class MyHolder  extends RecyclerView.ViewHolder{

        TextView populartxt;
        RecyclerView childlist;

        MyHolder(@NonNull View itemView) {
            super(itemView);
            populartxt = itemView.findViewById(R.id.populartxt);
            childlist=itemView.findViewById(R.id.childlist);
        }
    }
}

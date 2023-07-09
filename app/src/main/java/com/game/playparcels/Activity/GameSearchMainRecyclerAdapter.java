package com.game.playparcels.Activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.game.playparcels.ModelClasses.SearchGamesModelClass;
import com.game.playparcels.R;
import com.game.playparcels.ui.GamesTab.RinR.ChildAdapter;
import com.game.playparcels.ui.SearchFragment;
import com.game.playparcels.ui.GamesTab.RinR.NestedRecyclerLinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class GameSearchMainRecyclerAdapter extends RecyclerView.Adapter<GameSearchMainRecyclerAdapter.MyHolder>
{

    Context context;
    List<SearchGamesModelClass.Category> categories;
    private LayoutInflater inflater;
    View.OnClickListener clickListener;
    int idd = 0;
    Boolean isSearch;

    public GameSearchMainRecyclerAdapter(Context context, List<SearchGamesModelClass.Category> categories, View.OnClickListener clickListener,Boolean isSearch) {
        this.context = context;
        this.categories = categories;
        this.clickListener = clickListener;
        this.isSearch = isSearch;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public GameSearchMainRecyclerAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            return new MyHolder(inflater.inflate(R.layout.category_recycler_search, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull GameSearchMainRecyclerAdapter.MyHolder holder, final int position) {
        holder.populartxt.setText(categories.get(position).name);
        // holder.populartxt.setOnClickListener(clickListener);




        ArrayList<SearchGamesModelClass.Post> p = categories.get(position).posts;

        holder.childlist.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        ChildSearchAdapterMain childAdapter = new ChildSearchAdapterMain(context,p,clickListener,0,isSearch);
        holder.childlist.setAdapter(childAdapter);
        holder.childlist.setHasFixedSize(true);
        holder.childlist.setItemViewCacheSize(5);
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

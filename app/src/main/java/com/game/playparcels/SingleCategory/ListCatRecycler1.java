package com.game.playparcels.SingleCategory;

import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.game.playparcels.Activity.MainActivity;
import com.game.playparcels.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

class ListCatRecycler1 extends RecyclerView.Adapter<ListCatRecycler1.MyHolder> {
    List tagcatdata;
    Context context;
    private LayoutInflater inflater;
    public View.OnClickListener clickListener;
    String categoryId;

    public ListCatRecycler1(Context context, List tagcatdata, View.OnClickListener clickListener, String categoryId) {
        this.tagcatdata = tagcatdata;
        this.context = context;
        this.clickListener = clickListener;
        this.categoryId = categoryId;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ListCatRecycler1.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyHolder(inflater.inflate(R.layout.catelistrecycler_new, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ListCatRecycler1.MyHolder holder, final int position) {
        if (tagcatdata.get(position).toString().length() > 2) {
            holder.listtitle.setText(tagcatdata.get(position).toString().substring(0, 1).toUpperCase() + tagcatdata.get(position).toString().substring(1));
        } else {
            holder.listtitle.setText(tagcatdata.get(position).toString().toUpperCase());
        }


        Log.d("TAG", "onBindViewHolder: " + tagcatdata.get(position).toString());
        if (position == 0) {
            holder.view1.setVisibility(View.GONE);
        }


        if (tagcatdata.get(position).toString().contains("pegi") || tagcatdata.get(position).toString().contains("Pegi")) {
            holder.pegiImg.setVisibility(View.VISIBLE);
            holder.listtitle.setVisibility(View.GONE);

            if (tagcatdata.get(position).toString().contains("3")) {
                Picasso.with(context).load(R.drawable.pegi3).fit().into(holder.pegiImg);

            } else if (tagcatdata.get(position).toString().contains("7")) {
                Picasso.with(context).load(R.drawable.pegi7).fit().into(holder.pegiImg);

            } else if (tagcatdata.get(position).toString().contains("12")) {
                Picasso.with(context).load(R.drawable.pegi12).fit().into(holder.pegiImg);

            } else if (tagcatdata.get(position).toString().contains("16")) {
                Picasso.with(context).load(R.drawable.pegi16).fit().into(holder.pegiImg);

            } else if (tagcatdata.get(position).toString().contains("18")) {
                Picasso.with(context).load(R.drawable.pegi18).fit().into(holder.pegiImg);

            } else {
                holder.pegiImg.setVisibility(View.GONE);
                holder.listtitle.setVisibility(View.VISIBLE);
            }
        } else {
            holder.pegiImg.setVisibility(View.GONE);
        }


        holder.rLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putString("tag", tagcatdata.get(position).toString());
                args.putString("categoryId", categoryId);

                FullCategoryFragment fragment1 = FullCategoryFragment.newInstance();
                FragmentTransaction ft1 = ((YouTubeBaseActivity) context).getFragmentManager().beginTransaction();
                fragment1.setArguments(args);
                ft1.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
                ft1.replace(R.id.container, fragment1);
                ft1.commit();

            }
        });


    }

    @Override
    public int getItemCount() {

        return null != tagcatdata ? tagcatdata.size() : 0;
    }

    public static class MyHolder extends RecyclerView.ViewHolder {

        TextView listtitle;
        ImageView pegiImg;
        RelativeLayout rLayout;
        View view1;


        MyHolder(@NonNull View itemView) {
            super(itemView);

            listtitle = itemView.findViewById(R.id.listtitle);
            pegiImg = itemView.findViewById(R.id.pegiImg);
            rLayout = itemView.findViewById(R.id.rLayout);
            view1 = itemView.findViewById(R.id.view1);

        }
    }

}

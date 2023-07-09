package com.game.playparcels.ui.AccountTab;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.game.playparcels.ModelClasses.MyProfileModelClass;
import com.game.playparcels.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

class ProductRentsRecycler extends RecyclerView.Adapter<ProductRentsRecycler.MyHolder> {

    Context context;
    List<MyProfileModelClass.ProductRent> productRents;
    private LayoutInflater inflater;

    ArrayList<MyProfileModelClass.ProductRent> alldataaa;

    public ProductRentsRecycler(Context context, List<MyProfileModelClass.ProductRent> productRents) {
        this.context = context;
        this.productRents = productRents;
        if (context!=null)
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyHolder(inflater.inflate(R.layout.rentproduct_recycler, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        Picasso.with(context)
                .load(productRents.get(position).imageUrl)
                .into(holder.img);

    }

    @Override
    public int getItemCount() {
        return productRents.size();
    }


    class MyHolder extends RecyclerView.ViewHolder {

        ImageView img;

        MyHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.imgggg);
        }


    }
}

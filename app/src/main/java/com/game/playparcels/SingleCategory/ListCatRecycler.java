package com.game.playparcels.SingleCategory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.game.playparcels.R;
import com.squareup.picasso.Picasso;

import java.util.List;

class ListCatRecycler  extends RecyclerView.Adapter<ListCatRecycler.MyHolder> {
    List tagcatdata;
    Context context;
    private LayoutInflater inflater;

    public ListCatRecycler(Context context, List tagcatdata) {
        this.tagcatdata = tagcatdata;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ListCatRecycler.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ListCatRecycler.MyHolder(inflater.inflate(R.layout.catelistrecycler, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ListCatRecycler.MyHolder holder, int position) {
       holder.listtitle.setText(tagcatdata.get(position).toString());

        Picasso.with(context).load(R.drawable.checkbox).fit().into(holder.chkbox);
       if (tagcatdata.get(position).toString().contains("pegi")){
           holder.chkbox.setVisibility(View.GONE);
           holder.pegiImg.setVisibility(View.VISIBLE);
           holder.listcattxt.setText("Age Rating");

           if (tagcatdata.get(position).toString().contains("3")){
               Picasso.with(context).load(R.drawable.pegi3).fit().into(holder.pegiImg);

           } else  if (tagcatdata.get(position).toString().contains("7")){
               Picasso.with(context).load(R.drawable.pegi7).fit().into(holder.pegiImg);

           } else  if (tagcatdata.get(position).toString().contains("12")){
               Picasso.with(context).load(R.drawable.pegi12).fit().into(holder.pegiImg);

           } else  if (tagcatdata.get(position).toString().contains("16")){
               Picasso.with(context).load(R.drawable.pegi16).fit().into(holder.pegiImg);

           } else  if (tagcatdata.get(position).toString().contains("18")){
               Picasso.with(context).load(R.drawable.pegi18).fit().into(holder.pegiImg);

           } else {

               holder.chkbox.setVisibility(View.VISIBLE);
               holder.pegiImg.setVisibility(View.GONE);
           }
       }
       else {
           holder.listcattxt.setText("Category");
           holder.chkbox.setVisibility(View.VISIBLE);
           holder.pegiImg.setVisibility(View.GONE);
       }

    }

    @Override
    public int getItemCount() {

        return  null!=tagcatdata?tagcatdata.size():0;
    }

    public class MyHolder  extends RecyclerView.ViewHolder{

        TextView listtitle,listcattxt;
        ImageView chkbox,pegiImg;


        MyHolder(@NonNull View itemView) {
            super(itemView);

            listtitle = itemView.findViewById(R.id.listtitle);
            listcattxt=itemView.findViewById(R.id.listcattxt);
            chkbox = itemView.findViewById(R.id.chkbox);
            pegiImg = itemView.findViewById(R.id.pegiImg);



        }
    }

}

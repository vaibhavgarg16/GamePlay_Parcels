package com.game.playparcels.ui.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.game.playparcels.R;
import net.webilisim.webslider.WEBSliderViewAdapter;
import java.util.List;

public class SliderAdapter extends WEBSliderViewAdapter<SliderAdapter.AdapterViewHolder> {

    private Context context;
    private List<String> mSliderItems;

    public SliderAdapter(Context context, List<String> mSliderItems) {
        this.context = context;
        this.mSliderItems = mSliderItems;
    }

    @Override
    public SliderAdapter.AdapterViewHolder onCreateViewHolder(ViewGroup parent) {
        @SuppressLint("InflateParams")
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_webslider_adapter_layout1, null);
        return new SliderAdapter.AdapterViewHolder(inflate);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(SliderAdapter.AdapterViewHolder viewHolder, int position) {
        String item = mSliderItems.get(position);
        if(item != null) {
            Glide.with(context)
                    .load(item)
                    .into(viewHolder.imageView);
        }
    }

    @Override
    public int getCount() {
        return mSliderItems.size();
    }

    class AdapterViewHolder extends WEBSliderViewAdapter.ViewHolder {
        ImageView imageView;
        AdapterViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.webslider_image);
        }
    }
}
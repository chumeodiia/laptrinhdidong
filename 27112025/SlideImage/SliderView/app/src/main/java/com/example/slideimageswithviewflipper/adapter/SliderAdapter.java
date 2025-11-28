package com.example.slideimageswithviewflipper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.example.slideimageswithviewflipper.R;

import java.util.ArrayList;
import  com.smarteist.autoimageslider.SliderViewAdapter;


public class SliderAdapter extends SliderViewAdapter<SliderAdapter.SliderHolder> {

    private Context context;
    private ArrayList<Integer> arrayList;

    public SliderAdapter(Context context, ArrayList<Integer> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public SliderHolder onCreateViewHolder(@NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_auto_image_slider, parent, false);
        return new SliderHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SliderHolder viewHolder, int position) {
        int imgResId = arrayList.get(position);
        viewHolder.imgSlider.setImageResource(imgResId);
    }

    @Override
    public int getCount() {
        return arrayList != null ? arrayList.size() : 0;
    }

    public static class SliderHolder extends SliderViewAdapter.ViewHolder {

        ImageView imgSlider;

        public SliderHolder(@NonNull View itemView) {
            super(itemView);
            imgSlider = itemView.findViewById(R.id.imgSlider);
        }
    }
}

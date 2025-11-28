package com.example.slideimageswithviewflipper.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.slideimageswithviewflipper.R;

import java.util.List;

public class ImagesViewPagerAdapter extends PagerAdapter {

    private Context context;
    private List<String> imageUrls;   // dùng URL như bên ViewFlipper

    public ImagesViewPagerAdapter(Context context, List<String> imageUrls) {
        this.context = context;
        this.imageUrls = imageUrls;
    }

    @Override
    public int getCount() {
        return imageUrls != null ? imageUrls.size() : 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_viewpager_image, container, false);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        ImageView imgBanner = view.findViewById(R.id.imgBanner);

        String url = imageUrls.get(position);
        Glide.with(context)
                .load(url)
                .into(imgBanner);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container,
                            int position,
                            @NonNull Object object) {
        container.removeView((View) object);
    }
}

package com.example.slideimageswithviewflipper.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.slideimageswithviewflipper.R;
import com.example.slideimageswithviewflipper.adapter.SliderAdapter;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

public class SliderViewActivity extends AppCompatActivity {

    private SliderView imageSlider;           // sliderView trong slide
    private SliderAdapter sliderAdapter;      // Adapter
    private ArrayList<Integer> imagesList;    // danh sách hình
    ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider_view);

        // ánh xạ sliderView
        imageSlider = findViewById(R.id.imageSlider);
        imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(v -> finish());

        imagesList = getListImages();

        // gọi adapter, truyền dữ liệu
        sliderAdapter = new SliderAdapter(this, imagesList);
        imageSlider.setSliderAdapter(sliderAdapter);

        // Xác lập một số thuộc tính cho SliderView
        imageSlider.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        imageSlider.setScrollTimeInSec(2);  // thời gian chuyển hình
        imageSlider.setAutoCycle(true);
        imageSlider.startAutoCycle();
    }
    private ArrayList<Integer> getListImages() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(R.drawable.slide_1);
        list.add(R.drawable.slide_2);
        list.add(R.drawable.slide_3);
        list.add(R.drawable.slide_4);
        return list;
    }
}

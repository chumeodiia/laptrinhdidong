package com.example.viewpagerandindicator;

import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.viewpagerandindicator.model.Images;
import com.example.viewpagerandindicator.model.adapter.ImagesViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private CircleIndicator circleIndicator;
    private List<Images> imagesList;

    //autorun
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if(viewPager.getCurrentItem() == imagesList.size() - 1){
                viewPager.setCurrentItem(0);
            }else{
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.viewpager);
        circleIndicator = findViewById(R.id.circle_indicator);

        imagesList = getListImages();
        ImagesViewPagerAdapter adapter = new ImagesViewPagerAdapter(imagesList);
        viewPager.setAdapter(adapter);

        //Liên kết viewpager và indicator
        circleIndicator.setViewPager(viewPager);

        //gọi runnable
        handler.postDelayed(runnable, 3000);

        //Lắng nghe viewpager chuyển trang
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, 3000);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }
    private List<Images> getListImages(){
        List<Images> advertisementImages = new ArrayList<>();
        advertisementImages.add(new Images(R.drawable.quangcao));
        advertisementImages.add(new Images(R.drawable.cofee));
        advertisementImages.add(new Images(R.drawable.companypizza));
        advertisementImages.add(new Images(R.drawable.themoingon));
        return advertisementImages;
    }
    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(runnable, 3000);
    }
}
package com.example.slideimageswithviewflipper.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.slideimageswithviewflipper.R;
import com.example.slideimageswithviewflipper.adapter.ImagesViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

public class ViewPagerActivity extends AppCompatActivity {

    private ViewPager viewPagerImages;
    private CircleIndicator circleIndicator;
    private ImagesViewPagerAdapter imagesAdapter;
    private ImageView imgBack;


    private List<String> imageUrls;

    // cho auto slide
    private Handler handler = new Handler();
    private Runnable runnable;
    private int currentPage = 0;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        viewPagerImages = findViewById(R.id.viewPagerImages);
        circleIndicator = findViewById(R.id.circleIndicator);
        imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(v -> finish());

        imageUrls = new ArrayList<>();
        imageUrls.add("https://img.tripi.vn/cdn-cgi/image/width=700,height=700/https://gcs.tripi.vn/public-tripi/tripi-feed/img/474102xrM/hinh-nen-moto-4k-do-phan-giai-cao-cuc-dep_101527906.jpg");
        imageUrls.add("https://img.tripi.vn/cdn-cgi/image/width=700,height=700/https://gcs.tripi.vn/public-tripi/tripi-feed/img/474102fPP/hinh-nen-moto-4k_101648520_thumb.jpg");
        imageUrls.add("https://img.tripi.vn/cdn-cgi/image/width=700,height=700/https://gcs.tripi.vn/public-tripi/tripi-feed/img/474102Uce/hinh-nen-xe-moto-bmw-hp4_101657420.jpg");
        imageUrls.add("https://img.tripi.vn/cdn-cgi/image/width=700,height=700/https://gcs.tripi.vn/public-tripi/tripi-feed/img/474102wUY/hinh-nen-xe-moto-phan-khoi-lon-dep_101700887.jpg");

        // 2. Gắn adapter cho ViewPager
        imagesAdapter = new ImagesViewPagerAdapter(this, imageUrls);
        viewPagerImages.setAdapter(imagesAdapter);

        // 3. Liên kết CircleIndicator với ViewPager
        circleIndicator.setViewPager(viewPagerImages);

        // 4. Tạo auto slide
        setupAutoSlide();
    }

    private void setupAutoSlide() {
        runnable = new Runnable() {
            @Override
            public void run() {
                int count = imagesAdapter.getCount();
                if (count == 0) return;

                if (currentPage >= count) {
                    currentPage = 0;
                }
                viewPagerImages.setCurrentItem(currentPage, true);
                currentPage++;

                // lặp lại sau 3 giây
                handler.postDelayed(this, 3000);
            }
        };

        // bắt đầu chạy lần đầu sau 3 giây
        handler.postDelayed(runnable, 2000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // vào lại thì chạy auto slide tiếp
        if (runnable != null) {
            handler.postDelayed(runnable, 2000);
        }
    }
}

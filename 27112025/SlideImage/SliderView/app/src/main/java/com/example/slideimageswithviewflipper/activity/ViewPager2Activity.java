package com.example.slideimageswithviewflipper.activity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.slideimageswithviewflipper.R;
import com.example.slideimageswithviewflipper.adapter.ImagesVp2Adapter;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;

public class ViewPager2Activity extends AppCompatActivity {

    private ViewPager2 viewPager2Images;
    private CircleIndicator3 circleIndicator3;
    private ImagesVp2Adapter imagesVp2Adapter;
    private ImageView imgBack;

    private List<String> imageUrls;

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (imageUrls == null || imageUrls.isEmpty()) return;

            int lastIndex = imageUrls.size() - 1;
            int current = viewPager2Images.getCurrentItem();

            // nếu đang ở trang cuối thì quay về 0, ngược lại +1
            if (current == lastIndex) {
                viewPager2Images.setCurrentItem(0, true);
            } else {
                viewPager2Images.setCurrentItem(current + 1, true);
            }
        }
    };
    // -----------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager2);

        viewPager2Images = findViewById(R.id.viewPager2Images);
        circleIndicator3 = findViewById(R.id.circleIndicator3);
        imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(v -> finish());

        // 1. Chuẩn bị danh sách URL (4 ảnh)
        imageUrls = new ArrayList<>();
        imageUrls.add("https://aozoom.com.vn/storage/uploads/content/images/Man-nhan-voi-mau-sieu-xe-ma-ban-yeu-thich.jpg");
        imageUrls.add("https://aozoom.com.vn/storage/uploads/content/images/mau-sieu-xe-cai-lam-hinh-nen.jpg");
        imageUrls.add("https://aozoom.com.vn/storage/uploads/content/images/Tai-ngay-ve-may-hinh-nen-xe-o-to-sang-trong.jpg");
        imageUrls.add("https://aozoom.com.vn/storage/uploads/content/images/Tam-anh-hinh-nen-mang-lai-cho-cam-giac-nhu-chinh-chu-so-huu.jpg");

        // 2. Adapter cho ViewPager2
        imagesVp2Adapter = new ImagesVp2Adapter(this, imageUrls);
        viewPager2Images.setAdapter(imagesVp2Adapter);

        // 3. Indicator
        circleIndicator3.setViewPager(viewPager2Images);
        imagesVp2Adapter.registerAdapterDataObserver(
                circleIndicator3.getAdapterDataObserver()
        );

        // 4. Lắng nghe chuyển trang để reset timer (đúng như slide)
        viewPager2Images.registerOnPageChangeCallback(
                new ViewPager2.OnPageChangeCallback() {
                    @Override
                    public void onPageSelected(int position) {
                        super.onPageSelected(position);
                        // mỗi lần sang trang mới -> reset lại đếm 3s
                        handler.removeCallbacks(runnable);
                        handler.postDelayed(runnable, 3000);
                    }
                }
        );

        // 5. PageTransformer (hiệu ứng Depth như slide)
        viewPager2Images.setPageTransformer(new DepthPageTransformer());
    }

    @Override
    protected void onResume() {
        super.onResume();
        // bắt đầu auto sau 3s khi màn hình hiển thị
        handler.postDelayed(runnable, 3000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // rời màn hình thì dừng lại
        handler.removeCallbacks(runnable);
    }
}

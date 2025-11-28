package com.example.slideimageswithviewflipper.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.slideimageswithviewflipper.R;

import java.util.ArrayList;
import java.util.List;

public class ViewFlipperActivity extends AppCompatActivity {

    private ViewFlipper viewFlipperMain;
    private  ImageView imgBack;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_flipper);

        // ánh xạ ViewFlipper trong layout
        viewFlipperMain = findViewById(R.id.viewFlipperMain);
        imgBack = (ImageView) findViewById(R.id.imgBack);

        // gọi hàm xử lý flipper
        ActionViewFlipperMain();
        imgBack.setOnClickListener(v -> finish());
    }

    // hàm Flipper giống slide
    private void ActionViewFlipperMain() {
        // 1. Tạo danh sách URL hình
        List<String> arrayListFlipper = new ArrayList<>();
        arrayListFlipper.add("https://vmstyle.vn/wp-content/uploads/2025/09/hinh-nen-pokemon-3d-chan-thuc-hieu-ung-chieu-sau-song-dong.jpg");
        arrayListFlipper.add("https://vmstyle.vn/wp-content/uploads/2025/09/anh-nen-squirtle-he-nuoc-dang-yeu-full-hd4k.jpg");
        arrayListFlipper.add("https://vmstyle.vn/wp-content/uploads/2025/09/hinh-nen-gekkouga-mega-4k-sieu-ngau-he-nuoc.jpg");
        arrayListFlipper.add("https://vmstyle.vn/wp-content/uploads/2025/09/hinh-nen-pikachu-he-dien-4k-mau-vang-noi-bat.jpg");

        // 2. Với mỗi URL → tạo 1 ImageView, load hình bằng Glide, add vào ViewFlipper
        for (int i = 0; i < arrayListFlipper.size(); i++) {
            ImageView imageView = new ImageView(getApplicationContext());

            Glide.with(getApplicationContext())
                    .load(arrayListFlipper.get(i))
                    .into(imageView);

            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipperMain.addView(imageView);
        }

        // 3. Thiết lập thời gian tự chuyển & tự động chạy
        viewFlipperMain.setFlipInterval(2000); // 3 giây đổi 1 ảnh
        viewFlipperMain.setAutoStart(true);

        // 4. Thiết lập animation cho flipper (vào / ra)
        Animation slide_in = AnimationUtils.loadAnimation(
                getApplicationContext(), R.anim.slide_in_right);
        Animation slide_out = AnimationUtils.loadAnimation(
                getApplicationContext(), R.anim.slide_out_left);

        viewFlipperMain.setInAnimation(slide_in);
        viewFlipperMain.setOutAnimation(slide_out);


    }
}

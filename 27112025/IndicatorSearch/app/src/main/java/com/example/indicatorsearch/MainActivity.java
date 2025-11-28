package com.example.indicatorsearch;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.widget.SearchView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.indicatorsearch.adapter.IconAdapter;
import com.example.indicatorsearch.model.IconModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    float DP;
    RecyclerView rcIcon;
    ArrayList<IconModel> arrayList1;
    IconAdapter iconAdapter;

    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        DP = getResources().getDisplayMetrics().density;

        rcIcon = findViewById(R.id.rclcon);  // hoặc binding.rclcon nếu dùng ViewBinding

        arrayList1 = new ArrayList<>();
        arrayList1.add(new IconModel(R.drawable.icon1, "fjdjfdjf djfdh"));
        arrayList1.add(new IconModel(R.drawable.icon1, "sdfdf sdfsf"));
        arrayList1.add(new IconModel(R.drawable.icon1, "sdfdf sfds"));
        arrayList1.add(new IconModel(R.drawable.icon1, "dfgfhyh sxdff"));
        arrayList1.add(new IconModel(R.drawable.icon1, "jfjdjfdjf djfdh"));
        arrayList1.add(new IconModel(R.drawable.icon1, "sdfdf sfds"));
        arrayList1.add(new IconModel(R.drawable.icon1, "sdfdf sfds"));
        arrayList1.add(new IconModel(R.drawable.icon1, "dfgfhyh sxdff"));
        arrayList1.add(new IconModel(R.drawable.icon1, "dfgfhyh sxdff"));
        arrayList1.add(new IconModel(R.drawable.icon1, "fjdjfdjf djfdh"));
        arrayList1.add(new IconModel(R.drawable.icon1, "sdfdf sfds"));
        arrayList1.add(new IconModel(R.drawable.icon1, "sdfdf sfds"));
        arrayList1.add(new IconModel(R.drawable.icon1, "dfgfhyh sxdff"));
        arrayList1.add(new IconModel(R.drawable.icon1, "jfjdjfdjf djfdh"));
        arrayList1.add(new IconModel(R.drawable.icon1, "sdfdf sfds"));
        arrayList1.add(new IconModel(R.drawable.icon1, "sdfdf sfds"));
        arrayList1.add(new IconModel(R.drawable.icon1, "dfgfhyh sxdff"));
        arrayList1.add(new IconModel(R.drawable.icon1, "dfgfhyh sxdff"));

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.HORIZONTAL, false);
        rcIcon.setLayoutManager(gridLayoutManager);

        iconAdapter = new IconAdapter(getApplicationContext(), arrayList1);
        rcIcon.setAdapter(iconAdapter);

        rcIcon.addItemDecoration(new LinePagerIndicatorDecoration());

        searchView = findViewById(R.id.searchView);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterListener(newText);
                return true;
            }
        });
    }
    public class LinePagerIndicatorDecoration extends RecyclerView.ItemDecoration {

        private final Paint paint = new Paint();
        private final int indicatorHeight = 20;           // chiều cao của thanh trượt
        private final float indicatorStrokeWidth = 6f;    // độ dày thanh trượt
        private final int activeColor = 0xFFFFFFFF;       // màu trắng khi active
        private final int inactiveColor = 0x88FFFFFF;     // màu xám khi không active

        public LinePagerIndicatorDecoration() {
            paint.setStrokeWidth(indicatorStrokeWidth);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeCap(Paint.Cap.ROUND);
        }

        @Override
        public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.onDrawOver(c, parent, state);

            int itemCount = parent.getAdapter().getItemCount();

            // tính chiều dài mỗi đoạn
            float totalLength = indicatorStrokeWidth * itemCount;
            float paddingBetween = (parent.getWidth() - totalLength) / (itemCount + 1f);

            LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
            int activePosition = layoutManager.findFirstVisibleItemPosition();
            if (activePosition == RecyclerView.NO_POSITION) return;

            View activeChild = layoutManager.findViewByPosition(activePosition);
            if (activeChild == null) return;

            // vị trí bắt đầu vẽ
            float indicatorStartX = paddingBetween + indicatorStrokeWidth / 2f;
            float indicatorEndX = parent.getWidth() - paddingBetween - indicatorStrokeWidth / 2f;

            // vẽ các đoạn inactive
            for (int i = 0; i < itemCount; i++) {
                paint.setColor(inactiveColor);
                float x = indicatorStartX + (paddingBetween + indicatorStrokeWidth) * i;
                c.drawLine(x, parent.getHeight() - indicatorHeight / 2f,
                        x + indicatorStrokeWidth, parent.getHeight() - indicatorHeight / 2f, paint);
            }

            // vẽ đoạn active (trắng đậm)
            paint.setColor(activeColor);
            float activeX = indicatorStartX + (paddingBetween + indicatorStrokeWidth) * activePosition;
            c.drawLine(activeX, parent.getHeight() - indicatorHeight / 2f,
                    activeX + indicatorStrokeWidth, parent.getHeight() - indicatorHeight / 2f, paint);
        }

        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.bottom = indicatorHeight + 20; // chừa chỗ cho indicator
        }
    }
    private void filterListener(String text) {
        List<IconModel> list = new ArrayList<>();
        for (IconModel iconModel : arrayList1) {
            if (iconModel.getDesc().toLowerCase().contains(text.toLowerCase())) {
                list.add(iconModel);
            }
        }
        if (list.isEmpty()) {
            Toast.makeText(this, "Không có dữ liệu", Toast.LENGTH_SHORT).show();
        } else {
            iconAdapter.setListenerList(list);
        }
    }
}
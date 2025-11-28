package com.example.recyclerviewwithindicator_search;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class LinePagerIndicatorDecoration extends RecyclerView.ItemDecoration {

    private final int colorActive = 0xFFFFFFFF;   // trắng
    private final int colorInactive = 0x44FFFFFF; // trắng mờ

    private final float DP;
    private final float indicatorHeight;
    private final float indicatorStrokeWidth;
    private final float indicatorItemLength;
    private final float indicatorItemPadding;

    private final Paint paint = new Paint();
    // khởi tạo các thông số vẽ, chuyển đổi đơn vị từ dp sang px để hieển thị đẹp trên các màn hình khác nhauu
    public LinePagerIndicatorDecoration(Context context) {
        // 1. Calculate dimensions first
        DP = context.getResources().getDisplayMetrics().density;
        indicatorHeight = 16 * DP;
        indicatorStrokeWidth = 2 * DP;
        indicatorItemLength = 8 * DP;
        indicatorItemPadding = 4 * DP;

        // 2. Configure paint AFTER dimensions are set
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(indicatorStrokeWidth); // Now safe to use
        paint.setAntiAlias(true);
    }

    @Override
    public void onDrawOver(@NonNull Canvas c,
                           @NonNull RecyclerView parent,
                           @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        // mỗi khi item thay đổi thì vẽ lại, lấy số phàn tử trong adp để vẽ tất cả các indicator
        int itemCount = parent.getAdapter() != null
                ? parent.getAdapter().getItemCount() : 0;
        if (itemCount <= 0) return;

        float totalLength = indicatorItemLength * itemCount;
        float paddingBetweenItems = Math.max(0, itemCount - 1) * indicatorItemPadding;
        float indicatorTotalWidth = totalLength + paddingBetweenItems;
        float indicatorStartX = (parent.getWidth() - indicatorTotalWidth) / 2f;
        float indicatorPosY = parent.getHeight() - indicatorHeight;

        // vẽ tất cả indicator inactive - vẽ các gạch mờ
        drawInactiveIndicators(c, indicatorStartX, indicatorPosY, itemCount);

        // item đang hiển thị
        LinearLayoutManager layoutManager =
                (LinearLayoutManager) parent.getLayoutManager();
        if (layoutManager == null) return;
        // lấy vị trí của item đang hiển thị
        int activePosition = layoutManager.findFirstVisibleItemPosition();
        if (activePosition == RecyclerView.NO_POSITION) return;

        drawHighlights(c, indicatorStartX, indicatorPosY, activePosition);
    }

    private void drawInactiveIndicators(Canvas c,
                                        float startX,
                                        float posY,
                                        int itemCount) {
        paint.setColor(colorInactive);

        float itemWidth = indicatorItemLength + indicatorItemPadding;
        float start = startX;

        for (int i = 0; i < itemCount; i++) {
            c.drawLine(start, posY,
                    start + indicatorItemLength, posY, paint);
            start += itemWidth;
        }
    }

    private void drawHighlights(Canvas c,
                                float startX,
                                float posY,
                                int highlightPosition) {
        paint.setColor(colorActive);

        float itemWidth = indicatorItemLength + indicatorItemPadding;
        float highlightStart = startX + itemWidth * highlightPosition;
        c.drawLine(highlightStart, posY,
                highlightStart + indicatorItemLength, posY, paint);
    }
}

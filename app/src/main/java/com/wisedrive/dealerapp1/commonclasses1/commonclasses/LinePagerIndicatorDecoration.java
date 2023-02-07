package com.wisedrive.dealerapp1.commonclasses1.commonclasses;

import static androidx.annotation.Dimension.DP;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;

import androidx.annotation.ColorInt;
import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wisedrive.dealerapp1.R;

import org.jetbrains.annotations.NotNull;

public class LinePagerIndicatorDecoration extends RecyclerView.ItemDecoration{

    //set indicator bottom padding
    private final int indicatorHeight=(int) (DP * 16);
    private final int indicatorItemPadding=(int)(DP * 6);
    private final int radius=(int)(DP * 3);
    private final Paint inactivePaint = new Paint();
    private final Paint activePaint = new Paint();
    private final int circleRadius = 8;
  Context context;
    private int colorActive = 0xFFFFFFFF;
  //  private int colorInactive = 0x66FFFFFF;
  private final float mIndicatorItemLength = DP * 16;
  private final Interpolator mInterpolator = new AccelerateDecelerateInterpolator();

    private final Paint mPaint = new Paint();

    private static final float DP = Resources.getSystem().getDisplayMetrics().density;
    public LinePagerIndicatorDecoration(@ColorInt int colorInactive, @ColorInt int colorActive,Context context)
    {
        float strokeWidth = Resources.getSystem().getDisplayMetrics().density * 1;
        inactivePaint.setStrokeCap(Paint.Cap.ROUND);
        inactivePaint.setStrokeWidth(strokeWidth);
        inactivePaint.setStyle(Paint.Style.STROKE);
        inactivePaint.setAntiAlias(true);
        inactivePaint.setColor(colorInactive);

        activePaint.setStrokeCap(Paint.Cap.ROUND);
        activePaint.setStrokeWidth(strokeWidth);
        activePaint.setStyle(Paint.Style.FILL);
        activePaint.setAntiAlias(true);
        activePaint.setColor(colorActive);
        this.context=context;

    }

    @Override
    public void onDrawOver(@NotNull Canvas c, @NotNull RecyclerView parent, @NotNull RecyclerView.State state)
    {
        super.onDrawOver(c, parent, state);

        final RecyclerView.Adapter adapter = parent.getAdapter();

        if (adapter == null) {
            return;
        }

        int itemCount = adapter.getItemCount();

        // center horizontally, calculate width and subtract half from center
        float totalLength = this.radius * 2 * itemCount;
        float paddingBetweenItems = Math.max(0, itemCount - 1) * indicatorItemPadding;
        float indicatorTotalWidth = totalLength + paddingBetweenItems;
        float indicatorStartX = (parent.getWidth() - indicatorTotalWidth) / 2f;

        // center vertically in the allotted space
        float indicatorPosY = parent.getHeight() - indicatorHeight / 2f;


       // drawInactiveDots(c, indicatorStartX, indicatorPosY, itemCount);


//        int itemCount = parent.getAdapter().getItemCount();
//
//        // center horizontally, calculate width and subtract half from center
//        float totalLength = mIndicatorItemLength * itemCount;
//        float paddingBetweenItems = Math.max(0, itemCount - 1) * mIndicatorItemPadding;
//        float indicatorTotalWidth = totalLength + paddingBetweenItems;
//        float indicatorStartX = (parent.getWidth() - indicatorTotalWidth) / 2F;

        // center vertically in the allotted space
       // float indicatorPosY = parent.getHeight() - mIndicatorHeight / 2F;

        drawInactiveIndicators(c, indicatorStartX, indicatorPosY, itemCount);

//        final int activePosition;
//
//        if (parent.getLayoutManager() instanceof GridLayoutManager) {
//            activePosition = ((GridLayoutManager) parent.getLayoutManager()).findFirstVisibleItemPosition();
//        } else if (parent.getLayoutManager() instanceof LinearLayoutManager) {
//            activePosition = ((LinearLayoutManager) parent.getLayoutManager()).findFirstVisibleItemPosition();
//        } else {
//            // not supported layout manager
//            return;
//        }
//
//        if (activePosition == RecyclerView.NO_POSITION) {
//            return;
//        }
//
//        // find offset of active page if the user is scrolling
//        final View activeChild = parent.getLayoutManager().findViewByPosition(activePosition);
//        if (activeChild == null) {
//            return;
//        }
//
//        drawActiveDot(c, indicatorStartX, indicatorPosY, activePosition);

        // find active page (which should be highlighted)
        LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
        int activePosition = layoutManager.findFirstVisibleItemPosition();
        if (activePosition == RecyclerView.NO_POSITION) {
            return;
        }

        // find offset of active page (if the user is scrolling)
        final View activeChild = layoutManager.findViewByPosition(activePosition);
        int left = activeChild.getLeft();
        int width = activeChild.getWidth();

        // on swipe the active item will be positioned from [-width, 0]
        // interpolate offset for smooth animation
        float progress = mInterpolator.getInterpolation(left * -1 / (float) width);

        drawHighlights(c, indicatorStartX, indicatorPosY, activePosition, progress, itemCount);
    }

    private void drawHighlights(Canvas c, float indicatorStartX, float indicatorPosY,
                                int highlightPosition, float progress, int itemCount) {
        final int colorActive = ContextCompat.getColor(context, R.color.blue);
        mPaint.setColor(colorActive);
        mPaint.setColor(colorActive);

        // width of item indicator including padding
        final float itemWidth = mIndicatorItemLength + indicatorItemPadding;

        if (progress == 0F) {
            // no swipe, draw a normal indicator
            float highlightStart = indicatorStartX + itemWidth * highlightPosition;
            c.drawLine(highlightStart, indicatorPosY,
                    highlightStart + mIndicatorItemLength, indicatorPosY, mPaint);
        } else {
            float highlightStart = indicatorStartX + itemWidth * highlightPosition;
            // calculate partial highlight
            float partialLength = mIndicatorItemLength * progress;

            // draw the cut off highlight
            c.drawLine(highlightStart + partialLength, indicatorPosY,
                    highlightStart + mIndicatorItemLength, indicatorPosY, mPaint);

            // draw the highlight overlapping to the next item as well
            if (highlightPosition < itemCount - 1) {
                highlightStart += itemWidth;
                c.drawLine(highlightStart, indicatorPosY,
                        highlightStart + partialLength, indicatorPosY, mPaint);
            }
        }
    }
    private void drawInactiveIndicators(Canvas c, float indicatorStartX, float indicatorPosY, int itemCount) {
        final int colorInactive = ContextCompat.getColor(context, R.color.grey);
        mPaint.setColor(colorInactive);
        mPaint.setColor(colorInactive);

        // width of item indicator including padding
        final float itemWidth = indicatorHeight + indicatorItemPadding;

        float start = indicatorStartX;
        for (int i = 0; i < itemCount; i++) {
            // draw the line for every item
            c.drawLine(start, indicatorPosY, start + indicatorHeight, indicatorPosY, mPaint);
            start += itemWidth;
        }
    }
    private void drawInactiveDots(Canvas c, float indicatorStartX, float indicatorPosY, int itemCount) {
        // width of item indicator including padding
        final float itemWidth = this.radius * 2 + indicatorItemPadding;

        float start = indicatorStartX + radius;
        for (int i = 0; i < itemCount; i++) {
            c.drawCircle(start, indicatorPosY, radius, inactivePaint);
            start += itemWidth;
        }
    }

    private void drawActiveDot(Canvas c, float indicatorStartX, float indicatorPosY,
                               int highlightPosition) {
        // width of item indicator including padding
        final int colorInactive = ContextCompat.getColor(context, R.color.blue);
        mPaint.setColor(colorInactive);
        final float itemWidth = this.radius * 2 + indicatorItemPadding;
        float highlightStart = indicatorStartX + radius + itemWidth * highlightPosition;
       // c.drawCircle(highlightStart, indicatorPosY, radius, activePaint);
        c.drawLine(highlightStart, indicatorPosY, highlightStart + itemWidth, indicatorPosY, mPaint);
    }

    @Override
    public void getItemOffsets(@NotNull Rect outRect, @NotNull View view, @NotNull RecyclerView parent, @NotNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom = indicatorHeight;
    }

}

package com.naver.temy123.baseproject.base.Objects;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by HW on 2015-08-28.
 */
public class BaseItemDecoration extends RecyclerView.ItemDecoration {

    private Drawable mDivider ;
    private int mSize = 1;

    public BaseItemDecoration(Context context) {
        final TypedArray a = context.obtainStyledAttributes(new int[]{
                android.R.attr.listDivider
        });

        mDivider = a.getDrawable(0);
        a.recycle();

        // 크기 설정
        mSize = mDivider.getIntrinsicHeight();
    }

    public BaseItemDecoration(int color) {
        mDivider = new ColorDrawable(color);
    }

    /**
     * Size 설정
     * @return
     */
    public BaseItemDecoration setSize ( int size ) {
        mSize = size ;
        return this;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams)
                    child.getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + mSize;
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(0,0,0,mSize);
    }
}

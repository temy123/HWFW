package com.naver.temy123.baseproject.base.Widgets;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 　　　　　$ ================================== $
 * 　　　　　　　　TimeHub 이현우 2016-04-27 제작
 * 　　　　　$ ================================== $
 */
public class BaseNoneScrollRecyclerView extends RecyclerView {

    public BaseNoneScrollRecyclerView(Context context) {
        super(context);
    }

    public BaseNoneScrollRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseNoneScrollRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        return false;
    }
}

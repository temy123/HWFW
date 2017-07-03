package com.naver.temy123.baseproject.base.Widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * 　　　　　$ ================================== $
 * 　　　　　　　　TimeHub 이현우 2016-04-26 제작
 * 　　　　　$ ================================== $
 */
public class BaseNoneScrollView extends ScrollView {

    public BaseNoneScrollView(Context context) {
        super(context);
    }

    public BaseNoneScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseNoneScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public BaseNoneScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }
}

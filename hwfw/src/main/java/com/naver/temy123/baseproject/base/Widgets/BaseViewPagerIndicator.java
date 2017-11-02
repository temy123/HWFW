package com.naver.temy123.baseproject.base.Widgets;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by lhw on 2017. 3. 7..
 */

public class BaseViewPagerIndicator extends LinearLayout implements ViewPager.OnPageChangeListener,
        ViewPager.OnAdapterChangeListener {

    private ViewPager viewPager;
    private int notSelectedView, selectedView;
    private int state = ViewPager.SCROLL_STATE_IDLE;
    private int count = 1;
    private int margin = 0;

    public BaseViewPagerIndicator(Context context) {
        super(context);
    }

    public BaseViewPagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseViewPagerIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BaseViewPagerIndicator(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * Pager 에서 선택 될 때 마다 Indicator 설정합니다
     *
     * @param notSelectedView 선택 안되었을 때 View
     * @param selectedView    선택 되었을 때 View
     */
    public void setIndicatorView(@LayoutRes int notSelectedView, @LayoutRes int selectedView) {
        this.notSelectedView = notSelectedView;
        this.selectedView = selectedView;
    }

    /**
     * ViewPager 설정
     *
     * @param viewPager ViewPager
     */
    public void setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
        viewPager.addOnPageChangeListener(this);
        viewPager.removeOnPageChangeListener(this);
        viewPager.addOnPageChangeListener(this);

        this.count = viewPager.getAdapter() == null ? 0 : viewPager.getAdapter().getCount();

        clearIndicatorViews();
        addIndicatorViews();
    }

    /**
     * Indicator 들을 모두 삭제합니다
     */
    private void clearIndicatorViews() {
        if (isShown()) {
            removeAllViews();
            removeAllViewsInLayout();
            requestLayout();
        }
    }

    /**
     * Indicator 들을 추가합니다
     */
    private void addIndicatorViews() {
        int selectedCount = viewPager.getCurrentItem();
        int viewCount = 0;
        if (count == 0) {
            viewCount = 0;
        } else {
            viewCount = selectedCount % count;
        }

        LayoutInflater inflater = LayoutInflater.from(getContext());

        for (int i = 0; i < count; i++) {
            View view = null;
            if (viewCount == i) {
                view = inflater.inflate(selectedView, this, false);
            } else {
                view = inflater.inflate(notSelectedView, this, false);
            }

            // 0 이 아닐 때 왼쪽에 margin 값 넣어주기
            if (i != 0) {
                LinearLayout.LayoutParams params = (LayoutParams) view.getLayoutParams();
                params.leftMargin = margin;
            }

            addView(view);
        }
    }

    /**
     * 화면에 보여주기 위한 Count 값
     *
     * @param count
     */
    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        clearIndicatorViews();
        addIndicatorViews();
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        this.state = state;
    }


    @Override
    public void onAdapterChanged(@NonNull ViewPager viewPager, @Nullable PagerAdapter oldAdapter, @Nullable PagerAdapter newAdapter) {
        setViewPager(viewPager);
    }

    public int getMargin() {
        return margin;
    }

    public void setMargin(int margin) {
        this.margin = margin;
    }



}

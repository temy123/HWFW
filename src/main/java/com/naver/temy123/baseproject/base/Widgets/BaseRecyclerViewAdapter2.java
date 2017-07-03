package com.naver.temy123.baseproject.base.Widgets;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;

import com.naver.temy123.baseproject.base.Interface.BaseDefaultRecyclerViewAdapterInterface;
import com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface;

/**
 * 　　　　　$ ================================== $
 * 　　　　　　　　TimeHub 이현우 2016-07-05 제작
 * 　　　　　$ ================================== $
 */

public class BaseRecyclerViewAdapter2<T> extends BaseRecyclerViewAdapter {

    private BaseRecyclerViewAdapterInterface mAction ;

    public BaseRecyclerViewAdapter2(Context context) {
        super(context);
    }

    public BaseRecyclerViewAdapter2(ArrayList mArray, Context context) {
        super(mArray, context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return mAction.onCreateViewHolder(parent, viewType);
    }

    @Override
    public RecyclerView.ViewHolder onCreateListViewHolder(ViewGroup parent) {
        if (mAction instanceof BaseDefaultRecyclerViewAdapterInterface) {
            return ((BaseDefaultRecyclerViewAdapterInterface)mAction).onCreateListViewHolder(parent);
        } else {
            return null ;
        }
    }

    @Override
    public void onListViewHolder(RecyclerView.ViewHolder holder, int position, int realPosition) {
        if (mAction instanceof BaseDefaultRecyclerViewAdapterInterface) {
            ((BaseDefaultRecyclerViewAdapterInterface) mAction).onListViewHolder(holder, position, realPosition);
        }
    }

    @Override
    public void onFooterViewHolder(RecyclerView.ViewHolder holder, int position, int realPosition) {
        if (mAction instanceof BaseDefaultRecyclerViewAdapterInterface) {
            ((BaseDefaultRecyclerViewAdapterInterface) mAction).onFooterViewHolder(holder, position, realPosition);
        }
    }

    @Override
    public void onHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (mAction instanceof BaseDefaultRecyclerViewAdapterInterface) {
            ((BaseDefaultRecyclerViewAdapterInterface) mAction).onHeaderViewHolder(holder, position);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        mAction.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemViewType(int position) {
        return mAction.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return mAction.getItemCount();
    }

    public BaseRecyclerViewAdapterInterface getAction() {
        return mAction;
    }

    public void setAction(BaseRecyclerViewAdapterInterface mAction) {
        this.mAction = mAction;
    }

}

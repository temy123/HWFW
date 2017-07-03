package com.naver.temy123.baseproject.base.Widgets;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.naver.temy123.baseproject.base.Interface.BaseRecyclerViewAdapterInterface;

import java.util.ArrayList;
import java.util.Collection;

import kr.timehub.baseproject.R;

/**
 * 　　　　　$ ================================== $
 * 　　　　　　　　TimeHub 이현우 2016-07-05 제작
 * 　　　　　$ ================================== $
 */

public abstract class BaseListFragment<T> extends BaseFragment {

    private View mVStatus;
    private RecyclerView mBaseRv;

    private BaseRecyclerViewAdapter2<T> mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_recyclerview, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
        initAdapter();
    }

    private void initViews(View view) {
        mBaseRv = (RecyclerView) view.findViewById(R.id.base_rv);
        mVStatus = view.findViewById(R.id.v_status);
    }

    public abstract BaseRecyclerViewAdapterInterface initListInterface(BaseFragment fragment, RecyclerView view);

    private void initAdapter() {
        mAdapter = new BaseRecyclerViewAdapter2(getContext());
        mAdapter.setAction(initListInterface(this, mBaseRv));

        mBaseRv.setAdapter(mAdapter);
        mBaseRv.setLayoutManager(new LinearLayoutManager(getContext()));
    }


    public RecyclerView getListView() {
        return mBaseRv;
    }

    public void setListView(RecyclerView mBaseRv) {
        this.mBaseRv = mBaseRv;
    }

    public BaseRecyclerViewAdapter2<T> getAdapter() {
        return mAdapter;
    }

    public void setAdapter(BaseRecyclerViewAdapter2<T> mAdapter) {
        this.mAdapter = mAdapter;
    }

    /*                                                              /*
            ===============================================
            ===============================================
            ===============================================

                        Delegate Methods

            ===============================================
            ===============================================
            ===============================================
     /*                                                             */

    public void addHeaderView(View view) {
        mAdapter.addHeaderView(view);
    }

    public void addHeaderView(int resId) {
        mAdapter.addHeaderView(resId);
    }

    public void addFooterView(int resId) {
        mAdapter.addFooterView(resId);
    }

    public void addFooterView(View view) {
        mAdapter.addFooterView(view);
    }

    public long getItemId(int position) {
        return mAdapter.getItemId(position);
    }

    public int getHeaderSize() {
        return mAdapter.getHeaderSize();
    }

    public int getFooterSize() {
        return mAdapter.getFooterSize();
    }

    public boolean add(T object) {
        return mAdapter.add(object);
    }

    public void add(int index, T object) {
        mAdapter.add(index, object);
    }

    public boolean addAll(Collection collection) {
        return mAdapter.addAll(collection);
    }

    public boolean addAll(int index, Collection collection) {
        return mAdapter.addAll(index, collection);
    }

    public void clear() {
        mAdapter.clear();
    }

    public T get(int index) {
        return (T) mAdapter.get(index);
    }

    public int size() {
        return mAdapter.size();
    }

    public boolean isEmpty() {
        return mAdapter.isEmpty();
    }

    public boolean contains(T object) {
        return mAdapter.contains(object);
    }

    public T remove(int index) {
        return (T) mAdapter.remove(index);
    }

    public boolean remove(T object) {
        return mAdapter.remove(object);
    }

    public T set(int index, Object object) {
        return (T) mAdapter.set(index, object);
    }

    public ArrayList<View> getHeaderViews() {
        return mAdapter.getHeaderViews();
    }

    public void setHeaderViews(ArrayList mHeaderViews) {
        mAdapter.setHeaderViews(mHeaderViews);
    }

    public ArrayList<View> getFooterViews() {
        return mAdapter.getFooterViews();
    }

    public void setFooterViews(ArrayList mFooterViews) {
        mAdapter.setFooterViews(mFooterViews);
    }

    public void notifyItemChanged(int position) {
        mAdapter.notifyItemChanged(position);
    }

    public void notifyItemChanged(int position, Object payload) {
        mAdapter.notifyItemChanged(position, payload);
    }

    public void notifyItemRangeChanged(int positionStart, int itemCount) {
        mAdapter.notifyItemRangeChanged(positionStart, itemCount);
    }

    public void notifyItemRangeChanged(int positionStart, int itemCount, Object payload) {
        mAdapter.notifyItemRangeChanged(positionStart, itemCount, payload);
    }

    public void notifyItemInserted(int position) {
        mAdapter.notifyItemInserted(position);
    }

    public void notifyItemMoved(int fromPosition, int toPosition) {
        mAdapter.notifyItemMoved(fromPosition, toPosition);
    }

    public void notifyItemRangeInserted(int positionStart, int itemCount) {
        mAdapter.notifyItemRangeInserted(positionStart, itemCount);
    }

    public void notifyItemRemoved(int position) {
        mAdapter.notifyItemRemoved(position);
    }

    public void notifyItemRangeRemoved(int positionStart, int itemCount) {
        mAdapter.notifyItemRangeRemoved(positionStart, itemCount);
    }

    public void notifyDataSetChanged() {
        mAdapter.notifyDataSetChanged();
    }
}

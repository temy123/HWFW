package com.naver.temy123.baseproject.base.Interface;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * 　　　　　$ ================================== $
 * 　　　　　　　　TimeHub 이현우 2016-08-11 제작
 * 　　　　　$ ================================== $
 */

public abstract class BaseDefaultRecyclerViewAdapterInterface2 implements BaseRecyclerViewAdapterInterface {

    public final static int TYPE_HEADER = -3;
    public final static int TYPE_FOOTER = -2;
    public final static int TYPE_LIST = -1;

    public abstract void onListViewHolder (RecyclerView.ViewHolder holder, int pos1, int pos2) ;
    public void onHeaderViewHolder (RecyclerView.ViewHolder holder, int pos1) { }
    public void onFooterViewHolder (RecyclerView.ViewHolder holder, int pos1, int pos2) { }

    private int mIntHeaderCount = 0;
    private int mIntFooterCount = 0;
    private int mIntListCount = 0;

    /**
     *  Header 를 설정 하기 위해서 Count 를 설정 해줌
     */
    public abstract int setHeaderViewCount ( ) ;

    /**
     *  Footer 를 설정 하기 위해서 Count 를 설정 해줌
     */
    public abstract int setFooterViewCount ( ) ;

    /**
     *  List 를 설정 하기 위해서 Count 를 설정 해줌
     * @return
     */
    public abstract int setListViewCount ( ) ;

    /**
     * Header, List, Footer 의 사이즈를 직접 얻어와서 getItemCount() 구현
     * @return
     */
    public int setAllCounts ( ) {
        mIntHeaderCount = setHeaderViewCount();
        mIntListCount = setListViewCount();
        mIntFooterCount = setFooterViewCount();
        return mIntHeaderCount + mIntListCount + mIntFooterCount;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // onCreateViewHolder 가 호출 되기 전, 현재 구현하려는 Adapter 의 크기를 미리 산정
        return onBaseCreateViewHolder(parent, viewType, setAllCounts());
    }

    /**
     * onCreateViewHolder 대신 ViewHolder 를 반환하도록 구현하는 추상 메소드
     *
     * @param parent
     * @param viewType
     * @param itemCounts
     * @return
     */
    public abstract RecyclerView.ViewHolder onBaseCreateViewHolder (ViewGroup parent, int viewType, int itemCounts) ;

    /**
     * position 을 따라, Header 인지 아닌지 구별
     * @param position
     * @return
     */
    public boolean isHeaderView (int position) {
        return position < mIntHeaderCount;
    }

    /**
     * position 을 따라, Footer 인지 아닌지 구별
     * @param position
     * @return
     */
    public boolean isFooterView (int position) {
        return mIntHeaderCount + mIntListCount <= position;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (isHeaderView(position)) {
            onHeaderViewHolder(holder, position);
        } else if (isFooterView(position)) {
            onFooterViewHolder(holder, position, position - (mIntHeaderCount + mIntFooterCount));
        } else {
            onListViewHolder(holder, position, position - mIntHeaderCount);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(isHeaderView(position)) {
            return position;
        } else if (isFooterView(position)) {
            return position;
        } else {
            return TYPE_LIST;
        }
    }

    @Override
    public int getItemCount() {
        return mIntHeaderCount + mIntListCount + mIntFooterCount;
    }


}

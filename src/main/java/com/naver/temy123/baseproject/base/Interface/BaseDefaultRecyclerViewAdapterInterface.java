package com.naver.temy123.baseproject.base.Interface;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.naver.temy123.baseproject.base.Widgets.BaseRecyclerViewAdapter;

import static com.naver.temy123.baseproject.base.Widgets.BaseRecyclerViewAdapter.TYPE_DEFAULT_LIST;

/**
 * 　　　　　$ ================================== $
 * 　　　　　　　　TimeHub 이현우 2016-07-05 제작
 * 　　　　　$ ================================== $
 */

public abstract class BaseDefaultRecyclerViewAdapterInterface implements BaseRecyclerViewAdapterInterface {

    private BaseRecyclerViewAdapter mAdapter ;

    public BaseDefaultRecyclerViewAdapterInterface(BaseRecyclerViewAdapter adapter) {
        this.mAdapter = adapter;
    }

    // 구현 필요
    public abstract RecyclerView.ViewHolder onCreateListViewHolder (ViewGroup parent) ;
    public abstract void onHeaderViewHolder (RecyclerView.ViewHolder holder, int position);
    public abstract void onListViewHolder (RecyclerView.ViewHolder holder, int position, int realPosition);
    public abstract void onFooterViewHolder (RecyclerView.ViewHolder holder, int position, int realPosition);

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int headerSize = mAdapter.getHeaderSize();

        if ( viewType == TYPE_DEFAULT_LIST) {
            return mAdapter.onCreateListViewHolder(parent);
        } else if ( headerSize > viewType ) {
            return mAdapter.onCreateHeaderViewHolder(parent);
        } else if ( headerSize + mAdapter.size() - 1 < viewType ) {
            return mAdapter.onCreateFooterViewHolder(parent);
        }

        return null;
    }

    @Override
    public int getItemCount() {
        int headerSize = mAdapter.getHeaderSize() ;
        int footerSize = 0 ;

        if (mAdapter.getFooterViews() != null) {
            footerSize = mAdapter.getFooterSize();
        }

        return mAdapter.size() + headerSize + footerSize;
    }

    @Override
    public int getItemViewType(int position) {
        mAdapter.setLatestPosition(position);

        int headerSize = mAdapter.getHeaderSize();

        // Header
        if ( headerSize > position ) {
            return position;
            // Footer
            // 구하는 공식
        /*
            0. Footer 가 있을때 !
            1. Data와 Header는 없으나 Footer 가 있는 경우
            2. Data와 Header 가 있고, 그 두 합을 초과하고 있는 데이터를 원할 경우
         */
        } else if ( mAdapter.getFooterSize() != 0 && ( mAdapter.getHeaderSize() == 0 && mAdapter.size() == 0 && mAdapter.getFooterSize() != 0)
                || headerSize + mAdapter.size() - 1 < position ) {
            int type = headerSize + mAdapter.size() - 1 + position;
            type = type < 0 ? 0 : type;
            return type;
        }
        return TYPE_DEFAULT_LIST;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int headerSize = mAdapter.getHeaderSize();

        // 헤더가 있었을 경우
        if (mAdapter.getItemViewType(position) == TYPE_DEFAULT_LIST ) {
            mAdapter.onListViewHolder(holder, position - mAdapter.getHeaderSize(), position);
        } else if ( headerSize > position ) {
            mAdapter.onHeaderViewHolder(holder, position);
        } else if ( headerSize + mAdapter.size() - 1 < position ) {
            mAdapter.onFooterViewHolder(holder, position - mAdapter.getHeaderSize() - mAdapter.size(), position);
        }
    }

}

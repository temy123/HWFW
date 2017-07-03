package com.naver.temy123.baseproject.base.Interface;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * 　　　　　$ ================================== $
 * 　　　　　　　　TimeHub 이현우 2016-07-05 제작
 * 　　　　　$ ================================== $
 */

public interface BaseRecyclerViewAdapterInterface {

    RecyclerView.ViewHolder onCreateViewHolder (ViewGroup parent, int viewType) ;
    void onBindViewHolder (RecyclerView.ViewHolder viewholder, int position) ;
    int getItemCount ( ) ;
    int getItemViewType (int position) ;

}

package com.naver.temy123.baseproject.base.Widgets;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by HW on 2015-08-24.
 */

// BaseRE
public class BaseRecyclerViewAdapter<K> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // Header 의 Size 만큼 HeaderIndex 증가
    // Footer 도 마찬가지
    // 그 이외는 List로 처리
    public static final int TYPE_DEFAULT_LIST = -1;

//    public final int TYPE_HEADER = 0 ;
//    public final int TYPE_FOOTER = 1 ;
//    public final int TYPE_DEFAULT_LIST = 2 ;

    private ArrayList<K> mArray = new ArrayList<>();
    private Context mContext;

    // HeaderViews, FooterViews 저장
    private ArrayList<View> mHeaderViews ;
    private ArrayList<View> mFooterViews ;

    private int mLatestPosition = 0 ;

    public LayoutInflater mInflater ;

    public BaseRecyclerViewAdapter(Context context) {
        this.mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public BaseRecyclerViewAdapter(ArrayList<K> mArray, Context context) {
        this.mArray = mArray;
        this.mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // 헤더 추가
    public final void addHeaderView (View view) {
        // 기본 기능을 사용하기 전에 등록함
        if (mHeaderViews == null) {
            mHeaderViews = new ArrayList<>();
        }

        mHeaderViews.add(view);
        notifyDataSetChanged();
    }

    // 헤더 추가
    public final void addHeaderView (int resId) {
        // 기본 기능을 사용하기 전에 등록함
        if (mHeaderViews == null) {
            mHeaderViews = new ArrayList<>();
        }

        View view = mInflater.inflate(resId, null, false);
        mHeaderViews.add(view);
        notifyDataSetChanged();
    }

    // 헤더 추가
    public final void addFooterView (int resId) {
        // 기본 기능을 사용하기 전에 등록함
        if (mFooterViews == null) {
            mFooterViews = new ArrayList<>();
        }

        View view = mInflater.inflate(resId, null, false);
        mFooterViews.add(view);
        notifyDataSetChanged();
    }

    // 푸터 추가
    public final void addFooterView (View view) {
        // 기본 기능을 사용하기 전에 등록함
        if (mFooterViews == null) {
            mFooterViews = new ArrayList<>();
        }

        mFooterViews.add(view);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int headerSize = getHeaderSize();

        if ( viewType == TYPE_DEFAULT_LIST) {
            return onCreateListViewHolder(parent);
        } else if ( headerSize > viewType ) {
            return onCreateHeaderViewHolder(parent);
        } else if ( headerSize + mArray.size() - 1 < viewType ) {
            return onCreateFooterViewHolder(parent);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int headerSize = getHeaderSize();

        // 헤더가 있었을 경우
        if ( getItemViewType(position) == TYPE_DEFAULT_LIST) {
            onListViewHolder(holder, position - getHeaderSize(), position);
        } else if ( headerSize > position ) {
            onHeaderViewHolder(holder, position);
        } else if ( headerSize + mArray.size() - 1 < position ) {
            onFooterViewHolder(holder, position - getHeaderSize() - size(), position);
        }
    }

    /*
        사용할때 필요한 부분만 구현하여 사용
     */
    public void onHeaderViewHolder (RecyclerView.ViewHolder holder, int position) {}
    public void onListViewHolder (RecyclerView.ViewHolder holder, int position, int realPosition) {}
    public void onFooterViewHolder (RecyclerView.ViewHolder holder, int position, int realPosition) {}

    public RecyclerView.ViewHolder onCreateHeaderViewHolder (ViewGroup parent) {
        RecyclerView.ViewHolder holder = BaseViewHolder.newInstance(mHeaderViews.get(mLatestPosition));
        return holder;
    }
    public RecyclerView.ViewHolder onCreateFooterViewHolder (ViewGroup parent) {
        RecyclerView.ViewHolder holder = BaseViewHolder.newInstance(mFooterViews.get(mLatestPosition - (getHeaderSize() + mArray.size())));
        return holder;
    }
    public RecyclerView.ViewHolder onCreateListViewHolder (ViewGroup parent) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        mLatestPosition = position;

        int headerSize = getHeaderSize();

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
        } else if ( getFooterSize() != 0 && ( getHeaderSize() == 0 && mArray.size() == 0 && getFooterSize() != 0)
                || headerSize + mArray.size() - 1 < position ) {
            int type = headerSize + mArray.size() - 1 + position;
            type = type < 0 ? 0 : type;
            return type;
        }
        return TYPE_DEFAULT_LIST;
    }

    @Override
    public int getItemCount() {
        int headerSize = getHeaderSize() ;
        int footerSize = 0 ;

        if (mFooterViews != null) {
            footerSize = mFooterViews.size();
        }

        return mArray.size() + headerSize + footerSize;
    }

    public Context getContext ( ) {
        return mContext;
    }

    // 헤더 사이즈 반환
    public int getHeaderSize ( ) {
        if ( mHeaderViews != null )
            return mHeaderViews.size();
        else
            return 0;
    }

    // 푸터 사이즈 반환
    public int getFooterSize ( ) {
        if ( mFooterViews != null )
            return mFooterViews.size();
        else
            return 0;
    }

    /*
        Delegate Methods
     */

    public boolean add(K object) {
        boolean result = mArray.add(object);
        if (result) {
            notifyDataSetChanged();
        }
        return result ;
    }

    public void add(int index, K object) {
        mArray.add(index, object);
        notifyDataSetChanged();
    }

    public boolean addAll(Collection<? extends K> collection) {
        boolean result = mArray.addAll(collection);
        if (result) {
            notifyDataSetChanged();
        }
        return result;
    }

    public boolean addAll(int index, Collection<? extends K> collection) {
        boolean result = mArray.addAll(index, collection);
        if (result) {
            notifyDataSetChanged();
        }
        return result;
    }

    public void clear() {
        mArray.clear();
        notifyDataSetChanged();
    }

    public K get(int index) {
        return mArray.get(index);
    }

    public int size() {
        return mArray.size();
    }

    public boolean isEmpty() {
        return mArray.isEmpty();
    }

    public boolean contains(Object object) {
        return mArray.contains(object);
    }

    public K remove(int index) {
        K model = mArray.remove(index);
        notifyDataSetChanged();
        return model;
    }

    public boolean remove(Object object) {
        boolean model = mArray.remove(object);
        notifyDataSetChanged();
        return model;
    }

    public K set(int index, K object) {
        K model = mArray.set(index, object);
        notifyDataSetChanged();
        return model;
    }

    public ArrayList<View> getHeaderViews() {
        return mHeaderViews;
    }

    public void setHeaderViews(ArrayList<View> mHeaderViews) {
        this.mHeaderViews = mHeaderViews;
    }

    public ArrayList<View> getFooterViews() {
        return mFooterViews;
    }

    public void setFooterViews(ArrayList<View> mFooterViews) {
        this.mFooterViews = mFooterViews;
    }

    public int getLatestPosition() {
        return mLatestPosition;
    }

    public void setLatestPosition(int mLatestPosition) {
        this.mLatestPosition = mLatestPosition;
    }

}

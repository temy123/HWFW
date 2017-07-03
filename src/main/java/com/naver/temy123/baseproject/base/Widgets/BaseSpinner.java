package com.naver.temy123.baseproject.base.Widgets;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Spinner;

import com.naver.temy123.baseproject.base.Entry.BeanSpinnerExtends;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.function.UnaryOperator;

/**
 * Created by lhw on 2017. 3. 9..
 */

public class BaseSpinner extends Spinner {

    private ArrayList<BeanSpinnerExtends> mArrayList = new ArrayList<>();
    private CustomBaseAdapter mAdapter;

    private class CustomBaseAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mArrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return mArrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return mArrayList.get(position).getSeq();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = BaseSpinner.this.getDropDownView(position, convertView, parent);
            return convertView;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            convertView = BaseSpinner.this.getDropDownView(position, convertView, parent);
            return convertView;
        }
    }

    /**
     * 드롭다운 View 설정
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        BaseViewHolder holder;
        if (convertView == null) {
            convertView = generateDropDownView(parent);
        }

        BeanSpinnerExtends bean = mArrayList.get(position);

        holder = (BaseViewHolder) convertView.getTag();
        holder.setText(android.R.id.text1, bean.getText());
        return convertView;
    }

    /**
     * View 설정
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseViewHolder holder;
        if (convertView == null) {
            convertView = generateView(parent);
        }

        BeanSpinnerExtends bean = mArrayList.get(position);

        holder = (BaseViewHolder) convertView.getTag();
        holder.setText(android.R.id.text1, bean.getText());

        return convertView;
    }

    public BaseSpinner(Context context) {
        super(context);
        initAdapter();
    }

    public BaseSpinner(Context context, int mode) {
        super(context, mode);
        initAdapter();
    }

    public BaseSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAdapter();
    }

    public BaseSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAdapter();
    }

    public BaseSpinner(Context context, AttributeSet attrs, int defStyleAttr, int mode) {
        super(context, attrs, defStyleAttr, mode);
        initAdapter();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BaseSpinner(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes, int mode) {
        super(context, attrs, defStyleAttr, defStyleRes, mode);
        initAdapter();
    }

    @TargetApi(Build.VERSION_CODES.M)
    public BaseSpinner(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes, int mode, Resources.Theme popupTheme) {
        super(context, attrs, defStyleAttr, defStyleRes, mode, popupTheme);
        initAdapter();
    }

    private void initAdapter() {
        mAdapter = new CustomBaseAdapter();
        setAdapter(mAdapter);
    }

    /**
     * Return SelectedText
     *
     * @return
     */
    public String getSelectedTextByBean() {
        String text;
        BeanSpinnerExtends bean = (BeanSpinnerExtends) getSelectedItem();
        text = bean.getText();
        return text;
    }

    /**
     * Selected Item 내보내기
     *
     * @return
     */
    public BeanSpinnerExtends getSelectedItemByBean() {
        BeanSpinnerExtends bean = (BeanSpinnerExtends) super.getSelectedItem();
        return bean;
    }

    protected View generateView(ViewGroup parent) {
        BaseViewHolder holder = BaseViewHolder.newInstance(getContext(), android.R.layout.simple_list_item_1, parent);
        View view = holder.itemView;
        view.setTag(holder);
        return view;
    }

    protected View generateDropDownView(ViewGroup parent) {
        BaseViewHolder holder = BaseViewHolder.newInstance(getContext(), android.R.layout.simple_spinner_dropdown_item, parent);
        View view = holder.itemView;
        view.setTag(holder);
        return view;
    }

    /*#########
        Delegate Methods
     ##########*/
    public int size() {
        return mArrayList.size();
    }

    public boolean isEmpty() {
        return mArrayList.isEmpty();
    }

    public boolean contains(Object o) {
        return mArrayList.contains(o);
    }

    public int indexOf(Object o) {
        return mArrayList.indexOf(o);
    }

    public BeanSpinnerExtends get(int index) {
        return mArrayList.get(index);
    }

    public BeanSpinnerExtends set(int index, BeanSpinnerExtends element) {
        BeanSpinnerExtends bean = mArrayList.set(index, element);
        mAdapter.notifyDataSetChanged();
        return bean;
    }

    public boolean add(BeanSpinnerExtends beanSpinnerExtends) {
        boolean result = mArrayList.add(beanSpinnerExtends);
        mAdapter.notifyDataSetChanged();
        return result;
    }

    public void add(int index, BeanSpinnerExtends element) {
        mArrayList.add(index, element);
        mAdapter.notifyDataSetChanged();
    }

    public BeanSpinnerExtends remove(int index) {
        BeanSpinnerExtends bean = mArrayList.remove(index);
        mAdapter.notifyDataSetChanged();
        return bean;
    }

    public boolean remove(Object o) {
        boolean result = mArrayList.remove(o);
        mAdapter.notifyDataSetChanged();
        return result;
    }

    public void clear() {
        mArrayList.clear();
        mAdapter.notifyDataSetChanged();
    }

    public boolean addAll(Collection<? extends BeanSpinnerExtends> c) {
        boolean result = mArrayList.addAll(c);
        mAdapter.notifyDataSetChanged();
        return result;
    }

    public boolean addAll(int index, Collection<? extends BeanSpinnerExtends> c) {
        boolean result = mArrayList.addAll(index, c);
        mAdapter.notifyDataSetChanged();
        return result;
    }

    public boolean removeAll(Collection<?> c) {
        boolean result = mArrayList.removeAll(c);
        mAdapter.notifyDataSetChanged();
        return result;
    }

    public void replaceAll(UnaryOperator<BeanSpinnerExtends> operator) {
        mArrayList.replaceAll(operator);
        mAdapter.notifyDataSetChanged();
    }

    public void sort(Comparator<? super BeanSpinnerExtends> c) {
        mArrayList.sort(c);
        mAdapter.notifyDataSetChanged();
    }

}

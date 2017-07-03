package com.naver.temy123.baseproject.base.Widgets;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * 　　　　　$ ================================== $
 * 　　　　　　　　TimeHub 이현우 2016-04-18 제작
 * 　　　　　$ ================================== $
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {

    // 저장해놓는 View Array
    private ArrayList<View> mViewArray = new ArrayList<>();

    public static final BaseViewHolder newInstance(Context context, int resId) {
        View view = LayoutInflater.from(context).inflate(resId, null, false);
        BaseViewHolder holder = new BaseViewHolder(view);
        return holder;
    }

    public static final BaseViewHolder newInstance(Context context, int resId, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(resId, parent, false);
        BaseViewHolder holder = new BaseViewHolder(view);
        return holder;
    }

    public static final BaseViewHolder newInstance(View view) {
        BaseViewHolder holder = new BaseViewHolder(view);
        return holder;
    }

    private BaseViewHolder(View itemView) {
        super(itemView);
    }

    /*
        View 관련 함수들
     */
    public <T extends View> T getView(int resId) {
        // Array에 저장해놓은 View 가 있는지 확인
        for (int i = 0; i < mViewArray.size(); i++) {
            View v = mViewArray.get(i);
            // ID 값이 같을 때에는 추가해놓았던 View 반환
            if (v.getId() == resId) {
                return (T) v;
            }
        }

        // Array에 저장한 View 가 없을 경우 추가, 반환
        View view = itemView.findViewById(resId);
        if (view != null) {
            mViewArray.add(view);
        } else {
            new Exception(String.format("(getView (%d) ) is NULL!!", resId)).printStackTrace();
        }
        return (T) view;
    }

    /*
        기본적인 데이터 설정
     */
    public void setText(int resId, CharSequence data) {
        View view = getView(resId);
        if (view instanceof TextView) {
            ((TextView) view).setText(data);
        } else if (view instanceof EditText) {
            ((EditText) view).setText(data);
        }
    }

    public void setHint(int resId, CharSequence data) {
        View view = getView(resId);
        if (view instanceof TextView) {
            ((TextView) view).setHint(data);
        } else if (view instanceof EditText) {
            ((EditText) view).setHint(data);
        }
    }

    public void setWidth(int resId, int width) {
        View view = getView(resId);
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.width = width;
        view.setLayoutParams(params);
    }

    public void setHeight(int resId, int height) {
        View view = getView(resId);
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = height;
        view.setLayoutParams(params);
    }

    public void setSize(int resId, int width, int height) {
        View view = getView(resId);
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.width = width;
        params.height = height;
        view.setLayoutParams(params);
    }

    public int getMeasureWidth(int resId) {
        View view = getView(resId);
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        return view.getMeasuredWidth();
    }

    public int getMeasureHeight(int resId) {
        View view = getView(resId);
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        return view.getMeasuredHeight();
    }

    public void setTextColor(int resId, Object color) {
        View view = getView(resId);

        if (view instanceof TextView) {
            if (color instanceof Integer) {
                ((TextView) view).setTextColor((Integer) color);
            } else if (color instanceof ColorStateList) {
                ((TextView) view).setTextColor((ColorStateList) color);
            }
        } else if (view instanceof EditText) {
            if (color instanceof Integer) {
                ((EditText) view).setTextColor((Integer) color);
            } else if (color instanceof ColorStateList) {
                ((EditText) view).setTextColor((ColorStateList) color);
            }
        }
    }

    public void setHintColor(int resId, Object color) {
        View view = getView(resId);

        if (view instanceof TextView) {
            if (color instanceof Integer) {
                ((TextView) view).setHintTextColor((Integer) color);
            } else if (color instanceof ColorStateList) {
                ((TextView) view).setHintTextColor((ColorStateList) color);
            }
        } else if (view instanceof EditText) {
            if (color instanceof Integer) {
                ((EditText) view).setHintTextColor((Integer) color);
            } else if (color instanceof ColorStateList) {
                ((EditText) view).setHintTextColor((ColorStateList) color);
            }
        }
    }

    public void setImage(int resId, Object data) {
        if (data instanceof Drawable) {
            getImageView(resId).setImageDrawable((Drawable) data);
        } else if (data instanceof Integer) {
            getImageView(resId).setImageResource((Integer) data);
        } else if (data instanceof Bitmap) {
            getImageView(resId).setImageBitmap((Bitmap) data);
        }
    }

    public void setBackground(int resId, Object data) {
        if (data instanceof Drawable) {
            getView(resId).setBackground((Drawable) data);
        } else if (data instanceof Integer) {
            getView(resId).setBackgroundResource((int) data);
        }
    }

    /**
     * View 가 확실히 ImageView 인 경우에만 사용해주세요
     *
     * @param resId View Id
     * @return ImageView
     */
    public ImageView getImageView(int resId) {
        return (ImageView) itemView.findViewById(resId);
    }

    /**
     * View 가 확실히 TextView 인 경우에만 사용해주세요
     *
     * @param resId View Id
     * @return TextView
     */
    public TextView getTextView(int resId) {
        return (TextView) itemView.findViewById(resId);
    }

    /**
     * View 가 확실히 EditText 인 경우에만 사용해주세요
     *
     * @param resId View Id
     * @return EditText
     */
    public EditText getEditText(int resId) {
        return (EditText) itemView.findViewById(resId);
    }

}

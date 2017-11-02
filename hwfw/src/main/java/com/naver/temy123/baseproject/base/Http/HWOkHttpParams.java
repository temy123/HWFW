package com.naver.temy123.baseproject.base.Http;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * HW Ok Http Params
 * <p>
 * HWOkHttpClient 에 기본이 되는 파라미터 정의 모델 입니다
 * <p>
 * Header , Body 에 사용 됩니다
 */

public class HWOkHttpParams implements Iterable<HWOkHttpNameValuePair> {

    public static final int TYPE_JSON = 1;
    public static final int TYPE_FORM = 0;

    private ArrayList<HWOkHttpNameValuePair> params = new ArrayList();
    private int paramsType = TYPE_FORM;
    private Object pendingJson; // Can put JSONArray or JSONObject type.

    public HWOkHttpParams() {
    }

    public HWOkHttpParams(int paramsType) {
        this.paramsType = paramsType;
    }

    public HWOkHttpParams(JSONObject jsonObject) {
        paramsType = TYPE_JSON;
        pendingJson = jsonObject;
    }

    public HWOkHttpParams(JSONArray jsonArray) {
        paramsType = TYPE_JSON;
        pendingJson = jsonArray;
    }

    public boolean hasParam(String key) {
        for (HWOkHttpNameValuePair param : params) {
            if (TextUtils.equals(param.getKey(), key)) {
                return true;
            }
        }
        return false;
    }

    public HWOkHttpParams add(String key, String value) {
        HWOkHttpNameValuePair param = new HWOkHttpNameValuePair(key, value);
        params.add(param);
        return this;
    }

    public HWOkHttpParams add(String key, File value) {
        HWOkHttpNameValuePair param = new HWOkHttpNameValuePair(key, value);
        params.add(param);
        return this;
    }

    public HWOkHttpParams add(HWOkHttpNameValuePair param) {
        params.add(param);
        return this;
    }

    public HWOkHttpParams append(int position, String key, String value) {
        HWOkHttpNameValuePair param = new HWOkHttpNameValuePair(key, value);
        params.add(position, param);
        return this;
    }

    public HWOkHttpParams append(int position, HWOkHttpNameValuePair param) {
        params.add(position, param);
        return this;
    }

    public HWOkHttpParams remove(String key) {
        for (HWOkHttpNameValuePair param : params) {
            if (TextUtils.equals(param.getKey(), key)) {
                params.remove(param);
            }
        }
        return this;
    }

    public HWOkHttpParams remove(HWOkHttpNameValuePair param) {
        params.remove(param);
        return this;
    }

    public int getParamsType() {
        return paramsType;
    }

    public void setParamsType(int paramsType) {
        this.paramsType = paramsType;
    }

    public Object getPendingJson() {
        return pendingJson;
    }

    public void setPendingJson(Object pendingJson) {
        this.pendingJson = pendingJson;
    }

    public int size() {
        return params.size();
    }

    @Override
    public Iterator iterator() {
        Iterator i = new Iterator() {
            int seq = 0;

            @Override
            public boolean hasNext() {
                return seq < params.size();
            }

            @Override
            public HWOkHttpNameValuePair next() {
                return params.get(seq++);
            }

        };
        return i;
    }

}

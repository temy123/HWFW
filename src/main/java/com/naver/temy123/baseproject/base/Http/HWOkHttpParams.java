package com.naver.temy123.baseproject.base.Http;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Iterator;

import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

/**
 * Created by lhw on 2017. 4. 26..
 */

public class HWOkHttpParams implements Iterable<NameValuePair> {

    private ArrayList<NameValuePair> params = new ArrayList();

    public boolean hasParam(String key) {
        for (NameValuePair param : params) {
            if (TextUtils.equals(param.getName(), key)) {
                return true;
            }
        }
        return false;
    }

    public HWOkHttpParams add(String key, String value) {
        NameValuePair param = new BasicNameValuePair(key, value);
        params.add(param);
        return this;
    }

    public HWOkHttpParams add(NameValuePair param) {
        params.add(param);
        return this;
    }

    public HWOkHttpParams append(int position, String key, String value) {
        NameValuePair param = new BasicNameValuePair(key, value);
        params.add(position, param);
        return this;
    }

    public HWOkHttpParams append(int position, NameValuePair param) {
        params.add(position, param);
        return this;
    }

    public HWOkHttpParams remove(String key) {
        for (NameValuePair param : params) {
            if (TextUtils.equals(param.getName(), key)) {
                params.remove(param);
            }
        }
        return this;
    }

    public HWOkHttpParams remove(NameValuePair param) {
        params.remove(param);
        return this;
    }


    @Override
    public Iterator iterator() {
        Iterator i = new Iterator<NameValuePair>() {
            int seq = 0;

            @Override
            public boolean hasNext() {
                return seq < params.size();
            }

            @Override
            public NameValuePair next() {
                return params.get(seq++);
            }

        };
        return i;
    }

}

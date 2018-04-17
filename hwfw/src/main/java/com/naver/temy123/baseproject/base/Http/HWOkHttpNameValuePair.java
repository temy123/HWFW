package com.naver.temy123.baseproject.base.Http;

import android.support.annotation.NonNull;

/**
 * HWOkHttpParams 모델에 Core 가 되는 모델 입니다
 *
 * @see com.naver.temy123.baseproject.base.Http.HWOkHttpParams
 */

public class HWOkHttpNameValuePair {

    private String key;
    private Object value;
    private String fileName;

    public HWOkHttpNameValuePair(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    public HWOkHttpNameValuePair(String key, Object value, @NonNull String fileName) {
        this.key = key;
        this.value = value;
        this.fileName = fileName;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

}

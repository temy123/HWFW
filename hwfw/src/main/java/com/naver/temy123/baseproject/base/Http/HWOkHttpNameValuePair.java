package com.naver.temy123.baseproject.base.Http;

/**
 * HWOkHttpParams 모델에 Core 가 되는 모델 입니다
 *
 * @see com.naver.temy123.baseproject.base.Http.HWOkHttpParams
 */

public class HWOkHttpNameValuePair {

    private String key;
    private Object value;

    public HWOkHttpNameValuePair(String key, Object value) {
        this.key = key;
        this.value = value;
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

}

package com.naver.temy123.baseproject.base.Utils;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * 　　　　　$ ================================== $
 * 　　　　　　　　TimeHub 이현우 2015-11-24 제작
 * 　　　　　$ ================================== $
 */
public class Gson {

    public static <T> T parseToModel ( T tClass, String data ) {
        com.google.gson.Gson gson = new com.google.gson.Gson();
        Type type = new TypeToken<T>(){}.getType();
        return gson.fromJson(data, type);
    }

    public static <T> String parseToString ( T tClass, Object data ) {
        com.google.gson.Gson gson = new com.google.gson.Gson();
        Type type = new TypeToken<T>(){}.getType();
        return gson.toJson(data, type);
    }

}

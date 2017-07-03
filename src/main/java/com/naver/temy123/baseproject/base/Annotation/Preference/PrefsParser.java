package com.naver.temy123.baseproject.base.Annotation.Preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Set;

/**
 * 　　　　　$ ================================== $
 * 　　　　　　　　TimeHub 이현우 2015-12-01 제작
 * 　　　　　$ ================================== $
 */

public class PrefsParser {

    private static PrefsParser instance = null ;

    public static PrefsParser getInstance ( Context context ) {
        if (instance != null) {
            instance = new PrefsParser(context);
        }
        return instance;
    }

    private SharedPreferences prefs ;

    private PrefsParser(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    /*
        Setter
     */
    public boolean set (String key, boolean value) {
        boolean result = prefs.edit().putBoolean(key, value).commit();
        return result;
    }

    public boolean set (String key, int value) {
        boolean result = prefs.edit().putInt(key, value).commit();
        return result;
    }

    public boolean set (String key, float value) {
        boolean result = prefs.edit().putFloat(key, value).commit();
        return result;
    }

    public boolean set (String key, long value) {
        boolean result = prefs.edit().putLong(key, value).commit();
        return result;
    }

    public boolean set (String key, String value) {
        boolean result = prefs.edit().putString(key, value).commit();
        return result;
    }

    public boolean set (String key, Set<String> value) {
        boolean result = prefs.edit().putStringSet(key, value).commit();
        return result;
    }

    /*
        Getter
     */
    public boolean getBoolean (String key) {
        return prefs.getBoolean(key, false);
    }

    public float getFloat (String key) {
        return prefs.getFloat(key, 0.0f);
    }

    public int getInt (String key) {
        return prefs.getInt(key, 0);
    }

    public long getLong (String key) {
        return prefs.getLong(key, 0);
    }

    public String getString (String key) {
        return prefs.getString(key, null);
    }

    public Set<String> getStringSet (String key) {
        return prefs.getStringSet(key, null);
    }

}

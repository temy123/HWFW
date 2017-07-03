package com.naver.temy123.baseproject.base.Utils;

/**
 * 　　　　　$ ================================== $
 * 　　　　　　　　TimeHub 이현우 2015-11-11 제작
 * 　　　　　$ ================================== $
 */
public class Log {

    private static final int STATE_DEBUG = 0;
    private static final int STATE_RELEASE = 1;

    private static int STATE = STATE_DEBUG;

    public static int getSTATE() {
        return STATE;
    }

    public static void setSTATE(int STATE) {
        Log.STATE = STATE;
    }

    public static final int d (String s1, String s2 ) {
        if (STATE == STATE_DEBUG) {
            return android.util.Log.d(s1, s2);
        }
        return -1;
    }

    public static final int i ( String s1, String s2 ) {
        if (STATE == STATE_DEBUG) {
            return android.util.Log.i(s1, s2);
        }
        return -1;
    }

    public static final int e ( String s1, String s2 ) {
        if (STATE == STATE_DEBUG) {
            return android.util.Log.e(s1, s2);
        }
        return -1;
    }

}

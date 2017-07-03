package com.naver.temy123.baseproject.base.Utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import com.naver.temy123.baseproject.base.Interface.OnPermissionListener;
import com.naver.temy123.baseproject.base.Widgets.BaseActivity;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 　　　　　$ ================================== $
 * 　　　　　　　　TimeHub 이현우 2016-05-04 제작
 * 　　　　　$ ================================== $
 */
public class PermissionHelper {

    public static final int REQUEST_CODE = 10;

    public static void requestPermission ( BaseActivity activity, String[] permissions, OnPermissionListener listener ) {
        String[] permissions2 ;
        activity.setOnPermissionListener(listener);

        ArrayList<String> requestPermissionList = new ArrayList<>() ;

        // 권한 요청이 필요한 것들인지 체크
        for (String permission : permissions) {
            if (!isGrantedPermission(activity, permission)) {
                requestPermissionList.add(permission);
            }
        }

        permissions2 = requestPermissionList.toArray(new String[requestPermissionList.size()]);
        if (permissions2 != null && permissions2.length > 0) {
            ActivityCompat.requestPermissions(activity, permissions2, REQUEST_CODE);
        } else {
            if (listener != null) {
                listener.onGranted(REQUEST_CODE, Arrays.asList(permissions));
            }
        }
    }

    public static void requestPermission ( BaseActivity activity, int requestCode, String[] permissions, OnPermissionListener listener ) {
        String[] permissions2 ;
        activity.setOnPermissionListener(listener);

        ArrayList<String> requestPermissionList = new ArrayList<>() ;

        // 권한 요청이 필요한 것들인지 체크
        for (String permission : permissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                requestPermissionList.add(permission);
            }
        }

        permissions2 = requestPermissionList.toArray(new String[requestPermissionList.size()]);
        if (permissions2 != null && permissions2.length > 0) {
            ActivityCompat.requestPermissions(activity, permissions2, requestCode);
        } else {
            if (listener != null) {
                listener.onGranted(REQUEST_CODE, Arrays.asList(permissions));
            }
        }
    }

    /**
     * Permission 체크 용
     * @param permission
     * @return
     */
    public static boolean isGrantedPermission ( Context context, String permission ) {
        switch (ActivityCompat.checkSelfPermission(context, permission)) {
            case PackageManager.PERMISSION_GRANTED:
                return true ;
        }
        return false;
    }

}

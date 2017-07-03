package com.naver.temy123.baseproject.base.Interface;

import java.util.List;

/**
 * Created by HW on 2016-09-30.
 */

public interface OnPermissionListener {
        void onGranted ( int requestCode, List<String> grantedPermission ) ;
        void onDenied ( int requestCode, List<String> diniedPermissions ) ;
}

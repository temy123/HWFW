package com.naver.temy123.baseproject.base.Widgets;

import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.naver.temy123.baseproject.base.Interface.OnPermissionListener;
import com.naver.temy123.baseproject.base.Utils.PermissionHelper;

import java.util.ArrayList;

/**
 * Created by HW on 2015-08-24.
 */
public class BaseActivity extends AppCompatActivity {

    private OnPermissionListener onPermissionListener;

    /**
     * 권한 요청하기
     *
     * @param permissions
     * @param requestCode
     * @param onListener
     */
    public void requestPermission(String[] permissions, int requestCode, OnPermissionListener onListener) {
        PermissionHelper.requestPermission(this, requestCode, permissions, onListener);
    }

    /**
     * 권한 요청하기
     *
     * @param permissions
     * @param onListener
     */
    public void requestPermission(String[] permissions, OnPermissionListener onListener) {
        PermissionHelper.requestPermission(this, permissions, onListener);
    }

    /**
     * 권한 요청하기
     *
     * @param permissions
     */
    public void requestPermission(String[] permissions) {
        PermissionHelper.requestPermission(this, permissions, onPermissionListener);
    }

    /**
     * 해당 권한 체크 가져오기
     * <p>
     * TRUE : 권한 있음
     * FALSE : 권한 없음
     *
     * @param permission
     * @return
     */
    public boolean isCheckPermissions(String permission) {
        return PermissionHelper.isGrantedPermission(this, permission);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (getOnPermissionListener() != null) {
            ArrayList<String> grantedPermissions = new ArrayList<>();
            ArrayList<String> diniedPermissions = new ArrayList<>();

            for (int i = 0; i < permissions.length; i++) {
                String permission = permissions[i];
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    diniedPermissions.add(permission);
                } else if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    grantedPermissions.add(permission);
                }
            }

            // Listener 에 데이터 전달
            getOnPermissionListener().onGranted(requestCode, grantedPermissions);
            getOnPermissionListener().onDenied(requestCode, diniedPermissions);

            // Listener 초기화
            setOnPermissionListener(null);
        }
    }

    /***
     * 4.4 이상 킷캣부터 지원
     * 상태바 투명화
     */
    public void updateStatusbarTranslate() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
            );
        }
    }

    /***
     * 4.4 이상 킷캣부터 지원
     * 상태바 투명화 & 투명 배경 추가
     */
    public void updateStatusbarTranslate(View vStatusBar) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ViewGroup.LayoutParams params = vStatusBar.getLayoutParams();

            int id = getResources().getIdentifier("status_bar_height", "dimen", "android");
            int statusHeight = getResources().getDimensionPixelSize(id);

            params.height = statusHeight;
            vStatusBar.setLayoutParams(params);

            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
            );
        }
    }

    /***
     * 4.4 이상 킷캣부터 지원
     * 네비게이션 바 투명화
     */
    public void updateNavigationBarTranslate() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
            );
        }
    }

    /***
     * @param color 4.4 이상 킷캣부터 지원
     *              상태바가 color 값 지정
     */
    public void setStatusbarColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(color);
        }
    }

    /***
     * @param color 4.4 이상 킷캣부터 지원
     *              네비게이션바 color 값 지정
     */
    public void setNavigationbarColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(color);
        }
    }

    /**
     * Fragment 바꾸기 [애니메이션 없음]
     */
    public void replaceFragment(int id, Fragment fragment, boolean addToBackStack) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(id, fragment);
        if (addToBackStack) {
            ft.addToBackStack(null);
        }
        ft.commit();
    }

    /**
     * Fragment 바꾸기 [애니메이션 있음]
     */
    public void replaceFragment(int id, Fragment fragment, boolean addToBackStack, int anim1, int anim2) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(anim1, anim2);
        ft.replace(id, fragment);
        if (addToBackStack) {
            ft.addToBackStack(null);
        }
        ft.commit();
    }

    /**
     * Fragment 바꾸기 [애니메이션 있음]
     */
    public void replaceFragment(int id, Fragment fragment, boolean addToBackStack, int anim1, int anim2, int anim3, int anim4) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(anim1, anim2, anim3, anim4);
        if (addToBackStack) {
            ft.addToBackStack(null);
        }
        ft.replace(id, fragment);
        ft.commit();
    }

    /*
            Permission 관련
         */
    public OnPermissionListener getOnPermissionListener() {
        return onPermissionListener;
    }

    public void setOnPermissionListener(OnPermissionListener onPermissionListener) {
        this.onPermissionListener = onPermissionListener;
    }
}

package com.naver.temy123.baseproject.base.Widgets;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.naver.temy123.baseproject.base.Interface.OnHwNetworkCallback;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 　　　　　$ ================================== $
 * 　　　　　　　　TimeHub 이현우 2015-11-10 제작
 * 　　　　　$ ================================== $
 */
public abstract class BaseFragment extends Fragment implements OnHwNetworkCallback {

    /**
     * replaceFragment() 를 사용할것 [ 같은 기능 ]
     *
     * @param id
     * @param fragment
     */
    @Deprecated
    public void replaceFragmentFromParent(int id, Fragment fragment) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(id, fragment);
        ft.commit();
    }


    /**
     * Fragment 바꾸기 [애니메이션 없음]
     */
    public void replaceFragment(int id, Fragment fragment, boolean addToBackStack) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
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
        FragmentTransaction ft = getFragmentManager().beginTransaction();
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
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.setCustomAnimations(anim1, anim2, anim3, anim4);
        if (addToBackStack) {
            ft.addToBackStack(null);
        }
        ft.replace(id, fragment);
        ft.commit();
    }

    /**
     * Fragment 내에서 Fragment 바꾸기 [애니메이션 없음]
     */
    public void replaceFragmentFromChild(int id, Fragment fragment, boolean addToBackStack) {
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        ft.replace(id, fragment);
        if (addToBackStack) {
            ft.addToBackStack(null);
        }
        ft.commit();
    }

    /**
     * Fragment 내에서 Fragment 바꾸기 [애니메이션 있음]
     */
    public void replaceFragmentFromChild(int id, Fragment fragment, boolean addToBackStack, int anim1, int anim2) {
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        ft.setCustomAnimations(anim1, anim2);
        ft.replace(id, fragment);
        if (addToBackStack) {
            ft.addToBackStack(null);
        }
        ft.commit();
    }

    /**
     * Fragment 내에서 Fragment 바꾸기 [애니메이션 있음]
     */
    public void replaceFragmentFromChild(int id, Fragment fragment, boolean addToBackStack, int anim1, int anim2, int anim3, int anim4) {
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        ft.setCustomAnimations(anim1, anim2, anim3, anim4);
        if (addToBackStack) {
            ft.addToBackStack(null);
        }
        ft.replace(id, fragment);
        ft.commit();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        // View Pager 안에 BaseFragment 가 포함 되어 있을 때를 위해
        // 미리 값을 저장해서 호출하도록
        if (getView() != null) {
            onPageChanged(isVisibleToUser);
        }
    }

    /**
     * ViewPager 안에 BaseFragment 가 포함 되어있을때<br>
     * 상태 변경을 확인 할 수 있는 함수
     */
    public void onPageChanged(boolean isShow) {
        onPageChanged(null, isShow);
    }

    /**
     * ViewPager 안에 BaseFragment 가 포함 되어있을때<br>
     * 상태 변경을 확인 할 수 있는 함수
     */
    public void onPageChanged(ViewPager viewPager, boolean isShow) {

    }

    @Nullable
    @Override
    public abstract View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    @Override
    public void onFailed(Intent intent, IOException e) {

    }

    @Override
    public void onUIResponsed(Call call, Intent intent, String body, String message, int status) {

    }

    @Override
    public void onNetworkResponsed(Call call, Intent intent, Response response, String body, int status) {

    }

}

package com.naver.temy123.baseproject.base.Widgets;

import android.app.Dialog;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by lhw on 2017. 5. 29..
 */

public class BaseDialogManager {

    /*
            현재는 같은 다이얼로그를 관리하기 위해 ArrayList로 관리하지만
            나중에 모든 다이얼로그를 관리하기 시작한다면

            각각 Dialog 마다 Key값을 가지고 관리를 해야 합니다

            현재는 서버 통신 마다 다이얼로그를 띄우고
            통신이 끝날때마다 순서대로 다이얼로그를 띄우기 때문에

            다이얼로그의 내용은 같기 때문에 문제가 없습니다
     */

    private ArrayList<Dialog> dialogs = new ArrayList<>();

    private static BaseDialogManager instance;

    public static BaseDialogManager getInstance() {
        if (instance == null) {
            instance = new BaseDialogManager();
        }
        return instance;
    }

    private BaseDialogManager() {

    }

    /**
     * Showing 중인 Dialog return
     *
     * @return
     */
    public ArrayList<Dialog> getRunningDialog() {
        ArrayList<Dialog> runningDialogs = new ArrayList<>();
        for (Dialog dialog : dialogs) {
            runningDialogs.add(dialog);
        }

        return runningDialogs;
    }

    public void show(final Dialog dialog) {
        // 현재 다이얼로그를 다시 설정하고, 표시
        if (dialog != null) {

            dialogs.add(dialog);
            dialog.show();
        }
    }

    public boolean isShowing(int index) {
        Dialog dialog = dialogs.get(index);
        if (dialog == null) {
            dialogs.remove(index);
            return false;
        }
        return dialog.isShowing();
    }

    /**
     * 등록된 다이얼로그 중 마지막 데이터 가져오기
     *
     * @return
     */
    public Dialog lastDialog() {
        Dialog dialog = null;
        if (dialogs.size() > 0) {
            dialog = dialogs.get(dialogs.size() - 1);
        }

        return dialog;
    }

    /**
     * 등록된 다이얼로그 중 첫번째 데이터 가져오기
     *
     * @return
     */
    private Dialog firstDialog() {
        Dialog dialog = null;
        if (dialogs.size() > 0) {
            dialog = dialogs.get(0);
        }

        return dialog;
    }

    public void dismiss(int index) {
        Log.d("DialogManager", "-- dismiss, size() : " + dialogs.size() + ", index : " + index);

        if (dialogs.size() <= 0) {
            return;
        }

        Dialog dialog = dialogs.get(index);
        if (dialog != null) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }
        if (dialogs.size() > index) {
            dialogs.remove(index);
        }
    }

}

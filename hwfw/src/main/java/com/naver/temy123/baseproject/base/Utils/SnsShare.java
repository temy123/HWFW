package com.naver.temy123.baseproject.base.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Parcelable;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 　　　　　$ ================================== $
 * 　　　　　　　　TimeHub 이현우 2016-05-10 제작
 * 　　　　　$ ================================== $
 */
public class SnsShare {

    public static final void shareToSns ( Context context, int requestCode, String dialogTitle, String title, String facebookText, String otherText ) {
        // TODO: SNS 공유 기능 추가할 것
        // BAND, FACEBOOK, KAKAOSTORY
        // 밴드는 추가할 지 말지 추후에

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");

        List<ResolveInfo> resInfo = context.getPackageManager().queryIntentActivities(intent, 0);
        if (resInfo.isEmpty()) {
            return ;
        }

        List<Intent> shareIntentList = new ArrayList<>();

        for (ResolveInfo resolveInfo : resInfo) {
            Intent shareIntent = (Intent) intent.clone();

            if (TextUtils.equals(resolveInfo.activityInfo.packageName.toLowerCase(), "com.facebook.katana")) {
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, facebookText);
            } else {
                shareIntent.setType("image/*");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, title);
                shareIntent.putExtra(Intent.EXTRA_TEXT, otherText);
            }
            shareIntent.setPackage(resolveInfo.activityInfo.packageName);
            shareIntentList.add(shareIntent);
        }

        Intent chooserIntent = Intent.createChooser(shareIntentList.remove(0), dialogTitle);
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, shareIntentList.toArray(new Parcelable[]{}));
        ((Activity)context).startActivityForResult(chooserIntent, requestCode);

        /*
            Share 기능은 기본적으로 다음과 같이 나눌 수 있다

            - Text Share : 텍스트 공유를 하기 위해 사용
            - Link Share : 링크만 공유가 가능한 곳을 위해 사용
            - Image Share : 이미지만 공유가 가능한 곳을 위해 사용
            - Text + Image Share : 텍스트와 이미지를 함께 공유할 수 있는 곳에 사용

            * 페이스북 같은 경우 Link Share 방식 만 사용 가능
            * 카카오톡의 경우 Image Share 혹은 Text + Link Share 타입을 사용 가능
            * 트위터의 경우 모든 Share 타입을 사용할 수 있음
         */

    }

}

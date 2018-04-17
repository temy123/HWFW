package com.naver.temy123.baseproject.base.Interface;

import android.content.Intent;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by temy1 on 2017-11-03.
 */

public interface OnHwNetworkCallback {

    void onFailed(Intent intent, IOException e);

    void onUIResponsed(Call call, Intent intent, String body, String message, int status);

    void onNetworkResponsed(Call call, Intent intent, Response response, String body, int status);


}

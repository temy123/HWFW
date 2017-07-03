package com.naver.temy123.baseproject.base.Http;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import com.naver.temy123.baseproject.base.Utils.HW_Params;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by lhw on 2017. 3. 6..
 */

public class HWOkHttpCallBack implements Callback {

    private Context context;
    private Handler handler;
    private int requestCode = -1;

    public HWOkHttpCallBack(Context context) {
        this.context = context;
    }

    public HWOkHttpCallBack(Context context, int requestCode) {
        this.context = context;
        this.requestCode = requestCode;
    }


    @Override
    public void onResponse(final Call call, final Response response) {
        // Log
        printLogCall(call);

        // Data Parse
        handler = new Handler(context.getMainLooper());
        String body = "";
        String msg = response.message();
        try {
            body = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        final String data = body;
        final String message = msg;

        // Network Thread
        onNetworkResponsed(call, response, body);

        // UI Thread
        handler.post(new Runnable() {
            @Override
            public void run() {
                onUIResponsed(call, data, message);
            }
        });
    }


    public void onFailed(Intent intent, IOException e) {
    }

    /**
     * on Background Thread
     *
     * @param call
     * @param response
     * @param body
     */
    public void onNetworkResponsed(Call call, Response response, String body) {

    }

    public void onUIResponsed(Call call, String body, String message) {
        // Receiver 등록
        sendBroadcast(call, body, message);
    }

    private void printLogCall(Call call) {
        Request request = call.request();
        Log.d("HWOkHttpCallBack", "-- " + request);

//        String url = request.url().url().toString();
//        String parameter = request.url().query();
//        String method = request.method();
//        String body = request.toString()
    }

    private void sendBroadcast(Call call, String body, String message) {
        Intent intent = generateIntent(call, body, message);
        intent.setAction(HW_Params.HW_NETWORK_ACTION);
        context.sendBroadcast(intent);
    }

    private Intent generateIntent (Call call, String body, String message) {
        Intent intent = new Intent();

        String method = call.request().method();
        String url = call.request().url().url().toString();
        String headers = call.request().headers().toString();

        intent.putExtra(HW_Params.HW_NETWORK_EXTRA_METHOD, method);
        intent.putExtra(HW_Params.HW_NETWORK_EXTRA_URL, url);
        intent.putExtra(HW_Params.HW_NETWORK_EXTRA_HEADERS, headers);
        intent.putExtra(HW_Params.HW_NETWORK_EXTRA_REQ, requestCode);
        intent.putExtra(HW_Params.HW_NETWORK_EXTRA_BODY, body);
        intent.putExtra(HW_Params.HW_NETWORK_EXTRA_MESSAGE, message);

        return intent;
    }

    @Override
    public void onFailure(final Call call, final IOException e) {
        // Log
        printLogCall(call);

        // Intent 정보 담기
        final Intent intent = generateIntent(call, null, null);

        handler = new Handler(context.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                onFailed(intent, e);
            }
        });
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}

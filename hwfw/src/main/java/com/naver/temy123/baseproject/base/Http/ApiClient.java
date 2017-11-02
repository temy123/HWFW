package com.naver.temy123.baseproject.base.Http;

import android.content.Context;
import android.net.ConnectivityManager;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

/**
 *  @Deprecated AsyncHttpClient 는 이제 사용하지않습니다 <br />
 *
 *      15-11-09 에 업데이트
 *      기본 방식
 */
@Deprecated
public class ApiClient {

    private static final int TIMEOUT_CONNECTION = 10 * 1000 ;
    private static final int TIMEOUT_RESPONSE = 10 * 1000 ;
    private static final int TIMEOUT_DEFAULT = 10 * 1000 ;

    private AsyncHttpClient mClient ;

    private Context mContext ;
    // 인터넷 확인 위한 Manager
    private ConnectivityManager mConnectivityManager ;

    /*
        인스턴스
     */
    private static ApiClient instance ;
    public static ApiClient newInstance ( Context context ) {
        instance = new ApiClient(context);
        return instance;
    }

    public static ApiClient getInstance ( ) {
        return instance;
    }

    private ApiClient(Context context) {
        setContext(context);

        // 생성 시 Timeout 설정
        mClient = new AsyncHttpClient();
        mClient.setTimeout(TIMEOUT_DEFAULT);
        mClient.setConnectTimeout(TIMEOUT_CONNECTION);
        mClient.setResponseTimeout(TIMEOUT_RESPONSE);
    }

    // 인터넷 확인
    public boolean checkInternet ( ) {
        mConnectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (mConnectivityManager.getActiveNetworkInfo() == null || mConnectivityManager.getActiveNetworkInfo().isConnectedOrConnecting() == false) {
            return false ;
        }

        return true ;
    }

    /*
        쿠키 설정
        Application 등록 시 한번만 설정 해도 사용 가능
     */
    public void setCookieStore ( Context context ) {
        context = context.getApplicationContext();
        mClient.setCookieStore(new PersistentCookieStore(context));
    }

    /*
        쿠키 삭제
     */
    public void clearCookieStore ( Context context ) {
        context = context.getApplicationContext();
        PersistentCookieStore cookieStore = new PersistentCookieStore(context);
        cookieStore.clear();
        mClient.setCookieStore(cookieStore);
    }

    /*
        쿠키 가져오기
     */
    public PersistentCookieStore getCookieStore ( Context context ) {
        context = context.getApplicationContext();
        PersistentCookieStore cookieStore = new PersistentCookieStore(context);
        return cookieStore;
    }


    /**
     *  테스트 용 : onStart, onFinish 만 호출하도록 되어있음
     */
    public void testJSONData (String json, AsyncHttpResponseHandler handler) {
        handler.onStart();
        handler.onFinish();
        handler.onSuccess(0, null, json.getBytes());
    }

    /*
        서버 요청
     */
    public RequestHandle get(String url, RequestParams params, ResponseHandlerInterface responseHandler) {
        if (checkInternet() == false) {
            return null;
        }
        return mClient.get(url, params, responseHandler);
    }

    public RequestHandle post(String url, RequestParams params, ResponseHandlerInterface responseHandler) {
        if (checkInternet() == false) {
            return null;
        }
        return mClient.post(url, params, responseHandler);
    }

    public RequestHandle put(String url, RequestParams params, ResponseHandlerInterface responseHandler) {
        if (checkInternet() == false) {
            return null;
        }
        return mClient.put(url, params, responseHandler);
    }

    public void delete(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        if (checkInternet() == false) {
            return ;
        }
        mClient.delete(url, params, responseHandler);
    }

    public RequestHandle patch(String url, RequestParams params, ResponseHandlerInterface responseHandler) {
        if (checkInternet() == false) {
            return null;
        }
        return mClient.patch(url, params, responseHandler);
    }

    public RequestHandle head(String url, RequestParams params, ResponseHandlerInterface responseHandler) {
        if (checkInternet() == false) {
            return null;
        }
        return mClient.head(url, params, responseHandler);
    }

    /*
        타임아웃
     */
    public void setTimeout(int value) {
        mClient.setTimeout(value);
    }

    public void setConnectTimeout(int value) {
        mClient.setConnectTimeout(value);
    }

    public void setResponseTimeout(int value) {
        mClient.setResponseTimeout(value);
    }

    /*
            Getters / Setters
         */
    public Context getContext() {
        return mContext;
    }

    public void setContext(Context mContext) {
        this.mContext = mContext;
    }

    public AsyncHttpClient getClient() {
        return mClient;
    }

    public void setClient(AsyncHttpClient mClient) {
        this.mClient = mClient;
    }

}

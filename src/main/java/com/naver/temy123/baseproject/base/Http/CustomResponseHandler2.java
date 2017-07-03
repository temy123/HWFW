package com.naver.temy123.baseproject.base.Http;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import com.naver.temy123.baseproject.base.Utils.Comm_RtnKey;

/**
 *  @Deprecated AsyncHttpClient 는 이제 사용하지않습니다 <br />
 *
 * Created by HW on 2015-09-03.
 */
@Deprecated
public abstract class CustomResponseHandler2 extends AsyncHttpResponseHandler {

    private static String STT_RTNKEY = "RtnKey" ;
    private static String STT_RTNVALUE = "RtnValue" ;

    // 다시시도
    private boolean mBoolRetry = false ;
    // 유저 커스텀 사용 여부
    private boolean isCustom = false ;

    // Retry 시도 딜레이
    private int mDelay = 100 ;
    private int retryLimitCount = 3 ;
    private int retryCount = 0 ;

    private String mRtnKey ;
    private String mRtnValue ;
    private String mData ;
    private JSONObject mJsonObject;
    private ProgressDialog mProgressDialog;
    private Context mContext ;

    // 다이얼로그 안띄우기
    public CustomResponseHandler2() {}

    // 다이얼로그 안띄우기
    public CustomResponseHandler2(Context context) {
        mContext = context;
    }

    // 다이얼로그 띄우기
    public CustomResponseHandler2(Context context, boolean isShowDialog) {
        mContext = context;
        if (isShowDialog) {
            mProgressDialog = new ProgressDialog(context);
        }
    }

    // 다이얼로그 띄우기
    public CustomResponseHandler2(Context context, boolean isShowDialog, boolean retry) {
        mContext = context;
        this.mBoolRetry = retry;
        if (isShowDialog) {
            mProgressDialog = new ProgressDialog(context);
        }
    }

    // 다이얼로그 띄우기
    public CustomResponseHandler2(Context context, boolean isShowDialog, int retryDelay) {
        mContext = context;
        this.mBoolRetry = true;
        this.mDelay = retryDelay;
        if (isShowDialog) {
            mProgressDialog = new ProgressDialog(context);
        }
    }

    // 다이얼로그 관련
    @Override
    public void onStart() {
        super.onStart();
        if (mProgressDialog != null) {
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
        }
    }

    @Override
    public void onFinish() {
        super.onFinish();
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        boolean result = false ;
        mData = new String(responseBody);

        try {
            mJsonObject = new JSONObject(mData);

            mRtnKey = mJsonObject.getString(getSttRtnkey());
            mRtnValue = mJsonObject.getString(getSttRtnvalue());

            // 리턴 값에 대해
            switch (mRtnKey) {
                case Comm_RtnKey.CMOK :
                    result = CMOK();
                    break ;
                case Comm_RtnKey.CMSE :
                    result = CMSE();
                    break ;
                case Comm_RtnKey.DAOK :
                    result = DAOK();
                    break ;
                case Comm_RtnKey.DANO :
                    result = DANO();
                    break ;
                case Comm_RtnKey.MBRS :
                    result = MBRS();
                    break ;
                case Comm_RtnKey.MBRF :
                    result = MBRF();
                    break ;
                case Comm_RtnKey.MBVD :
                    result = MBVD();
                    break ;
                case Comm_RtnKey.MLOS :
                    result = MLOS();
                    break ;
                case Comm_RtnKey.MLOF :
                    result = MLOF();
                    break ;
                case Comm_RtnKey.MINO :
                    result = MINO();
                    break ;
                case Comm_RtnKey.MBFD :
                    result = MBFD();
                    break ;
                case Comm_RtnKey.MBGD :
                    result = MBGD();
                    break ;
                case Comm_RtnKey.MLGD :
                    result = MLGD();
                    break ;
                case Comm_RtnKey.MLFD :
                    result = MLFD();
                    break ;
                case Comm_RtnKey.PWRS :
                    result = PWRS();
                    break ;
                case Comm_RtnKey.PWRF :
                    result = PWRF();
                    break ;
            }

            // 항상 실행되는 함수
            DEFAULT();

            // 리턴 콜백 이외의 처리가 필요할때
            if (!result) {
                OTHER();
            }

            // 유저가 임의로 호출 가능 한 함수
            if (isCustom) {
                CUSTOM();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
        if (isRetry()) {
            // Retry 가 가능 할 때
            if (retryCount++ < retryLimitCount) {
                if (Looper.myLooper() == Looper.getMainLooper()) {
                    Handler handler = new Handler(Looper.myLooper());
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            String url = getRequestURI().toString();
                            if (getRequestHeaders() == null || getRequestHeaders().length <= 0) {
                                ApiClient.getInstance().get(url, null, CustomResponseHandler2.this);
                            } else {
                                ApiClient.getInstance().getClient().post(getmContext(), url, getRequestHeaders(), (RequestParams) null, null, CustomResponseHandler2.this);
                            }
                        }
                    }, mDelay);
                }
            // Retry 가 모두 실패 했을 때
            } else {
                onRetryFailure(statusCode, headers, responseBody, error);
            }
        }
    }

    /**
     * 구현 필요
     * 모든 Retry 가 실패했을 때
     *
     * @param statusCode
     * @param headers
     * @param responseBody
     * @param error
     */
    public void onRetryFailure (int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

    }

    /**
     *  리턴 값에 따른 콜백
     */

    /*
        DEFAULT : 항상 실행 되는 함수
     */
    public void DEFAULT ( ) {

    }

    /*
        CUSTOM : 사용자가 임의로 호출 여부를 결정 가능 한 함수
     */
    public void CUSTOM ( ) {

    }

    /*
        OTHER : 사용자가 임의로 무시 여부 결정 가능 한 함수
     */
    public void OTHER ( ) {

    }

    /*
        RTNKEY : CMOK
        공통코드 : 정상적으로 처리되었습니다.
     */
    public boolean CMOK ( ) { return false; }

    /*
        RTNKEY : CMSE
        공통코드 : 서버 에러 발생
     */
    public boolean CMSE ( ) { return false; }

    /*
        RTNKEY : DAOK
        공통코드 : 데이터 있습니다.
     */
    public boolean DAOK ( ) { return false; }

    /*
        RTNKEY : DANO
        공통코드 : 데이터 없습니다.
     */
    public boolean DANO ( ) { return false; }

    /*
        RTNKEY : MBRS
        회원가입 성공
     */
    public boolean MBRS ( ) { return false; }

    /*
        RTNKEY : MBRF
        회원가입 실패
     */
    public boolean MBRF ( ) { return false; }

    /*
        RTNKEY : MBVD
        이미 회원가입된 이메일 주소입니다.
     */
    public boolean MBVD ( ) { return false; }

    /*
        RTNKEY : MLOS
        로그인 성공
     */
    public boolean MLOS ( ) { return false; }

    /*
        RTNKEY : MLOF
        로그인 실패
     */
    public boolean MLOF ( ) { return false; }

    /*
        RTNKEY : MINO
        권한이 존재하지 않습니다
     */
    public boolean MINO ( ) { return false; }

    /*
        RTNKEY : MBFD
        해당 아이디는 이미 Facebook 계정으로 회원가입 되어 있습니다.
     */
    public boolean MBFD ( ) { return false; }

    /*
        RTNKEY : MBGD
        해당 아이디는 이미 Google 계정으로 회원가입 되어 있습니다.
     */
    public boolean MBGD ( ) { return false; }

    /*
        RTNKEY : MLGD
        이미 Google 을 통해 가입된 이메일 계정이 있습니다.
     */
    public boolean MLGD ( ) { return false; }

    /*
        RTNKEY : MLFD
        이미 Facebook 을 통해 가입된 이메일 계정이 있습니다.
     */
    public boolean MLFD ( ) { return false; }

    /*
        RTNKEY : PWRS
        이메일 요청 성공
     */
    public boolean PWRS ( ) { return false; }

    /*
        RTNKEY : PWRF
        이메일 요청 실패
     */
    public boolean PWRF ( ) { return false; }

    /**
     *  Getters / Setters
     */
    public String getRtnKey() {
        return mRtnKey;
    }

    public void setRtnKey(String mRtnKey) {
        this.mRtnKey = mRtnKey;
    }

    public String getRtnValue() {
        return mRtnValue;
    }

    public void setRtnValue(String mRtnValue) {
        this.mRtnValue = mRtnValue;
    }

    public String getData() {
        return mData;
    }

    public void setData(String mData) {
        this.mData = mData;
    }

    public JSONObject getJsonObject() {
        return mJsonObject;
    }

    public void setJsonObject(JSONObject mJsonObject) {
        this.mJsonObject = mJsonObject;
    }

    public boolean isRetry() {
        return mBoolRetry;
    }

    public void setRetry(boolean mBoolRetry) {
        this.mBoolRetry = mBoolRetry;
    }

    public boolean isCustom() {
        return isCustom;
    }

    public void callCustom( ) {
        this.isCustom = true;
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public static String getSttRtnkey() {
        return STT_RTNKEY;
    }

    public static void setSttRtnkey(String sttRtnkey) {
        STT_RTNKEY = sttRtnkey;
    }

    public static String getSttRtnvalue() {
        return STT_RTNVALUE;
    }

    public static void setSttRtnvalue(String sttRtnvalue) {
        STT_RTNVALUE = sttRtnvalue;
    }

    public ProgressDialog getProgressDialog() {
        return mProgressDialog;
    }

    public void setProgressDialog(ProgressDialog mProgressDialog) {
        this.mProgressDialog = mProgressDialog;
    }

    public int getRetryLimitCount() {
        return retryLimitCount;
    }

    public void setRetryLimitCount(int retryLimitCount) {
        this.retryLimitCount = retryLimitCount;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

}

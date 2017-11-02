package com.naver.temy123.baseproject.base.Http;

import android.app.ProgressDialog;
import android.content.Context;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.naver.temy123.baseproject.base.Utils.Comm_RtnKey;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * @Deprecated AsyncHttpClient 는 이제 사용하지않습니다 <br />
 * Created by HW on 2015-09-03.
 */
@Deprecated
public abstract class CustomResponseHandler extends AsyncHttpResponseHandler {

    // 다시시도 ( 한번만 )
    private boolean mBoolRetry = true;
    // 성공 이후 무시 해도 되는 스텝 여부
    private boolean isOther = true;
    // 유저 커스텀 사용 여부
    private boolean isCustom = false;

    private String mRtnKey;
    private String mRtnValue;
    private String mData;
    private JSONObject mJsonObject;
    private ProgressDialog mProgressDialog;
    private boolean isShowDialog;
    private Context mContext;

    // 다이얼로그 안띄우기
    public CustomResponseHandler() {
    }

    // 다이얼로그 띄우기
    public CustomResponseHandler(Context context, boolean isShowDialog) {
        mContext = context;
        mProgressDialog = new ProgressDialog(context);
        this.isShowDialog = isShowDialog;
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
        mData = new String(responseBody);

        try {
            mJsonObject = new JSONObject(mData);

            mRtnKey = mJsonObject.getString("RtnKey");
            mRtnValue = mJsonObject.getString("RtnValue");

            // 리턴 값에 대해
            switch (mRtnKey) {
                case Comm_RtnKey.CMOK:
                    CMOK();
                    break;
                case Comm_RtnKey.CMSE:
                    CMSE();
                    break;
                case Comm_RtnKey.DAOK:
                    DAOK();
                    break;
                case Comm_RtnKey.DANO:
                    DANO();
                    break;
                case Comm_RtnKey.MBRS:
                    MBRS();
                    break;
                case Comm_RtnKey.MBRF:
                    MBRF();
                    break;
                case Comm_RtnKey.MBVD:
                    MBVD();
                    break;
                case Comm_RtnKey.MLOS:
                    MLOS();
                    break;
                case Comm_RtnKey.MLOF:
                    MLOF();
                    break;
                case Comm_RtnKey.MINO:
                    MINO();
                    break;
                case Comm_RtnKey.MBFD:
                    MBFD();
                    break;
                case Comm_RtnKey.MBGD:
                    MBGD();
                    break;
                case Comm_RtnKey.MLGD:
                    MLGD();
                    break;
                case Comm_RtnKey.MLFD:
                    MLFD();
                    break;
                case Comm_RtnKey.PWRS:
                    PWRS();
                    break;
                case Comm_RtnKey.PWRF:
                    PWRF();
                    break;
            }

            // 항상 실행되는 함수
            DEFAULT();

            // 리턴 콜백 이외의 처리가 필요할때
            if (isOther) {
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

    }

    /*
        실패시 다시시도가 가능한 상태인지 체크해줌
     */
    public boolean canRetry() {
        if (isRetry()) {
            setRetry(false);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 리턴 값에 따른 콜백
     */

    /*
        DEFAULT : 항상 실행 되는 함수
     */
    protected void DEFAULT() {

    }

    /*
        CUSTOM : 사용자가 임의로 호출 여부를 결정 가능 한 함수
     */
    protected void CUSTOM() {

    }

    /*
        OTHER : 사용자가 임의로 무시 여부 결정 가능 한 함수
     */
    protected void OTHER() {

    }

    /*
        RTNKEY : CMOK
        공통코드 : 정상적으로 처리되었습니다.
     */
    protected void CMOK() {
    }

    /*
        RTNKEY : CMSE
        공통코드 : 서버 에러 발생
     */
    protected void CMSE() {
    }

    /*
        RTNKEY : DAOK
        공통코드 : 데이터 있습니다.
     */
    protected void DAOK() {
    }

    /*
        RTNKEY : DANO
        공통코드 : 데이터 없습니다.
     */
    protected void DANO() {
    }

    /*
        RTNKEY : MBRS
        회원가입 성공
     */
    protected void MBRS() {
    }

    /*
        RTNKEY : MBRF
        회원가입 실패
     */
    protected void MBRF() {
    }

    /*
        RTNKEY : MBVD
        이미 회원가입된 이메일 주소입니다.
     */
    protected void MBVD() {
    }

    /*
        RTNKEY : MLOS
        로그인 성공
     */
    protected void MLOS() {
    }

    /*
        RTNKEY : MLOF
        로그인 실패
     */
    protected void MLOF() {
    }

    /*
        RTNKEY : MINO
        권한이 존재하지 않습니다
     */
    protected void MINO() {
    }

    /*
        RTNKEY : MBFD
        해당 아이디는 이미 Facebook 계정으로 회원가입 되어 있습니다.
     */
    protected void MBFD() {
    }

    /*
        RTNKEY : MBGD
        해당 아이디는 이미 Google 계정으로 회원가입 되어 있습니다.
     */
    protected void MBGD() {
    }

    /*
        RTNKEY : MLGD
        이미 Google 을 통해 가입된 이메일 계정이 있습니다.
     */
    protected void MLGD() {
    }

    /*
        RTNKEY : MLFD
        이미 Facebook 을 통해 가입된 이메일 계정이 있습니다.
     */
    protected void MLFD() {
    }

    /*
        RTNKEY : PWRS
        이메일 요청 성공
     */
    protected void PWRS() {
    }

    /*
        RTNKEY : PWRF
        이메일 요청 실패
     */
    protected void PWRF() {
    }

    /*
        무시
     */
    protected void ignoreOther() {
        this.isOther = false;
    }

    /**
     * Getters / Setters
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

    public void callCustom() {
        this.isCustom = true;
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

}

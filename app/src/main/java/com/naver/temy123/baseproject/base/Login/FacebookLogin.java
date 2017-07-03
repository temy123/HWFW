package com.naver.temy123.baseproject.base.Login;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.util.Arrays;
import java.util.Collection;

/**
 * 　　　　　$ ================================== $
 * 　　　　　　　　TimeHub 이현우 2016-01-26 제작
 * 　　　　　$ ================================== $
 */
public class FacebookLogin {

    public interface OnFacebookLoginListener {
        void onSuccess (LoginResult loginResult) ;
        void onCanceled ( ) ;
        void onError (FacebookException error) ;
    }

    private static FacebookLogin instance ;
    private LoginResult mLoginResult ;
    private CallbackManager mCallbackManager ;
    private LoginManager mLoginManager;

    public static FacebookLogin getInstance (Context context) {
        if (instance == null) {
            instance = new FacebookLogin(context);
        }
        return instance;
    }

    private FacebookLogin(Context context) {
        // sdk 초기화
        FacebookSdk.sdkInitialize(context);

    }

    // CallbackManager 받아놓기
    public CallbackManager init ( ) {
        mLoginManager = LoginManager.getInstance();
        return mCallbackManager = CallbackManager.Factory.create();
    }

    /**
     * 페이스북 기본 로그인
     * @param activity
     */
    public void login ( Activity activity, final OnFacebookLoginListener listener ) {
        Collection<String> permissions = Arrays.asList("email", "public_profile", "user_friends");

        mLoginManager.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                mLoginResult = loginResult;

                // Listener 연동
                listener.onSuccess(loginResult);
            }

            @Override
            public void onCancel() {
                listener.onCanceled();
            }

            @Override
            public void onError(FacebookException error) {
                listener.onError(error);
            }
        });
        mLoginManager.logInWithReadPermissions(activity, permissions);
    }

    /**
     * 페이스북 기본 로그인
     * @param activity
     */
    public void login ( Activity activity, final OnFacebookLoginListener listener, Collection<String> permissions ) {
        mLoginManager.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                mLoginResult = loginResult;

                // Listener 연동
                listener.onSuccess(loginResult);
            }

            @Override
            public void onCancel() {
                listener.onCanceled();
            }

            @Override
            public void onError(FacebookException error) {
                listener.onError(error);
            }
        });
        mLoginManager.logInWithReadPermissions(activity, permissions);
    }

    /**
     * 페이스북 기본 로그인
     * @param fragment
     */
    public void login ( Fragment fragment, final OnFacebookLoginListener listener ) {
        Collection<String> permissions = Arrays.asList("email", "public_profile", "user_friends");

        mLoginManager.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                mLoginResult = loginResult;

                // Listener 연동
                listener.onSuccess(loginResult);
            }

            @Override
            public void onCancel() {
                listener.onCanceled();
            }

            @Override
            public void onError(FacebookException error) {
                listener.onError(error);
            }
        });
        mLoginManager.logInWithReadPermissions(fragment, permissions);
    }

    /**
     * 페이스북 기본 로그인
     * @param fragment
     */
    public void login ( Fragment fragment, final OnFacebookLoginListener listener, Collection<String> permissions ) {
        mLoginManager.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                mLoginResult = loginResult;

                // Listener 연동
                listener.onSuccess(loginResult);
            }

            @Override
            public void onCancel() {
                listener.onCanceled();
            }

            @Override
            public void onError(FacebookException error) {
                listener.onError(error);
            }
        });
        mLoginManager.logInWithReadPermissions(fragment, permissions);
    }

    /**
     * 기본적으로 요청할 때 다음 정보를 포함해서 Graph 를 요청하도록 함
     * ID, NAME, EMAIL, GENDER, BIRTHDAY
     * @param token
     * @param callback
     */
    public void basicRequestGraph ( AccessToken token, GraphRequest.GraphJSONObjectCallback callback ) {
        GraphRequest request = GraphRequest.newMeRequest(token, callback);

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,gender,birthday");
        request.setParameters(parameters);
        request.executeAsync();
    }


    /*
        Getters / Setters
     */
    public CallbackManager getCallbackManager() {
        return mCallbackManager;
    }

    public void setCallbackManager(CallbackManager mCallbackManager) {
        this.mCallbackManager = mCallbackManager;
    }

    public LoginResult getLoginResult() {
        return mLoginResult;
    }

    public void setLoginResult(LoginResult mLoginResult) {
        this.mLoginResult = mLoginResult;
    }
}

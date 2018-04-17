package com.naver.temy123.baseproject.base.Http;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.naver.temy123.baseproject.base.Interface.OnHwNetworkCallback;
import com.naver.temy123.baseproject.base.Utils.HWException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by lhw on 2017. 3. 6..
 */

public class HWOkHttpClient {

    public static final String PREFS_KEY_COOKIES = "PREFS_KEY_COOKIES";
    private static final String PREFS_NAME = "HWOkhttpClient";

    /**
     * Http Type 정의
     */
    public class HWHttpType {
        public static final int HTTP_TYPE_GET = 0;
        public static final int HTTP_TYPE_POST = 1;
        public static final int HTTP_TYPE_PATCH = 2;
        public static final int HTTP_TYPE_DELETE = 3;
        public static final int HTTP_TYPE_PUT = 4;
        public static final int HTTP_TYPE_OPTIONS = 5;
        public static final int HTTP_TYPE_HEAD = 6;
    }

    private static HWOkHttpClient instance;
    private Context context;
    private OkHttpClient okHttpClient;
    private List<Cookie> mCookiesList = new ArrayList<>();
    private SharedPreferences prefs;

    private class HWOkHttpClientCookie {
        private HttpUrl url;
        private List<Cookie> cookies;

        public HWOkHttpClientCookie() {
        }

        public HWOkHttpClientCookie(HttpUrl url, List<Cookie> cookies) {
            this.url = url;
            this.cookies = cookies;
        }

        public HttpUrl getUrl() {
            return url;
        }

        public void setUrl(HttpUrl url) {
            this.url = url;
        }

        public List<Cookie> getCookies() {
            return cookies;
        }

        public void setCookies(List<Cookie> cookies) {
            this.cookies = cookies;
        }
    }

    private CookieJar cookieJar = new CookieJar() {
        Gson gson = new Gson();

        @Override
        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
            mCookiesList = new ArrayList<>(cookies);
            Type type = new TypeToken<List<Cookie>>() {
            }.getType();
            String strJsonCookies = gson.toJson(mCookiesList, type);
            prefs.edit().putString(PREFS_KEY_COOKIES, strJsonCookies).commit();
        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl url) {
            return mCookiesList != null ? mCookiesList : new ArrayList<Cookie>();
        }
    };

    public static HWOkHttpClient newInstance(Context context) {
        instance = new HWOkHttpClient(context);
        return instance;
    }

    public static HWOkHttpClient newInstance(Context context, SSLSocketFactory sslSocketFactory, X509TrustManager x509TrustManager) {
        instance = new HWOkHttpClient(context, sslSocketFactory, x509TrustManager);
        return instance;
    }

    public static HWOkHttpClient getInstance(Context context) {
        if (instance == null) {
            instance = new HWOkHttpClient(context);
        }

        return instance;
    }


    /**
     * @Deprecate !!! More Exceptable !!!
     * <p>
     * <p>
     * Just returns instance.
     * If no initiated you must response returns null.
     */
    public static HWOkHttpClient getInstance() {
        return instance;
    }

    private HWOkHttpClient(Context context) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(10, TimeUnit.SECONDS);
        builder.writeTimeout(10, TimeUnit.SECONDS);
        builder.cookieJar(cookieJar);
        this.okHttpClient = builder.build();
        this.context = context;
        initPreferences();
        initCookies();
    }

    private HWOkHttpClient(Context context, SSLSocketFactory sslSocketFactory, X509TrustManager x509TrustManager) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.sslSocketFactory(sslSocketFactory, x509TrustManager);
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(10, TimeUnit.SECONDS);
        builder.writeTimeout(10, TimeUnit.SECONDS);
        builder.cookieJar(cookieJar);
        this.okHttpClient = builder.build();
        this.context = context;
        initPreferences();
        initCookies();
    }

    private HWOkHttpCallBack generateCallback(int reqCode, final OnHwNetworkCallback callback) {
        HWOkHttpCallBack callBack = new HWOkHttpCallBack(context, reqCode) {
            @Override
            public void onFailed(Intent intent, IOException e) {
                callback.onFailed(intent, e);
            }

            @Override
            public void onNetworkResponsed(Call call, Intent intent, Response response, String body, int status) {
                callback.onNetworkResponsed(call, intent, response, body, status);
            }

            @Override
            public void onUIResponsed(Call call, Intent intent, String body, String message, int status) {
                callback.onUIResponsed(call, intent, body, message, status);
            }
        };
        return callBack;
    }

    private void initPreferences() {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    private void initCookies() {
        String strJsonCookies = prefs.getString(PREFS_KEY_COOKIES, "");
        if (!TextUtils.isEmpty(strJsonCookies)) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<Cookie>>() {
            }.getType();
            mCookiesList = gson.fromJson(strJsonCookies, type);
        }
    }

    @Nullable
    public Response request(Request request) {
        Response response = null;
        try {
            response = okHttpClient
                    .newCall(request)
                    .execute();

        } catch (IOException e) {
            String msg = "HWOkHttpClient get Error : " + request.url();
            HWException
                    .newInstance(msg)
                    .printStackTrace();
        }

        return response;
    }

    public void request(int httpType, int requestCode, String url, HWOkHttpParams params, @Nullable OnHwNetworkCallback callBack) {
        // todo : Taja에서 파라메터는 header에 넣지 않으므로 수정하였습니다.
        request(httpType, requestCode, url, params, new HWOkHttpParams(), callBack);
    }

    @Nullable
    public void request(int httpType,
                        int requestCode,
                        String url,
                        HWOkHttpParams params,
                        HWOkHttpParams headers,
                        @NonNull OnHwNetworkCallback callback) {
        Uri uri = Uri.parse(url);
        request(httpType, requestCode, uri, params, headers, callback);
    }

    /**
     * 서버 요청
     *
     * @param httpType
     * @param uri
     * @param params
     * @param headers
     * @param callBack
     */
    @Nullable
    public void request(int httpType,
                        int requestCode,
                        Uri uri,
                        HWOkHttpParams params,
                        HWOkHttpParams headers,
                        @Nullable OnHwNetworkCallback callBack) {
        Request request = null;
        Request.Builder requestBuilder = new Request.Builder();
        HttpUrl httpUrl = null;
        HttpUrl.Builder httpUrlBuilder = new HttpUrl.Builder();
        httpUrlBuilder.scheme(uri.getScheme());
        httpUrlBuilder.host(uri.getHost());
        httpUrlBuilder.query(uri.getQuery());
        httpUrlBuilder.fragment(uri.getFragment());
        if (uri.getPort() >= 0) {
            httpUrlBuilder.port(uri.getPort());
        }

        httpUrlBuilder = generateQueryParameters(uri, httpUrlBuilder);
        httpUrlBuilder = generateSegment(uri, httpUrlBuilder);
        httpUrl = httpUrlBuilder.build();

        RequestBody body = null;
        requestBuilder.url(httpUrl);

        switch (httpType) {
            // ********
            //  GET
            // ********
            case HWHttpType.HTTP_TYPE_GET:
                // todo : url 을 다시 재정의 하지 않아서 추가하였습니다.
                httpUrlBuilder = generateParams(params, httpUrlBuilder);
                httpUrl = httpUrlBuilder.build();

                requestBuilder =
                        generateHeaders(requestBuilder, headers)
                                .url(httpUrl)
                                .get();
                break;

            // ********
            //  POST
            // ********
            case HWHttpType.HTTP_TYPE_POST:
                body = generateParams(params);
//                requestBuilder.method("POST", RequestBody.create(null, new byte[0]));
                requestBuilder =
                        generateHeaders(requestBuilder, headers)
                                .post(body);
                break;

            // ********
            //  PATCH
            // ********
            case HWHttpType.HTTP_TYPE_PATCH:
                body = generateParams(params);
                requestBuilder = generateHeaders(requestBuilder, headers)
                        .patch(body);
                break;

            // ********
            //  DELETE
            // ********
            case HWHttpType.HTTP_TYPE_DELETE:
                body = generateParams(params);
                requestBuilder = generateHeaders(requestBuilder, headers)
                        .delete(body);
                break;

            // ********
            //  PUT
            // ********
            case HWHttpType.HTTP_TYPE_PUT:
                body = generateParams(params);
                requestBuilder = generateHeaders(requestBuilder, headers)
                        .put(body);
                break;

            default:
                break;
        }

        request = requestBuilder.build();

        okHttpClient
                .newCall(request)
                .enqueue(generateCallback(requestCode, callBack));
    }

    /**
     * @param builder
     * @param headers
     * @return
     */
    private Request.Builder generateHeaders(Request.Builder builder, HWOkHttpParams headers) {
        for (HWOkHttpNameValuePair header : headers) {
            String key = header.getKey();
            Object value = header.getValue();

            if (value instanceof String) {
                String newValue = (String) value;
                if (newValue != null) {
                    if (key.length() > 0) {
                        builder.addHeader(key, newValue);
                    }
                }
            } else {
                Log.d("HWOkHttpClient", "-- Doesn't support this parameter types.");
            }
        }
        return builder;
    }

    /**
     * HTTP 파라미터 담기
     *
     * @param uri
     * @param httpUrlBuilder
     * @return
     */
    private HttpUrl.Builder generateQueryParameters(Uri uri, HttpUrl.Builder httpUrlBuilder) {
        for (String key : uri.getQueryParameterNames()) {
            String value = uri.getQueryParameter(key);
            httpUrlBuilder.addQueryParameter(key, value);
        }
        return httpUrlBuilder;
    }

    private HttpUrl.Builder generateSegment(Uri uri, HttpUrl.Builder httpUrlBuilder) {
        for (String segment : uri.getPathSegments()) {
            httpUrlBuilder.addPathSegment(segment);
        }

        return httpUrlBuilder;
    }

    /**
     * 파라미터 뽑아내기<br />
     * GET 용<br />
     *
     * @param params 파라미터 집합
     * @return OkHttpClient 용 HttpUrl
     */
    private HttpUrl.Builder generateParams(HWOkHttpParams params, HttpUrl.Builder httpUrlBuilder) {
        for (HWOkHttpNameValuePair param : params) {
            String key = param.getKey();
            Object value = param.getValue();

            if (value instanceof String) {
                String newValue = (String) value;
                if (!TextUtils.isEmpty(newValue)) {
                    if (key.length() > 0 &&
                            newValue.length() > 0) {
                        httpUrlBuilder.addEncodedQueryParameter(key, newValue);
                    }
                }
            } else {
                Log.d("HWOkHttpClient", "-- Doesn't support this parameter types.");
            }
        }

        return httpUrlBuilder;
    }

    /**
     * 파라미터 뽑아내기<br />
     * GET 이외의 Form, JSON Type 용<br />
     *
     * @param params 파라미터 집합
     * @return OkHttpClient 용 RequestBody
     */
    private RequestBody generateParams(HWOkHttpParams params) {
        int paramsType = params.getParamsType();
        // Form 형식의 파라미터가 없는 경우
        if (paramsType != HWOkHttpParams.TYPE_JSON &&
                params.size() <= 0) {
            return new FormBody.Builder().build();
        }

        RequestBody body = null;

        // Type == Form Data
        if (paramsType == HWOkHttpParams.TYPE_MULTIPART_FORM) {
            MultipartBody.Builder builder = new MultipartBody.Builder();
            builder.setType(MultipartBody.FORM);

            for (HWOkHttpNameValuePair param : params) {
                String key = param.getKey();
                Object value = param.getValue();

                // 단순 String 타입 추가
                if (value instanceof String) {
                    String newValue = (String) value;
                    newValue = newValue == null ? "" : newValue;
                    builder.addFormDataPart(key, newValue);
                }
                // File 타입 추가
                else if (value instanceof File) {
                    File file = (File) value;
                    int extensionIndex = file.getName().lastIndexOf(".");
                    String fileExtension = file.getName().substring(extensionIndex + 1);
                    String fileName = TextUtils.isEmpty(param.getFileName()) ?
                            file.getName() :
                            param.getFileName();

                    MediaType mediaType = MediaType.parse(HWOkHttpMimeType.getMimeType(fileExtension));
                    builder.addFormDataPart(key, fileName
                            , RequestBody.create(mediaType, file));
                }
            }

            body = builder.build();

        }
        // TYPE == JSON 형식
        else if (paramsType == HWOkHttpParams.TYPE_JSON) {
            String jsonString = params.getPendingJson() != null ? params.getPendingJson().toString() : "";

            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(jsonString);
                for (HWOkHttpNameValuePair param : params) {
                    String key = param.getKey();
                    Object value = param.getValue();

                    try {
                        jsonObject.put(key, value);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                jsonString = jsonObject.toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            body = RequestBody.create(
                    MediaType.parse(HWOkHttpMimeType.MIME_APPLICATION_JSON),
                    jsonString);
        }
        // TYPE == X-WWW-FORM-URLENCODED
        else if (paramsType == HWOkHttpParams.TYPE_X_WWW_FORM_URLENCODED) {
            FormBody.Builder builder = new FormBody.Builder();

            for (HWOkHttpNameValuePair param : params) {
                String key = param.getKey();
                Object value = param.getValue();

                // 단순 String 타입 추가
                String newValue = (String) value;
                newValue = newValue == null ? "" : newValue;
                builder.addEncoded(key, newValue);
            }

            body = builder.build();
        }
        // 이외에는 지원 하지 않음
        else {
            Log.d("HWOkHttpClient", "Does not support this params type");
        }

        return body;
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    public void setOkHttpClient(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }

    public List<Cookie> getCookiesList() {
        return mCookiesList;
    }

    public void setCookiesList(List<Cookie> mCookiesList) {
        this.mCookiesList = mCookiesList;
    }

    public void removeCookies() {
        mCookiesList = new ArrayList<Cookie>();
        prefs.edit().putString(PREFS_KEY_COOKIES, null).commit();
    }

}

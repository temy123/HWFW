package com.naver.temy123.baseproject.base.Http;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.naver.temy123.baseproject.base.Utils.HWException;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

import cz.msebera.android.httpclient.NameValuePair;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by lhw on 2017. 3. 6..
 */

public class HWOkHttpClient {

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

    public HWOkHttpClient(Context context) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(5, TimeUnit.SECONDS);
        builder.readTimeout(5, TimeUnit.SECONDS);
        builder.writeTimeout(5, TimeUnit.SECONDS);
        this.okHttpClient = builder.build();
        this.context = context;
    }

    public HWOkHttpClient(Context context, SSLSocketFactory sslSocketFactory, X509TrustManager x509TrustManager) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.sslSocketFactory(sslSocketFactory, x509TrustManager);
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(10, TimeUnit.SECONDS);
        builder.writeTimeout(10, TimeUnit.SECONDS);
        this.okHttpClient = builder.build();
        this.context = context;
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

    /**
     * @param builder
     * @param headers
     * @return
     */
    private Request.Builder generateHeaders(Request.Builder builder, HWOkHttpParams headers) {
        for (NameValuePair header : headers) {
            String name = header.getName();
            String value = header.getValue();
            builder.addHeader(name, value);
        }
        return builder;
    }

    public void request(int httpType, String url, HWOkHttpParams params, @Nullable HWOkHttpCallBack callBack) {
        // todo : Taja에서 파라메터는 header에 넣지 않으므로 수정하였습니다.
        request(httpType, url, params, new HWOkHttpParams(), callBack);
//        request(httpType, url, new HWOkHttpParams(),params , callBack);

    }

    @Nullable
    public void request(int httpType,
                        String url,
                        HWOkHttpParams params,
                        HWOkHttpParams headers,
                        @Nullable HWOkHttpCallBack callBack) {
        Uri uri = Uri.parse(url);
        request(httpType, uri, params, headers, callBack);
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
                        Uri uri,
                        HWOkHttpParams params,
                        HWOkHttpParams headers,
                        @Nullable HWOkHttpCallBack callBack) {
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
                requestBuilder.get();
                // todo : url 을 다시 재정의 하지 않아서 추가하였습니다.
                httpUrlBuilder = generateParams(params, httpUrlBuilder);
                httpUrl = httpUrlBuilder.build();
                requestBuilder.url(httpUrl);
                requestBuilder = generateHeaders(requestBuilder, headers);
                break;

            // ********
            //  POST
            // ********
            case HWHttpType.HTTP_TYPE_POST:
                body = generateParams(params);
//                requestBuilder.method("POST", RequestBody.create(null, new byte[0]));
                requestBuilder.post(body);
                requestBuilder = generateHeaders(requestBuilder, headers);
                break;

            // ********
            //  PATCH
            // ********
            case HWHttpType.HTTP_TYPE_PATCH:
                body = generateParams(params);
                requestBuilder.patch(body);
                requestBuilder = generateHeaders(requestBuilder, headers);
                break;

            default:
                break;
        }

        request = requestBuilder.build();

        okHttpClient
                .newCall(request)
                .enqueue(callBack);
    }

    /**
     * 파라미터 뽑아내기<br />
     * GET 용<br />
     *
     * @param params 파라미터 집합
     * @return OkHttpClient 용 HttpUrl
     */
    private HttpUrl.Builder generateParams(HWOkHttpParams params, HttpUrl.Builder httpUrlBuilder) {
        for (NameValuePair param : params) {
            String key = param.getName();
            String value = param.getValue();

            if (!TextUtils.isEmpty(value)) {
                if (key.length() > 0 &&
                        value.length() > 0) {
                    httpUrlBuilder.addEncodedQueryParameter(key, value);
                }
            }
        }

        return httpUrlBuilder;
    }

    /**
     * 파라미터 뽑아내기<br />
     * POST 용<br />
     *
     * @param params 파라미터 집합
     * @return OkHttpClient 용 RequestBody
     */
//    private RequestBody generateParams(HWOkHttpParams params, int type) {
//
//    }
//
//    private RequestBody generateParams(HWOkHttpParams params, int type, ) {
//        switch (type) {
//            case HWHttpType.HTTP_TYPE_GET:
//                break;
//            case HWHttpType.HTTP_TYPE_POST:
//                break;
//            case HWHttpType.HTTP_TYPE_PATCH:
//                break;
//        }
//    }
    private RequestBody generateParams(HWOkHttpParams params) {
        FormBody.Builder builder = new FormBody.Builder();
//        MultipartBody.Builder builder = new MultipartBody.Builder()
//                .setType(MultipartBody.FORM)
//                .addFormDataPart("", "");

        for (NameValuePair param : params) {
            String key = param.getName();
            String value = param.getValue();

            if (!TextUtils.isEmpty(value)) {
                builder.addEncoded(key, value);
            }
        }

        return builder.build();
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    public void setOkHttpClient(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }

}

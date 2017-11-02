package com.naver.temy123.baseproject.base.Http;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.naver.temy123.baseproject.base.Utils.HWException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

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
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(10, TimeUnit.SECONDS);
        builder.writeTimeout(10, TimeUnit.SECONDS);
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
        for (HWOkHttpNameValuePair header : headers) {
            String key = header.getKey();
            Object value = header.getValue();

            if (value instanceof String) {
                String newValue = (String) value;
                if (!TextUtils.isEmpty(newValue)) {
                    if (key.length() > 0 &&
                            newValue.length() > 0) {
                        builder.addHeader(key, newValue);
                    }
                }
            } else {
                Log.d("HWOkHttpClient", "-- Doesn't support this parameter types.");
            }
        }
        return builder;
    }

    public void request(int httpType, String url, HWOkHttpParams params, @Nullable HWOkHttpCallBack callBack) {
        // todo : Taja에서 파라메터는 header에 넣지 않으므로 수정하였습니다.
        request(httpType, url, params, new HWOkHttpParams(), callBack);
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
        // 파라미터가 없는 경우
        if (params.size() <= 0) {
            return new FormBody.Builder().build();
        }

        RequestBody body = null;
        int paramsType = params.getParamsType();

        // Type == Form
        if (paramsType == HWOkHttpParams.TYPE_FORM) {

            MultipartBody.Builder builder = new MultipartBody.Builder();
            builder.setType(MultipartBody.FORM);
            builder.addFormDataPart("", "");

            for (HWOkHttpNameValuePair param : params) {
                String key = param.getKey();
                Object value = param.getValue();

                // 단순 String 타입 추가
                if (value instanceof String) {
                    String newValue = (String) value;
                    if (!TextUtils.isEmpty(newValue)) {
                        builder.addFormDataPart(key, newValue);
                    }
                }
                // File 타입 추가
                else if (value instanceof File) {
                    File file = (File) value;
                    int extensionIndex = file.getName().lastIndexOf(".");
                    String fileExtension = file.getName().substring(extensionIndex + 1);

                    MediaType mediaType = MediaType.parse(HWOkHttpMimeType.getMimeType(fileExtension));
                    builder.addFormDataPart(key, file.getName()
                            , RequestBody.create(mediaType, file));
                }
            }

        } else if (paramsType == HWOkHttpParams.TYPE_JSON) {
            String jsonString = params.getPendingJson() != null ? params.getPendingJson().toString() : null;

            if (TextUtils.isEmpty(jsonString)) {
                JSONObject jsonObject = new JSONObject();
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
            }

            body = RequestBody.create(
                    MediaType.parse(HWOkHttpMimeType.MIME_APPLICATION_JSON),
                    jsonString);
        } else {
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

}

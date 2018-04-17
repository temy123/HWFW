package kr.timehub.testapplication;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.naver.temy123.baseproject.base.Http.ApiClient;
import com.naver.temy123.baseproject.base.Http.HWOkHttpClient;
import com.naver.temy123.baseproject.base.Http.HWOkHttpParams;
import com.naver.temy123.baseproject.base.Utils.HW_Params;
import com.naver.temy123.baseproject.base.Widgets.BaseActivity;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import cz.msebera.android.httpclient.Header;
import okhttp3.Call;
import okhttp3.Response;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ApiClient.newInstance(this);
        ApiClient.getInstance().setTimeout(10000);

        RequestParams params = new RequestParams();
        params.add("Test1", "AAA");
        params.add("Test2", "BBB");

        ApiClient.getInstance().post("http://httpbin.org/post", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                Log.d("MainActivity", "Success");
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Log.d("MainActivity", "Failure");
            }
        });

//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 1000:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
//                    String path = getRealPathFromURI(this, uri);
                    String path = null;
                    try {
                        path = getFilePath(this, uri);
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                    requestFile(path);
                }
                break;
        }
    }

    private void requestFile(String uri) {
        HWOkHttpClient.getInstance(this).request(
                HWOkHttpClient.HWHttpType.HTTP_TYPE_POST,
                100,
                "http://httpbin.org/post",
                new HWOkHttpParams()
//                        .add("key", "gdgdgd"),
                        .add("key", new File(uri), "file1.png")
                        .add("key", new File(uri), "file2.png")
                        .add("key", new File(uri), "file3.png")
                        .add("key", new File(uri), "file4.png")
                        .add("key", new File(uri), "file5.png")
                        .add("key", new File(uri), "file6.png")
                        .add("key", new File(uri), "file7.png")
                        .add("key", new File(uri), "file8.png"),
//                        .add("key", new File("/storage/emulated/0/Pictures/Screenshots/Screenshot_20170821-194130.png")),
                this
        );
    }

    @SuppressLint("NewApi")
    public static String getFilePath(Context context, Uri uri) throws URISyntaxException {
        String selection = null;
        String[] selectionArgs = null;
        // Uri is different in versions after KITKAT (Android 4.4), we need to
        if (Build.VERSION.SDK_INT >= 19 && DocumentsContract.isDocumentUri(context.getApplicationContext(), uri)) {
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                return Environment.getExternalStorageDirectory() + "/" + split[1];
            } else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                uri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
            } else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("image".equals(type)) {
                    uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                selection = "_id=?";
                selectionArgs = new String[]{
                        split[1]
                };
            }
        }
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = {
                    MediaStore.Images.Media.DATA
            };
            Cursor cursor = null;
            try {
                cursor = context.getContentResolver()
                        .query(uri, projection, selection, selectionArgs, null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, null, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    @Override
    public void onFailed(Intent intent, IOException e) {
        super.onFailed(intent, e);
    }

    @Override
    public void onUIResponsed(Call call, Intent intent, String body, String message, int status) {
        Toast.makeText(this, body, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNetworkResponsed(Call call, Intent intent, Response response, String body, int status) {

        int req = intent.getIntExtra(HW_Params.HW_NETWORK_EXTRA_REQ, -1);

        Log.d("MainActivity", body);
    }
}

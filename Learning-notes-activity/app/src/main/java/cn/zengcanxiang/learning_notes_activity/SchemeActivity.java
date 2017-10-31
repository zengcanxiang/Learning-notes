package cn.zengcanxiang.learning_notes_activity;


import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class SchemeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Uri uri = getIntent().getData();
        if (uri != null) {
            // 完整的url信息
            Log.e(TAG, "url: " + uri.toString());
            // scheme部分
            Log.e(TAG, "scheme: " +  uri.getScheme());
            // host部分
            Log.e(TAG, "host: " + uri.getHost());
            //port部分
            Log.e(TAG, "host: " + uri.getPort());
            // 访问路劲
            Log.e(TAG, "path: " + uri.getPath());
            List<String> pathSegments = uri.getPathSegments();
            // Query部分
            Log.e(TAG, "query: " + uri.getQuery());
            //获取指定参数值
            Log.e(TAG, "temp: " + uri.getQueryParameter("temp"));
        } else {
            finish();
        }
    }
}

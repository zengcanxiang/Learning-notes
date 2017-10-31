package cn.zengcanxiang.learning_notes_activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class BaseActivity extends Activity {

    public String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "-------- onCreate() --------");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "-------- onSaveInstanceState() --------");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "-------- onRestoreInstanceState() --------");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d(TAG, "-------- onNewIntent() --------");
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "-------- onRestart() --------");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "-------- onStart() --------");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "-------- onResume() --------");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "-------- onPause() --------");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "-------- onStop() --------");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "-------- onDestroy() --------");
    }

}

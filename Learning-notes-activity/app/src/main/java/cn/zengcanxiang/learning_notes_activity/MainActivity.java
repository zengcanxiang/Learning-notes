package cn.zengcanxiang.learning_notes_activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void toLifeCycleActivity(View view) {
        Intent i = new Intent(this, SimpleLifeCycleActivity.class);
        startActivity(i);
    }

    public void toLaunchModeActivity(View view) {
        Intent i = new Intent(this, LaunchModeActivity.class);
        startActivity(i);
    }

    public void toScheme(View view) {
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("zengcanxiang://learning:8888/schemeSimple?temp=从main传来的值"));
        startActivity(i);
    }
}

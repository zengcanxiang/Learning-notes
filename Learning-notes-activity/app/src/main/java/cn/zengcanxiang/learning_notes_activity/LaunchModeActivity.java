package cn.zengcanxiang.learning_notes_activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class LaunchModeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_launmode);
        Toast.makeText(this, "TaskId :" + getTaskId(), Toast.LENGTH_SHORT).show();
    }


    public void toLaunchModeActivity(View view) {
        Intent i = new Intent(this, LaunchModeActivity.class);
        startActivity(i);
    }

    public void toLaunchMode2Activity(View view) {
        Intent i = new Intent(this, LaunchMode2Activity.class);
        startActivity(i);
    }

    public void toLaunchMode3Activity(View view) {
        Intent i = new Intent(this, LaunchMode3Activity.class);
        startActivity(i);
    }

}

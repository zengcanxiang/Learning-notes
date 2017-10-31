package cn.zengcanxiang.learning_notes_activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class LaunchMode3Activity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_launmode_3);
        Toast.makeText(this, "TaskId :" + getTaskId(), Toast.LENGTH_SHORT).show();
    }

    public void toLaunchMode4Activity(View view) {
        Intent i = new Intent(this, LaunchMode4Activity.class);
        startActivity(i);
    }
}

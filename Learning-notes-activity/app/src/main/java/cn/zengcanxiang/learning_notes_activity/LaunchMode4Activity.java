package cn.zengcanxiang.learning_notes_activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

/**
 * 测试：如果以singleInstance启动模式，启动两个标明在同一个栈内的，他们的taskId是不是一致的
 * 结果:会出现他们分别存在一个Activity栈内，栈名一样，但是taskId会不一致
 */
public class LaunchMode4Activity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_launmode_4);
        Toast.makeText(this, "TaskId :" + getTaskId(), Toast.LENGTH_SHORT).show();
    }

    public void toLaunchMode3Activity(View view) {
        Intent i = new Intent(this, LaunchMode3Activity.class);
        startActivity(i);
    }
}

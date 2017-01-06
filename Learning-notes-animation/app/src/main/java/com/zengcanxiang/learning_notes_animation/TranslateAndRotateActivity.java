package com.zengcanxiang.learning_notes_animation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * 位移和旋转动画
 */
public class TranslateAndRotateActivity extends AppCompatActivity {

    View left, right;
    Button b;
    boolean isStart;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate_rotate);
        left = findViewById(R.id.left);
        right = findViewById(R.id.right);
        b = (Button) findViewById(R.id.start);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isStart) {
                    stopAnimator();
                }
                startAnimator();
            }
        });

    }

    private void stopAnimator() {

    }

    private void startAnimator() {
        //初始化位移动画



        //初始化旋转动画

        //监听位移动画执行完成，开启旋转动画

    }
}

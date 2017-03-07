package com.zengcanxiang.learning_notes_n.shortcuts;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.zengcanxiang.learning_notes_n.R;

public class Shortcuts2Activity extends AppCompatActivity {

    TextView shortcut;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shortcut);
        shortcut = (TextView) findViewById(R.id.shortcut);
        shortcut.setText("shortcut2:" + getIntent().getStringExtra("msg"));
    }
}

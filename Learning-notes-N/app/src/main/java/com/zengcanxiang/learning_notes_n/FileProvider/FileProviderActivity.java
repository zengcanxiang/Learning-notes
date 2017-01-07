package com.zengcanxiang.learning_notes_n.FileProvider;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.zengcanxiang.learning_notes_n.R;

/**
 * 文件夹访问规则
 */
public class FileProviderActivity extends AppCompatActivity {
    ImageView img;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file_provider);
        img = (ImageView) findViewById(R.id.img);
        //拍照保存到文件夹下面，并且显示出来

    }
}

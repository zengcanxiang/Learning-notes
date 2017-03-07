package com.zengcanxiang.learning_notes_n.shortcuts;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.zengcanxiang.learning_notes_n.Main;
import com.zengcanxiang.learning_notes_n.R;
import com.zengcanxiang.learning_notes_n.fileProvider.FileProviderActivity;

import shortbread.Shortcut;

@Shortcut(id = "Shortcuts3Activity", icon = R.mipmap.ic_launcher,
        shortLabel = "注解shortcut短名字-类", longLabel = "注解shortcut-长名字-类",
        activity = Main.class, action = "xx", disabledMessage = "禁止提示文字", backStack = {Main.class})
public class Shortcuts3Activity extends AppCompatActivity {

    //backStack 后退栈,决定按返回键展示的界面


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shortcut3);

        String action = getIntent().getAction();
        Log.d("TAG", action);
        if (action.equals("xx")) {
            Toast.makeText(this, "注解shortcut类调用", Toast.LENGTH_SHORT).show();
        }

    }

    @Shortcut(id = "Shortcuts3Method", icon = R.mipmap.ic_launcher,
            shortLabel = "注解shortcut短名字-方法", longLabel = "注解shortcut-短名字-方法",
            activity = Main.class, action = "yy", disabledMessage = "禁止提示文字", backStack = {FileProviderActivity.class})
    public void shortcut3() {
        Toast.makeText(this, "注解shortcut方法调用", Toast.LENGTH_SHORT).show();
    }
}

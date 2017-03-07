package com.zengcanxiang.learning_notes_n;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.zengcanxiang.learning_notes_n.fileProvider.FileProviderActivity;
import com.zengcanxiang.learning_notes_n.notify.NotifyActivity;
import com.zengcanxiang.learning_notes_n.shortcuts.Shortcuts2Activity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by zengcanxiang on 2017/2/27.
 */
public class Main extends Activity {
    Button fileProvider, notify;
    Button initShortcuts, addShortcuts, delShortcuts, updateShortcuts;
    ShortcutManager shortcutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        fileProvider = (Button) findViewById(R.id.fileProvider);
        notify = (Button) findViewById(R.id.notify);
        initShortcuts = (Button) findViewById(R.id.initShortcuts);
        addShortcuts = (Button) findViewById(R.id.addShortcuts);
        delShortcuts = (Button) findViewById(R.id.delShortcuts);
        updateShortcuts = (Button) findViewById(R.id.updateShortcuts);

        shortcutManager = getSystemService(ShortcutManager.class);
        fileProvider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main.this, FileProviderActivity.class));
            }
        });
        notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main.this, NotifyActivity.class));
            }
        });
        initShortcuts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initShortcut();
            }
        });

        addShortcuts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addShortcut();
            }
        });

        updateShortcuts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateShortcut();
            }
        });

        delShortcuts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delShortcut();
            }
        });
    }

    private void initShortcut() {
        //清空，只能清空动态配置的，静态配置的不能清空
        shortcutManager.removeAllDynamicShortcuts();
        List<ShortcutInfo> infos = new ArrayList<>();

        Intent intent = new Intent(this, Shortcuts2Activity.class);
        intent.setAction(Intent.ACTION_VIEW);
        intent.putExtra("msg", "动态Shortcut--初始化shortcut");

        ShortcutInfo info = new ShortcutInfo.Builder(this, "initShortId")
                .setShortLabel("动态Shortcut-初始化短名字")
                //如果长名字能够完全显示就会显示这个
                .setLongLabel("动态Shortcut-初始化长名字")
                .setIcon(Icon.createWithResource(Main.this, R.mipmap.ic_launcher))
                .setDisabledMessage(getString(R.string.shortcut_toast_msg))
                .setIntent(intent)
                .build();
        infos.add(info);

        shortcutManager.setDynamicShortcuts(infos);
    }

    private void addShortcut() {
        List<ShortcutInfo> infos = new ArrayList<>();

        Intent intent = new Intent(this, Shortcuts2Activity.class);
        intent.setAction(Intent.ACTION_VIEW);
        intent.putExtra("msg", "动态Shortcut--添加shortcut");

        ShortcutInfo info = new ShortcutInfo.Builder(this, "addShortId")
                .setShortLabel("动态Shortcut-添加短名字")
                //如果长名字能够完全显示就会显示这个
                .setLongLabel("动态Shortcut-添加长名字")
                .setIcon(Icon.createWithResource(Main.this, R.mipmap.ic_launcher))
                .setDisabledMessage(getString(R.string.shortcut_toast_msg))
                .setIntent(intent)
                .build();
        infos.add(info);
        //重复添加不会报错，但是也不会有效果，猜测是根据Id来过滤
        shortcutManager.addDynamicShortcuts(infos);
    }

    private void updateShortcut() {
        List<ShortcutInfo> dynamicShortcuts = shortcutManager.getDynamicShortcuts();
        List<ShortcutInfo> updateData = new ArrayList<>();
        for (int i = 0; i < dynamicShortcuts.size(); i++) {
            ShortcutInfo shortcutInfo = dynamicShortcuts.get(i);
            Intent intent = shortcutInfo.getIntent();
            intent.putExtra("msg", "动态Shortcut--修改shortcut");

            ShortcutInfo info = new ShortcutInfo.Builder(this, shortcutInfo.getId())
                    .setShortLabel("修改-动态Shortcut-短名字")
                    .setLongLabel("修改-动态Shortcut-长名字")
                    .setIcon(Icon.createWithResource(Main.this, R.mipmap.ic_launcher))
                    .setDisabledMessage(shortcutInfo.getDisabledMessage())
                    .setIntent(intent)
                    .build();
            updateData.add(info);
        }

        shortcutManager.updateShortcuts(updateData);
    }

    /**
     * 对动态的进行随机判断，true，则删除
     */
    private void delShortcut() {
        //静态的不能通过API来进行操作(ShortcutInfo的任何方法)
        List<ShortcutInfo> infos = shortcutManager.getDynamicShortcuts();
        List<String> delId = new ArrayList<>();
        for (int i = 0; i < infos.size(); i++) {
            ShortcutInfo info = infos.get(i);
            Random random = new Random();
            if (random.nextBoolean()) {
                String id = null;
                try {
                    id = info.getId();
                } catch (Exception e) {
                    continue;
                }
                Log.d("TAG", id + ":随机删除");
                delId.add(id);
                //禁用要删除的，防止将快捷方式单独拖出来的情况
                shortcutManager.disableShortcuts(Arrays.asList(id), "动态Shortcut-取消该shortcut");
            }
        }
        shortcutManager.removeDynamicShortcuts(delId);

    }

}

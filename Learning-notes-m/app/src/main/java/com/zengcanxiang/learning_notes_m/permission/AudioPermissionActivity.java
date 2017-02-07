package com.zengcanxiang.learning_notes_m.permission;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;

/**
 * 录音
 */
public class AudioPermissionActivity extends BasePermissionActivity {
    @Override
    public String getPermission() {
        return Manifest.permission.RECORD_AUDIO;
    }

    @Override
    public void showMessageOKCancel() {
        new AlertDialog.Builder(context)
                .setMessage("申请录音权限，否则不能工作")
                .setPositiveButton("现在就去", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //点击确认就跳转系统权限设置界面
                        toSetting();
                    }
                })
                .setNegativeButton("以后在说", null)
                .create()
                .show();
    }

    @Override
    public void success() {
        Intent intent = new Intent(context, AudioActivity.class);
        startActivity(intent);
    }
}

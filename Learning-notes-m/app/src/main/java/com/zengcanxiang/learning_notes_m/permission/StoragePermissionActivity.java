package com.zengcanxiang.learning_notes_m.permission;

import android.Manifest;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

/**
 * 文件读取权限组申请
 */
public class StoragePermissionActivity extends BasePermissionActivity {
    @Override
    public String getPermission() {
        return Manifest.permission.WRITE_EXTERNAL_STORAGE;
    }

    @Override
    public void showMessageOKCancel() {
        new AlertDialog.Builder(context)
                .setMessage("申请文件读取权限，否则不能工作")
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
        Toast.makeText(context, "文件读取权限申请成功", Toast.LENGTH_SHORT).show();
    }
}

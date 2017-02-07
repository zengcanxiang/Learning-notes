package com.zengcanxiang.learning_notes_m.permission;

import android.Manifest;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

/**
 * 传感器
 */
public class SensorsPermissionActivity extends BasePermissionActivity{
    @Override
    public String getPermission() {
        return Manifest.permission.BODY_SENSORS;
    }

    @Override
    public void showMessageOKCancel() {
        new AlertDialog.Builder(context)
                .setMessage("申请传感器使用权限，否则不能工作")
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
        Toast.makeText(context, "传感器权限申请成功", Toast.LENGTH_SHORT).show();
    }
}

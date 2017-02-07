package com.zengcanxiang.learning_notes_m.permission;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;

/**
 * 拨打电话
 */
public class CallPhonePermissionActivity extends BasePermissionActivity {
    @Override
    public String getPermission() {
        return Manifest.permission.CALL_PHONE;
    }

    @Override
    public void showMessageOKCancel() {
        new AlertDialog.Builder(context)
                .setMessage("申请拨打电话权限，否则不能工作")
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
        Intent intent = new Intent("android.intent.action.CALL", Uri.parse("tel:13973017904"));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}

package com.zengcanxiang.learning_notes_m.permission;

import android.Manifest;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.telephony.SmsManager;

import java.util.List;

/**
 * 短信
 */
public class SMSPermissionActivity extends BasePermissionActivity {
    @Override
    public String getPermission() {
        return Manifest.permission.READ_SMS;
    }

    @Override
    public void showMessageOKCancel() {
        new AlertDialog.Builder(context)
                .setMessage("申请短信权限，否则不能工作")
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
        //申请了读取短信，同时也会拥有发送短信的权限
        String value = "测试短信发送";
        String phoneNumber = "13973017904";
        PendingIntent sentIntent = PendingIntent.getBroadcast(context, 0, new Intent(), 0);
        SmsManager smsManager = SmsManager.getDefault();
        if (value.length() >= 70) {
            List<String> ms = smsManager.divideMessage(value);
            for (String str : ms) {
                smsManager.sendTextMessage(phoneNumber, null, str, sentIntent, null);
            }
        } else {
            smsManager.sendTextMessage(phoneNumber, null, value, sentIntent, null);
        }
    }
}

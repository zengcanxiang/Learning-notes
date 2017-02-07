package com.zengcanxiang.learning_notes_m.permission;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;

/**
 * 相机权限申请Activity
 */
public class CameraPermissionActivity extends BasePermissionActivity {

    @Override
    public String getPermission() {
        return Manifest.permission.CAMERA;
    }

    @Override
    public void showMessageOKCancel() {
        new AlertDialog.Builder(context)
                .setMessage("申请相机权限，否则不能工作")
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
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}

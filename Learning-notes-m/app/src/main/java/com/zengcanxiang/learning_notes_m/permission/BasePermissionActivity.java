package com.zengcanxiang.learning_notes_m.permission;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.zengcanxiang.learning_notes_m.R;

/**
 * 单个权限申请baseActivity
 */
public abstract class BasePermissionActivity extends AppCompatActivity {
    private String permission;
    protected Button btn;
    protected Activity context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission_check);
        permission = getPermission();
        context = this;
        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(context, new String[]{permission}, 1);
                } else {
                    success();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                success();
            } else {
                //当应用第一次申请前，这个函数返回false。在onRequestPermissionsResult回调函数中，如果用户拒绝，会返回true
                //当应用第一次申请之后，这个函数返回true。在onRequestPermissionsResult回调函数中，如果用户拒绝，会返回true
                //当用户选择不在提示，在申请前返回true。在onRequestPermissionsResult回调函数中，会返回false
                //当用户选择允许，返回false。
                //在不允许这个权限的情况下，一般最后一次提示一下去设置界面设置权限
                //也可以在申请前判断，如果是false(第一次或者是已经选择不在提示)，提示弹出对话框提示
                if (!ActivityCompat.shouldShowRequestPermissionRationale(context, permission)) {
                    showMessageOKCancel();
                }
            }
        }
    }

    /**
     * 跳转应用权限设置界面
     */
    public void toSetting(){
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.fromParts("package", getPackageName(), null));
        startActivity(intent);
    }
    public abstract String getPermission();

    public abstract void showMessageOKCancel();

    public abstract void success();
}

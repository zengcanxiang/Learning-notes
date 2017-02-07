package com.zengcanxiang.learning_notes_m.finger_mark;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.os.CancellationSignal;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zengcanxiang.learning_notes_m.R;

import static android.os.Build.VERSION_CODES.M;

/**
 * 指纹界面首页
 */
public class FingerMarkActivity extends AppCompatActivity {
    TextView text;
    Button btn, btn2;
    FingerprintManager manager;
    FingerprintManager.AuthenticationCallback callback;
    CancellationSignal cancellationSignal;
    int requestFingerMarkCode = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        text = (TextView) findViewById(R.id.text);
        btn = (Button) findViewById(R.id.btn);
        btn2 = (Button) findViewById(R.id.btn2);
        cancellationSignal = new CancellationSignal();
        callback = new FingerprintManager.AuthenticationCallback() {

            @Override
            public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
                text.setText("指纹识别状态:成功");
            }

            @Override
            public void onAuthenticationFailed() {
                text.setText("指纹识别状态:失败，该指纹不是已有指纹");
            }

            @Override
            public void onAuthenticationError(int errorCode, CharSequence errString) {
                text.setText("指纹识别状态:错误，" + errString);
            }

            @Override
            public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
                text.setText("指纹识别状态:失败，指纹没有识别出来");
            }
        };
        cancellationSignal.setOnCancelListener(new CancellationSignal.OnCancelListener() {
            @Override
            public void onCancel() {
                text.setText("指纹识别状态:指纹识别取消");
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkFingerMarkPermission();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancellationSignal.cancel();
            }
        });
    }

    private void checkFingerMarkPermission() {
        //指纹识别是普通权限，不需要申请
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) == PackageManager.PERMISSION_GRANTED) {
            fingerMark();
        } else {
            //申请权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.USE_FINGERPRINT}, requestFingerMarkCode);
        }
    }

    @SuppressWarnings("all")
    @TargetApi(Build.VERSION_CODES.M)
    private void fingerMark() {
        manager = (FingerprintManager) this.getSystemService(Context.FINGERPRINT_SERVICE);
        //判断API版本
        if (Build.VERSION.SDK_INT < M) {
            text.setText("指纹识别状态:不支持运行与APi23以下的版本");
            return;
        }
        //判断硬件是否支持
        if (!manager.isHardwareDetected()) {
            text.setText("指纹识别状态:手机不支持指纹识别");
            return;
        }
        //判断是否已经录入了指纹
        if (!manager.hasEnrolledFingerprints()) {
            text.setText("指纹识别状态:手机没有录入至少一个指纹");
            return;
        }
        text.setText("指纹识别状态:启动指纹识别");
        //启动指纹识别
        manager.authenticate(null, null, 0, callback, null);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //判断是指纹权限申请
        if (requestCode == requestFingerMarkCode) {
            //判断该权限申请结果
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //成功
                fingerMark();
            } else {
                Log.d("TAG", "拒绝了指纹权限申请");
                if (!shouldShowRequestPermissionRationale(Manifest.permission.USE_FINGERPRINT)) {
                    Log.d("TAG", "多次拒绝");
                }
            }
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        cancellationSignal.cancel();
    }
}

package com.zengcanxiang.learning_notes_n.fileProvider;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.zengcanxiang.learning_notes_n.R;

import java.io.File;

/**
 * 文件夹访问规则
 */
public class FileProviderActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView img;
    Button btn1, btn2, btn3;
    Activity mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file_provider);
        mContext = this;
        img = (ImageView) findViewById(R.id.img);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
    }

    private void toGallery(File file) {
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        Log.d("TAG", file.getAbsolutePath());
        //通过FileProvider创建一个content类型的Uri
        //此处的authority需要和manifest里面保持一致
        Uri imageUri = FileProvider.getUriForFile(this, getString(R.string.app_file_provider), file);
        Log.d("TAG", imageUri.toString());
        Intent intent = new Intent();
        //添加这一句表示对目标应用临时授权该Uri所代表的文件
        //点开FileProvider的getUriForFile()看看，注释就写着需要添加权限
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        //设置Action为拍照
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        //将拍取的照片保存到指定URI
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, 1);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
                Toast.makeText(mContext, "拒绝了访问存储内容权限", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //判断是那个回调
        if (requestCode == 2) {
            //判断结果是成功还是失败
            if (resultCode == RESULT_OK) {
                Log.d("TAG", "成功获取图片");
            } else {
                Log.d("TAG", "获取图片失败");
            }
        }
    }

    @Override
    public void onClick(View v) {
        File file = null;
        switch (v.getId()) {
            case R.id.btn1:
                file = new File(getFilesDir(), "/image/" + System.currentTimeMillis() + ".jpg");
                break;
            case R.id.btn2:
                file = new File(getCacheDir(), "/image/" + System.currentTimeMillis() + ".jpg");
                break;
            case R.id.btn3:
                file = new File(Environment.getExternalStorageDirectory(), "/image/" + System.currentTimeMillis() + ".jpg");
                break;
        }
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(mContext, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            toGallery(file);
        }
    }
}

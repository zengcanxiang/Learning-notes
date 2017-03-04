package com.zengcanxiang.learning_notes_messenger_client;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

/**
 * <br/>作者：zengcanxiang<br/>
 * 时间：2017/3/3
 */

public class MessengerActivity extends AppCompatActivity {

    private Messenger serviceMessenger, clientMessenger;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            serviceMessenger = new Messenger(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        clientMessenger = new Messenger(handler);
        View v = new View(this);
        v.postDelayed(new Runnable() {
            @Override
            public void run() {
                Bundle b = new Bundle();
                b.putString("clientMsg", "activity发送消息给service:“你好，我是activity发送的信息”");
                Message message = Message.obtain();
                message.what = 1;
                message.setData(b);
                try {
                    message.replyTo = clientMessenger;
                    serviceMessenger.send(message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }, 100);

    }

    @Override
    protected void onStart() {
        super.onStart();
        bindService();
    }

    private void bindService() {

        Intent intent = new Intent();
        intent.setAction("com.zengcanxiang.messenger.service");
        intent.addCategory(Intent.CATEGORY_DEFAULT);

        PackageManager pm = getPackageManager();
        //我们先通过一个隐式的Intent获取可能会被启动的Service的信息
        ResolveInfo info = pm.resolveService(intent, 0);

        if (info != null) {
            //如果ResolveInfo不为空，说明我们能通过上面隐式的Intent找到对应的Service
            //我们可以获取将要启动的Service的package信息以及类型
            String packageName = info.serviceInfo.packageName;
            String serviceName = info.serviceInfo.name;
            //然后我们需要将根据得到的Service的包名和类名，构建一个ComponentName
            //从而设置intent要启动的具体的组件信息，这样intent就从隐式变成了一个显式的intent
            //之所以大费周折将其从隐式转换为显式intent，是因为从Android 5.0 Lollipop开始，
            //Android不再支持通过通过隐式的intent启动Service，只能通过显式intent的方式启动Service
            //在Android 5.0 Lollipop之前的版本倒是可以通过隐式intent启动Service
            ComponentName componentName = new ComponentName(packageName, serviceName);
            intent.setComponent(componentName);
            try {
                bindService(intent, connection, BIND_AUTO_CREATE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(connection);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Log.d("MessengerActivity","收到信息");
            switch (msg.what) {
                case 0:
                    Log.d("MessengerActivity", msg.getData().getString("serviceMsg"));
                    break;
            }
        }
    };
}

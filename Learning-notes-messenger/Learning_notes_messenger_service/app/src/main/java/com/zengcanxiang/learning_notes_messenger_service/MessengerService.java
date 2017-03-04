package com.zengcanxiang.learning_notes_messenger_service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * <br/>作者：zengcanxiang<br/>
 * 时间：2017/3/3
 */
public class MessengerService extends Service {

    private Messenger messenger = new Messenger(new IncomingMessageHandler());


    private Messenger clientMessenger;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("messengerService", "service创建");
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("messengerService", "serviceBind");
        return messenger.getBinder();
    }


    private class IncomingMessageHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            Log.d("MessengerService", "收到信息");
            switch (msg.what) {
                case 1:
                    Log.d("MessengerService", msg.getData().getString("clientMsg"));
                    try {
                        clientMessenger = msg.replyTo;

                        Bundle bundle = new Bundle();
                        bundle.putString("serviceMsg", "service返回activity消息:“你好，我是service返回来的信息”");
                        Message fh = Message.obtain();
                        fh.what = 0;
                        fh.setData(bundle);

                        clientMessenger.send(fh);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }
}

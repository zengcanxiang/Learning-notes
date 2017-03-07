package com.zengcanxiang.learning_notes_n.notify;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.RemoteInput;
import android.util.Log;

import com.zengcanxiang.learning_notes_n.R;

public class SendMsgService extends Service {

    private Intent intent = new Intent("com.example.communication.RECEIVER");

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle resultsFromIntent = RemoteInput.getResultsFromIntent(intent);
        if (resultsFromIntent != null) {
            String reply = resultsFromIntent.getString(NotifyActivity.RESULT_KEY);
            replyTo(reply, intent.getExtras().getInt("notifyId"));
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void replyTo(final String reply, final int notifyId) {
        Log.d("TAG", reply + ",notifyId:" + notifyId);
        final NotificationManager nm = getSystemService(NotificationManager.class);
        final Handler handler = new Handler(getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                // 更新通知为“回复成功”
                Notification notification = new NotificationCompat.Builder(SendMsgService.this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentText("回复成功")
                        .build();
                nm.notify(notifyId, notification);

                intent.putExtra("value", reply);
                intent.putExtra("notifyId", notifyId);
                sendBroadcast(intent);
            }
        });
        // 最后将通知取消
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                nm.cancel(notifyId);
            }
        }, 1500);

    }
}

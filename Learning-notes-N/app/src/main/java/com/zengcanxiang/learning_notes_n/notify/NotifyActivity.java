package com.zengcanxiang.learning_notes_n.notify;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.RemoteInput;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zengcanxiang.learning_notes_n.R;

/**
 * 创建能够回复信息的notify
 */
public class NotifyActivity extends AppCompatActivity {

    TextView value;
    Button send, clean;
    //这个是直接回复动作配置的在service中拿到用户输入的值的key(如果存在多个可以回复的通知，那么获取值是会一个怎样的顺序？)
    public static String RESULT_KEY = "RESULT_KEY";
    public static int NOTIFICATION_ID = 0;
    UpdateValueReceiver msgReceiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notify);
        send = (Button) findViewById(R.id.send);
        clean = (Button) findViewById(R.id.clean);
        value = (TextView) findViewById(R.id.value);

        //动态注册广播接收器
        msgReceiver = new UpdateValueReceiver();
        msgReceiver.setUpdate(new UpdateValueReceiver.Update() {
            @Override
            public void update(String v,int notifyId) {
                value.setText(notifyId+"："+v);
            }
        });
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.communication.RECEIVER");
        registerReceiver(msgReceiver, intentFilter);


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotify();
            }
        });

        clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cleanNotify();
            }
        });
    }

    private void cleanNotify() {
        NotificationManager nm = getSystemService(NotificationManager.class);
        nm.cancelAll();
    }

    /**
     * 发送可以直接回复的Notify，实质上其实就是普通的通知，加上一个action。这个action就是创建的回复动作。
     */
    private void sendNotify() {
        //创建用来回复的input
        RemoteInput remoteInput = new RemoteInput.Builder(RESULT_KEY)
                .setLabel("回复这条消息")
                .build();

        //申明回复的动作
        Intent intent = new Intent(this, SendMsgService.class);

        //http://www.cnblogs.com/anrainie/articles/2383941.html
        //这样能够保证每次传递的intent为唯一的
        intent.setData(Uri.parse("custom://" + System.currentTimeMillis()));
        //如果打开的不是service，是activity，这样会导致每次打开的activity都会存在
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        Bundle b = new Bundle();
        b.putInt("notifyId", NOTIFICATION_ID);
        intent.putExtras(b);

        PendingIntent pi = PendingIntent.getService(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        //创建通知回复的动作
        NotificationCompat.Action act = new NotificationCompat.Action.Builder(R.mipmap.ic_launcher, "回复", pi)
                .addRemoteInput(remoteInput)
                .build();

        //创建消息
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(NOTIFICATION_ID + "请问是否需要信用卡?")
                .setContentText("您好，我是XX银行的XX经理， 请问你需要办理信用卡吗？")
                .setColor(Color.CYAN)
                .addAction(act)
                .setCategory(Notification.CATEGORY_MESSAGE);

        //发送通知
        NotificationManager nm = getSystemService(NotificationManager.class);
        nm.notify(NOTIFICATION_ID, builder.build());
        NOTIFICATION_ID++;
    }
}

package com.zengcanxiang.learning_notes_jobscheduler;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.lang.ref.WeakReference;

public class Main extends AppCompatActivity {
    private static String TAG = "main";

    private Button start;
    private JobScheduler scheduler;
    private ComponentName mServiceComponent;

    private IncomingMessageHandler mHandler;
    private int mJobId = 0;

    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
//            messengerIncoming = null;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
//            messengerIncoming = new Messenger(service);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        start = (Button) findViewById(R.id.start);
        mServiceComponent = new ComponentName(this, JobSchedulerService.class);
        mHandler = new IncomingMessageHandler(this);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
                JobInfo.Builder builder = new JobInfo.Builder(mJobId++, mServiceComponent);
                //设置job服务属性

                //设置执行网络条件，默认NETWORK_TYPE_NONE
                //JobInfo.NETWORK_TYPE_NONE             不管是否有网络这个任务都会被执行
                //JobInfo.NETWORK_TYPE_ANY              需要任意一种网络才使得任务可以执行
                //JobInfo.NETWORK_TYPE_UNMETERED        不是蜂窝网络(比如在WIFI连接时)时任务才会被执行。
                //JobInfo.NETWORK_TYPE_NOT_ROAMING      在不漫游的情况才执行
                builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);


                //设置设备重启之后，任务是否执行
                builder.setPersisted(true);

                //设置是否当设备在充电时这个任务才会被执,默认false
                builder.setRequiresCharging(false);

                //设置是否当用户没有在使用该设备且有一段时间没有使用时才会启动该任务,默认false
                builder.setRequiresDeviceIdle(false);

                //设置最大延迟时间(如果没有条件满足，到了规定时间也要执行)
//                builder.setOverrideDeadline(2000);

                //设置重复时间,每隔多少时间执行一次,与setMinimumLatency()、setOverrideDeadline()方法不能同时调用
                //如果使用startService()+setPeriodic()方式启动JobService，无法进入onStartJob()方法。
                builder.setPeriodic(300);

                int result = scheduler.schedule(builder.build());
                if (result < 0) {
                    Log.e(TAG, "启动job服务失败");
                } else {
                    Log.d(TAG, "启动job服务返回值:" + result + "，" + "jobId：" + (mJobId - 1));
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Start service and provide it a way to communicate with this class.
        Intent startServiceIntent = new Intent(this, JobSchedulerService.class);
        Messenger messengerIncoming = new Messenger(mHandler);
        startServiceIntent.putExtra("messenger", messengerIncoming);
//        startService(startServiceIntent);
        bindService(startServiceIntent, connection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
//        stopService(new Intent(this, JobSchedulerService.class));
        unbindService(connection);
        super.onStop();
    }

    private static class IncomingMessageHandler extends Handler {

        private WeakReference<Main> mActivity;

        IncomingMessageHandler(Main activity) {
            super();
            this.mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case JobSchedulerService.MSG_START:
                    Log.d(TAG, "收到信息：jobService启动,jobId：" + msg.obj);
                    break;
                case JobSchedulerService.MSG_STOP:
                    Log.d(TAG, "收到信息：jobService结束,jobId：" + msg.obj);
                    break;
            }
        }
    }

}

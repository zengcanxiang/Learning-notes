package com.zengcanxiang.learning_notes_jobscheduler;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import java.util.Random;

public class JobSchedulerService extends JobService {

    private String TAG = getClass().getSimpleName();

    private Messenger mActivityMessenger;
    /**
     * 启动消息
     */
    public static final int MSG_START = 2;
    /**
     * 结束消息
     */
    public static final int MSG_STOP = 3;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "jobService创建");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mActivityMessenger = intent.getParcelableExtra("messenger");
        return START_NOT_STICKY;
    }

    @Override
    public boolean onStartJob(final JobParameters params) {
        Log.d(TAG, "jobService启动");
        //发送服务启动的信息
        sendMessage(MSG_START, params.getJobId());
        //判断是否满足每个条件
        Random random = new Random();
        if (random.nextBoolean()) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //访问网络
                    Log.d(TAG, "执行网络操作");
                    sendMessage(MSG_STOP, params.getJobId());
                    jobFinished(params, false);
                }
            }, 500);
            return true;
        } else {
            sendMessage(MSG_STOP, params.getJobId());
            Log.d(TAG, "立即结束");
            jobFinished(params, false);
            //如果会立马结束，那么就返回false,如果开始执行任务，则返回true
            //结束前要调用jobFinished()方法
            return false;
        }
    }


    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG, "jobId为：" + params.getJobId() + "的job服务结束");
        return false;
    }

    /**
     * 通信方法
     *
     * @param type   要发送的消息类别
     * @param params 要发送的消息数据
     */
    private void sendMessage(int type, Object params) {
        if (mActivityMessenger == null) {
            Log.d(TAG, "Service is bound, not started. There's no callback to send a message to.");
            return;
        }
        Message m = Message.obtain();
        m.what = type;
        m.obj = params;
        try {
            mActivityMessenger.send(m);
        } catch (RemoteException e) {
            Log.e(TAG, "Error passing service object back to activity.");
        }
    }
}

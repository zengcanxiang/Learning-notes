package com.zengcanxiang.learning_notes_n.notify;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * 通知直接回复广播
 */
public class UpdateValueReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        String value = intent.getStringExtra("value");
        int notifyId=intent.getIntExtra("notifyId",0);
        update.update(value,notifyId);
    }

    public interface Update {
        void update(String value,int notifyId);
    }

    private Update update;

    public void setUpdate(Update update) {
        this.update = update;
    }
}

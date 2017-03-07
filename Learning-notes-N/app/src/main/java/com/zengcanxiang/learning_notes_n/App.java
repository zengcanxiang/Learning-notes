package com.zengcanxiang.learning_notes_n;

import android.app.Application;

import shortbread.Shortbread;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Shortbread.create(this);
    }
}

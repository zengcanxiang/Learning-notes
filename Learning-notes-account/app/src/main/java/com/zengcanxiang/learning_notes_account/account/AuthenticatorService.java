package com.zengcanxiang.learning_notes_account.account;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by zengcanxiang on 2017/2/16.
 */

public class AuthenticatorService extends Service {
    private Authenticator mAuthenticator;

    public AuthenticatorService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mAuthenticator = new Authenticator(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mAuthenticator.getIBinder();
    }
}

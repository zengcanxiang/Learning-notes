package com.zengcanxiang.learning_notes_account.account;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.zengcanxiang.learning_notes_account.AddAccountActivity;

/**
 * Created by zengcanxiang on 2017/2/16.
 */

public class Authenticator extends AbstractAccountAuthenticator {
    Context context;

    public Authenticator(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public Bundle editProperties(AccountAuthenticatorResponse response, String accountType) {
        Log.d("TAG", "调用:addAccount");
        return null;
    }

    @Override
    public Bundle addAccount(AccountAuthenticatorResponse response, String accountType, String authTokenType, String[] requiredFeatures, Bundle options) throws NetworkErrorException {
        Log.d("TAG", "调用:addAccount");
        Bundle ret = new Bundle();
        Intent intent = new Intent(context, AddAccountActivity.class);
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
        ret.putParcelable(AccountManager.KEY_INTENT, intent);
        return ret;
    }

    @Override
    public Bundle confirmCredentials(AccountAuthenticatorResponse response, Account account, Bundle options) throws NetworkErrorException {
        Log.d("TAG", "调用:addAccount");
        return null;
    }

    @Override
    public Bundle getAuthToken(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws NetworkErrorException {
        Log.d("TAG", "调用:addAccount");
        return null;
    }

    @Override
    public String getAuthTokenLabel(String authTokenType) {
        Log.d("TAG", "调用:addAccount");
        return null;
    }

    @Override
    public Bundle updateCredentials(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws NetworkErrorException {
        Log.d("TAG", "调用:addAccount");
        return null;
    }

    @Override
    public Bundle hasFeatures(AccountAuthenticatorResponse response, Account account, String[] features) throws NetworkErrorException {
        Log.d("TAG", "调用:addAccount");
        return null;
    }
}

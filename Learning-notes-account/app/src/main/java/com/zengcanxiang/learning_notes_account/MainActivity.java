package com.zengcanxiang.learning_notes_account;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button add, del, see, sync;
    AccountManager accountManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        accountManager = AccountManager.get(this);
        add = (Button) findViewById(R.id.account_add);
        del = (Button) findViewById(R.id.account_del);
        see = (Button) findViewById(R.id.account_see);
        sync = (Button) findViewById(R.id.account_sync);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SYNC_SETTINGS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SYNC_SETTINGS}, 1);
        } else {
            add.setOnClickListener(this);
            del.setOnClickListener(this);
            see.setOnClickListener(this);
            sync.setOnClickListener(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                add.setOnClickListener(this);
                del.setOnClickListener(this);
                see.setOnClickListener(this);
                sync.setOnClickListener(this);
            }
        }
    }


    @Override
    @SuppressWarnings("all")
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.account_add:
                Intent i = new Intent(MainActivity.this, AddAccountActivity.class);
                startActivity(i);
                break;
            case R.id.account_see:
                Account[] accounts = accountManager.getAccountsByType(getString(R.string.account_type));
                for (Account account : accounts) {
                    Log.d("TAG", "自身应用:账户名:" + account.name + "，密码：" + accountManager.getPassword(account) + " ，账户类型 " + account.type);
                }

                Account[] accounts1 = accountManager.getAccounts();
                for (Account account : accounts1) {
                    Log.d("TAG", "所有APP应用:账户名:" + account.name + "，密码：" + accountManager.getPassword(account) + " ，账户类型 " + account.type);
                }
                break;
            case R.id.account_del:

                break;
            case R.id.account_sync:
                Account account = new Account("同步帐号测试", getString(R.string.account_type));
                accountManager.addAccountExplicitly(account, "123456", null);
                ContentResolver.setIsSyncable(account, getString(R.string.account_provider), 1);
                ContentResolver.setSyncAutomatically(account, getString(R.string.account_provider), true);
                ContentResolver.addPeriodicSync(account, getString(R.string.account_provider), Bundle.EMPTY, 10);
                break;
        }
    }
}

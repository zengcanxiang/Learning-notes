package com.zengcanxiang.learning_notes_account;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class AddAccountActivity extends AccountAuthenticatorActivity {

    EditText name, pwd;
    Button btn;
    AccountManager accountManager;

    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_add_account);
        name = (EditText) findViewById(R.id.addAccountName);
        pwd = (EditText) findViewById(R.id.addAccountPwd);
        btn = (Button) findViewById(R.id.addAccountConfirm);

        accountManager = AccountManager.get(this);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Account account = new Account(name.getText().toString(), getString(R.string.account_type));
                boolean b = accountManager.addAccountExplicitly(account, pwd.getText().toString(), null);
                if (b) {
                    Bundle result = new Bundle();
                    result.putString(AccountManager.KEY_ACCOUNT_NAME, name.getText().toString());
                    result.putString(AccountManager.KEY_ACCOUNT_TYPE, getString(R.string.account_type));
                    setAccountAuthenticatorResult(result);
                }
            }
        });
    }
}

package com.telifoun.mqttchat.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;


import com.telifoun.mqttchat.core.clbs.CallbackListener;
import com.telifoun.mqttchat.gui.MqttChat;
import com.telifoun.mqttchat.tools.alert_dialog.AlertDialog;

public class LoginActivity extends AppCompatActivity {
    private Button signInBtn;
    private Button registerBtn;
    private EditText mUserId;
    private ProgressBar mProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mUserId=(EditText) findViewById(R.id.userId);
        mProgressBar=(ProgressBar) findViewById(R.id.login_progress);
        signInBtn =(Button) findViewById(R.id.sign_in_button);
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {
                    // TODO: handle exception
                }
                mqttchatLogin();
            }
        });

        registerBtn =(Button) findViewById(R.id.register_button);
        registerBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(i);
            }
        });
    }


    /**
     *
     */
    private void mqttchatLogin(){

        signInBtn.setEnabled(false);
        String userId = mUserId.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid email address.
        if (TextUtils.isEmpty(userId)) {
            mUserId.setError(getString(R.string.error_field_required));
            focusView = mUserId;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
            signInBtn.setEnabled(true);
        } else {

           mProgressBar.setVisibility(View.VISIBLE);

            MqttChat.getInstance().logIn(getApplication(), Integer.parseInt(userId), new CallbackListener() {
                @Override
                public void onSuccess(Object o) {
                    MqttChat.getInstance().Connect(new CallbackListener() {
                        @Override
                        public void onSuccess(Object o) {
                            mProgressBar.setVisibility(View.GONE);
                            signInBtn.setEnabled(true);
                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(i);
                            finish();
                        }

                        @Override
                        public void onError(String s) {
                            AlertDialog.YesOnly(LoginActivity.this,"Error",s);
                            mProgressBar.setVisibility(View.GONE);
                            signInBtn.setEnabled(true);
                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(i);
                            finish();
                        }

                    });

                }

                @Override
                public void onError(String s) {
                    signInBtn.setEnabled(true);
                    mUserId.setError(s);
                    mUserId.requestFocus();
                    mProgressBar.setVisibility(View.GONE);
                }
            });


        }

    }
}

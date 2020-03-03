package com.telifoun.mqttchat.app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.telifoun.mqttchat.core.Callback;
import com.telifoun.mqttchat.gui.Mqttchat;
import com.telifoun.mqttchat.sdk.RestResponse;
import com.telifoun.mqttchat.sdk.sdkCallback;
import com.telifoun.mqttchat.sdk.sdkUser;

public class RegisterActivity extends AppCompatActivity {

    private Button registerBtn;
    private EditText userId,name,surname;
    private RadioGroup radioSexGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        radioSexGroup=(RadioGroup)findViewById(R.id.sexgroup);
        userId=(EditText) findViewById(R.id.userId);
        name=(EditText) findViewById(R.id.name);
        surname=(EditText) findViewById(R.id.surname);
        registerBtn=(Button) findViewById(R.id.register_button);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkEmptyField()){
                    Toast.makeText(getApplicationContext(),"Empty field error!",Toast.LENGTH_SHORT).show();
                }else{
                    addUser();
                }
            }
        });


    }


    private boolean checkEmptyField(){
        return userId.getText().equals("") || name.getText().equals("") || surname.getText().equals("");
    }


    private void addUser(){

        RadioButton radioButton = (RadioButton) findViewById(radioSexGroup.getCheckedRadioButtonId());
        final int gender=radioButton.getText().equals("Male")?0:1;

        final ProgressDialog mProgressDialog = ProgressDialog.show(this, "Adding new User ",
                "Adding new User to MQTTCHAT ...", true);

        new Thread((new Runnable() {
            @Override
            public void run() {
                mProgressDialog.setMessage("Adding User ...");
                sdkUser user =new sdkUser();
                user.Set(Integer.parseInt(userId.getText().toString()),name.getText().toString(),surname.getText().toString(),gender);
                user.Add(new sdkCallback() {
                    @Override
                    public void OK(RestResponse restResponse) {
                        mProgressDialog.setMessage("User add success");
                        mProgressDialog.setMessage("Login to demo application with new user ...");
                        Mqttchat.getmInstance().logIn(getApplication(), Integer.parseInt(userId.getText().toString()), new Callback() {
                            @Override
                            public void OK(Object o) {
                                mProgressDialog.dismiss();
                                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(i);
                                finish();
                            }

                            @Override
                            public void KO(String s) {
                                mProgressDialog.dismiss();
                                Toast.makeText(getApplicationContext(),"Login Error !:"+s,Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    @Override
                    public void KO(RestResponse restResponse) {
                        mProgressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),"Add new user error !:"+restResponse.getError().getError(),Toast.LENGTH_LONG).show();
                    }
                });

            }
        })).start();
    }
}

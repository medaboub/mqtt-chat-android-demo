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


import com.telifoun.mqttchat.core.clbs.CallbackListener;
import com.telifoun.mqttchat.gui.MqttChat;
import com.telifoun.mqttchat.sdk.RestResponse;
import com.telifoun.mqttchat.sdk.sdkCallback;
import com.telifoun.mqttchat.sdk.sdkUser;
import com.telifoun.mqttchat.volley.toolbox.JsonObjectRequest;

import java.util.HashMap;

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

                HashMap<String,Object> user_data=new HashMap<String,Object>();
                user_data.put("userid", Integer.parseInt(userId.getText().toString()));
                user_data.put("name", name.getText().toString());
                user_data.put("surname",surname.getText().toString());
                user_data.put("gender",gender);

                (new restRequest()).request(JsonObjectRequest.Method.POST, Config.URL_USER_REGISTER, user_data, new CallbackListener() {
                    @Override
                    public void onSuccess(Object o) {
                        mProgressDialog.setMessage("User add success");
                        mProgressDialog.setMessage("Login to demo application with new user ...");
                        MqttChat.getInstance().logIn(getApplication(), Integer.parseInt(userId.getText().toString()), new CallbackListener() {
                            @Override
                            public void onSuccess(Object o) {
                                mProgressDialog.dismiss();
                                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(i);
                                finish();
                            }

                            @Override
                            public void onError(String s) {
                                mProgressDialog.dismiss();
                                Toast.makeText(getApplicationContext(),"Login Error !:"+s,Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    @Override
                    public void onError(String s) {
                        mProgressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),"Add new user error !:"+s,Toast.LENGTH_LONG).show();
                    }
                });
            }
        })).start();
    }
}

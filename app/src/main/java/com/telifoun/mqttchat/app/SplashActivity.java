package com.telifoun.mqttchat.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;


import com.telifoun.mqttchat.core.clbs.CallbackListener;
import com.telifoun.mqttchat.gui.MqttChat;
import com.telifoun.mqttchat.tools.alert_dialog.AlertDialog;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideSystemUI();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getWindow().getAttributes().layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        }


        setContentView(R.layout.activity_splash);
        start();


    }

    public  void hideSystemUI() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LOW_PROFILE
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_IMMERSIVE
        );
    }
    /**
     *
     */
    private void start(){

        if(MqttChat.getInstance().getLoggedUser().isLogged()){

            MqttChat.getInstance().Connect(new CallbackListener() {
                @Override
                public void onSuccess(Object o) {
                    Intent myIntent = new Intent(SplashActivity.this,MainActivity.class);
                    startActivity(myIntent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    finish();
                }

                @Override
                public void onError(String error) {
                    AlertDialog.YesOnly(SplashActivity.this,"error", error);
                    Intent myIntent = new Intent(SplashActivity.this,MainActivity.class);
                    startActivity(myIntent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    finish();
                }
            });



        }else {
            Intent myIntent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(myIntent);
           // overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        }
    }
}

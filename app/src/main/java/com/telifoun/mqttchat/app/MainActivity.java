package com.telifoun.mqttchat.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.telifoun.mqttchat.core.Callback;
import com.telifoun.mqttchat.core.MqttchatA;
import com.telifoun.mqttchat.gui.Mqttchat;


public class MainActivity extends AppCompatActivity {

    private Button lounchMqttchat;
    private Button logOut;
    private TextView welcom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_activity_main);

        welcom =(TextView) findViewById(R.id.welcom);
        welcom.setText("Welcom: "+Mqttchat.getmInstance().getLoggedUser().getName()+" "+
                Mqttchat.getmInstance().getLoggedUser().getSurName());


        lounchMqttchat=(Button) findViewById(R.id.lounchMqttchat);
        lounchMqttchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Mqttchat.getmInstance().lounchMqttChat(getApplicationContext(), new Callback() {
                    @Override
                    public void OK(Object o) {

                    }
                    @Override
                    public void KO(String s) {
                      Toast.makeText(getApplicationContext(),"MQTTCHAT lounch error: "+s, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        logOut=(Button) findViewById(R.id.logout);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Mqttchat.getmInstance().logOut(new Callback() {
                    @Override
                    public void OK(Object o) {
                      Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                      startActivity(i);
                      finish();
                    }

                    @Override
                    public void KO(String s) {
                        Toast.makeText(getApplicationContext(),"MQTTCHAT logOut error: "+s, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        checkUserIsLoged();
    }


    /**
     *
     */
    private void checkUserIsLoged(){
        if(!Mqttchat.getmInstance().getLoggedUser().isLogged()){
            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(i);
            finish();
        }
    }
}

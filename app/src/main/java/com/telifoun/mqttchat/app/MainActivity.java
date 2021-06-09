package com.telifoun.mqttchat.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.telifoun.mqttchat.core.Callback;
import com.telifoun.mqttchat.core.MqttchatA;
import com.telifoun.mqttchat.gui.Mqttchat;


public class MainActivity extends AppCompatActivity {

    private Button lounchFragment;
    private Button logOut;
    private TextView welcom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_activity_main);

        welcom =(TextView) findViewById(R.id.welcom);
        welcom.setText("Lounch Chat");


        lounchFragment=(Button) findViewById(R.id.lounchMqttchatFragment);
        lounchFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(MainActivity.this,FragmentActivity.class));
            }
        });


       final EditText toUserId=(EditText) findViewById(R.id.toUserId) ;
       Button chatWithUserId=(Button) findViewById(R.id.chatWithUserId);
       chatWithUserId.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View view) {
              try{
                   Mqttchat.getmInstance().startChatWith(MainActivity.this, Integer.parseInt(toUserId.getText().toString()), new Callback() {
                       @Override
                       public void OK(Object o) {
                       }

                       @Override
                       public void KO(String s) {
                           Toast.makeText(getApplicationContext(), "MQTTCHAT openChatWindowWith error: " + s, Toast.LENGTH_LONG).show();
                       }
                   });
               }catch(Exception ex){
                  Toast.makeText(getApplicationContext(),"MQTTCHAT lounch error: "+ex.getMessage(), Toast.LENGTH_LONG).show();
              }
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

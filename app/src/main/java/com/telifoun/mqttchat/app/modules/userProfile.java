package com.telifoun.mqttchat.app.modules;

import android.content.Context;
import android.content.Intent;


import com.telifoun.mqttchat.sdk.sdk;
import com.telifoun.mqttchat.app.MainActivity;
import com.telifoun.mqttchat.app.ProfileActivity;


public class userProfile extends com.telifoun.mqttchat.modules.moduleA {

    /**
     *
     * @param ctx
     * @param Name
     * @param Label
     */
    public userProfile(android.content.Context ctx, String Name, String Label) {
        super(ctx, Name, Label);
    }

    @Override
    public void init() {

    }

    @Override
    public void onMqttConnect() {

    }

    @Override
    public void onMqttLost() {

    }

    @Override
    public void onMqttReconnect() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onMessageActivityResult(int i, int i1, Intent intent) {

    }

    @Override
    public void onRequestPermissionsResult(int i, String[] strings, int[] ints) {

    }



    @Override
    public void moduleMenuItemCLicked() {
        /* lounch profile activity on menu item click */
        Intent i = new Intent(getMessageActivity(), ProfileActivity.class);
        getMessageActivity().startActivity(i);
    }
}

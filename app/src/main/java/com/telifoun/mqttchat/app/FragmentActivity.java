package com.telifoun.mqttchat.app;

import android.os.Bundle;

import com.telifoun.mqttchat.core.ui.PresenceActivityA;
import com.telifoun.mqttchat.gui.ui.fragments.mqttchat.MqttChatFragment;


public class FragmentActivity extends PresenceActivityA {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);
        getSupportFragmentManager().beginTransaction().replace(R.id.mqttchatFragment, new MqttChatFragment(), "mqttchat").commit();
    }
}

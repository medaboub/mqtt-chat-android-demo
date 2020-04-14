package com.telifoun.mqttchat.app;

import android.os.Bundle;

import com.telifoun.mqttchat.core.presence.PresenceActivityA;
import com.telifoun.mqttchat.gui.MqttchatFragment;

public class FragmentActivity extends PresenceActivityA {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);
        getSupportFragmentManager().beginTransaction().add(R.id.mqttchatFragment, MqttchatFragment.newInstance(), "mqttchat").commit();
    }
}

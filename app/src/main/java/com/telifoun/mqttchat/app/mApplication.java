package com.telifoun.mqttchat.app;

import android.app.Application;
import android.util.Log;


import com.telifoun.mqttchat.core.listeners.MqttChatListener;
import com.telifoun.mqttchat.core.messenger.Ack;
import com.telifoun.mqttchat.core.messenger.Message;

import com.telifoun.mqttchat.core.messenger.Notification;
import com.telifoun.mqttchat.gui.MqttChat;
import com.telifoun.mqttchat.plugins.picture_cam.listeners.pictureCamListener;
import com.telifoun.mqttchat.plugins.picture_cam.pictureCam;
import com.telifoun.mqttchat.plugins.picture_disk.listeners.pictureDiskListener;
import com.telifoun.mqttchat.plugins.picture_disk.pictureDisk;
import com.telifoun.mqttchat.plugins.pluginA;
import com.telifoun.mqttchat.plugins.records.listeners.recordsListener;
import com.telifoun.mqttchat.plugins.records.records;
import com.telifoun.mqttchat.plugins.stickers.Stickers;
import com.telifoun.mqttchat.plugins.stickers.listeners.stickersListener;
import com.telifoun.mqttchat.sdk.sdk;
import com.telifoun.mqttchat.app.modules.userProfile;
import com.telifoun.mqttchat.app.plugins.samplePlugin;

import androidx.multidex.MultiDexApplication;

public class mApplication  extends MultiDexApplication {
    public final String Tag="mqttchat_demo";
    @Override
    public void onCreate() {
        super.onCreate();

        // do the ACRA init here
        new MqttChat.getBuilder()
                .context(this.getApplicationContext())
                .appIcon(R.drawable.ic_mqttchat_logo_short)
                .domain("mqtt-chat.com")
                .appId("mqttchat-87226030")
                .appSecret("mqttchat-56vvfbvnpe0uvuid")
                .debugMode(true)
                .useFriends(false)
                .build();

           MqttChat.getInstance().addMqttchatListener(new MqttChatListener() {
            @Override
            public void onLoadComplete() {
              Log.i(Tag,"MQTT Chat load complete");
            }

            @Override
            public void onUserPresenceUpdate(int i, String s, boolean b, boolean b1, String s1) {
             Log.i(Tag,"User presence update : userId:"+i +"; new status:"+s+" ;voice capacity:"+b +" ;video capacity:"+b1+"; timestamp:"+s1);
            }

            @Override
            public void onSendMessage(int i, Message message) {
                Log.i(Tag,"Logged user send message to userId :"+i +"; message:"+message.toString());
            }

            @Override
            public void onAckMessage(int i, Ack ack, Message message) {
                Log.i(Tag,"Logged user received Ack from server for message sent to userId :"+i +"; ack :"+ack.toString()+"; message:"+message.toString());
            }

            @Override
            public void onRejectMessage(int i, String s, Message message) {
                Log.i(Tag,"Logged user has sent message to userId:"+i+"; message was rejected by server because of :"+s+"; and message content :"+message.toString());
            }

            @Override
            public void onIncomingMessage(int i, Message message) {
                Log.i(Tag,"Logged user received new message from userId:"+i+"; message content:"+message.toString());
            }

            @Override
            public void onReceiveNotification(Notification notification) {

            }

            @Override
            public void onUserTyping(int i, boolean b) {
                Log.i(Tag,"A user  userId:"+i+" is currently typing to logged user; is typing :"+b);
            }

            @Override
            public void onReadingMessage(int i, int i1, String s) {
                Log.i(Tag,"A user userId:"+i+"; is reading message id:"+i1+" ; reading date :"+s);
            }

            @Override
            public void onNotReadMessagesCountUpdate(int i) {
                Log.i(Tag,"Number of unread messages is changed for logged user, new number is :"+i);
            }

               @Override
            public void onError(String s) {
                   Log.e(Tag,"MQTTCHAT error message :"+s);
            }


        });


        /** add custom user profile module to MQTTCHAT APP **/
        userProfile mProfile=new userProfile(getApplicationContext(),"User Profile","Go to Profile");
        MqttChat.getInstance().getModules().add(mProfile);

        /** sample plugin to MQTTCHAT **/
        samplePlugin p =new samplePlugin(getApplicationContext(),
                "SamplePlugin",
                "Sample Plugin",
                true,
                R.drawable.ic_share_off,
                R.drawable.ic_share_on);
        p.setmViewType(pluginA.viewType.OTHERS);
        MqttChat.getInstance().getPlugins().add(p);

        MqttChat.getInstance().debugCore(true,"plugins size:"+p.getpIndex());

    }
}

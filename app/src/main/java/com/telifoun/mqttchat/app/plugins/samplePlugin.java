package com.telifoun.mqttchat.app.plugins;

import android.content.Context;
import android.content.Intent;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

;
import com.telifoun.mqttchat.core.clbs.CallbackListener;
import com.telifoun.mqttchat.core.messenger.Message;

import com.telifoun.mqttchat.app.R;
import com.telifoun.mqttchat.gui.MqttChat;

import org.w3c.dom.Text;

public class samplePlugin extends com.telifoun.mqttchat.plugins.pluginA {

    private Context mContext;
    private  LinearLayout view;
    private boolean canSend;
    /**
     *
     * @param ctx
     * @param Name
     * @param Label
     * @param useIcon
     * @param idleIconId
     * @param pressedIconId
     */
    public samplePlugin(android.content.Context ctx, String Name, String Label, boolean useIcon, int idleIconId, int pressedIconId) {
        super(ctx, Name, Label, useIcon, idleIconId, pressedIconId);
        mContext=ctx;
    }

    @Override
    public void init() {
      this.buildPluginView();
      this.canSend=false;
    }



    /**
     * function that build plugin view inside send message box
     */
    private void buildPluginView(){
        view = new LinearLayout(mContext);
        view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        view.setOrientation(LinearLayout.HORIZONTAL);
        TextView iv = new TextView(mContext);
        iv.setText("Plugin Gui here");
        view.addView(iv);
    }

    /**
     * function that render plugin item in messages listview
     * @param linearLayout
     * @param view
     * @param message
     * @return
     */
    @Override
    public View render(LinearLayout linearLayout, View view, Message message, int i) {
        if(view==null){
            LayoutInflater mInflater = (LayoutInflater) linearLayout.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = mInflater.inflate(R.layout.render_plugin, linearLayout, false);
        }
        LinearLayout pluginLayout=(LinearLayout) view.findViewById(R.id.pluginLayout);
        TextView txt =(TextView) view.findViewById(R.id.renderItemText) ;
        this.setAlignment(pluginLayout,message.getFrom());
        txt.setText(message.getMessage().toString());
        return view;
     }

    /**
     * function that add text to last message send in contacts listview
     * @param i
     * @param message
     * @return
     */
    @Override
    public SpannableStringBuilder preview(int i, Message message) {
        SpannableStringBuilder builder = new SpannableStringBuilder();
        if(message.getFrom()== MqttChat.getInstance().getLoggedUser().getUserId()){ //out message
            builder.append("Outbox plugin message ");
        }else{
            builder.append("Inbox plugin message ");
        }
        return builder;
    }

    @Override
    public View getRootView() {
        return view;
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
    public void onOpen() {
        this.canSend=true;
        getMessageActivity().requestMessageBtnOn(getName());
    }

    @Override
    public void onCLose() {
        this.canSend=false;
        getMessageActivity().requestMessageBtnOff(getName());
    }

    @Override
    public void onMessageActivityResult(int i, int i1, Intent intent) {

    }

    @Override
    public void onRequestPermissionsResult(int i, String[] strings, int[] ints) {

    }

    /**
     * function called when user select plugin (plugin oppened) and click in send button
     */
    @Override
    public void onSendMessageButtonCLicked() {
      if(!canSend){
          return;
      }

      Message message=this.addMessage(getMessageActivity().getToUserId(),"plugin text message to Send");
      sendMessage(message, new CallbackListener() {
          @Override
          public void onSuccess(Object o) {
              getMessageActivity().requestMessageBtnOff(getName());
          }

          @Override
          public void onError(String s) {
              getMessageActivity().requestMessageBtnOff(getName());
              onError(s);
          }
      });


    }
}

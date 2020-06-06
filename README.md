# mqttchat-android
MQTT CHAT Android is an android library that provides full chat functionality and can be integrated into any android application from API 14.
<br><br>Below some screenshots of the demo application hosted in this github repository.
<table><tr><td>
<a href="https://ibb.co/SVJrG5H"><img src="https://i.ibb.co/WBstjG7/2020-02-06-18-48-22.jpg" alt="MQTT CHAT screenshot" border="0" height=360></a>
  </td><td>
  <a href="https://ibb.co/jkTv0pL"><img src="https://i.ibb.co/N2s6D4L/2020-02-06-18-51-19.jpg" alt="MQTT CHAT screenshot" border="0" height=360></a>
  </td><td>
 <a href="https://ibb.co/LrV80DB"><img src="https://i.ibb.co/2SGyF0R/2020-02-06-18-53-14.jpg" alt="MQTT CHAT screenshot" border="0" height=360></a>
  </td></tr>
  </table>
  <table><tr><td>
<a href="https://ibb.co/mDcjRwj"><img src="https://i.ibb.co/TvgfwSf/2020-02-06-18-57-32.jpg" alt="MQTT CHAT screenshot" border="0" height=360></a>
  </td><td>
 <a href="https://ibb.co/27Xss9r"><img src="https://i.ibb.co/5Wqnn03/2020-02-06-19-01-12.jpg" alt="MQTT CHAT screenshot" border="0" height=360></a> 
  </td><td>
<a href="https://ibb.co/mHR1zPV"><img src="https://i.ibb.co/D71sKjH/2020-02-09-11-23-27.jpg" alt="2020-02-09-11-23-27" border="0" height=360></a>
  </td>
  </tr>
  </table>
  
  

## Library features
- Real-time Text Messaging
- One-on-one Chat
- Photos Sharing
- Capture photos from Webcam
- Stickers & Emojis
- Block Users
- Friends feature for dating or social networks websites
- Voice Notes
- Audio & Video Calls using WebRTC
- Typing Indicators
- Read Receipts
- Languages : ar, fr, en
- Storage Space : up to 2Go
- Data Retention : up to 30 days
- Offline notifications
- Offline audio and video calls
- Presence Callback URL

## Importing the Library
1- Add MQTTCHAT Artifactory repository to the list of Maven repositories in your top level <code>build.gradle</code> file:
```android
allprojects {
    repositories {       
        maven {
            url "http://mqttchat.telifoun.com:8081/artifactory/libs-release-local"
            credentials {
                username = "mqttchat"
                password = "telifoun"
            }
        }
    }
}
```

2-You can then simply add MQTT CHAT artifacts as a dependencies in the build.gradle file of your main project:
```android
dependencies {
    implementation 'com.telifoun.mqttchat:mqttchat-core:1.0.2'
    implementation 'com.telifoun.mqttchat:mqttchat-gui:1.0.2'
}
```

## Usage

1. In <code>Application</code> class of your Android App add the following code to init MQTT CHAT:  You can get your own App_Id and App_Secret after registration to MQTT CHAT website: <a href="https://mqttchat.telifoun.com" target="_blank">https://mqttchat.telifoun.com</a>.

```android
 new Mqttchat.getBuilder()
             .context(this.getApplicationContext())
             .appName(getApplicationContext().getResources().getString(R.string.app_name))
             .appIcon(R.drawable.ic_launcher)
             .domain("your_domain.com")
             .appId("App_Id")
             .appSecret("App_Secret")
             .showBackButton(true)
             .debugMode(false)
             .useFriends(false)
             .build();
```
 
2. In LoginActivity After user login success, You can connect user to MQTT CHAT using <code>login</code> function.
```android
  Mqttchat.getmInstance().logIn(getApplication(), userId, new Callback() {
   @Override
   public void OK(Object o) {
   }
   @Override
   public void KO(String s) {
   }
   });
```
3- Once the user is connected to MQTT CHAT you can launch chat interface whenever you want using this code :
```android
  Mqttchat.getmInstance().lounchMqttChat(getApplicationContext(), new Callback() {
   @Override
   public void OK(Object o) {
   }
   @Override
   public void KO(String s) {   
   }
});
```
4- To log out a user :
```android
 Mqttchat.getmInstance().logOut(new Callback() {
  @Override
   public void OK(Object o) {
   }
   @Override
   public void KO(String s) {   
   }
});
```


## Capture events
You can capture all MQTT CHAT events using a <code>mqttchatListener</code> :
```android
 Mqttchat.getmInstance().addMqttchatListener(new mqttchatListener() {
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
            public void onError(int i, String s) {
                Log.e(Tag,"MQTTCHAT error code :"+i+";message :"+s);
            }
        });

```
## Add, modify and delete users to MQTT CHAT
- Adding new user to MQTT CHAT (usually done once the user is successfully registered to your application).
```android
  sdkUser user =new sdkUser();
  user.Set(1,"user name","user surname",0);
  user.Add(new sdkCallback() {
   @Override
   public void OK(RestResponse restResponse) {
      Log.i(Tag,restResponse.getData());
   }
   @Override
   public void KO(RestResponse restResponse) {
     Log.e(Tag,restResponse.getError().getError());
   }
});
```
- Editing added user Informations.
```android
  sdkUser user =new sdkUser();
  user.setUserId(1);
  user.setAvatar_link("avatar link");  
  user.Update(new sdkCallback() {
  @Override
   public void OK(RestResponse restResponse) {
      Log.i(Tag,restResponse.getData());
   }
   @Override
   public void KO(RestResponse restResponse) {
     Log.e(Tag,restResponse.getError().getError());
   } 
});
```
- Removing added user .
```android
  sdkUser user =new sdkUser();
  user.setUserId(1); 
  user.Remove(new sdkCallback() {
   @Override
   public void OK(RestResponse restResponse) {
      Log.i(Tag,restResponse.getData());
   }
   @Override
   public void KO(RestResponse restResponse) {
     Log.e(Tag,restResponse.getError().getError());
   }  
});
```
- Getting user infos
```android
  sdkUser user =new sdkUser();
  user.setUserId(1); 
  user.Get(new sdkCallback() {
   @Override
   public void OK(RestResponse restResponse) {
      Log.i(Tag,restResponse.getData());
   }
   @Override
   public void KO(RestResponse restResponse) {
     Log.e(Tag,restResponse.getError().getError());
   }
});
```
## Use Friends feature
If you enable this option (by setting the <code>useFriends</code> parameter to true) in the MQTT CHAT initialisation, all MQTT CHAT features will be limited to the friends list. That is, a user can only exchange messages with a user who belongs to his friend list. In case this option is disabled (by setting the <code>useFriends</code> to false), a user can browse the list of all users and can chat with any one.

To add a contact to friends list :
```android
sdkUser user =new sdkUser();
  user.setUserId(1); 
  user.friendWith(2,new sdkCallback() {
   @Override
   public void OK(RestResponse restResponse) {
      Log.i(Tag,restResponse.getData());
   }
   @Override
   public void KO(RestResponse restResponse) {
     Log.e(Tag,restResponse.getError().getError());
   }
});
```
To remove a contact from friends list :
```android
sdkUser user =new sdkUser();
  user.setUserId(1); 
  user.notFriendWith(2,new sdkCallback() {
   @Override
   public void OK(RestResponse restResponse) {
      Log.i(Tag,restResponse.getData());
   }
   @Override
   public void KO(RestResponse restResponse) {
     Log.e(Tag,restResponse.getError().getError());
   }
});
```
To check if a user is friend with a contact
```android
sdkUser user =new sdkUser();
  user.setUserId(1); 
  user.isFriendWith(2,new sdkCallback() {
   @Override
   public void OK(RestResponse restResponse) {
      Log.i(Tag,restResponse.getData());
   }
   @Override
   public void KO(RestResponse restResponse) {
     Log.e(Tag,restResponse.getError().getError());
   }
});
```

__For more informations please read the complete <a href="https://mqttchat.telifoun.com/doc">MQTT CHAT documentation</a>.__


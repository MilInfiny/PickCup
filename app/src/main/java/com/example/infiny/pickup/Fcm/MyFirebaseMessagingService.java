package com.example.infiny.pickup.Fcm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;

import com.example.infiny.pickup.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by infiny on 10/5/17.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService{
    Long temp;
    int id = 0;
    String photo;

    private static final String TAG = "PushRecevier";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        JSONObject obj = new JSONObject(remoteMessage.getData());

//        if (remoteMessage.getData() instanceof ArrayMap) {
//            data = (ArrayMap<String, String>) remoteMessage.getData();
//        }
//        if (remoteMessage.getData() instanceof HashMap) {
//            data1 = (HashMap<String, String>) remoteMessage.getData();
//        }
//        notification = remoteMessage.getNotification();

        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            try {
                String flag=obj.getString("flag");
                flag = flag.replace("\\", "");
                switch (flag) {
                    case "orderCompleted":
                        showSmallNotificationsMessages();
                        break;
                    default:
                        Log.d(TAG, "Default Mode set");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            // Handle message within 10 seconds
//            handleNow();


        }
        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

    }
   public void showSmallNotificationsMessages(){
       NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
       mBuilder.setContentTitle("Pick Cup");
       mBuilder.setContentText("you are order is ");
       mBuilder.setPriority(Notification.PRIORITY_HIGH);
       mBuilder.setVisibility(Notification.VISIBILITY_PUBLIC);
       mBuilder.setSmallIcon(R.drawable.cofeecup);
       mBuilder.setDefaults(Notification.DEFAULT_ALL);
       NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
       mNotificationManager.notify(1, mBuilder.build());
   }


}

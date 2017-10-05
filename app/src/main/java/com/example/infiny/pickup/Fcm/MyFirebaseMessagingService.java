package com.example.infiny.pickup.Fcm;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

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
        super.onMessageReceived(remoteMessage);

        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.toString());
            JSONObject obj = new JSONObject(remoteMessage.getData());
            try {
                String body = obj.getString("body");
                body = body.replaceAll("\\\\", "");
                String flag = obj.getString("flag");


            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (remoteMessage.getNotification() != null) {
                Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            }
        }

    }


}

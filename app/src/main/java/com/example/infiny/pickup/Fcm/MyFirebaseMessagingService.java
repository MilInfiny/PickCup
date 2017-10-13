package com.example.infiny.pickup.Fcm;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;

import com.example.infiny.pickup.Activity.OrderDetailsActivity;
import com.example.infiny.pickup.Activity.SplashActivity;
import com.example.infiny.pickup.Model.Order_History_Data;
import com.example.infiny.pickup.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by infiny on 10/5/17.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService{
    Long temp;
    int id = 0;
    String photo;

    String  message;

    private static final String TAG = "PushRecevier";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getNotification().getBody());
        JSONObject obj = new JSONObject(remoteMessage.getData());

            message=remoteMessage.getNotification().getBody();


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
                String orderId=obj.getString("data");
                flag = flag.replace("\\", "");
                switch (flag) {
                    case "orderCompleted":
                        showSmallNotificationsMessages(orderId);
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
   public void showSmallNotificationsMessages(String orderId){
           Notification notification=new Notification();
           NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
           mBuilder.setContentTitle("Pick Cup");
           mBuilder.setContentText(message);
           mBuilder.setPriority(Notification.PRIORITY_HIGH);
           mBuilder.setVisibility(Notification.VISIBILITY_PUBLIC);
           mBuilder.setSmallIcon(R.drawable.cofeecup);
           mBuilder.setAutoCancel(true);
           mBuilder.setDefaults(Notification.DEFAULT_ALL);
           Intent resultIntent = new Intent(this, OrderDetailsActivity.class);

           resultIntent.putExtra("orderId", orderId);
       resultIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
       notification.flags |= Notification.FLAG_AUTO_CANCEL;


       PendingIntent notificationIntent= PendingIntent.getActivity(getApplicationContext(),0, resultIntent,PendingIntent.FLAG_UPDATE_CURRENT);

           mBuilder.setContentIntent(notificationIntent);
           NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
           //  notificationID allows you to update the notification later on.
           mNotificationManager.notify(001, mBuilder.build());



     /*  NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

       //  notificationID allows you to update the notification later on.



       Intent notificationIntent = new Intent(getApplicationContext(), Order_History_Data.class);
       notificationIntent.putExtra("orderId",orderId);

/*//**add this line**
       notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

/*//**edit this line to put requestID as requestCode**
       PendingIntent contentIntent = PendingIntent.getActivity(this, 0,notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
       mBuilder.setContentIntent(contentIntent);
       mNotificationManager.notify(001, mBuilder.build());*/
   }
    public static boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }

        return isInBackground;
    }


}

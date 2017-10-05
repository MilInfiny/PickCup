package com.example.infiny.pickup.Fcm;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.infiny.pickup.R;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by infiny on 10/5/17.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = "FcmIntentService";
    SharedPreferences sharedpreferences;
    @Override
    public void onTokenRefresh() {
       String FcmToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "PushRecevier: " + FcmToken);

        sharedpreferences = getSharedPreferences("Messaging", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("FcmId",FcmToken);
        editor.commit();

    }

}

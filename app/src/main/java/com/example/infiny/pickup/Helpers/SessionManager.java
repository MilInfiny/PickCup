package com.example.infiny.pickup.Helpers;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by infiny on 9/4/17.
 */

public class SessionManager {
    private static final String KEY_LOGIN_DATA = "loginData";
    private String TAG = SessionManager.class.getSimpleName();
    private static final String IS_LOGIN = "IsLoggedIn";

    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "LoginData";


    // Constructor
    public SessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }


    public void clear() {
        editor.clear();
        editor.commit();

    }


}
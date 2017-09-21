package com.example.infiny.pickup.Helpers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.infiny.pickup.Activity.LoginActivity;

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

    //data
    public static final String name = "name";
    public static final String email = "email";
    public static final String surname = "surname";
    public static final String token = "token";


    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    public static final String PREF_NAME = "LoginData";


    // Constructor
    public SessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }
    public void createLoginSession(String name, String email,  String surname,String token) {

        editor.putBoolean(IS_LOGIN, true);

        editor.putString(this.name, name);
//        editor.putString(name, fname);
        editor.putString(this.email, email);
        editor.putString(this.surname, surname);
        editor.putString(this.token, token);


        editor.commit();
    }


    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }


    public void clear() {
        editor.clear();
        editor.commit();
    }

}
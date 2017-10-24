package com.example.infiny.pickup.Helpers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.infiny.pickup.Activity.LoginActivity;
import com.example.infiny.pickup.Model.ItemData;

/**
 * Created by infiny on 9/4/17.
 */

public class SessionManager {
    private static final String KEY_LOGIN_DATA = "loginData";
    private String TAG = SessionManager.class.getSimpleName();
    private static final String IS_LOGIN = "IsLoggedIn";
    private static final String ADD_TO_CART = "ADDTOCART";
    private static final String CURR_LAT = "latitude";
    private static final String CURR_LONG = "longitude";

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
    public static final String dob = "dob";
    public static final String postalcode = "postalcode";
    public static final String city = "city";
    public  static  final String address="address";
    public  static  final String image="image";


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
    public void createLoginSession(String name, String email,  String surname,String token,String dob,String Postalcode,String city,String address,String image) {

        editor.putBoolean(IS_LOGIN, true);

        editor.putString(this.name, name);
//        editor.putString(name, fname);
        editor.putString(this.email, email);
        editor.putString(this.surname, surname);
        editor.putString(this.token, token);
        editor.putString(this.dob, dob);
        editor.putString(this.postalcode, Postalcode);
        editor.putString(this.city, city);
        editor.putString(this.address,address);
        editor.putString(this.image,image);


        editor.commit();
    }




    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }


    public void clear() {
        editor.clear();
        editor.commit();
    }
    public void storeLocation(double lat, double longi) {
        editor.putString(CURR_LAT, String.valueOf(lat));
        editor.putString(CURR_LONG, String.valueOf(longi));
        editor.commit();

    }

}
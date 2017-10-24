package com.example.infiny.pickup.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.infiny.pickup.Helpers.GPSTracker;
import com.example.infiny.pickup.Helpers.SessionManager;
import com.example.infiny.pickup.R;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SplashActivity extends AppCompatActivity {


    private Context mContext;
    private final int SPLASH_DISPLAY_LENGTH = 3000;
    private SessionManager sessionManager;
    GPSTracker gps;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        mContext = this;
        sessionManager=new SessionManager(mContext);

    }


    @Override
    protected void onResume() {
        super.onResume();

        gps = new GPSTracker(SplashActivity.this);
        if (gps.canGetLocation()) {
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
            sessionManager.storeLocation(latitude, longitude);
            if(checkPermission()){
                nextScreen();
            }else {
                PermissionListener permissionlistener = new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
//                Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show();
                        nextScreen();
                    }

                    @Override
                    public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                        Toast.makeText(mContext, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
                    }


                };

                new TedPermission(this)
                        .setPermissionListener(permissionlistener)
                        .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                        .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE
                                , Manifest.permission.ACCESS_FINE_LOCATION
                                ,Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_NETWORK_STATE,
                                Manifest.permission.CHANGE_NETWORK_STATE,
                                Manifest.permission.CAMERA,
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.READ_EXTERNAL_STORAGE)
                        .check();
            }

        } else {
            gps.showSettingsAlert();
        }



    }

    public void nextScreen() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(sessionManager.isLoggedIn()){
                    startActivity(new Intent(mContext, MainActivity.class));
                    finish();
                }else{
                    startActivity(new Intent(mContext, LoginActivity.class));
                    finish();
                }
            }
        }, SPLASH_DISPLAY_LENGTH);

    }

    private boolean checkPermission(){
        int resultLoc = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION);
        int resultStorage = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (resultLoc == PackageManager.PERMISSION_GRANTED && resultStorage == PackageManager.PERMISSION_GRANTED){
            return true;
        } else {
            return false;
        }
    }
}


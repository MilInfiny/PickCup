package com.example.infiny.pickup;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by infiny on 9/5/17.
 */

public class PickCup extends MultiDexApplication {
    private static PickCup mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
//        Fabric.with(this, new Crashlytics());
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/opensanslight.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
    public static synchronized PickCup getInstance() {
        return mInstance;
    }

}

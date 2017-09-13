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
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
        MultiDex.install(this);
    }
}
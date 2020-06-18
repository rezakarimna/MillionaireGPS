package com.MillionaireGPS;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class MyFont extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder().setDefaultFontPath("fonts/iran_dn.ttf")
        .setFontAttrId(R.attr.fontPath).build());
    }
}

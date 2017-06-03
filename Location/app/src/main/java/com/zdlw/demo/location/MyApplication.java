package com.zdlw.demo.location;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Handler;
import android.view.ViewConfiguration;

import com.baidu.mapapi.SDKInitializer;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2017/4/9.
 */
public class MyApplication extends Application {
    private static Context context;
    private static Handler handler;
    private static int mainThreadid;
//    private static Typeface typeface;

    @Override
    public void onCreate() {
        super.onCreate();
        SDKInitializer.initialize(getApplicationContext());
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        }
        catch (Exception ex) {
            // Ignore
        }
        super.onCreate();
        context=getApplicationContext();
        handler=new Handler();
        mainThreadid=android.os.Process.myTid();//主线程ID
    }



    public static Context getContext() {
        return context;
    }

    public static Handler getHandler() {
        return handler;
    }

    public static int getMainThreadid() {
        return mainThreadid;
    }
//    public static Typeface getTypeface(){
//        return typeface;
//    }
}

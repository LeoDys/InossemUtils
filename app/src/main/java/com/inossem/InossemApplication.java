package com.inossem;

import android.app.Application;

import com.inossem_library.tips.logcat.util.LogUtils;

/**
 * @author Leo E-mail:changwen.sun@inossem.com 2019/7/23 15:02
 * @version 1.0.0
 * @since JDK-1.8
 */

public class InossemApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.initLogger();
    }
}

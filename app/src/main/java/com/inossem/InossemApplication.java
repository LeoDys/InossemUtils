package com.inossem;

import android.app.Application;

import com.inossem.other.greendao.DaoManager;
import com.inossem_library.crash_exception.CrashExceptionUtils;
import com.inossem_library.tips.logcat.util.LogUtils;
import com.inossem_library.tips.toast.util.ToastUtils;
import com.tencent.mmkv.MMKV;
import com.zxy.tiny.Tiny;

import me.jessyan.autosize.AutoSizeConfig;
import me.jessyan.autosize.unit.Subunits;

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
        initAutoSize();
        initTiny();
        initToast();
        MMKV.initialize(getApplicationContext());
        // 初始化异常捕获
        CrashExceptionUtils.initCrashException(this, null);
        DaoManager.getInstance().init(this);
    }

    private void initToast() {
        ToastUtils.init(this);
    }

    /**
     * 初始化压缩框架tiny
     */
    private void initTiny() {
        Tiny.getInstance().init(this);
    }

    /**
     * 初始化屏幕适配框架
     */
    private void initAutoSize() {
        AutoSizeConfig.getInstance().getUnitsManager()
                .setSupportDP(false)
                .setSupportSP(false)
                .setSupportSubunits(Subunits.PT);
    }
}

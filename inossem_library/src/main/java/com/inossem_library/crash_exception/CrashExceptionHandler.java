package com.inossem_library.crash_exception;

import android.content.Context;
import android.os.Looper;

import com.inossem_library.exception.InossemException;
import com.inossem_library.exception.constant.ExceptionEnum;
import com.inossem_library.writelogs.util.LogWriteUtils;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread.UncaughtExceptionHandler;

import androidx.annotation.NonNull;

/**
 * @author 郭晓龙
 * @time on 2020-12-25 10:08:51
 * @email xiaolong.guo@inossem.com
 * @describe 异常捕获控制器
 */
public class CrashExceptionHandler implements UncaughtExceptionHandler {
    /**
     * 保存异常信息
     */
    private StringBuffer sb = new StringBuffer();
    private UncaughtExceptionHandler uncaughtExceptionHandler;
    private static CrashExceptionHandler carshExceptionHandler;
    private CrashExceptionUtils.CrashExceptionResponse mCrashExceptionResponse;
    private Context mContext;

    /**
     * 获取CrashExceptionHandler对象
     *
     * @return CrashExceptionHandler对象
     */
    public static synchronized CrashExceptionHandler getInstance() {
        if (carshExceptionHandler == null) {
            carshExceptionHandler = new CrashExceptionHandler();
        }
        return carshExceptionHandler;
    }

    /**
     * 初始化
     *
     * @param context                上下文
     * @param crashExceptionResponse 捕获异常响应监听
     */
    public void init(@NonNull Context context, CrashExceptionUtils.CrashExceptionResponse crashExceptionResponse) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context 不能为空！");
        }
        // 获取默认异常捕获
        uncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        this.mContext = context;
        this.mCrashExceptionResponse = crashExceptionResponse;
    }

    /**
     * 未捕获的异常
     *
     * @param thread 线程对象
     * @param ex     异常对象
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        try {
            // 处理捕获的异常
            crashException(mContext, ex);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            uncaughtExceptionHandler.uncaughtException(thread, ex);
        }
        if (mCrashExceptionResponse != null) {
            // 回调函数
            new Thread() {
                @Override
                public void run() {
                    Looper.prepare();
                    mCrashExceptionResponse.onResponse(mContext);
                    Looper.loop();
                }
            }.start();
        }
    }

    /**
     * 捕获异常
     *
     * @param context 上下文
     * @param ex      异常对象
     */
    private void crashException(Context context, Throwable ex) {
        // 获取APP版本，查询设备基本信息，获取崩溃信息，保存文件
        collectCrashInfo(context, ex);
        Logger.e(sb.toString()); // 打印日志
        LogWriteUtils.logCatchExceptionE(context, sb.toString(), ex);//保存到文件
    }

    /**
     * 收集异常信息
     *
     * @param context 上下文
     * @param ex      异常对象
     */
    private void collectCrashInfo(Context context, Throwable ex) {
        // 正常打印异常
        ex.printStackTrace();
        sb.append(exceptionToString(ex));

    }

    /**
     * 异常转为String描述
     *
     * @param throwable
     * @return 异常描述
     */
    private static String exceptionToString(Throwable throwable) {
        if (throwable == null) {
            return "";
        }
        String exceptionMessage = "";
        try {
            StringWriter sw = new StringWriter();
            throwable.printStackTrace(new PrintWriter(sw, true));
            exceptionMessage = sw.toString();
            sw.flush();
            sw.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return exceptionMessage;
    }

}

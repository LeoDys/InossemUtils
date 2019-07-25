package com.inossem_library.tips.logcat.util;

import android.util.Log;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

/**
 * @author 王斯宇
 * @time on 2019/7/16 16:31
 * @email siyu.wang@inossem.com
 */
public class LogUtils {

    private static final int LOG_LEVEL = 8; // 当LOG_LEVEL等于0时所有日志将不再输出

    public static final String TAG_INOSSEM = "INOSSEM";

    public static final String TAG_LOG = "LOG";

//-----------------------------------------------------------系统Log----------------------------------------------------------
    /**
     * 输出Info级别日志
     *
     * @param message 输出信息
     */
    public static void i(String message) {
        if (LOG_LEVEL > Log.INFO) {
            Log.i(TAG_LOG, message);
        }
    }


    /**
     * 输出Error级别日志
     *
     * @param message 输出信息
     */
    public static void e(String message) {
        if (LOG_LEVEL > Log.ERROR) {
            Log.e(TAG_LOG, message);
        }
    }

    /**
     * 输出Error级别日志包含运行时异常信息
     *
     * @param message   输出信息
     * @param throwable 运行时异常类
     */
    public static void e(String message, Throwable throwable) {
        if (LOG_LEVEL > Log.ERROR) {
            if (throwable == null) {
                Log.e(TAG_INOSSEM, message);
            } else {
                Log.e(TAG_INOSSEM, message, throwable);
            }

        }
    }

    /**
     * 输出Warn级别日志包含运行时异常信息
     *
     * @param message 输出信息
     */
    public static void w(String message) {
        if (LOG_LEVEL > Log.WARN) {
            Log.w(TAG_INOSSEM, message);
        }
    }

    /**
     * 输出Warn级别日志包含运行时异常信息
     *
     * @param message   输出信息
     * @param throwable 运行时异常类
     */
    public static void w(String message, Throwable throwable) {
        if (LOG_LEVEL > Log.WARN) {
            if (throwable == null) {
                Log.w(TAG_INOSSEM, message);
            } else {
                Log.w(TAG_INOSSEM, message, throwable);
            }
        }
    }


    /**
     * 输出Debug级别日志包含运行时异常信息
     *
     * @param message 输出信息
     */
    public static void d(String message) {
        if (LOG_LEVEL > Log.DEBUG) {
            Log.d(TAG_INOSSEM, message);
        }
    }


    /**
     * 输出Verbose级别日志包含运行时异常信息
     *
     * @param message 输出信息
     */
    public static void v(String message) {
        if (LOG_LEVEL > Log.VERBOSE) {
            Log.v(TAG_INOSSEM, message);
        }
    }
//----------------------------------------------------------------Logger-----------------------------------------------------------------

    /**
     * 初始化logger，放在Application里
     */
    public static void initLogger (){
        Logger.addLogAdapter(new AndroidLogAdapter());
    }
    /**
     * 输出Info级别日志
     *
     * @param message 输出信息
     */
    public static void loggerI(String message) {
        if (LOG_LEVEL > Log.INFO) {
            Logger.i(message);
        }
    }

    /**
     * 输出Error级别日志
     *
     * @param message 输出信息
     */
    public static void loggerE(String message) {
        if (LOG_LEVEL > Log.ERROR) {
            Logger.e(message);
        }
    }

    /**
     * 输出Error级别日志包含运行时异常信息
     *
     * @param message   输出信息
     * @param throwable 运行时异常类
     */
    public static void loggerE(String message, Throwable throwable) {
        if (LOG_LEVEL > Log.ERROR) {
            if (throwable == null) {
                Logger.e( message);
            } else {
                Logger.e(message, throwable);
            }

        }
    }

    /**
     * 输出Warn级别日志包含运行时异常信息
     *
     * @param message 输出信息
     */
    public static void loggerW(String message) {
        if (LOG_LEVEL > Log.WARN) {
            Logger.w(message);
        }
    }

    /**
     * 输出Warn级别日志包含运行时异常信息
     *
     * @param message   输出信息
     * @param throwable 运行时异常类
     */
    public static void loggerW(String message, Throwable throwable) {
        if (LOG_LEVEL > Log.WARN) {
            if (throwable == null) {
                Logger.w(message);
            } else {
                Logger.w(message, throwable);
            }
        }
    }
    /**
     * 输出Debug级别日志包含运行时异常信息
     *
     * @param message 输出信息
     */
    public static void loggerD(String message) {
        if (LOG_LEVEL > Log.DEBUG) {
            Logger.d(message);
        }
    }


    /**
     * 输出Verbose级别日志包含运行时异常信息
     *
     * @param message 输出信息
     */
    public static void loggerV(String message) {
        if (LOG_LEVEL > Log.VERBOSE) {
            Logger.v(message);
        }
    }
}

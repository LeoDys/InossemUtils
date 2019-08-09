package com.inossem_library.writelogs.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.inossem_library.exception.util.ExceptionUtil;
import com.inossem_library.tips.logcat.util.LogUtils;
import com.inossem_library.writelogs.constant.LogWriteConstant;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 将log写入本地工具类   同事打印log到控制台
 *
 * @author Leo E-mail:changwen.sun@inossem.com 2019/8/2 17:30
 * @version 1.0.7
 * @since 1.0.7
 */
public class LogWriteUtils {
//    /**
//     * 输出Error级别日志
//     *
//     * @param message   要保存和打印的信息 输出信息
//     * @author leij
//     */
//    public static void e(String message) {
//        if (LogWriteConstant.LOG_LEVEL > Log.ERROR) {
//            Log.e(LogWriteConstant.TAG_LOG, message);
//        }
//    }
//    /**
//     * 输出Error级别日志包含运行时异常信息
//     *
//     * @param message   要保存和打印的信息 输出信息
//     *                //     * @param throwable 异常 运行时异常类
//     * @author leij
//     */
//    public static void e(String message, Throwable throwable) {
//        if (LogWriteConstant.LOG_LEVEL > Log.ERROR) {
//            if (throwable == null) {
//                Log.e(LogWriteConstant.TAG_INOSSEM, message);
//            } else {
//                Log.e(LogWriteConstant.TAG_INOSSEM, message, throwable);
//            }
//
//        }
//    }
//    /**
//     * 输出Warn级别日志包含运行时异常信息
//     *
//     * @param message   要保存和打印的信息   输出信息
//     * @param throwable 异常 运行时异常类
//     * @author leij
//     */
//    private static void w(String message, Throwable throwable) {
//        if (LogWriteConstant.LOG_LEVEL > Log.WARN) {
//            if (throwable == null) {
//                Log.w(LogWriteConstant.TAG_INOSSEM, message);
//            } else {
//                Log.w(LogWriteConstant.TAG_INOSSEM, message, throwable);
//            }
//        }
//    }
//    /**
//     * 输出Info级别日志
//     *
//     * @param message   要保存和打印的信息 输出信息
//     * @author leij
//     */
//    public static void i(String message) {
//        if (LogWriteConstant.LOG_LEVEL > Log.INFO) {
//            Log.i(LogWriteConstant.TAG_LOG, message);
//        }
//    }
//
//    /**
//     * 输出Info级别日志包含运行时异常信息
//     *
//     * @param message   要保存和打印的信息   输出信息
//     * @param throwable 异常 运行时异常类
//     * @author leij
//     */
//    private static void i(String message, Throwable throwable) {
//        if (LogWriteConstant.LOG_LEVEL > Log.INFO) {
//            if (throwable == null) {
//                Log.i(LogWriteConstant.TAG_INOSSEM, message);
//            } else {
//                Log.i(LogWriteConstant.TAG_INOSSEM, message, throwable);
//            }
//        }
//    }
//    /**
//     * 输出Debug级别日志包含运行时异常信息
//     *
//     * @param message   要保存和打印的信息   输出信息
//     * @param throwable 异常 运行时异常类
//     * @author leij
//     */
//    private static void d(String message, Throwable throwable) {
//        if (LogWriteConstant.LOG_LEVEL > Log.DEBUG) {
//            if (throwable == null) {
//                Log.d(LogWriteConstant.TAG_INOSSEM, message);
//            } else {
//                Log.d(LogWriteConstant.TAG_INOSSEM, message, throwable);
//            }
//        }
//    }
//    /**
//     * 输出Verbose级别日志包含运行时异常信息
//     *
//     * @param message   要保存和打印的信息   输出信息
//     * @param throwable 异常 运行时异常类
//     * @author leij
//     */
//    private static void v(String message, Throwable throwable) {
//        if (LogWriteConstant.LOG_LEVEL > Log.VERBOSE) {
//            if (throwable == null) {
//                Log.v(LogWriteConstant.TAG_INOSSEM, message);
//            } else {
//                Log.v(LogWriteConstant.TAG_INOSSEM, message, throwable);
//            }
//        }
//    }

    /**
     * 请求失败打印
     *
     * @param context   上下文
     * @param message   要保存和打印的信息
     * @param throwable 异常
     */
    public static void logRequestE(Context context, String message, Throwable throwable) {
        logE(context, LogWriteConstant.LOG_NAME_REQUEST, message, throwable);
    }

    /**
     * 请求成功打印使用
     *
     * @param context 上下文
     * @param message 要保存和打印的信息
     */
    public static void logRequestI(Context context, String message) {
        logI(context, LogWriteConstant.LOG_NAME_REQUEST, message);
    }

    /**
     * 数据库相关出库打印使用
     *
     * @param context   上下文
     * @param message   要保存和打印的信息
     * @param throwable 异常
     */
    public static void logDatabaseE(Context context, String message, Throwable throwable) {
        logE(context, LogWriteConstant.LOG_NAME_DATABASE, message, throwable);
    }

    /**
     * 数据库基本操作打印使用
     *
     * @param context 上下文
     * @param message 要保存和打印的信息
     */
    public static void logDatabaseI(Context context, String message) {
        logI(context, LogWriteConstant.LOG_NAME_DATABASE, message);
    }

    /**
     * 出异常打印使用
     *
     * @param context   上下文
     * @param message   要保存和打印的信息
     * @param throwable 异常
     */
    public static void logCatchExceptionE(Context context, String message, Throwable throwable) {
        logE(context, LogWriteConstant.LOG_NAME_CRASHEXCEPTION, message, throwable);
    }

    /**
     * 扫码日志打印使用
     *
     * @param context 上下文
     * @param message 要保存和打印的信息
     */
    public static void logQRCodeI(Context context, String message) {
        logI(context, LogWriteConstant.LOG_NAME_QRCODE, message);
    }

    /**
     * VERBOSE 级别日志（最低级别）
     *
     * @param context   上下文
     * @param type      日志类型
     * @param message   要保存和打印的信息
     * @param throwable 异常
     */
    public static void logV(Context context, String type, String message, Throwable throwable) {
        saveLog(context, type, message + LogWriteConstant.NEW_LINE + ExceptionUtil.exceptionToString(throwable));
//        LogUtils.loggerV(message);
    }

    /**
     * info级别日志
     *
     * @param context 上下文
     * @param type    日志类型
     * @param message 要保存和打印的信息
     */
    public static void logI(Context context, String type, String message) {
        logI(context, type, message, null);
    }

    /**
     * info级别日志
     *
     * @param context   上下文
     * @param type      日志类型
     * @param message   要保存和打印的信息
     * @param throwable 异常
     */
    public static void logI(Context context, String type, String message, Throwable throwable) {
        saveLog(context, type, message + LogWriteConstant.NEW_LINE + ExceptionUtil.exceptionToString(throwable));
//        LogUtils.loggerI(message);
    }

    /**
     * debug级别日志
     *
     * @param context 上下文
     * @param type    日志类型
     * @param message 要保存和打印的信息
     */
    public static void logD(Context context, String type, String message) {
        logD(context, type, message, null);
    }

    /**
     * debug级别日志
     *
     * @param context   上下文
     * @param type      日志类型 日志类型
     * @param message   要保存和打印的信息   要保存和打印的信息
     * @param throwable 异常
     */
    public static void logD(Context context, String type, String message, Throwable throwable) {
        saveLog(context, type, message + LogWriteConstant.NEW_LINE + ExceptionUtil.exceptionToString(throwable));
//        LogUtils.loggerD(message);
    }

    /**
     * warning级别日志
     *
     * @param context 上下文
     * @param type    日志类型
     * @param message 要保存和打印的信息
     */
    public static void logW(Context context, String type, String message) {
        logW(context, type, message, null);
    }

    /**
     * warning级别日志
     *
     * @param context   上下文
     * @param type      日志类型
     * @param message   要保存和打印的信息
     * @param throwable 异常 异常
     */
    public static void logW(Context context, String type, String message, Throwable throwable) {
        saveLog(context, type, message + LogWriteConstant.NEW_LINE + ExceptionUtil.exceptionToString(throwable));
//        LogUtils.loggerW(message, throwable);
    }

    /**
     * Error级别日志
     *
     * @param context   上下文
     * @param type      日志类型
     * @param message   要保存和打印的信息
     * @param throwable 异常
     */
    public static void logE(Context context, String type, String message, Throwable throwable) {
        saveLog(context, type, message + LogWriteConstant.NEW_LINE + ExceptionUtil.exceptionToString(throwable));
//        LogUtils.loggerE(message, throwable);
    }

    /**
     * 保存指定类型日志
     *
     * @param context     上下文
     * @param type        日志类型
     * @param information 日志信息
     * @return 保存日志结果
     */
    public static boolean saveLog(Context context, String type, String information) {
        return LogWriteFile.saveLog(context, type, information);
    }

}

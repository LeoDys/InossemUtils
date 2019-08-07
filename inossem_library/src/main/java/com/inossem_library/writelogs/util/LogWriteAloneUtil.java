package com.inossem_library.writelogs.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.inossem_library.tips.logcat.util.LogUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author Leo E-mail:changwen.sun@inossem.com 2019/8/5 9:06
 * @version 1.0.7
 * @since 1.0.7
 */

public class LogWriteAloneUtil {
    /**
     * 获取sp文件中存储的文件名   下次写log的时候直接读取出来
     *
     * @param context      上下文对象
     * @param name         sp的xml的文件名
     * @param key          存储的log类型的键
     * @param defaultValue 默认值
     * @return 存储的log类型的键对应值——文件名字
     */
    public static String getString(Context context, String name, String key, String defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(name,
                Activity.MODE_PRIVATE);
        return sharedPreferences.getString(key, defaultValue);
    }

    /**
     * 将log的文件名写入sp文件中   下次写log的时候直接读取出来
     *
     * @param context 上下文对象
     * @param name    sp的xml的文件名
     * @param key     存储的log类型的键
     * @param value   默认值
     */
    public static void putString(Context context, String name, String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(name,
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    /**
     * 将Date格式化
     *
     * @param format 格式化的格式
     * @param date   要格式化的时间
     * @return 格式化完成的时间
     */
    public static String getStringDateFromDate(String format, Date date) {
        String result;
        result = new SimpleDateFormat(format, Locale.getDefault()).format(date);
        return result;
    }

    /**
     * 获取当前时间
     *
     * @param format 格式化的格式
     * @return 当前时间
     */
    public static String getCurrentStringDate(String format) {
        Calendar calendar = Calendar.getInstance();
        return getStringDateFromDate(format, calendar.getTime());
    }

    /**
     * 获取当前时间戳
     *
     * @param format 格式化类型
     * @param time   要格式化的时间
     * @return 格式化完成的时间
     */
    public static long getTimeStampFromString(String format, String time) {
        return getDateFromString(format, time).getTime();
    }

    /**
     * 格式化时间
     *
     * @param format 格式化格式
     * @param time   要格式化的时间
     * @return 格式化完成的Date
     */
    public static Date getDateFromString(String format, String time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());
        Date date = null;
        try {
            date = dateFormat.parse(time);
        } catch (ParseException e) {
            LogUtils.e("", e);
        }
        return date;
    }
}

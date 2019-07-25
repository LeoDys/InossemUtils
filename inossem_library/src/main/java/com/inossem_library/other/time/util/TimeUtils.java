package com.inossem_library.other.time.util;

import android.content.Context;
import android.support.annotation.NonNull;

import com.inossem_library.R;
import com.inossem_library.exception.ExceptionEnum;
import com.inossem_library.exception.InossemException;
import com.inossem_library.other.string.util.StringUtils;
import com.inossem_library.other.time.constant.TimeConstants;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author 郭晓龙
 * @time on 2019/7/15
 * @email xiaolong.guo@inossem.com
 * @describe 时间封装 时间戳、字符串、日期类型互相转换，获取时间差等
 */
public class TimeUtils {

    /**
     * 不能被实例化
     */
    private TimeUtils() {
        throw new UnsupportedOperationException("不能被实例化");
    }

    /**
     * 默认时间格式
     *
     * @return 返回默认时间格式
     */
    private static SimpleDateFormat getDefaultFormat() {
        return getDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 时间格式
     *
     * @param pattern 时间格式"yyyy-MM-dd HH:mm:ss"
     * @return 返回时间格式
     */
    private static SimpleDateFormat getDateFormat(@NonNull String pattern) {
        if (StringUtils.isEmpty(pattern)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "pattern不能为空！");
        }
        SimpleDateFormat simpleDateFormat = TimeConstants.SDF_THREAD_LOCAL.get();
        if (simpleDateFormat == null) {
            simpleDateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
            TimeConstants.SDF_THREAD_LOCAL.set(simpleDateFormat);
        } else {
            simpleDateFormat.applyPattern(pattern);
        }
        return simpleDateFormat;
    }

    /**
     * 时间戳转默认格式字符串
     *
     * @param millis 时间戳
     * @return 字符串格式时间
     */
    public static String millis2String(final long millis) {
        return millis2String(millis, getDefaultFormat());
    }

    /**
     * 时间戳转固定格式字符串
     *
     * @param millis  时间戳
     * @param pattern 时间格式"yyyy-MM-dd HH:mm:ss"
     * @return 字符串格式时间
     */
    public static String millis2String(long millis, @NonNull final String pattern) {
        if (StringUtils.isEmpty(pattern)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "pattern不能为空！");
        }
        return millis2String(millis, getDateFormat(pattern));
    }

    /**
     * 时间戳转固定格式字符串
     *
     * @param millis 时间戳
     * @param format 时间格式"new SimpleDateFormat("yyyy年MM月dd日-HH:mm:ss", Locale.CHINESE);"
     * @return 字符串格式时间
     */
    public static String millis2String(final long millis, @NonNull final DateFormat format) {
        if (format == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "format不能为空！");
        }
        return format.format(new Date(millis));
    }

    /**
     * 默认格式字符串转时间戳
     *
     * @param time 字符串格式时间
     * @return 时间戳格式时间
     */
    public static long string2Millis(@NonNull final String time) throws ParseException {
        if (StringUtils.isEmpty(time)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "time不能为空！");
        }
        return string2Millis(time, getDefaultFormat());
    }

    /**
     * 固定格式字符串转时间戳
     *
     * @param time    字符串格式时间
     * @param pattern 时间格式"yyyy-MM-dd HH:mm:ss"
     * @return 时间戳格式时间
     */
    public static long string2Millis(@NonNull final String time, @NonNull final String pattern) throws ParseException {
        if (StringUtils.isEmpty(time)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "time不能为空！");
        }
        if (StringUtils.isEmpty(pattern)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "pattern不能为空！");
        }
        return string2Millis(time, getDateFormat(pattern));
    }

    /**
     * 固定格式字符串转时间戳
     *
     * @param time   字符串格式时间
     * @param format 时间格式"new SimpleDateFormat("yyyy年MM月dd日-HH:mm:ss", Locale.CHINESE);"
     * @return 时间戳格式时间
     */
    public static long string2Millis(@NonNull final String time, @NonNull final DateFormat format) throws ParseException {
        if (StringUtils.isEmpty(time)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "time不能为空！");
        }
        if (format == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "format不能为空！");
        }
        return format.parse(time).getTime();
    }

    /**
     * 默认格式字符串转日期
     *
     * @param time 字符串格式时间
     * @return 日期格式时间
     */
    public static Date string2Date(@NonNull final String time) throws ParseException {
        if (StringUtils.isEmpty(time)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "time不能为空！");
        }
        return string2Date(time, getDefaultFormat());
    }

    /**
     * 固定格式字符串转日期
     *
     * @param time    字符串格式时间
     * @param pattern 时间格式"yyyy-MM-dd HH:mm:ss"
     * @return 日期格式时间
     */
    public static Date string2Date(@NonNull final String time, @NonNull final String pattern) throws ParseException {
        if (StringUtils.isEmpty(time)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "time不能为空！");
        }
        if (pattern == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "pattern不能为空！");
        }
        return string2Date(time, getDateFormat(pattern));
    }

    /**
     * 固定格式字符串转日期
     *
     * @param time   字符串格式时间
     * @param format 时间格式"new SimpleDateFormat("yyyy年MM月dd日-HH:mm:ss", Locale.CHINESE);"
     * @return 日期格式时间
     */
    public static Date string2Date(@NonNull final String time, @NonNull final DateFormat format) throws ParseException {
        if (StringUtils.isEmpty(time)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "time不能为空！");
        }
        if (format == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "format不能为空！");
        }
        return format.parse(time);
    }

    /**
     * 默认格式日期转字符串
     *
     * @param date 日期格式时间
     * @return 字符串格式时间
     */
    public static String date2String(@NonNull final Date date) {
        if (date == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "date不能为空！");
        }
        return date2String(date, getDefaultFormat());
    }

    /**
     * 固定格式日期转字符串
     *
     * @param date    日期格式时间
     * @param pattern 时间格式"yyyy-MM-dd HH:mm:ss"
     * @return 字符串格式时间
     */
    public static String date2String(@NonNull final Date date, @NonNull final String pattern) {
        if (date == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "date不能为空！");
        }
        if (StringUtils.isEmpty(pattern)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "pattern不能为空！");
        }
        return getDateFormat(pattern).format(date);
    }

    /**
     * 固定格式日期转字符串
     *
     * @param date   日期格式时间
     * @param format 时间格式"new SimpleDateFormat("yyyy年MM月dd日-HH:mm:ss", Locale.CHINESE);"
     * @return 字符串格式时间
     */
    public static String date2String(@NonNull final Date date, @NonNull final DateFormat format) {
        if (date == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "date不能为空！");
        }
        if (format == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "format不能为空！");
        }
        return format.format(date);
    }

    /**
     * 日期转时间戳
     *
     * @param date 日期格式时间
     * @return 时间戳格式时间
     */
    public static long date2Millis(@NonNull final Date date) {
        if (date == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "date不能为空！");
        }
        return date.getTime();
    }

    /**
     * 时间戳转日期
     *
     * @param millis 时间戳
     * @return 日期格式时间
     */
    public static Date millis2Date(final long millis) {
        return new Date(millis);
    }

    /**
     * 字符串获取默认格式时间差
     *
     * @param time1 字符串格式时间
     * @param time2 字符串格式时间
     * @param unit  时间单位常量
     *              <ul>
     *              <li>{@link TimeConstants#MSEC}</li>
     *              <li>{@link TimeConstants#SEC }</li>
     *              <li>{@link TimeConstants#MIN }</li>
     *              <li>{@link TimeConstants#HOUR}</li>
     *              <li>{@link TimeConstants#DAY }</li>
     *              </ul>
     * @return 特定时间单位时间差
     */
    public static long getTimeSpan(@NonNull final String time1,
                                   @NonNull final String time2,
                                   @TimeConstants.Unit final int unit) throws ParseException {
        if (StringUtils.isEmpty(time1)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "time1不能为空！");
        }
        if (StringUtils.isEmpty(time2)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "time2不能为空！");
        }
        return getTimeSpan(time1, time2, getDefaultFormat(), unit);
    }

    /**
     * 字符串获取固定格式时间差
     *
     * @param time1  字符串格式时间
     * @param time2  字符串格式时间
     * @param format 时间格式"new SimpleDateFormat("yyyy年MM月dd日-HH:mm:ss", Locale.CHINESE);"
     * @param unit   时间单位常量
     *               <ul>
     *               <li>{@link TimeConstants#MSEC}</li>
     *               <li>{@link TimeConstants#SEC }</li>
     *               <li>{@link TimeConstants#MIN }</li>
     *               <li>{@link TimeConstants#HOUR}</li>
     *               <li>{@link TimeConstants#DAY }</li>
     *               </ul>
     * @return 特定时间单位时间差
     */
    public static long getTimeSpan(@NonNull final String time1,
                                   @NonNull final String time2,
                                   @NonNull final DateFormat format,
                                   @TimeConstants.Unit final int unit) throws ParseException {
        if (StringUtils.isEmpty(time1)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "time1不能为空！");
        }
        if (StringUtils.isEmpty(time2)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "time2不能为空！");
        }
        if (format == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "format不能为空！");
        }
        return millis2TimeSpan(string2Millis(time1, format) - string2Millis(time2, format), unit);
    }

    /**
     * 日期获取时间差
     *
     * @param date1 日期格式时间
     * @param date2 日期格式时间
     * @param unit  时间单位常量
     *              <ul>
     *              <li>{@link TimeConstants#MSEC}</li>
     *              <li>{@link TimeConstants#SEC }</li>
     *              <li>{@link TimeConstants#MIN }</li>
     *              <li>{@link TimeConstants#HOUR}</li>
     *              <li>{@link TimeConstants#DAY }</li>
     *              </ul>
     * @return 特定时间单位时间差
     */
    public static long getTimeSpan(@NonNull final Date date1,
                                   @NonNull final Date date2,
                                   @TimeConstants.Unit final int unit) {
        if (date1 == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "date1不能为空！");
        }
        if (date2 == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "date2不能为空！");
        }
        return millis2TimeSpan(date2Millis(date1) - date2Millis(date2), unit);
    }

    /**
     * 时间戳获取时间差
     *
     * @param millis1 时间戳格式时间
     * @param millis2 时间戳格式时间
     * @param unit    时间单位常量
     *                <ul>
     *                <li>{@link TimeConstants#MSEC}</li>
     *                <li>{@link TimeConstants#SEC }</li>
     *                <li>{@link TimeConstants#MIN }</li>
     *                <li>{@link TimeConstants#HOUR}</li>
     *                <li>{@link TimeConstants#DAY }</li>
     *                </ul>
     * @return 特定时间单位时间差
     */
    public static long getTimeSpan(final long millis1,
                                   final long millis2,
                                   @TimeConstants.Unit final int unit) {
        return millis2TimeSpan(millis1 - millis2, unit);
    }

    /**
     * 字符串获取合适型时间差
     *
     * @param context   上下文
     * @param time1     字符串格式时间
     * @param time2     字符串格式时间
     * @param precision 时间差精度
     *                  <ul>
     *                  <li>precision = 0, return null</li>
     *                  <li>precision = 1, return 天</li>
     *                  <li>precision = 2, return 天, 小时</li>
     *                  <li>precision = 3, return 天, 小时, 分钟</li>
     *                  <li>precision = 4, return 天, 小时, 分钟, 秒</li>
     *                  <li>precision &gt;= 5，return 天, 小时, 分钟, 秒, 毫秒</li>
     *                  </ul>
     * @return 特定精度时间差
     */
    public static String getFitTimeSpan(@NonNull Context context, @NonNull final String time1,
                                        @NonNull final String time2,
                                        final int precision) throws ParseException {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context不能为空！");
        }
        if (StringUtils.isEmpty(time1)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "time1不能为空！");
        }
        if (StringUtils.isEmpty(time2)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "time2不能为空！");
        }
        long delta = string2Millis(time1, getDefaultFormat()) - string2Millis(time2, getDefaultFormat());
        return millis2FitTimeSpan(context, delta, precision);
    }

    /**
     * 字符串获取合适型时间差
     *
     * @param context   上下文
     * @param time1     字符串格式时间
     * @param time2     字符串格式时间
     * @param format    时间格式"new SimpleDateFormat("yyyy年MM月dd日-HH:mm:ss", Locale.CHINESE);"
     * @param precision 时间差精度
     *                  <ul>
     *                  <li>precision = 0, return null</li>
     *                  <li>precision = 1, return 天</li>
     *                  <li>precision = 2, return 天, 小时</li>
     *                  <li>precision = 3, return 天, 小时, 分钟</li>
     *                  <li>precision = 4, return 天, 小时, 分钟, 秒</li>
     *                  <li>precision &gt;= 5，return 天, 小时, 分钟, 秒, 毫秒</li>
     *                  </ul>
     * @return 特定精度时间差
     */
    public static String getFitTimeSpan(@NonNull Context context, @NonNull final String time1,
                                        @NonNull final String time2,
                                        @NonNull final DateFormat format,
                                        final int precision) throws ParseException {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context不能为空！");
        }
        if (StringUtils.isEmpty(time1)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "time1不能为空！");
        }
        if (StringUtils.isEmpty(time2)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "time2不能为空！");
        }
        if (format == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "format不能为空！");
        }
        long delta = string2Millis(time1, format) - string2Millis(time2, format);
        return millis2FitTimeSpan(context, delta, precision);
    }

    /**
     * 日期获取合适型时间差
     *
     * @param context   上下文
     * @param date1     日期格式时间
     * @param date2     日期格式时间
     * @param precision 时间差精度
     *                  <ul>
     *                  <li>precision = 0, return null</li>
     *                  <li>precision = 1, return 天</li>
     *                  <li>precision = 2, return 天, 小时</li>
     *                  <li>precision = 3, return 天, 小时, 分钟</li>
     *                  <li>precision = 4, return 天, 小时, 分钟, 秒</li>
     *                  <li>precision &gt;= 5，return 天, 小时, 分钟, 秒, 毫秒</li>
     *                  </ul>
     * @return 特定精度时间差
     */
    public static String getFitTimeSpan(@NonNull Context context, @NonNull final Date date1, @NonNull final Date date2, final int precision) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context不能为空！");
        }
        if (date1 == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "date1不能为空！");
        }
        if (date2 == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "date2不能为空！");
        }
        return millis2FitTimeSpan(context, date2Millis(date1) - date2Millis(date2), precision);
    }

    /**
     * 时间戳获取合适型时间差
     *
     * @param context   上下文
     * @param millis1   时间戳格式时间
     * @param millis2   时间戳格式时间
     * @param precision 时间差精度
     *                  <ul>
     *                  <li>precision = 0, return null</li>
     *                  <li>precision = 1, return 天</li>
     *                  <li>precision = 2, return 天, 小时</li>
     *                  <li>precision = 3, return 天, 小时, 分钟</li>
     *                  <li>precision = 4, return 天, 小时, 分钟, 秒</li>
     *                  <li>precision &gt;= 5，return 天, 小时, 分钟, 秒, 毫秒</li>
     *                  </ul>
     * @return 特定精度时间差
     */
    public static String getFitTimeSpan(@NonNull Context context, final long millis1,
                                        final long millis2,
                                        final int precision) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context不能为空！");
        }
        return millis2FitTimeSpan(context, millis1 - millis2, precision);
    }

    /**
     * 获取当前毫秒时间戳
     *
     * @return 当前时间戳格式时间
     */
    public static long getNowMills() {
        return System.currentTimeMillis();
    }

    /**
     * 获取当前默认格式时间字符串
     *
     * @return 当前字符串格式时间
     */
    public static String getNowString() {
        return millis2String(System.currentTimeMillis(), getDefaultFormat());
    }

    /**
     * 获取当前固定格式时间字符串
     *
     * @param format 时间格式"new SimpleDateFormat("yyyy年MM月dd日-HH:mm:ss", Locale.CHINESE);"
     * @return 当前字符串格式时间
     */
    public static String getNowString(@NonNull final DateFormat format) {
        if (format == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "format不能为空！");
        }
        return millis2String(System.currentTimeMillis(), format);
    }

    /**
     * 获取当前日期
     *
     * @return 当前日期格式时间
     */
    public static Date getNowDate() {
        return new Date();
    }

    /**
     * 字符串获取与当前时间的差
     *
     * @param time 字符串格式时间
     * @param unit 时间单位常量
     *             <ul>
     *             <li>{@link TimeConstants#MSEC}</li>
     *             <li>{@link TimeConstants#SEC }</li>
     *             <li>{@link TimeConstants#MIN }</li>
     *             <li>{@link TimeConstants#HOUR}</li>
     *             <li>{@link TimeConstants#DAY }</li>
     *             </ul>
     * @return 与当前时间的特定单位时间差
     */
    public static long getTimeSpanByNow(@NonNull final String time, @TimeConstants.Unit final int unit) throws ParseException {
        if (StringUtils.isEmpty(time)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "time不能为空！");
        }
        return getTimeSpan(time, getNowString(), getDefaultFormat(), unit);
    }

    /**
     * 字符串获取与当前时间的差
     *
     * @param time   字符串格式时间
     * @param format 时间格式"new SimpleDateFormat("yyyy年MM月dd日-HH:mm:ss", Locale.CHINESE);"
     * @param unit   时间单位常量
     *               <ul>
     *               <li>{@link TimeConstants#MSEC}</li>
     *               <li>{@link TimeConstants#SEC }</li>
     *               <li>{@link TimeConstants#MIN }</li>
     *               <li>{@link TimeConstants#HOUR}</li>
     *               <li>{@link TimeConstants#DAY }</li>
     *               </ul>
     * @return 与当前时间的特定单位时间差
     */
    public static long getTimeSpanByNow(@NonNull final String time,
                                        @NonNull final DateFormat format,
                                        @TimeConstants.Unit final int unit) throws ParseException {
        if (StringUtils.isEmpty(time)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "time不能为空！");
        }
        if (format == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "format不能为空！");
        }
        return getTimeSpan(time, getNowString(format), format, unit);
    }

    /**
     * 日期获取与当前时间的差
     *
     * @param date 日期格式时间
     * @param unit 时间单位常量
     *             <ul>
     *             <li>{@link TimeConstants#MSEC}</li>
     *             <li>{@link TimeConstants#SEC }</li>
     *             <li>{@link TimeConstants#MIN }</li>
     *             <li>{@link TimeConstants#HOUR}</li>
     *             <li>{@link TimeConstants#DAY }</li>
     *             </ul>
     * @return 与当前时间的特定单位时间差
     */
    public static long getTimeSpanByNow(@NonNull final Date date, @TimeConstants.Unit final int unit) {
        if (date == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "date不能为空！");
        }
        return getTimeSpan(date, new Date(), unit);
    }

    /**
     * 时间戳获取与当前时间的差
     *
     * @param millis 时间戳
     * @param unit   时间单位常量
     *               <ul>
     *               <li>{@link TimeConstants#MSEC}</li>
     *               <li>{@link TimeConstants#SEC }</li>
     *               <li>{@link TimeConstants#MIN }</li>
     *               <li>{@link TimeConstants#HOUR}</li>
     *               <li>{@link TimeConstants#DAY }</li>
     *               </ul>
     * @return 与当前时间的特定单位时间差
     */
    public static long getTimeSpanByNow(final long millis, @TimeConstants.Unit final int unit) {
        return getTimeSpan(millis, System.currentTimeMillis(), unit);
    }

    /**
     * 字符串获取与当前合适型时间差
     *
     * @param context   上下文
     * @param time      字符串格式时间
     * @param precision 时间差精度
     *                  <ul>
     *                  <li>precision = 0，返回 null</li>
     *                  <li>precision = 1，返回天</li>
     *                  <li>precision = 2，返回天和小时</li>
     *                  <li>precision = 3，返回天、小时和分钟</li>
     *                  <li>precision = 4，返回天、小时、分钟和秒</li>
     *                  <li>precision &gt;= 5，返回天、小时、分钟、秒和毫秒</li>
     *                  </ul>
     * @return 与当前时间的合适精度时间差
     */
    public static String getFitTimeSpanByNow(@NonNull Context context, @NonNull final String time, final int precision) throws ParseException {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context不能为空！");
        }
        if (StringUtils.isEmpty(time)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "time不能为空！");
        }
        return getFitTimeSpan(context, time, getNowString(), getDefaultFormat(), precision);
    }

    /**
     * 字符串获取与当前合适型时间差
     *
     * @param context   上下文
     * @param time      字符串格式时间
     * @param format    时间格式"new SimpleDateFormat("yyyy年MM月dd日-HH:mm:ss", Locale.CHINESE);"
     * @param precision 时间差精度
     *                  <ul>
     *                  <li>precision = 0，返回 null</li>
     *                  <li>precision = 1，返回天</li>
     *                  <li>precision = 2，返回天和小时</li>
     *                  <li>precision = 3，返回天、小时和分钟</li>
     *                  <li>precision = 4，返回天、小时、分钟和秒</li>
     *                  <li>precision &gt;= 5，返回天、小时、分钟、秒和毫秒</li>
     *                  </ul>
     * @return 与当前时间的合适精度时间差
     */
    public static String getFitTimeSpanByNow(@NonNull Context context, @NonNull final String time,
                                             @NonNull final DateFormat format,
                                             final int precision) throws ParseException {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context不能为空！");
        }
        if (StringUtils.isEmpty(time)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "time不能为空！");
        }
        if (format == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "format不能为空！");
        }
        return getFitTimeSpan(context, time, getNowString(format), format, precision);
    }

    /**
     * 日期获取与当前合适型时间差
     *
     * @param context   上下文
     * @param date      日期格式时间
     * @param precision 时间差精度
     *                  <ul>
     *                  <li>precision = 0，返回 null</li>
     *                  <li>precision = 1，返回天</li>
     *                  <li>precision = 2，返回天和小时</li>
     *                  <li>precision = 3，返回天、小时和分钟</li>
     *                  <li>precision = 4，返回天、小时、分钟和秒</li>
     *                  <li>precision &gt;= 5，返回天、小时、分钟、秒和毫秒</li>
     *                  </ul>
     * @return 与当前时间的合适精度时间差
     */
    public static String getFitTimeSpanByNow(@NonNull Context context, @NonNull final Date date, final int precision) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context不能为空！");
        }
        if (date == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "date不能为空！");
        }
        return getFitTimeSpan(context, date, getNowDate(), precision);
    }

    /**
     * 时间戳获取与当前合适型时间差
     *
     * @param context   上下文
     * @param millis    时间戳格式时间
     * @param precision 时间差精度
     *                  <ul>
     *                  <li>precision = 0，返回 null</li>
     *                  <li>precision = 1，返回天</li>
     *                  <li>precision = 2，返回天和小时</li>
     *                  <li>precision = 3，返回天、小时和分钟</li>
     *                  <li>precision = 4，返回天、小时、分钟和秒</li>
     *                  <li>precision &gt;= 5，返回天、小时、分钟、秒和毫秒</li>
     *                  </ul>
     * @return 与当前时间的合适精度时间差
     */
    public static String getFitTimeSpanByNow(@NonNull Context context, final long millis, final int precision) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context不能为空！");
        }
        return getFitTimeSpan(context, millis, System.currentTimeMillis(), precision);
    }

    /**
     * 字符串获取与当前友好型时间差
     *
     * @param context 上下文
     * @param time    字符串格式时间
     * @return 与当前时间的友好型时间差
     * <ul>
     * <li>如果小于 1 秒钟内，显示刚刚</li>
     * <li>如果在 1 分钟内，显示 XXX秒前</li>
     * <li>如果在 1 小时内，显示 XXX分钟前</li>
     * <li>如果在 1 小时外的今天内，显示今天15:32</li>
     * <li>如果是昨天的，显示昨天15:32</li>
     * <li>其余显示，2016-10-15</li>
     * <li>时间不合法的情况全部日期和时间信息，如星期六 十月 27 14:21:20 CST 2007</li>
     * </ul>
     */
    public static String getFriendlyTimeSpanByNow(@NonNull Context context, @NonNull final String time) throws ParseException {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context不能为空！");
        }
        if (StringUtils.isEmpty(time)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "time不能为空！");
        }
        return getFriendlyTimeSpanByNow(context, time, getDefaultFormat());
    }

    /**
     * 字符串获取与当前友好型时间差
     *
     * @param context 上下文
     * @param time    字符串格式时间
     * @param format  时间格式"new SimpleDateFormat("yyyy年MM月dd日-HH:mm:ss", Locale.CHINESE);"
     * @return 与当前时间的友好型时间差
     * <ul>
     * <li>如果小于 1 秒钟内，显示刚刚</li>
     * <li>如果在 1 分钟内，显示 XXX秒前</li>
     * <li>如果在 1 小时内，显示 XXX分钟前</li>
     * <li>如果在 1 小时外的今天内，显示今天15:32</li>
     * <li>如果是昨天的，显示昨天15:32</li>
     * <li>其余显示，2016-10-15</li>
     * <li>时间不合法的情况全部日期和时间信息，如星期六 十月 27 14:21:20 CST 2007</li>
     * </ul>
     */
    public static String getFriendlyTimeSpanByNow(@NonNull Context context, @NonNull final String time,
                                                  @NonNull final DateFormat format) throws ParseException {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context不能为空！");
        }
        if (StringUtils.isEmpty(time)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "time不能为空！");
        }
        if (format == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "format不能为空！");
        }
        return getFriendlyTimeSpanByNow(context, string2Millis(time, format));
    }

    /**
     * 日期获取与当前友好型时间差
     *
     * @param context 上下文
     * @param date    日期格式时间
     * @return 与当前时间的友好型时间差
     * <ul>
     * <li>如果小于 1 秒钟内，显示刚刚</li>
     * <li>如果在 1 分钟内，显示 XXX秒前</li>
     * <li>如果在 1 小时内，显示 XXX分钟前</li>
     * <li>如果在 1 小时外的今天内，显示今天15:32</li>
     * <li>如果是昨天的，显示昨天15:32</li>
     * <li>其余显示，2016-10-15</li>
     * <li>时间不合法的情况全部日期和时间信息，如星期六 十月 27 14:21:20 CST 2007</li>
     * </ul>
     */
    public static String getFriendlyTimeSpanByNow(@NonNull Context context, @NonNull final Date date) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context不能为空！");
        }
        if (date == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "date不能为空！");
        }
        return getFriendlyTimeSpanByNow(context, date.getTime());
    }

    /**
     * 时间戳获取与当前友好型时间差
     *
     * @param context 上下文
     * @param millis  时间戳
     * @return 与当前时间的友好型时间差
     * <ul>
     * <li>如果小于 1 秒钟内，显示刚刚</li>
     * <li>如果在 1 分钟内，显示 XXX秒前</li>
     * <li>如果在 1 小时内，显示 XXX分钟前</li>
     * <li>如果在 1 小时外的今天内，显示今天15:32</li>
     * <li>如果是昨天的，显示昨天15:32</li>
     * <li>其余显示，2016-10-15</li>
     * <li>时间不合法的情况全部日期和时间信息，如星期六 十月 27 14:21:20 CST 2007</li>
     * </ul>
     */
    public static String getFriendlyTimeSpanByNow(@NonNull Context context, final long millis) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context不能为空！");
        }
        long now = System.currentTimeMillis();
        long span = now - millis;
        if (span < 0)
            // U can read http://www.apihome.cn/api/java/Formatter.html to understand it.
//            return String.format("%tc", millis);
            return millis2String(millis);
        if (span < 1000) {
            return context.getResources().getString(R.string.time_constants_before_msec);
        } else if (span < TimeConstants.MIN) {
            return String.format(Locale.getDefault(), context.getResources().getString(R.string.time_constants_before_sec), span / TimeConstants.SEC);
        } else if (span < TimeConstants.HOUR) {
            return String.format(Locale.getDefault(), context.getResources().getString(R.string.time_constants_before_min), span / TimeConstants.MIN);
        }
        // 获取当天 00:00
        long wee = getWeeOfToday();
        if (millis >= wee) {
            return String.format(context.getResources().getString(R.string.time_constants_before_hour), millis);
        } else if (millis >= wee - TimeConstants.DAY) {
            return String.format(context.getResources().getString(R.string.time_constants_before_day), millis);
        } else {
            return String.format("%tF", millis);
        }
    }

    /**
     * 获取当天 00:00:00
     *
     * @return 时间戳格式的当天00:00:00
     */
    private static long getWeeOfToday() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    /**
     * 时间戳获取与给定时间等于时间差的时间戳
     *
     * @param millis   时间戳格式时间
     * @param timeSpan 时间戳格式时间差
     * @param unit     时间单位常量
     *                 <ul>
     *                 <li>{@link TimeConstants#MSEC}</li>
     *                 <li>{@link TimeConstants#SEC }</li>
     *                 <li>{@link TimeConstants#MIN }</li>
     *                 <li>{@link TimeConstants#HOUR}</li>
     *                 <li>{@link TimeConstants#DAY }</li>
     *                 </ul>
     * @return 时间戳格式时间
     */
    public static long getMillis(final long millis,
                                 final long timeSpan,
                                 @TimeConstants.Unit final int unit) {
        return millis + timeSpan2Millis(timeSpan, unit);
    }

    /**
     * 字符串获取与给定时间等于时间差的时间戳
     *
     * @param time     字符串格式时间
     * @param timeSpan 时间戳格式时间差
     * @param unit     时间单位常量
     *                 <ul>
     *                 <li>{@link TimeConstants#MSEC}</li>
     *                 <li>{@link TimeConstants#SEC }</li>
     *                 <li>{@link TimeConstants#MIN }</li>
     *                 <li>{@link TimeConstants#HOUR}</li>
     *                 <li>{@link TimeConstants#DAY }</li>
     *                 </ul>
     * @return 时间戳格式时间
     */
    public static long getMillis(@NonNull final String time,
                                 final long timeSpan,
                                 @TimeConstants.Unit final int unit) throws ParseException {
        if (StringUtils.isEmpty(time)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "time不能为空！");
        }
        return getMillis(time, getDefaultFormat(), timeSpan, unit);
    }

    /**
     * 时间戳获取与给定时间等于时间差的时间戳
     *
     * @param time     字符串格式时间
     * @param format   时间格式"new SimpleDateFormat("yyyy年MM月dd日-HH:mm:ss", Locale.CHINESE);"
     * @param timeSpan 时间戳格式时间差
     * @param unit     时间单位常量
     *                 <ul>
     *                 <li>{@link TimeConstants#MSEC}</li>
     *                 <li>{@link TimeConstants#SEC }</li>
     *                 <li>{@link TimeConstants#MIN }</li>
     *                 <li>{@link TimeConstants#HOUR}</li>
     *                 <li>{@link TimeConstants#DAY }</li>
     *                 </ul>
     * @return 时间戳格式时间
     */
    public static long getMillis(@NonNull final String time,
                                 @NonNull final DateFormat format,
                                 final long timeSpan,
                                 @TimeConstants.Unit final int unit) throws ParseException {
        if (StringUtils.isEmpty(time)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "time不能为空！");
        }
        if (format == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "format不能为空！");
        }
        return string2Millis(time, format) + timeSpan2Millis(timeSpan, unit);
    }

    /**
     * 日期获取与给定时间等于时间差的时间戳
     *
     * @param date     日期格式时间
     * @param timeSpan 时间戳格式时间差
     * @param unit     时间单位常量
     *                 <ul>
     *                 <li>{@link TimeConstants#MSEC}</li>
     *                 <li>{@link TimeConstants#SEC }</li>
     *                 <li>{@link TimeConstants#MIN }</li>
     *                 <li>{@link TimeConstants#HOUR}</li>
     *                 <li>{@link TimeConstants#DAY }</li>
     *                 </ul>
     * @return 时间戳格式时间
     */
    public static long getMillis(@NonNull final Date date,
                                 final long timeSpan,
                                 @TimeConstants.Unit final int unit) {
        if (date == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "date不能为空！");
        }
        return date2Millis(date) + timeSpan2Millis(timeSpan, unit);
    }

    /**
     * 时间戳获取与给定时间等于时间差的默认格式字符串
     *
     * @param millis   时间戳格式时间
     * @param timeSpan 时间戳格式时间差
     * @param unit     时间单位常量
     *                 <ul>
     *                 <li>{@link TimeConstants#MSEC}</li>
     *                 <li>{@link TimeConstants#SEC }</li>
     *                 <li>{@link TimeConstants#MIN }</li>
     *                 <li>{@link TimeConstants#HOUR}</li>
     *                 <li>{@link TimeConstants#DAY }</li>
     *                 </ul>
     * @return 字符串格式时间
     */
    public static String getString(final long millis,
                                   final long timeSpan,
                                   @TimeConstants.Unit final int unit) {
        return getString(millis, getDefaultFormat(), timeSpan, unit);
    }

    /**
     * 时间戳获取与给定时间等于时间差的固定格式字符串
     *
     * @param millis   时间戳格式时间
     * @param format   时间格式"new SimpleDateFormat("yyyy年MM月dd日-HH:mm:ss", Locale.CHINESE);"
     * @param timeSpan 时间戳格式时间差
     * @param unit     时间单位常量
     *                 <ul>
     *                 <li>{@link TimeConstants#MSEC}</li>
     *                 <li>{@link TimeConstants#SEC }</li>
     *                 <li>{@link TimeConstants#MIN }</li>
     *                 <li>{@link TimeConstants#HOUR}</li>
     *                 <li>{@link TimeConstants#DAY }</li>
     *                 </ul>
     * @return 字符串格式时间
     */
    public static String getString(final long millis,
                                   @NonNull final DateFormat format,
                                   final long timeSpan,
                                   @TimeConstants.Unit final int unit) {
        if (format == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "format不能为空！");
        }
        return millis2String(millis + timeSpan2Millis(timeSpan, unit), format);
    }

    /**
     * 字符串获取与给定时间等于时间差的默认格式字符串
     *
     * @param time     字符串格式时间
     * @param timeSpan 时间戳格式时间差
     * @param unit     时间单位常量
     *                 <ul>
     *                 <li>{@link TimeConstants#MSEC}</li>
     *                 <li>{@link TimeConstants#SEC }</li>
     *                 <li>{@link TimeConstants#MIN }</li>
     *                 <li>{@link TimeConstants#HOUR}</li>
     *                 <li>{@link TimeConstants#DAY }</li>
     *                 </ul>
     * @return 字符串格式时间
     */
    public static String getString(@NonNull final String time,
                                   final long timeSpan,
                                   @TimeConstants.Unit final int unit) throws ParseException {
        if (StringUtils.isEmpty(time)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "time不能为空！");
        }
        return getString(time, getDefaultFormat(), timeSpan, unit);
    }

    /**
     * 字符串获取与给定时间等于时间差的固定格式字符串
     *
     * @param time     字符串格式时间
     * @param format   时间格式"new SimpleDateFormat("yyyy年MM月dd日-HH:mm:ss", Locale.CHINESE);"
     * @param timeSpan 时间戳格式时间差
     * @param unit     时间单位常量
     *                 <ul>
     *                 <li>{@link TimeConstants#MSEC}</li>
     *                 <li>{@link TimeConstants#SEC }</li>
     *                 <li>{@link TimeConstants#MIN }</li>
     *                 <li>{@link TimeConstants#HOUR}</li>
     *                 <li>{@link TimeConstants#DAY }</li>
     *                 </ul>
     * @return 字符串格式时间
     */
    public static String getString(@NonNull final String time,
                                   @NonNull final DateFormat format,
                                   final long timeSpan,
                                   @TimeConstants.Unit final int unit) throws ParseException {
        if (StringUtils.isEmpty(time)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "time不能为空！");
        }
        if (format == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "format不能为空！");
        }
        return millis2String(string2Millis(time, format) + timeSpan2Millis(timeSpan, unit), format);
    }

    /**
     * 日期获取与给定时间等于时间差的默认格式字符串
     *
     * @param date     日期格式时间
     * @param timeSpan 时间戳格式时间差
     * @param unit     时间单位常量
     *                 <ul>
     *                 <li>{@link TimeConstants#MSEC}</li>
     *                 <li>{@link TimeConstants#SEC }</li>
     *                 <li>{@link TimeConstants#MIN }</li>
     *                 <li>{@link TimeConstants#HOUR}</li>
     *                 <li>{@link TimeConstants#DAY }</li>
     *                 </ul>
     * @return 字符串格式时间
     */
    public static String getString(@NonNull final Date date,
                                   final long timeSpan,
                                   @TimeConstants.Unit final int unit) {
        if (date == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "date不能为空！");
        }
        return getString(date, getDefaultFormat(), timeSpan, unit);
    }

    /**
     * 时间戳获取与给定时间等于时间差的固定格式字符串
     *
     * @param date     日期格式时间
     * @param format   时间格式"new SimpleDateFormat("yyyy年MM月dd日-HH:mm:ss", Locale.CHINESE);"
     * @param timeSpan 时间戳格式时间差
     * @param unit     时间单位常量
     *                 <ul>
     *                 <li>{@link TimeConstants#MSEC}</li>
     *                 <li>{@link TimeConstants#SEC }</li>
     *                 <li>{@link TimeConstants#MIN }</li>
     *                 <li>{@link TimeConstants#HOUR}</li>
     *                 <li>{@link TimeConstants#DAY }</li>
     *                 </ul>
     * @return 字符串格式时间
     */
    public static String getString(@NonNull final Date date,
                                   @NonNull final DateFormat format,
                                   final long timeSpan,
                                   @TimeConstants.Unit final int unit) {
        if (date == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "date不能为空！");
        }
        if (format == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "format不能为空！");
        }
        return millis2String(date2Millis(date) + timeSpan2Millis(timeSpan, unit), format);
    }

    /**
     * 时间戳获取与给定时间等于时间差的日期
     *
     * @param millis   时间戳格式时间
     * @param timeSpan 时间戳格式时间差
     * @param unit     时间单位常量
     *                 <ul>
     *                 <li>{@link TimeConstants#MSEC}</li>
     *                 <li>{@link TimeConstants#SEC }</li>
     *                 <li>{@link TimeConstants#MIN }</li>
     *                 <li>{@link TimeConstants#HOUR}</li>
     *                 <li>{@link TimeConstants#DAY }</li>
     *                 </ul>
     * @return 日期格式时间
     */
    public static Date getDate(final long millis,
                               final long timeSpan,
                               @TimeConstants.Unit final int unit) {
        return millis2Date(millis + timeSpan2Millis(timeSpan, unit));
    }

    /**
     * 默认格式字符串获取与给定时间等于时间差的日期
     *
     * @param time     字符串格式时间
     * @param timeSpan 时间戳格式时间差
     * @param unit     时间单位常量
     *                 <ul>
     *                 <li>{@link TimeConstants#MSEC}</li>
     *                 <li>{@link TimeConstants#SEC }</li>
     *                 <li>{@link TimeConstants#MIN }</li>
     *                 <li>{@link TimeConstants#HOUR}</li>
     *                 <li>{@link TimeConstants#DAY }</li>
     *                 </ul>
     * @return 日期格式时间
     */
    public static Date getDate(@NonNull final String time,
                               final long timeSpan,
                               @TimeConstants.Unit final int unit) throws ParseException {
        if (StringUtils.isEmpty(time)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "time不能为空！");
        }
        return getDate(time, getDefaultFormat(), timeSpan, unit);
    }

    /**
     * 固定格式字符串获取与给定时间等于时间差的日期
     *
     * @param time     字符串格式时间
     * @param format   时间格式"new SimpleDateFormat("yyyy年MM月dd日-HH:mm:ss", Locale.CHINESE);"
     * @param timeSpan 时间戳格式时间差
     * @param unit     时间单位常量
     *                 <ul>
     *                 <li>{@link TimeConstants#MSEC}</li>
     *                 <li>{@link TimeConstants#SEC }</li>
     *                 <li>{@link TimeConstants#MIN }</li>
     *                 <li>{@link TimeConstants#HOUR}</li>
     *                 <li>{@link TimeConstants#DAY }</li>
     *                 </ul>
     * @return 日期格式时间
     */
    public static Date getDate(@NonNull final String time,
                               @NonNull final DateFormat format,
                               final long timeSpan,
                               @TimeConstants.Unit final int unit) throws ParseException {
        if (StringUtils.isEmpty(time)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "time不能为空！");
        }
        if (format == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "format不能为空！");
        }
        return millis2Date(string2Millis(time, format) + timeSpan2Millis(timeSpan, unit));
    }

    /**
     * 日期获取与给定时间等于时间差的日期
     *
     * @param date     日期格式时间
     * @param timeSpan 时间戳格式时间差
     * @param unit     时间单位常量
     *                 <ul>
     *                 <li>{@link TimeConstants#MSEC}</li>
     *                 <li>{@link TimeConstants#SEC }</li>
     *                 <li>{@link TimeConstants#MIN }</li>
     *                 <li>{@link TimeConstants#HOUR}</li>
     *                 <li>{@link TimeConstants#DAY }</li>
     *                 </ul>
     * @return 日期格式时间
     */
    public static Date getDate(@NonNull final Date date,
                               final long timeSpan,
                               @TimeConstants.Unit final int unit) {
        if (date == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "date不能为空！");
        }
        return millis2Date(date2Millis(date) + timeSpan2Millis(timeSpan, unit));
    }

    /**
     * 时间戳获取与当前等于时间差的时间戳
     *
     * @param timeSpan 时间戳格式时间差
     * @param unit     时间单位常量
     *                 <ul>
     *                 <li>{@link TimeConstants#MSEC}</li>
     *                 <li>{@link TimeConstants#SEC }</li>
     *                 <li>{@link TimeConstants#MIN }</li>
     *                 <li>{@link TimeConstants#HOUR}</li>
     *                 <li>{@link TimeConstants#DAY }</li>
     *                 </ul>
     * @return 时间戳格式时间
     */
    public static long getMillisByNow(final long timeSpan, @TimeConstants.Unit final int unit) {
        return getMillis(getNowMills(), timeSpan, unit);
    }

    /**
     * 时间戳获取与当前等于时间差的默认格式字符串
     *
     * @param timeSpan 时间戳格式时间差
     * @param unit     时间单位常量
     *                 <ul>
     *                 <li>{@link TimeConstants#MSEC}</li>
     *                 <li>{@link TimeConstants#SEC }</li>
     *                 <li>{@link TimeConstants#MIN }</li>
     *                 <li>{@link TimeConstants#HOUR}</li>
     *                 <li>{@link TimeConstants#DAY }</li>
     *                 </ul>
     * @return 字符串格式时间
     */
    public static String getStringByNow(final long timeSpan, @TimeConstants.Unit final int unit) {
        return getStringByNow(timeSpan, getDefaultFormat(), unit);
    }

    /**
     * 时间戳获取与当前等于时间差的固定格式字符串
     *
     * @param timeSpan 时间戳格式时间差
     * @param format   时间格式"new SimpleDateFormat("yyyy年MM月dd日-HH:mm:ss", Locale.CHINESE);"
     * @param unit     时间单位常量
     *                 <ul>
     *                 <li>{@link TimeConstants#MSEC}</li>
     *                 <li>{@link TimeConstants#SEC }</li>
     *                 <li>{@link TimeConstants#MIN }</li>
     *                 <li>{@link TimeConstants#HOUR}</li>
     *                 <li>{@link TimeConstants#DAY }</li>
     *                 </ul>
     * @return 字符串格式时间
     */
    public static String getStringByNow(final long timeSpan,
                                        @NonNull final DateFormat format,
                                        @TimeConstants.Unit final int unit) {
        if (format == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "format不能为空！");
        }
        return getString(getNowMills(), format, timeSpan, unit);
    }

    /**
     * 时间戳获取与当前等于时间差的日期
     *
     * @param timeSpan 时间戳格式时间差
     * @param unit     时间单位常量
     *                 <ul>
     *                 <li>{@link TimeConstants#MSEC}</li>
     *                 <li>{@link TimeConstants#SEC }</li>
     *                 <li>{@link TimeConstants#MIN }</li>
     *                 <li>{@link TimeConstants#HOUR}</li>
     *                 <li>{@link TimeConstants#DAY }</li>
     *                 </ul>
     * @return 日期格式时间
     */
    public static Date getDateByNow(final long timeSpan, @TimeConstants.Unit final int unit) {
        return getDate(getNowMills(), timeSpan, unit);
    }

    /**
     * 默认格式字符串判断是否是今天
     *
     * @param time 字符串格式时间
     * @return {@code true}: 是<br>{@code false}: 不是
     */
    public static boolean isToday(@NonNull final String time) throws ParseException {
        if (StringUtils.isEmpty(time)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "time不能为空！");
        }
        return isToday(string2Millis(time, getDefaultFormat()));
    }

    /**
     * 固定格式字符串判断是否是今天
     *
     * @param time   字符串格式时间
     * @param format 时间格式"new SimpleDateFormat("yyyy年MM月dd日-HH:mm:ss", Locale.CHINESE);"
     * @return {@code true}: 是<br>{@code false}: 不是
     */
    public static boolean isToday(@NonNull final String time, @NonNull final DateFormat format) throws ParseException {
        if (StringUtils.isEmpty(time)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "time不能为空！");
        }
        if (format == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "format不能为空！");
        }
        return isToday(string2Millis(time, format));
    }

    /**
     * 日期判断是否是今天
     *
     * @param date 日期格式时间
     * @return {@code true}: 是<br>{@code false}: 不是
     */
    public static boolean isToday(@NonNull final Date date) {
        if (date == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "date不能为空！");
        }
        return isToday(date.getTime());
    }

    /**
     * 时间戳判断是否是今天
     *
     * @param millis 时间戳
     * @return {@code true}: 是<br>{@code false}: 不是
     */
    public static boolean isToday(final long millis) {
        long wee = getWeeOfToday();
        return millis >= wee && millis < wee + TimeConstants.DAY;
    }

    /**
     * 默认格式字符串判断是否是闰年
     *
     * @param time 字符串格式时间
     * @return {@code true}: 是<br>{@code false}: 不是
     */
    public static boolean isLeapYear(@NonNull final String time) throws ParseException {
        if (StringUtils.isEmpty(time)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "time不能为空！");
        }
        return isLeapYear(string2Date(time, getDefaultFormat()));
    }

    /**
     * 固定格式字符串判断是否是闰年
     *
     * @param time   字符串格式时间
     * @param format 时间格式"new SimpleDateFormat("yyyy年MM月dd日-HH:mm:ss", Locale.CHINESE);"
     * @return {@code true}: 是<br>{@code false}: 不是
     */
    public static boolean isLeapYear(@NonNull final String time, @NonNull final DateFormat format) throws ParseException {
        if (StringUtils.isEmpty(time)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "time不能为空！");
        }
        if (format == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "format不能为空！");
        }
        return isLeapYear(string2Date(time, format));
    }

    /**
     * 日期判断是否是闰年
     *
     * @param date 日期格式时间
     * @return {@code true}: 是<br>{@code false}: 不是
     */
    public static boolean isLeapYear(@NonNull final Date date) {
        if (date == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "date不能为空！");
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        return isLeapYear(year);
    }

    /**
     * 时间戳判断是否是闰年
     *
     * @param millis 时间戳
     * @return {@code true}: 是<br>{@code false}: 不是
     */
    public static boolean isLeapYear(final long millis) {
        return isLeapYear(millis2Date(millis));
    }

    /**
     * 整型判断是否是闰年
     *
     * @param year 年
     * @return {@code true}: 是<br>{@code false}: 不是
     */
    public static boolean isLeapYear(final int year) {
        return year % 4 == 0 && year % 100 != 0 || year % 400 == 0;
    }

    /**
     * 默认格式字符串获取中式星期
     *
     * @param time 字符串格式时间
     * @return 中式星期
     */
    public static String getChineseWeek(@NonNull final String time) throws ParseException {
        if (StringUtils.isEmpty(time)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "time不能为空！");
        }
        return getChineseWeek(string2Date(time, getDefaultFormat()));
    }

    /**
     * 固定格式字符串获取中式星期
     *
     * @param time   字符串格式时间
     * @param format 时间格式"new SimpleDateFormat("yyyy年MM月dd日-HH:mm:ss", Locale.CHINESE);"
     * @return 中式星期
     */
    public static String getChineseWeek(@NonNull final String time, @NonNull final DateFormat format) throws ParseException {
        if (StringUtils.isEmpty(time)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "time不能为空！");
        }
        if (format == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "format不能为空！");
        }
        return getChineseWeek(string2Date(time, format));
    }

    /**
     * 日期获取中式星期
     *
     * @param date 日期格式时间
     * @return 中式星期
     */
    public static String getChineseWeek(@NonNull final Date date) {
        if (date == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "date不能为空！");
        }
        return new SimpleDateFormat("E", Locale.CHINA).format(date);
    }

    /**
     * 时间戳获取中式星期
     *
     * @param millis 时间戳
     * @return 中式星期
     */
    public static String getChineseWeek(final long millis) {
        return getChineseWeek(new Date(millis));
    }

    /**
     * 默认格式字符串获取美式星期
     *
     * @param time 字符串格式时间
     * @return 美式星期
     */
    public static String getUSWeek(@NonNull final String time) throws ParseException {
        if (StringUtils.isEmpty(time)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "time不能为空！");
        }
        return getUSWeek(string2Date(time, getDefaultFormat()));
    }

    /**
     * 固定格式字符串获取美式星期
     *
     * @param time   字符串格式时间
     * @param format 时间格式"new SimpleDateFormat("yyyy年MM月dd日-HH:mm:ss", Locale.CHINESE);"
     * @return 美式星期
     */
    public static String getUSWeek(@NonNull final String time, @NonNull final DateFormat format) throws ParseException {
        if (StringUtils.isEmpty(time)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "time不能为空！");
        }
        if (format == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "format不能为空！");
        }
        return getUSWeek(string2Date(time, format));
    }

    /**
     * 日期获取美式星期
     *
     * @param date 日期格式时间
     * @return 美式星期
     */
    public static String getUSWeek(@NonNull final Date date) {
        if (date == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "date不能为空！");
        }
        return new SimpleDateFormat("EEEE", Locale.US).format(date);
    }

    /**
     * 时间戳获取美式星期
     *
     * @param millis 时间戳
     * @return 美式星期
     */
    public static String getUSWeek(final long millis) {
        return getUSWeek(new Date(millis));
    }

    /**
     * 默认格式字符串根据日历字段获取值
     *
     * @param time  字符串格式时间
     * @param field 特定日历字段
     *              <ul>
     *              <li>{@link Calendar#ERA}</li>
     *              <li>{@link Calendar#YEAR}</li>
     *              <li>{@link Calendar#MONTH}</li>
     *              <li>...</li>
     *              <li>{@link Calendar#DST_OFFSET}</li>
     *              </ul>
     * @return 特定日历字段的值
     */
    public static int getValueByCalendarField(@NonNull final String time, final int field) throws ParseException {
        if (StringUtils.isEmpty(time)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "time不能为空！");
        }
        return getValueByCalendarField(string2Date(time, getDefaultFormat()), field);
    }

    /**
     * 固定格式字符串根据日历字段获取值
     *
     * @param time   字符串格式时间
     * @param format 时间格式"new SimpleDateFormat("yyyy年MM月dd日-HH:mm:ss", Locale.CHINESE);"
     * @param field  特定日历字段
     *               <ul>
     *               <li>{@link Calendar#ERA}</li>
     *               <li>{@link Calendar#YEAR}</li>
     *               <li>{@link Calendar#MONTH}</li>
     *               <li>...</li>
     *               <li>{@link Calendar#DST_OFFSET}</li>
     *               </ul>
     * @return 特定日历字段的值
     */
    public static int getValueByCalendarField(@NonNull final String time,
                                              @NonNull final DateFormat format,
                                              final int field) throws ParseException {
        if (StringUtils.isEmpty(time)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "time不能为空！");
        }
        if (format == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "format不能为空！");
        }
        return getValueByCalendarField(string2Date(time, format), field);
    }

    /**
     * 日期根据日历字段获取值
     *
     * @param date  日期格式时间
     * @param field 特定日历字段
     *              <ul>
     *              <li>{@link Calendar#ERA}</li>
     *              <li>{@link Calendar#YEAR}</li>
     *              <li>{@link Calendar#MONTH}</li>
     *              <li>...</li>
     *              <li>{@link Calendar#DST_OFFSET}</li>
     *              </ul>
     * @return 特定日历字段的值
     */
    public static int getValueByCalendarField(@NonNull final Date date, final int field) {
        if (date == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "date不能为空！");
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(field);
    }

    /**
     * 时间戳根据日历字段获取值
     *
     * @param millis 时间戳
     * @param field  特定日历字段
     *               <ul>
     *               <li>{@link Calendar#ERA}</li>
     *               <li>{@link Calendar#YEAR}</li>
     *               <li>{@link Calendar#MONTH}</li>
     *               <li>...</li>
     *               <li>{@link Calendar#DST_OFFSET}</li>
     *               </ul>
     * @return 特定日历字段的值
     */
    public static int getValueByCalendarField(final long millis, final int field) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(millis);
        return cal.get(field);
    }

    /**
     * 默认格式字符串获取生肖
     *
     * @param context 上下文
     * @param time    字符串格式时间
     * @return 生肖
     */
    public static String getChineseZodiac(@NonNull Context context, @NonNull final String time) throws ParseException {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context不能为空！");
        }
        if (StringUtils.isEmpty(time)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "time不能为空！");
        }
        return getChineseZodiac(context, string2Date(time, getDefaultFormat()));
    }

    /**
     * 固定格式字符串获取生肖
     *
     * @param context 上下文
     * @param time    字符串格式时间
     * @param format  时间格式"new SimpleDateFormat("yyyy年MM月dd日-HH:mm:ss", Locale.CHINESE);"
     * @return 生肖
     */
    public static String getChineseZodiac(@NonNull Context context, @NonNull final String time, @NonNull final DateFormat format) throws ParseException {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context不能为空！");
        }
        if (StringUtils.isEmpty(time)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "time不能为空！");
        }
        if (format == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "format不能为空！");
        }
        return getChineseZodiac(context, string2Date(time, format));
    }

    /**
     * 日期获取生肖
     *
     * @param context 上下文
     * @param date    日期格式时间
     * @return 生肖
     */
    public static String getChineseZodiac(@NonNull Context context, @NonNull final Date date) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context不能为空！");
        }
        if (date == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "date不能为空！");
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        //生肖集合
        String[] zodiac = StringUtils.getStringArray(context, R.array.time_constants_zodiac);
        return zodiac[cal.get(Calendar.YEAR) % 12];
    }

    /**
     * 时间戳获取生肖
     *
     * @param context 上下文
     * @param millis  时间戳
     * @return 生肖
     */
    public static String getChineseZodiac(@NonNull Context context, final long millis) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context不能为空！");
        }
        return getChineseZodiac(context, millis2Date(millis));
    }

    /**
     * 整型获取生肖
     *
     * @param context 上下文
     * @param year    年
     * @return 生肖
     */
    public static String getChineseZodiac(@NonNull Context context, final int year) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context不能为空！");
        }
        //生肖集合
        String[] zodiac = StringUtils.getStringArray(context, R.array.time_constants_zodiac);
        return zodiac[year % 12];
    }

    /**
     * 默认格式字符串获取星座
     *
     * @param context 上下文
     * @param time    字符串格式时间
     * @return 星座
     */
    public static String getZodiac(@NonNull Context context, @NonNull final String time) throws ParseException {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context不能为空！");
        }
        if (StringUtils.isEmpty(time)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "time不能为空！");
        }
        return getZodiac(context, string2Date(time, getDefaultFormat()));
    }

    /**
     * 固定格式字符串获取星座
     *
     * @param context 上下文
     * @param time    字符串格式时间
     * @param format  时间格式"new SimpleDateFormat("yyyy年MM月dd日-HH:mm:ss", Locale.CHINESE);"
     * @return 星座
     */
    public static String getZodiac(@NonNull Context context, @NonNull final String time, @NonNull final DateFormat format) throws ParseException {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context不能为空！");
        }
        if (StringUtils.isEmpty(time)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "time不能为空！");
        }
        if (format == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "format不能为空！");
        }
        return getZodiac(context, string2Date(time, format));
    }

    /**
     * 日期获取星座
     *
     * @param context 上下文
     * @param date    日期格式时间
     * @return 星座
     */
    public static String getZodiac(@NonNull Context context, @NonNull final Date date) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context不能为空！");
        }
        if (date == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "date不能为空！");
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return getZodiac(context, month, day);
    }

    /**
     * 时间戳获取星座
     *
     * @param context 上下文
     * @param millis  时间戳
     * @return 星座
     */
    public static String getZodiac(@NonNull Context context, final long millis) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context不能为空！");
        }
        return getZodiac(context, millis2Date(millis));
    }

    /**
     * 整型获取星座
     *
     * @param context 上下文
     * @param month   月
     * @param day     日
     * @return 星座
     */
    public static String getZodiac(@NonNull Context context, final int month, final int day) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context不能为空！");
        }
        //星座集合
        String[] constellation = StringUtils.getStringArray(context, R.array.time_constants_constellation);
        return constellation[day >= TimeConstants.ZODIAC_FLAGS[month - 1]
                ? month - 1
                : (month + 10) % 12];
    }

    /**
     * 时间差转时间戳
     *
     * @param timeSpan 时间戳格式时间差
     * @param unit     时间单位常量
     * @return 时间戳格式时间
     */
    private static long timeSpan2Millis(final long timeSpan, @TimeConstants.Unit final int unit) {
        return timeSpan * unit;
    }

    /**
     * 时间戳转时间差
     *
     * @param millis 时间戳格式时间
     * @param unit   时间单位常量
     * @return 时间戳格式时间
     */
    private static long millis2TimeSpan(final long millis, @TimeConstants.Unit final int unit) {
        return millis / unit;
    }

    /**
     * 合适型时间差
     *
     * @param context   上下文
     * @param millis    时间戳格式时间
     * @param precision 时间差精度
     * @return 字符串格式时间
     */
    private static String millis2FitTimeSpan(Context context, long millis, int precision) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context不能为空！");
        }
        if (precision <= 0) {
            return null;
        }
        precision = Math.min(precision, 5);
//        String[] units = {"天", "小时", "分钟", "秒", "毫秒"};
        String[] units = {context.getResources().getString(R.string.time_constants_string_day),
                context.getResources().getString(R.string.time_constants_string_hour),
                context.getResources().getString(R.string.time_constants_string_min),
                context.getResources().getString(R.string.time_constants_string_sec),
                context.getResources().getString(R.string.time_constants_string_msec)};
        if (millis == 0) return 0 + units[precision - 1];
        StringBuilder sb = new StringBuilder();
        if (millis < 0) {
            sb.append("-");
            millis = -millis;
        }
        int[] unitLen = {86400000, 3600000, 60000, 1000, 1};
        for (int i = 0; i < precision; i++) {
            if (millis >= unitLen[i]) {
                long mode = millis / unitLen[i];
                millis -= mode * unitLen[i];
                sb.append(mode).append(units[i]);
            }
        }
        return sb.toString();
    }

}
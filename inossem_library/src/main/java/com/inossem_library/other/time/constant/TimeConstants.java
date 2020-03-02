package com.inossem_library.other.time.constant;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.SimpleDateFormat;

/**
 * @author 郭晓龙
 * @time on 2019/7/15
 * @email xiaolong.guo@inossem.com
 * @describe 时间工具类常量
 */

public class TimeConstants {



    public static final ThreadLocal<SimpleDateFormat> SDF_THREAD_LOCAL = new ThreadLocal<>();
    //判断星座常量
    public static final int[] ZODIAC_FLAGS = {20, 19, 21, 21, 21, 22, 23, 23, 23, 24, 23, 22};
    //毫秒
    public static final int MSEC = 1;
    //1秒=1000毫秒
    public static final int SEC = 1000;
    //1分=60000毫秒
    public static final int MIN = 60000;
    //1小时=3600000毫秒
    public static final int HOUR = 3600000;
    //1天=86400000毫秒
    public static final int DAY = 86400000;

    @IntDef({MSEC, SEC, MIN, HOUR, DAY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Unit {
    }

}
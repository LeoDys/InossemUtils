package com.inossem_library.other.convert.constant;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author 郭晓龙
 * @time on 2019/7/15
 * @email xiaolong.guo@inossem.com
 * @describe 转换工具类内存单位常量
 */

public class ConvertConstants {

    //16进制常量
    public static final char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    //1字节
    public static final int BYTE = 1;
    //1KB=1024字节
    public static final int KB = 1024;
    //1MB=1048576字节
    public static final int MB = 1048576;
    //1GB=1073741824字节
    public static final int GB = 1073741824;

    @IntDef({BYTE, KB, MB, GB})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Unit {
    }
}
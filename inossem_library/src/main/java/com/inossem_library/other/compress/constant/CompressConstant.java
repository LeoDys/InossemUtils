package com.inossem_library.other.compress.constant;

import android.graphics.Bitmap;

/**
 * 压缩常量类
 *
 * @author Leo E-mail:changwen.sun@inossem.com 2019/8/7 10:16
 * @version 1.0.8
 * @since 1.0.8
 */
public class CompressConstant {
    // 默认压缩格式
    public static final Bitmap.CompressFormat COMPRESS_FORMAT = Bitmap.CompressFormat.JPEG;
    // 默认质量值
    public static final int DEFAULT_QUALITY_SIZE = 100;
    // 压缩后的最大大小
    public static final int DEFAULT_MAX_SIZE = 200;
    //目标存储路径
    public static final String INOSSEM_DEFAULT_COMPRESS_TAGPATH = "InossemCompress";
    // 高质量尺寸
    public static final int MAXWIDTH = 768;
    public static final int MAXHEIGHT = 1280;
    // 中质量尺寸
    public static final int WIDTH_NORMAL = 360;
    public static final int HEIGHT_NORMAL = 640;
    // 低质量尺寸
    public static final int WIDTH_SMALL = 270;
    public static final int HEIGHT_SMALL = 480;

}

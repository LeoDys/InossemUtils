package com.zxy.custom_compress.constant;

import android.graphics.Bitmap;

/**
 * 压缩常量类
 * Created by wen40 on 2019/7/18.
 */

public class CompressConstants {
    // 默认压缩格式
    public static final Bitmap.CompressFormat COMPRESS_FORMAT = Bitmap.CompressFormat.JPEG;
    // 默认质量值
    public static final int DEFAULT_QUALITY_SIZE = 100;
    public static final int DEFAULT_MAX_SIZE = 200; // 压缩后的最大大小
    //目标存储路径
    public static final String INOSSEMCOMPRESS_TAGPATH = "InossemCompress";
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

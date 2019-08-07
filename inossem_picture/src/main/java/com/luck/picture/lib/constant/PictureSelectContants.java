package com.luck.picture.lib.constant;

/**
 * 图片选择常量类
 * Created by wen40 on 2019/7/18.
 */

public class PictureSelectContants {
    // 默认requestcode
    public static final int DEFAULT_PICTURE_REQUEST_CODE = 10012;
    // 默认拍照保存路径
    public static final String DEFAULT_PICTURE_INOSSEM_CAMERA = "/InossemCamera";
    // 默认裁剪保存路径
    public static final String DEFAULT_PICTURE_INOSSEM_COMPRESS = "/InossemCompress";
    // 默认最大选取图片张数
    public static final int DEFAULT_PICTURE_MAX_SIZE = 4;
    // 小于这个size kb的不压缩
    public static final int DEFAULT_PICTURE_MIN_NOT_COMPRESS_SIZE = 200;
    // 压缩质量 0-100
    public static final int DEFAULT_PICTURE_COMPRESS_QUALITY = 100;
    // glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
    public static final int DEFAULT_PICTURE_GLIDE_WIDTH = 160;
    public static final int DEFAULT_PICTURE_GLIDE_HEIGHT = 160;
}

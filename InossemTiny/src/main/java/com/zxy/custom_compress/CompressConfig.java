package com.zxy.custom_compress;

import android.content.Context;
import android.graphics.Bitmap;


import com.zxy.custom_compress.constant.CompressConstants;

import java.io.File;
import java.io.IOException;


/**
 * 压缩参数配置类
 * Created by wen40 on 2019/7/17.
 */

public class CompressConfig {
    // 默认宽度
    private int maxWidth = CompressConstants.MAXWIDTH;
    // 默认高度
    private int maxHeight = CompressConstants.MAXHEIGHT;
    // 默认压缩格式
    private Bitmap.CompressFormat compressFormat = CompressConstants.COMPRESS_FORMAT;
    // 默认质量值
    private int quality = CompressConstants.DEFAULT_QUALITY_SIZE;
    private int maxSize = CompressConstants.DEFAULT_MAX_SIZE; // 压缩后的最大大小
    private String tagPath;

    /**
     * 构造函数 初始化压缩文件存储路径
     *
     * @param context 上下文对象
     */
    public CompressConfig(Context context) {
        tagPath = context.getCacheDir().getPath() + File.separator + CompressConstants.INOSSEMCOMPRESS_TAGPATH;
    }

    /**
     * 设置最大宽度
     *
     * @param maxWidth 最大宽度值
     * @return 当前类
     */
    public CompressConfig setMaxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
        return this;
    }

    /**
     * 设置最大高度
     *
     * @param maxHeight 最大高度值
     * @return 当前类
     */
    public CompressConfig setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
        return this;
    }

    /**
     * 压缩格式  Bitmap.CompressFormat.JPEG
     * Bitmap.CompressFormat.PNG
     * Bitmap.CompressFormat.WEBP
     *
     * @param compressFormat 压缩格式
     * @return 当前类
     */
    public CompressConfig setCompressFormat(Bitmap.CompressFormat compressFormat) {
        this.compressFormat = compressFormat;
        return this;
    }

    /**
     * 压缩质量
     *
     * @param quality 质量(1,100] 大于0  小于等于100
     * @return 当前类
     */
    public CompressConfig setQuality(int quality) {
        this.quality = quality;
        return this;
    }

    /**
     * 压缩存储路径
     *
     * @param tagPath 存储路径
     * @return 当前类
     */
    public CompressConfig setTagPath(String tagPath) {
        this.tagPath = tagPath;
        return this;
    }

    /**
     * 舍弃
     //     * @param DEFAULT_MAX_SIZE
     * @return
     */
//    public CompressConfig setMaxSize(int DEFAULT_MAX_SIZE) {
//        this.DEFAULT_MAX_SIZE = DEFAULT_MAX_SIZE;
//        return this;
//    }

    /**
     * 压缩文件
     *
     * @param imageFile 源文件
     * @return 目标文件
     * @throws IOException 写入文件异常
     */
    public File compressToFile(File imageFile) throws IOException {
        return compressToFile(imageFile, imageFile.getName());
    }

    /**
     * 压缩文件
     *
     * @param imageFile          源文件
     * @param compressedFileName 目标文件名
     * @return 目标文件
     * @throws IOException 写入文件异常
     */
    public File compressToFile(File imageFile, String compressedFileName) throws IOException {
        return FileCompress.qualitySizeCompress(imageFile, quality, maxWidth, maxHeight,
                tagPath + File.separator + compressedFileName, compressFormat);
    }

    /**
     * 压缩文件为bitmap
     *
     * @param imageFile 源文件
     * @return 目标Bitmap
     * @throws IOException 写入文件异常
     */
    public Bitmap compressToBitmap(File imageFile) throws IOException {
        return BitmapCompress.qualitySizeCompress(imageFile, quality, maxWidth, maxHeight, compressFormat);
    }

}

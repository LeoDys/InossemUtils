package com.zxy.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import com.zxy.custom_compress.constant.CompressConstants;
import com.zxy.tiny.common.TinyException;

import java.io.File;

/**
 * Tiny压缩配置属性类  赋予默认值
 * Created by wen40 on 2019/7/17.
 */

public class TinyConfig {
    // 默认宽度 负数代表不给默认值 按照系统的压缩比例走
    private final int DEFAULT_MAXWIDTH = -1;
    // 默认高度 负数代表不给默认值 按照系统的压缩比例走
    private final int DEFAULT_MAXHEIGHT = -1;
    // 压缩后最大宽度
    private int maxWidth;
    // 压缩后最大高度
    private int maxHeight;
    // 压缩后的 Bitmap.ARGB 值 Bitmap.Config.ARGB_4444 Bitmap.Config.ARGB_8888 Bitmap.Config.RGB_565
    private Bitmap.Config config;
    // ？
    private String outfile;
    // 压缩质量
    private int quality;
    // 压缩后是否保存
    private boolean isKeepSampling;
    // 压缩后file的大小 kb
    private int fileSize;
    // 压缩后存储的路径
    private String compressDirectory;

    /**
     * 构造函数中赋默认值
     *
     * @param context 上下文
     */
    public TinyConfig(Context context) {
        maxWidth = DEFAULT_MAXWIDTH;
        maxHeight = DEFAULT_MAXHEIGHT;
        config = Bitmap.Config.ARGB_8888;
        outfile = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED) ? Environment.getExternalStorageDirectory() + File.separator + CompressConstants.INOSSEMCOMPRESS_TAGPATH
                : context.getCacheDir().getPath() + File.separator + CompressConstants.INOSSEMCOMPRESS_TAGPATH;
        quality = CompressConstants.DEFAULT_QUALITY_SIZE;
        isKeepSampling = false;
        fileSize = CompressConstants.DEFAULT_MAX_SIZE;
        compressDirectory = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED) ? Environment.getExternalStorageDirectory() + File.separator + CompressConstants.INOSSEMCOMPRESS_TAGPATH
                : context.getCacheDir().getPath() + File.separator + CompressConstants.INOSSEMCOMPRESS_TAGPATH;
    }

    /**
     * 压缩有文件的最大宽
     *
     * @param maxWidth
     * @return 当前类
     */
    public TinyConfig setMaxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
        return this;
    }

    /**
     * 压缩有文件的最大高
     *
     * @param maxHeight
     * @return 当前类
     */
    public TinyConfig setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
        return this;
    }

    /**
     * 压缩有文件的kb大小
     *
     * @param quality 文件的kb大小(0,100] 大于0小于等于100
     * @return 当前类
     */
    public TinyConfig setQuality(int quality) {
        if (quality <= 0 || quality > 100) {
            throw new TinyException.TidyParamterException("setQuality <= 0 || setQuality > 100");
        }
        this.quality = quality;
        return this;
    }

    /**
     * 压缩的ARGB
     *
     * @param config Bitmap.Config.ARGB_8888 Bitmap.Config.RGB_565  Bitmap.Config.ARGB_4444
     * @return 当前类
     */
    public TinyConfig setConfig(Bitmap.Config config) {
        this.config = config;
        return this;
    }

    /**
     * 暂未确定参数
     *
     * @param outfile
     * @return 当前类
     */
    public TinyConfig setOutfile(String outfile) {
        this.outfile = outfile;
        return this;
    }

    /**
     * 是否保持原有尺寸压缩
     *
     * @return 当前类
     */
    public TinyConfig setKeepSampling(boolean keepSampling) {
        isKeepSampling = keepSampling;
        return this;
    }

    /**
     * 压缩后文件大小 kb
     *
     * @return 当前类
     */
    public TinyConfig setFileSize(int fileSize) {
        this.fileSize = fileSize;
        return this;
    }

    /**
     * 压缩文件存储文件夹
     *
     * @param compressDirectory 文件夹地址
     * @return 当前类
     */
    public TinyConfig setCompressDirectory(String compressDirectory) {
        this.compressDirectory = compressDirectory;
        return this;
    }

    /**
     * 压缩后存储的路径
     *
     * @return 存储的路径
     */
    public String getCompressDirectory() {
        return compressDirectory;
    }

    /**
     * 压缩后最大宽度
     *
     * @return 宽度值
     */
    public int getMaxWidth() {
        return maxWidth;
    }

    /**
     * 压缩后最大高度
     *
     * @return 高度值
     */
    public int getMaxHeight() {
        return maxHeight;
    }

    /**
     * 压缩后的 Bitmap.ARGB值
     *
     * @return Bitmap.ARGB值
     */
    public Bitmap.Config getConfig() {
        return config;
    }

    public String getOutfile() {
        return outfile;
    }

    /**
     * 压缩质量
     *
     * @return 质量值
     */
    public int getQuality() {
        return quality;
    }

    /**
     * 是否保持原数据源图片的宽高
     *
     * @return true-是  false-坚持传入的尺寸或者压缩推荐尺寸
     */
    public boolean isKeepSampling() {
        return isKeepSampling;
    }

    /**
     * 压缩后的文件大小 kb
     *
     * @return 文件大小  kb
     */
    public int getFileSize() {
        return fileSize;
    }
}

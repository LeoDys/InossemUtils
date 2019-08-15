package com.inossem_library.other.compress.config;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.text.TextUtils;

import com.inossem_library.exception.InossemException;
import com.inossem_library.exception.constant.ExceptionEnum;
import com.inossem_library.other.compress.constant.CompressConstant;
import com.inossem_library.other.compress.util.FileCacheUtil;

import java.io.File;

/**
 * Tiny压缩配置属性类  赋予默认值
 *
 * @author Leo E-mail:changwen.sun@inossem.com 2019/8/7 10:16
 * @version 1.0.8
 * @since 1.0.8
 */

public class CompressConfig {
    // 默认宽度 负数代表不给默认值 按照系统的压缩比例走
    private final int DEFAULT_MAXWIDTH = -1;
    // 默认高度 负数代表不给默认值 按照系统的压缩比例走
    private final int DEFAULT_MAXHEIGHT = -1;
    private final Context context;
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
    private int compreeToSize;
    // 压缩后存储的路径
    private String compressDirectory;

    /**
     * 构造函数中赋默认值
     *
     * @param context 上下文
     */
    public CompressConfig(Context context) {
        this.context = context;
        maxWidth = DEFAULT_MAXWIDTH;
        maxHeight = DEFAULT_MAXHEIGHT;
        config = Bitmap.Config.ARGB_8888;
        outfile = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED) ? Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + CompressConstant.INOSSEM_DEFAULT_COMPRESS_TAGPATH
                : context.getCacheDir().getPath() + File.separator + CompressConstant.INOSSEM_DEFAULT_COMPRESS_TAGPATH;

//        outfile = FileCacheUtil.getDiskCacheDir(context, CompressConstant.INOSSEM_DEFAULT_COMPRESS_TAGPATH);

        quality = CompressConstant.DEFAULT_QUALITY_SIZE;
        isKeepSampling = false;
        compreeToSize = CompressConstant.DEFAULT_MAX_SIZE;
//        compressDirectory = FileCacheUtil.getDiskCacheDir(context, CompressConstant.INOSSEM_DEFAULT_COMPRESS_TAGPATH);
        compressDirectory = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED) ? Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + CompressConstant.INOSSEM_DEFAULT_COMPRESS_TAGPATH
                : context.getCacheDir().getPath() + File.separator + CompressConstant.INOSSEM_DEFAULT_COMPRESS_TAGPATH;
        File dir = new File(compressDirectory);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    /**
     * 压缩有文件的最大宽
     *
     * @param maxWidth
     * @return 当前类
     */
    public CompressConfig setMaxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
        return this;
    }

    /**
     * 压缩有文件的最大高
     *
     * @param maxHeight
     * @return 当前类
     */
    public CompressConfig setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
        return this;
    }

    /**
     * 压缩有文件的kb大小
     *
     * @param quality 文件的kb大小(0,100] 大于0小于等于100
     * @return 当前类
     */
    public CompressConfig setQuality(int quality) {
        if (quality <= 0 || quality > 100) {
            throw new InossemException(ExceptionEnum.ILLEGAL_PARAMS_RANGE, "setQuality <= 0 || setQuality > 100");
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
    public CompressConfig setConfig(Bitmap.Config config) {
        this.config = config;
        return this;
    }

    /**
     * 暂未确定参数
     *
     * @param outfile
     * @return 当前类
     */
//    public CompressConfig setOutfile(String outfile) {
//        this.outfile = outfile;
//        return this;
//    }

    /**
     * 是否保持原有尺寸压缩
     *
     * @return 当前类
     */
    public CompressConfig setKeepSampling(boolean keepSampling) {
        isKeepSampling = keepSampling;
        return this;
    }

    /**
     * 压缩后文件大小 kb
     *
     * @return 当前类
     */
    public CompressConfig setCompreeToSize(int compreeToSize) {
        if (compreeToSize <= 0) {
            throw new InossemException(ExceptionEnum.ILLEGAL_PARAMS_RANGE, "compressToSize must bigger than zero");
        }
        this.compreeToSize = compreeToSize;
        return this;
    }

    /**
     * 压缩文件存储文件夹
     *
     * @param compressDirectory 文件夹地址
     * @return 当前类
     */
    public CompressConfig setCompressDirectory(String compressDirectory) {
        if (TextUtils.isEmpty(compressDirectory) || compressDirectory.trim().length() == 0) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "compressDirectory can not be null");
        }
        this.compressDirectory = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED) ? Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + compressDirectory
                : context.getCacheDir().getPath() + File.separator + compressDirectory;
        File dir = new File(this.compressDirectory);
        if (!dir.exists()) {
            dir.mkdirs();
        }
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
    public int getCompreeToSize() {
        return compreeToSize;
    }
}

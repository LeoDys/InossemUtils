package com.inossem_library.other.compress.util;

import android.graphics.Bitmap;

import com.inossem_library.exception.InossemException;
import com.inossem_library.exception.constant.ExceptionEnum;
import com.inossem_library.other.compress.config.CompressConfig;
import com.zxy.tiny.Tiny;
import com.zxy.tiny.callback.BitmapCallback;
import com.zxy.tiny.callback.FileBatchCallback;
import com.zxy.tiny.callback.FileCallback;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * 图片压缩工具类
 * @author      Leo E-mail:changwen.sun@inossem.com 2019/8/7 10:18
 * @version     1.0.8
 * @since       1.0.8
 */
public class CompressUtils {
    /**
     * 压缩单个文件回调File地址
     */
    public interface TidyCompressSingleFileListener {
        /**
         * 压缩单个文件回调
         *
         * @param outfile File地址
         */
        void compressCallBack(String outfile);
    }

    /**
     * 压缩单个文件回调bitmap对象
     */
    public interface TidyCompressSingleBitmapListener {
        /**
         * 压缩单个文件回调
         *
         * @param bitmap bitmap对象
         */
        void compressCallBack(Bitmap bitmap);
    }

    /**
     * 压缩多个文件回调file path集合
     */
    public interface TidyCompressFilesListener {
        /**
         * 压缩多个文件回调
         *
         * @param outfiles file path集合
         */
        void compressCallBack(List<String> outfiles);
    }

    /**
     * 压缩多个文件回调bitmap集合
     */
    public interface TidyCompressBitmapsListener {
        /**
         * 压缩多个文件回调
         *
         * @param bitmaps bitmap集合
         */
        void compressCallBack(List<Bitmap> bitmaps);
    }

    /**
     * file压缩成bitmap
     *
     * @param file     源文件
     * @param config   配置参数 new对象传入有默认参数设置
     * @param listener 压缩回调
     */
    public static void bitmapCallBack(File file, CompressConfig config, final TidyCompressSingleBitmapListener listener) {
        Tiny.BitmapCompressOptions options = new Tiny.BitmapCompressOptions();
        options.config = config.getConfig();
        options.width = config.getMaxWidth();
        options.height = config.getMaxHeight();
        Tiny.getInstance().source(file).asBitmap().withOptions(options).compress(new BitmapCallback() {
            @Override
            public void callback(boolean isSuccess, Bitmap bitmap, Throwable t) {
                if (isSuccess) {
                    listener.compressCallBack(bitmap);
                } else {
                    listener.compressCallBack(null);
                }
            }
        });
    }

    /**
     * file压缩成file
     *
     * @param file     源文件
     * @param config   配置参数 new对象传入有默认参数设置
     * @param listener 压缩回调
     */
    public static void fileCallBack(File file, CompressConfig config, final TidyCompressSingleFileListener listener) {
        Tiny.FileCompressOptions options = new Tiny.FileCompressOptions();
        CompressConvertUtils.setOptions(config, options);
        Tiny.getInstance().source(file).asFile().withOptions(options).compress(new FileCallback() {
            @Override
            public void callback(boolean isSuccess, String outfile, Throwable t) {
                listener.compressCallBack(outfile);
            }
        });
    }

    /**
     * 多张图片的压缩压缩成file
     *
     * @param files    源文件
     * @param config   配置参数 new对象传入有默认参数设置
     * @param listener 压缩回调
     */
    public static void filesCallBack(List<File> files, CompressConfig config, final TidyCompressFilesListener listener) {
        if (config == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "CompressConfig null,please check");
        }
        File[] fileArray = CompressConvertUtils.getFilesArray(files);
        if (fileArray == null) return;

        Tiny.FileCompressOptions options = new Tiny.FileCompressOptions();
        CompressConvertUtils.setOptions(config, options);
        Tiny.getInstance().source(fileArray).batchAsFile().withOptions(options).batchCompress(new FileBatchCallback() {
            @Override
            public void callback(boolean isSuccess, String[] outfiles, Throwable t) {
                if (isSuccess) {
                    listener.compressCallBack(Arrays.asList(outfiles));
                } else {
                    listener.compressCallBack(null);
                }
            }
        });
    }
}

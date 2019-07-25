package com.zxy.util;

import com.zxy.tiny.Tiny;

import java.io.File;
import java.util.List;

/**
 * @author Leo E-mail:changwen.sun@inossem.com 2019/7/25 20:09
 * @version 1.0.0
 * @since JDK-1.8
 */

public class Utils {
    /**
     * 获取文件数组
     *
     * @param files 文件list
     * @return 文件数组
     */
    public static File[] getFilesArray(List<File> files) {
        File[] fileArray = null;
        if (files == null || files.isEmpty()) {
            return null;
        } else {
            fileArray = files.toArray(new File[files.size()]);
        }
        return fileArray;
    }

    /**
     * 设置自定义属性值
     *
     * @param config  自定义属性类
     * @param options TIny属性类
     */
    public static void setOptions(TinyConfig config, Tiny.FileCompressOptions options) {
        options.size = config.getFileSize();
        options.width = config.getMaxWidth();
        options.height = config.getMaxHeight();
        options.isKeepSampling = config.isKeepSampling();
        options.quality = config.getQuality();
        options.compressDirectory = config.getCompressDirectory();
    }
}

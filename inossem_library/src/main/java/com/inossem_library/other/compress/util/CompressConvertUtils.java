package com.inossem_library.other.compress.util;

import com.inossem_library.other.compress.config.CompressConfig;
import com.zxy.tiny.Tiny;

import java.io.File;
import java.util.List;

/**
 * 压缩转换工具类
 *
 * @author Leo E-mail:changwen.sun@inossem.com 2019/8/7 10:18
 * @version 1.0.8
 * @since 1.0.8
 */
public class CompressConvertUtils {
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
    public static Tiny.FileCompressOptions setOptions(CompressConfig config, Tiny.FileCompressOptions options) {
        options.size = config.getCompreeToSize();
        options.width = config.getMaxWidth();
        options.height = config.getMaxHeight();
        options.isKeepSampling = config.isKeepSampling();
        options.quality = config.getQuality();
        options.compressDirectory = config.getCompressDirectory();
        return options;
    }
}

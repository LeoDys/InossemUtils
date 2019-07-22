package com.zxy.custom_compress;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import com.zxy.custom_compress.constant.CompressConstants;

import java.io.File;
import java.io.IOException;

/**
 * 压缩工具类 推荐默认值
 * Created by wen40 on 2019/7/17.
 */

public class CompressUtils {

    static String getRootPath() {
        String rootPath = Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator + CompressConstants.INOSSEMCOMPRESS_TAGPATH;
        return rootPath;
    }

    /**
     * 正常尺寸File
     *
     * @param context
     * @param file
     * @return
     */
    public static File compressImageToFileNormal(Context context, File file) {
        try {
            return CompressBuildConfigUtil.compressImageToFile(context, file, CompressConstants.WIDTH_NORMAL, CompressConstants.HEIGHT_NORMAL, Bitmap.CompressFormat.WEBP, CompressConstants.DEFAULT_QUALITY_SIZE, getRootPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 低尺寸图片File
     *
     * @param context
     * @param file
     * @return
     */
    public static File compressImageToFileSmall(Context context, File file) {
        try {
            return CompressBuildConfigUtil.compressImageToFile(context, file, CompressConstants.WIDTH_SMALL, CompressConstants.HEIGHT_SMALL, Bitmap.CompressFormat.WEBP, CompressConstants.DEFAULT_QUALITY_SIZE, getRootPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 高尺寸文件File
     *
     * @param context
     * @param file
     * @return
     */
    public static File compressImageToFileLarge(Context context, File file) {
        try {
            return CompressBuildConfigUtil.compressImageToFile(context, file, CompressConstants.MAXWIDTH, CompressConstants.MAXHEIGHT, Bitmap.CompressFormat.WEBP, CompressConstants.DEFAULT_QUALITY_SIZE, getRootPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 正常尺寸bitmap
     *
     * @param context
     * @param file
     * @return
     */
    public static Bitmap compressImageToBitmapNormal(Context context, File file) {
        try {
            return CompressBuildConfigUtil.compressImageToBitmap(context, file, CompressConstants.WIDTH_NORMAL, CompressConstants.HEIGHT_NORMAL, Bitmap.CompressFormat.WEBP, CompressConstants.DEFAULT_QUALITY_SIZE, getRootPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 低尺寸bitmap
     *
     * @param context
     * @param file
     * @return
     */
    public static Bitmap compressImageToBitmapSmall(Context context, File file) {
        try {
            return CompressBuildConfigUtil.compressImageToBitmap(context, file, CompressConstants.WIDTH_SMALL, CompressConstants.HEIGHT_SMALL, Bitmap.CompressFormat.WEBP, CompressConstants.DEFAULT_QUALITY_SIZE, getRootPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 高尺寸bitmap
     *
     * @param context
     * @param file
     * @return
     */
    public static Bitmap compressImageToBitmapLarge(Context context, File file) {
        try {
            return CompressBuildConfigUtil.compressImageToBitmap(context, file, CompressConstants.MAXWIDTH, CompressConstants.MAXHEIGHT, Bitmap.CompressFormat.WEBP, 100, getRootPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

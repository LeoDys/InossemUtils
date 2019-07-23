package com.zxy.custom_compress;

import android.content.Context;
import android.graphics.Bitmap;

import java.io.File;
import java.io.IOException;

/**
 * Created by wen40 on 2019/7/17.
 */

public class CompressBuildConfigUtil {

    //图片文件压缩成File
    public static File compressImageToFile(Context context, File imageFile, int maxWidth, int maxHeight, Bitmap.CompressFormat compressFormat,
                                           int quality, String tagPath) throws IOException {
        return new CompressConfig(context)
                .setMaxWidth(maxWidth)
                .setMaxHeight(maxHeight)
                .setCompressFormat(compressFormat)
                .setQuality(quality)
                .setTagPath(tagPath)
                .compressToFile(imageFile);
    }

    //图片文件压缩成Bitmap
    public static Bitmap compressImageToBitmap(Context context, File imageFile, int maxWidth, int maxHeight, Bitmap.CompressFormat compressFormat
            , int quality, String tagPath) throws IOException {
        return new CompressConfig(context)
                .setMaxWidth(maxWidth)
                .setMaxHeight(maxHeight)
                .setCompressFormat(compressFormat)
                .setQuality(quality)
                .setTagPath(tagPath)
                .compressToBitmap(imageFile);
    }

}

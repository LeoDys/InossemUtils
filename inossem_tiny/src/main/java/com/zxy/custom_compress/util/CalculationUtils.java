package com.zxy.custom_compress.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.text.TextUtils;

import com.zxy.custom_compress.exception.InossemCompressException;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * 压缩计算工具类
 * Created by wen40 on 2019/7/17.
 */

public class CalculationUtils {
    /**
     * 算法获取最适应宽高
     *
     * @param options   获取Bitmap属性使用
     * @param reqWidth  期望宽
     * @param reqHeight 期望高
     * @return 宽高统一除以的int值
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    /**
     * 角度旋转
     *
     * @param srcImg 图片路径
     * @param bitmap 图片的bitmap对象
     * @return 旋转完成后的bitmap
     * @throws IOException
     */
    public static Bitmap rotatingImage(String srcImg, Bitmap bitmap) throws IOException {
        ExifInterface exifInterface = new ExifInterface(srcImg);
        if (exifInterface == null) return bitmap;
        Matrix matrix = new Matrix();
        int angle = 0;
        int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                angle = 90;
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                angle = 180;
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                angle = 270;
                break;
        }
        matrix.postRotate(angle);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    /**
     * BitmapCompress参数检查
     *
     * @param file      源文件
     * @param quality   质量
     * @param reqWidth  期望宽
     * @param reqHeight 期望高
     * @return
     */
    public static boolean checkParamterBitmap(File file, int quality, int reqWidth, int reqHeight) {
        if (file == null) {
            throw new InossemCompressException("源文件不能为空");
        }
        if (quality > 100 || quality <= 0) {
            throw new InossemCompressException("quality范围大于0小于100  包括100");
        }
        if (reqWidth <= 0 || reqHeight <= 0) {
            throw new InossemCompressException("期望宽高不能小于0");
        }
        return true;
    }

    /**
     * FileCompress参数检查
     *
     * @param file      源文件
     * @param quality   质量
     * @param reqWidth  期望宽
     * @param reqHeight 期望高
     * @param tagPath   目标存储路径
     * @return 参数是否合法
     */
    public static boolean checkParamterFile(File file, int quality, String tagPath, int reqWidth, int reqHeight) {
        if (file == null) {
            throw new InossemCompressException("源文件不能为空");
        }
        if (quality > 100 || quality <= 0) {
            throw new InossemCompressException("quality范围大于0小于100  包括100");
        }
        if (TextUtils.isEmpty(tagPath)) {
            throw new InossemCompressException("tagPath 目标路径不能为空");
        }
        if (reqWidth <= 0 || reqHeight <= 0) {
            throw new InossemCompressException("期望宽高不能小于0");
        }
        return true;
    }

}

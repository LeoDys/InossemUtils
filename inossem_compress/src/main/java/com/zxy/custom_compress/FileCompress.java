package com.zxy.custom_compress;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.zxy.custom_compress.constant.CompressConstants;
import com.zxy.custom_compress.util.CalculationUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * File压缩工具类
 * Created by wen40 on 2019/7/17.
 */

public class FileCompress {
    /**
     * 质量压缩
     *
     * @param file           源文件
     * @param quality        质量(0-100] quality范围大于0小于100  包括100
     * @param tagPath        压缩以后存储地址
     * @param compressFormat 压缩格式
     * @return 目标文件
     * @throws IOException
     */
    public static File qualityCompress(File file, int quality, String tagPath, Bitmap.CompressFormat compressFormat) throws IOException {

        CalculationUtils.checkParamterFile(file, quality, tagPath, CompressConstants.DEFAULT_QUALITY_SIZE, CompressConstants.DEFAULT_QUALITY_SIZE);

        FileOutputStream fileOutputStream = null;
        // 目标文件不存在  创建
        File fileTag = new File(tagPath).getParentFile();
        if (!fileTag.exists()) {
            fileTag.mkdirs();
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        // inJustDecoedBounds设置为true的话，解码bitmap时可以只返回其高、宽和Mime类型，而不必为其申请内存，从而节省了内存空间
        options.inJustDecodeBounds = false;
        try {
            // 生成bitmap
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
            fileOutputStream = new FileOutputStream(tagPath);
            // 压缩
            bitmap.compress(compressFormat, quality, fileOutputStream);
        } finally {
            if (fileOutputStream != null) {
                fileOutputStream.flush();
                fileOutputStream.close();
            }
        }
        return new File(tagPath);
    }

    /**
     * 尺寸压缩
     *
     * @param file           源文件
     * @param reqWidth       期望宽
     * @param reqHeight      期望高
     * @param tagPath        压缩以后存储地址
     * @param compressFormat 压缩格式
     * @return 目标文件
     */
    public static File sizeCompress(File file, int reqWidth, int reqHeight, String tagPath, Bitmap.CompressFormat compressFormat) throws IOException {

        CalculationUtils.checkParamterFile(file, CompressConstants.DEFAULT_QUALITY_SIZE, tagPath, reqWidth, reqHeight);

        FileOutputStream fileOutputStream = null;
        // 目标文件不存在  创建
        File fileTag = new File(tagPath).getParentFile();
        if (!fileTag.exists()) {
            fileTag.mkdirs();
        }

        // First decode with inJustDecodeBounds=true to check dimensions 获取原始的长宽维度
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getAbsolutePath(), options);
        // 计算inSampleSize值 对宽高进行操作
        options.inSampleSize = CalculationUtils.calculateInSampleSize(options, reqWidth, reqHeight);
        // 设置为true的话，解码bitmap时可以只返回其高、宽和Mime类型，而不必为其申请内存，从而节省了内存空间
        options.inJustDecodeBounds = false;
        Bitmap scaledBitmap = CalculationUtils.rotatingImage(file.getAbsolutePath(), BitmapFactory.decodeFile(file.getAbsolutePath(), options));
        try {
            fileOutputStream = new FileOutputStream(tagPath);
            // 开始压缩
            scaledBitmap.compress(compressFormat, 100, fileOutputStream);
        } finally {
            if (fileOutputStream != null) {
                fileOutputStream.flush();
                fileOutputStream.close();
            }
        }
        return new File(tagPath);
    }

    /**
     * 质量和尺寸同事压缩 先压缩尺寸  再压缩质量
     *
     * @param file           源文件
     * @param quality        质量 (0-100] quality范围大于0小于100  包括100
     * @param reqWidth       期望宽
     * @param reqHeight      期望高
     * @param tagPath        压缩以后存储地址
     * @param compressFormat 压缩格式
     * @return 目标文件
     * @throws IOException
     */
    public static File qualitySizeCompress(File file, int quality, int reqWidth, int reqHeight, String tagPath,
                                           Bitmap.CompressFormat compressFormat) throws IOException {
        CalculationUtils.checkParamterFile(file, quality, tagPath, reqWidth, reqHeight);

        FileOutputStream fileOutputStream = null;
        // 目标文件不存在  创建
        File fileTag = new File(tagPath).getParentFile();
        if (!fileTag.exists()) {
            fileTag.mkdirs();
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getAbsolutePath(), options);
        // 计算inSampleSize值 对宽高进行操作
        options.inSampleSize = CalculationUtils.calculateInSampleSize(options, reqWidth, reqHeight);
        // 设置为true的话，解码bitmap时可以只返回其高、宽和Mime类型，而不必为其申请内存，从而节省了内存空间
        options.inJustDecodeBounds = false;
        try {
            fileOutputStream = new FileOutputStream(tagPath);
            CalculationUtils.rotatingImage(file.getAbsolutePath(), BitmapFactory.
                    decodeFile(file.getAbsolutePath(), options)).
                    compress(compressFormat, quality, fileOutputStream);
        } finally {
            if (fileOutputStream != null) {
                fileOutputStream.flush();
                fileOutputStream.close();
            }
        }
        return new File(tagPath);
    }
}

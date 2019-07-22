package com.zxy.custom_compress;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.zxy.custom_compress.constant.CompressConstants;
import com.zxy.custom_compress.util.CalculationUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;


/**
 * Bitmap压缩工具类
 * Created by wen40 on 2019/7/17.
 */

public class BitmapCompress {

    /**
     * 质量压缩
     *
     * @param file           源文件
     * @param quality        质量 (0-100] quality范围大于0小于100  包括100
     * @param compressFormat 压缩格式
     * @return 目标mitmap
     */
    public static Bitmap qualityCompress(File file, int quality, Bitmap.CompressFormat compressFormat) {
        CalculationUtils.checkParamterBitmap(file, quality, CompressConstants.DEFAULT_QUALITY_SIZE, CompressConstants.DEFAULT_QUALITY_SIZE);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Bitmap bm = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        // inJustDecoedBounds设置为true的话，解码bitmap时可以只返回其高、宽和Mime类型，而不必为其申请内存，从而节省了内存空间
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
        bitmap.compress(compressFormat, quality, baos);
        byte[] bytes = baos.toByteArray();
        bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        return bm;
    }

    /**
     * 尺寸压缩 质量默认100
     *
     * @param file      源文件
     * @param reqWidth  期望宽
     * @param reqHeight 期望高
     * @return 目标mitmap
     * @throws IOException
     */
    public static Bitmap sizeCompress(File file, int reqWidth, int reqHeight) throws IOException {

        CalculationUtils.checkParamterBitmap(file, CompressConstants.DEFAULT_QUALITY_SIZE, reqWidth, reqHeight);

        // First decode with inJustDecodeBounds=true to check dimensions 获取原始的长宽维度
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getAbsolutePath(), options);
        // 计算inSampleSize值 对宽高进行操作
        options.inSampleSize = CalculationUtils.calculateInSampleSize(options, reqWidth, reqHeight);
        // 设置为true的话，解码bitmap时可以只返回其高、宽和Mime类型，而不必为其申请内存，从而节省了内存空间
        options.inJustDecodeBounds = false;
        Bitmap scaledBitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
        return CalculationUtils.rotatingImage(file.getAbsolutePath(), scaledBitmap);
    }

    /**
     * 尺寸和质量一起压缩 先压缩尺寸  再压缩质量
     *
     * @param file           源文件
     * @param quality        质量 (0-100] quality范围大于0小于100  包括100
     * @param reqWidth       期望宽
     * @param reqHeight      期望高
     * @param compressFormat 压缩格式
     * @return 目标mitmap
     */
    public static Bitmap qualitySizeCompress(File file, int quality, int reqWidth, int reqHeight, Bitmap.CompressFormat compressFormat) throws IOException {
        CalculationUtils.checkParamterBitmap(file, quality, reqWidth, reqHeight);
        Bitmap bm = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getAbsolutePath(), options);
        // 计算inSampleSize值 对宽高进行操作
        options.inSampleSize = CalculationUtils.calculateInSampleSize(options, reqWidth, reqHeight);
        // 设置为true的话，解码bitmap时可以只返回其高、宽和Mime类型，而不必为其申请内存，从而节省了内存空间
        options.inJustDecodeBounds = false;
        CalculationUtils.rotatingImage(file.getAbsolutePath(), BitmapFactory.
                decodeFile(file.getAbsolutePath(), options)).
                compress(compressFormat, quality, baos);
        byte[] bytes = baos.toByteArray();
        bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        return bm;
    }
}

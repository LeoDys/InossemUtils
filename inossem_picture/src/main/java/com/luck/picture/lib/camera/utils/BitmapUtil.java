package com.luck.picture.lib.camera.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;

/**
 * Created by wen40 on 2019/7/8.
 */

public class BitmapUtil {
    /**
     * 对图片的角度进行矫正
     *
     * @param angle
     * @param bitmap
     * @return
     */
    public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
        //旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizedBitmap;
    }

    /**
     * @param resource 原图
     *                 //     * @param hue        色相
     *                 //     * @param saturation 饱和度
     * @param lum      亮度
     * @return
     */
    public static Bitmap getChangedBitmap(Bitmap resource, float lum) {
        Bitmap out = Bitmap.createBitmap(resource.getWidth()
                , resource.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(out);
        Paint paint = new Paint();
        paint.setAntiAlias(true);//抗锯齿

        //调整饱和度
//        ColorMatrix saturationMatrix = new ColorMatrix();
//        saturationMatrix.setSaturation(saturation);

        //调整色相
//        ColorMatrix hueMatrix = new ColorMatrix();
//        hueMatrix.setRotate(0, hue);//调整红像素的色相
//        hueMatrix.setRotate(1, hue);
//        hueMatrix.setRotate(2, hue);

        //调整亮度
        ColorMatrix lumMatrix = new ColorMatrix();
        lumMatrix.setScale(lum, lum, lum, 1);

        //把色相/饱和度/明度 合并成一个ColorMatrix
        ColorMatrix colorMatrix = new ColorMatrix();
//        colorMatrix.postConcat(hueMatrix);
//        colorMatrix.postConcat(saturationMatrix);
        colorMatrix.postConcat(lumMatrix);

        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        canvas.drawBitmap(resource, 0, 0, paint);
        return out;

    }
}

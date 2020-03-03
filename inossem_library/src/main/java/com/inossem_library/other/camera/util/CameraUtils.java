package com.inossem_library.other.camera.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;

import com.inossem_library.tips.logcat.util.LogUtils;

/**
 * 相机工具类
 *
 * @author LeoDys E-mail:changwen.sun@inossem.com 2020/3/3 11:49
 * @version 1.0.8
 * @since 1.0.8
 */
public class CameraUtils {

    private static Camera camera;

    /**
     * 检查是否有相机
     *
     * @param context 上下文对象
     * @return 是否存在相机
     */
    public static boolean hasCamera(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

    /**
     * 打开相机
     *
     * @return 相机对象
     */
    public static Camera openCamera() {
        camera = null;
        try {
            camera = Camera.open(); // attempt to get a Camera instance
        } catch (Exception e) {
            // Camera is not available (in use or does not exist)
            LogUtils.e("Camera is not available (in use or does not exist)");
        }
        return camera; // returns null if camera is unavailable
    }

    public static Camera getCamera() {
        return camera;
    }
}

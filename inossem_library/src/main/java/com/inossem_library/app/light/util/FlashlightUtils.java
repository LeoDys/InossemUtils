package com.inossem_library.app.light.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.support.annotation.NonNull;
import android.util.Log;

import com.inossem_library.exception.ExceptionEnum;
import com.inossem_library.exception.InossemException;

import java.io.IOException;

import static android.hardware.Camera.Parameters.FLASH_MODE_OFF;
import static android.hardware.Camera.Parameters.FLASH_MODE_TORCH;

/**
 * 语言相关
 *
 * @author Lin
 */
public final class FlashlightUtils {
    private static Camera mCamera;
    private static SurfaceTexture mSurfaceTexture;

    /**
     * 返回设备是否支持闪光灯
     *
     * @return 是否支持闪光灯
     */
    public static boolean isFlashlightEnable(@NonNull Context context) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }

    /**
     * 返回手电筒是否工作
     *
     * @return 手电筒是否工作
     */
    public static boolean isFlashlightOn() {
        if (!init()) return false;
        Camera.Parameters parameters = mCamera.getParameters();
        return FLASH_MODE_TORCH.equals(parameters.getFlashMode());
    }

    /**
     * 打开或关掉手电筒
     *
     * @param isOn 打开或关掉手电筒
     */
    public static void setFlashlightStatus(final boolean isOn) {
        if (!init()) return;
        final Camera.Parameters parameters = mCamera.getParameters();
        if (isOn) {
            if (!FLASH_MODE_TORCH.equals(parameters.getFlashMode())) {
                try {
                    mCamera.setPreviewTexture(mSurfaceTexture);
                    mCamera.startPreview();
                    parameters.setFlashMode(FLASH_MODE_TORCH);
                    mCamera.setParameters(parameters);
                } catch (IOException e) {
                    Log.e("FlashlightUtils", "setFlashlightStatusOn: ", e);
                }
            }
        } else {
            if (!FLASH_MODE_OFF.equals(parameters.getFlashMode())) {
                parameters.setFlashMode(FLASH_MODE_OFF);
                mCamera.setParameters(parameters);
            }
        }
    }

    /**
     * 销毁手电筒
     */
    public static void destroy() {
        if (mCamera == null) return;
        mCamera.release();
        mSurfaceTexture = null;
        mCamera = null;
    }

    /**
     * 初始化摄像头
     */
    private static boolean init() {
        if (mCamera == null) {
            try {
                mCamera = Camera.open(0);
                mSurfaceTexture = new SurfaceTexture(0);
            } catch (Throwable t) {
                Log.e("FlashlightUtils", "init failed: ", t);
                return false;
            }
        }
        if (mCamera == null) {
            Log.e("FlashlightUtils", "init failed.");
            return false;
        }
        return true;
    }
}
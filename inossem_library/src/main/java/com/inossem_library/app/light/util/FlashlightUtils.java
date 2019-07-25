package com.inossem_library.app.light.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.support.annotation.NonNull;
import android.util.Log;

import com.inossem_library.exception.ExceptionEnum;
import com.inossem_library.exception.InossemException;

import static android.hardware.Camera.Parameters.FLASH_MODE_OFF;
import static android.hardware.Camera.Parameters.FLASH_MODE_TORCH;

/**
 * 闪光灯工具类
 *
 * @author Lin
 */
public final class FlashlightUtils {
    /**
     * 相机
     */
    private static Camera mCamera;
    /**
     * 以OpenGL ES纹理的形式从图像流中捕获帧。
     * 初始化相机用
     */
    private static SurfaceTexture mSurfaceTexture;

    /**
     * 判断设备是否支持闪光灯
     */
    public static boolean isFlashlightEnable(@NonNull Context context) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }

    /**
     * 判断闪光灯是否打开
     */
    public static boolean isFlashlightOn() {
        if (!init()) {
            return false;
        }
        Camera.Parameters parameters = mCamera.getParameters();
        // FLASH_MODE_TORCH 闪光灯常亮
        return FLASH_MODE_TORCH.equals(parameters.getFlashMode());
    }

    /**
     * 设置闪光灯状态
     *
     * @param isOn True是打开,False是关闭
     */
    public static void setFlashlightStatus(final boolean isOn) throws Throwable {
        if (!init()) {
            return;
        }
        final Camera.Parameters parameters = mCamera.getParameters();
        if (isOn) {
            // FLASH_MODE_TORCH 闪光灯常亮
            if (!FLASH_MODE_TORCH.equals(parameters.getFlashMode())) {
                // 打开相机 图像流
                mCamera.setPreviewTexture(mSurfaceTexture);
                // 开始预览
                mCamera.startPreview();
                parameters.setFlashMode(FLASH_MODE_TORCH);
                mCamera.setParameters(parameters);
            }
        } else {
            // FLASH_MODE_OFF 闪光灯关闭
            if (!FLASH_MODE_OFF.equals(parameters.getFlashMode())) {
                parameters.setFlashMode(FLASH_MODE_OFF);
                mCamera.setParameters(parameters);
            }
        }
    }

    /**
     * 销毁
     */
    public static void destroy() {
        if (mCamera == null) {
            return;
        }
        mCamera.release();
        mSurfaceTexture = null;
        mCamera = null;
    }

    /**
     * 闪光灯初始化
     *
     * @return 是否初始化成功
     */
    private static boolean init() {
        if (mCamera == null) {
            // CAMERA_FACING_BACK 主照相机
            mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
            mSurfaceTexture = new SurfaceTexture(0);
        }
        if (mCamera == null) {
            Log.e("闪光灯工具类", "初始化失败");
            return false;
        }
        return true;
    }
}
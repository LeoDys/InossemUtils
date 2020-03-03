package com.inossem_library.other.camera.custom;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.inossem_library.app.screen.util.ScreenUtils;
import com.inossem_library.other.camera.util.CameraUtils;

import java.util.List;

/**
 * Author       wildma
 * Github       https://github.com/wildma
 * Date         2018/6/24
 * Desc	        ${相机预览}
 */
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {

    private static String TAG = CameraPreview.class.getName();

    private Camera mCamera;
    private AutoFocusManager mAutoFocusManager;
    private SensorControler mSensorControler;
    private Context mContext;
    private SurfaceHolder mSurfaceHolder;

    public CameraPreview(Context context) {
        super(context);
        init(context);
    }

    public CameraPreview(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CameraPreview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CameraPreview(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
        mSurfaceHolder.setKeepScreenOn(true); // 保持屏幕处于打开状态
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        mSensorControler = SensorControler.getInstance(context.getApplicationContext()); // 对焦
    }

    public void surfaceCreated(SurfaceHolder holder) {
        mCamera = CameraUtils.openCamera();
        Log.e("Time error", String.valueOf(System.currentTimeMillis()));
        if (mCamera != null) {
            try {
                mCamera.setPreviewDisplay(holder); // 必须在{@link #startPreview()}之前调用
                Camera.Parameters mParameters = mCamera.getParameters();
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    //竖屏拍照时，需要设置旋转90度，否者看到的相机预览方向和界面方向不相同
                    mCamera.setDisplayOrientation(90);
                    mParameters.setRotation(90);
                } else {
                    mCamera.setDisplayOrientation(0);
                    mParameters.setRotation(0);
                }
                int screenWidth = ScreenUtils.getScreenWidth(mContext);
                int screenHeight = ScreenUtils.getScreenHeight(mContext);
                // 设置图片预览 宽高
                mParameters.setPictureSize(screenWidth, screenHeight);
                //获取所有支持的预览大小
                List<Camera.Size> sizeList = mParameters.getSupportedPreviewSizes();
                Camera.Size bestSize = getOptimalPreviewSize(sizeList, /*ScreenUtils.getScreenWidth(mContext), ScreenUtils.getScreenHeight(mContext)*/
                        this.getWidth(), this.getHeight());
                mParameters.setPreviewSize(bestSize.width, bestSize.height);//设置预览大小
//                mParameters.setPictureSize(screenWidth, screenHeight);
                mCamera.setParameters(mParameters);
                mCamera.startPreview();
                focus();//首次对焦
                //mAutoFocusManager = new AutoFocusManager(mCamera);//定时对焦
            } catch (Exception e) {
                Log.d(TAG, "Error setting mCamera preview: " + e.getMessage());
                try {
                    Camera.Parameters parameters = mCamera.getParameters();
                    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                        //竖屏拍照时，需要设置旋转90度，否者看到的相机预览方向和界面方向不相同
                        mCamera.setDisplayOrientation(90);
                        parameters.setRotation(90);
                    } else {
                        mCamera.setDisplayOrientation(0);
                        parameters.setRotation(0);
                    }
                    mCamera.setParameters(parameters);
                    mCamera.startPreview();
                    focus();//首次对焦
                    //mAutoFocusManager = new AutoFocusManager(mCamera);//定时对焦
                } catch (Exception e1) {
                    e.printStackTrace();
                    mCamera = null;
                }
            }
            Log.e("Time error", String.valueOf(System.currentTimeMillis()));
        }

    }

    /**
     * 获取最佳预览大小
     *
     * @param sizes 所有支持的预览大小
     * @param w     SurfaceView宽
     * @param h     SurfaceView高
     * @return
     */
    private Camera.Size getOptimalPreviewSize(List<Camera.Size> sizes, int w, int h) {
        final double ASPECT_TOLERANCE = 0.2;
        double targetRatio = (double) h / w;
        if (sizes == null)
            return null;

        Camera.Size optimalSize = null;
        double minDiff = Double.MAX_VALUE;

        int targetHeight = h;

        // Try to find an size match aspect ratio and size
        for (Camera.Size size : sizes) {
            double ratio = (double) size.width / size.height;
            if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE)
                continue;
            if (Math.abs(size.height - targetHeight) < minDiff) {
                optimalSize = size;
                minDiff = Math.abs(size.height - targetHeight);
            }
        }

        // Cannot find the one match the aspect ratio, ignore the requirement
        if (optimalSize == null) {
            minDiff = Double.MAX_VALUE;
            for (Camera.Size size : sizes) {
                if (Math.abs(size.height - targetHeight) < minDiff) {
                    optimalSize = size;
                    minDiff = Math.abs(size.height - targetHeight);
                }
            }
        }
        return optimalSize;
    }

    private Camera.Size getProperSize(List<Camera.Size> pictureSizeList, float screenRatio) {
        Camera.Size result = null;
        for (Camera.Size size : pictureSizeList) {
            float currentRatio = ((float) size.width) / size.height;
            if (currentRatio - screenRatio == 0) {
                result = size;
                break;
            }
        }
        if (null == result) {
            for (Camera.Size size : pictureSizeList) {
                float curRatio = ((float) size.width) / size.height;
                if (curRatio == 4f / 3) {// 默认w:h = 4:3
                    result = size;
                    break;
                }
            }
        }
        return result;
    }


    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        //因为设置了固定屏幕方向，所以在实际使用中不会触发这个方法
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        holder.removeCallback(this);
        //回收释放资源
        release();
    }

    /**
     * 释放资源
     */
    private void release() {
        if (mCamera != null) {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;

            if (mAutoFocusManager != null) {
                mAutoFocusManager.stop();
                mAutoFocusManager = null;
            }
        }
    }

    /**
     * 对焦，在CameraActivity中触摸对焦或者自动对焦
     */
    public void focus() {
        if (mCamera != null) {
            try {
                mCamera.autoFocus(null);
            } catch (Exception e) {
                Log.d(TAG, "takePhoto " + e);
            }
        }
    }

    /**
     * 开关闪光灯
     *
     * @return 闪光灯是否开启
     */
    public boolean switchFlashLight() {
        if (mCamera != null) {
            Camera.Parameters parameters = mCamera.getParameters();
            if (parameters.getFlashMode().equals(Camera.Parameters.FLASH_MODE_OFF)) {
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                mCamera.setParameters(parameters);
                return true;
            } else {
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                mCamera.setParameters(parameters);
                return false;
            }
        }
        return false;
    }

    /**
     * 拍摄照片
     *
     * @param pictureCallback 在pictureCallback处理拍照回调
     */
    public void takePhoto(Camera.PictureCallback pictureCallback) {
        if (mCamera != null) {
            try {
                mCamera.takePicture(null, null, pictureCallback);
            } catch (Exception e) {
                Log.d(TAG, "takePhoto " + e);
            }
        }
    }

    public void startPreview() {
        if (mCamera != null) {
            mCamera.startPreview();
        }
    }

    /**
     *
     */
    public void onStart() {
        addCallback();
        if (mSensorControler != null) {
            mSensorControler.onStart();
            mSensorControler.setCameraFocusListener(new SensorControler.CameraFocusListener() {
                @Override
                public void onFocus() {
                    focus();
                }
            });
        }
    }

    public void onStop() {
        if (mSensorControler != null) {
            mSensorControler.onStop();
        }
    }

    public void addCallback() {
        if (mSurfaceHolder != null) {
            mSurfaceHolder.addCallback(this);
        }
    }
}

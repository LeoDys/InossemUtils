package com.luck.picture.lib.camera.camera;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.luck.picture.lib.R;
import com.luck.picture.lib.camera.custom.CameraPreview;
import com.luck.picture.lib.camera.utils.BitmapUtil;
import com.luck.picture.lib.camera.utils.CameraUtils;
import com.luck.picture.lib.camera.utils.ImageUtils;
import com.luck.picture.lib.camera.utils.PermissionUtils;
import com.luck.picture.lib.camera.utils.ScreenUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by wen40 on 2019/7/8.
 *
 */

public class InossemCameraActivity extends Activity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    private Activity mActivity;
    private boolean isToast;
    private ImageView mImageView;
    private CameraPreview mCameraPreview;
    private ImageView ivCameraCrop;
    private ImageView ivCameraFlash;
    private ImageView ivCameraTake;
    private ImageView ivCameraClose;
    private RelativeLayout rlCameraOption;
    private LinearLayout llCameraResult;
    private TextView mViewFocusingTips;
    private float screenWidth;
    private float screenHeight;

    private ImageView ivCameraResultOk;
    private ImageView ivCameraResultCancel;
    private SeekBar mLumSeekBar;
    private Bitmap mTakeCropBitmap;

    private float mLum = 1f;
    private static final int MID_VALUE = 128;
    private Bitmap mResultBitmap;
    // 拍照的时候是否有裁剪框
    private boolean isCropBox = false;
    // 拍照完成后调整亮度
    private boolean isLight = false;
    // 照片的路径和名字以及后缀名
    private String takePhotoSavePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        /*动态请求需要的权限*/
        boolean checkPermissionFirst = PermissionUtils.checkPermissionFirst(this, InossemCustomCamera.PERMISSION_CODE_FIRST,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA});
        if (checkPermissionFirst) {
            init();
        }
    }

    /**
     * 权限申请回来后进行页面的渲染初始化
     */
    private void init() {
        setContentView(R.layout.activity_camera_portrait_layout);
        getExtra(getIntent());
        initView();
        initListener();
    }

    private void getExtra(Intent intent) {
        // TODO
        if (intent !=null) {
            isCropBox = intent.getBooleanExtra(InossemCustomCamera.IS_CROP_BOX, false);
            isLight = intent.getBooleanExtra(InossemCustomCamera.IS_CHANGE_LIGHT, false);
            takePhotoSavePath = intent.getStringExtra(InossemCustomCamera.TAKE_PHOTO_PATH);
        }
    }

    private void initListener() {
        ivCameraFlash.setOnClickListener(this);
        ivCameraTake.setOnClickListener(this);
        ivCameraClose.setOnClickListener(this);
        ivCameraResultOk.setOnClickListener(this);
        ivCameraResultCancel.setOnClickListener(this);
        mCameraPreview.setOnClickListener(this);
        mLumSeekBar.setOnSeekBarChangeListener(this);
    }

    private void initView() {
        mImageView = findViewById(R.id.iv_imageview);
        mCameraPreview = findViewById(R.id.camera_preview);
        ivCameraCrop = findViewById(R.id.iv_camera_crop);
        rlCameraOption = findViewById(R.id.ll_camera_option);
        ivCameraFlash = findViewById(R.id.iv_camera_flash);
        ivCameraTake = findViewById(R.id.iv_camera_take);
        ivCameraClose = findViewById(R.id.iv_camera_close);
        llCameraResult = findViewById(R.id.ll_camera_result);
        mViewFocusingTips = findViewById(R.id.view_focusing_tips);
        ivCameraResultOk = findViewById(R.id.iv_camera_result_ok);
        ivCameraResultCancel = findViewById(R.id.iv_camera_result_cancel);
        mLumSeekBar = findViewById(R.id.lum_seek_bar);
        if (isCropBox) {
            ivCameraCrop.setVisibility(View.VISIBLE);
            initViewRule();
        } else {
            ivCameraCrop.setVisibility(View.GONE);
            initCameraPreview();
        }
        if (isLight) {
            mLumSeekBar.setVisibility(View.VISIBLE);
        } else {
            mLumSeekBar.setVisibility(View.GONE);
        }
    }

    /**
     * 设置框的大小以及自动裁剪的大小
     */
    private void initViewRule() {
        // 获取屏幕尺寸
        screenWidth = ScreenUtils.getScreenWidth(this);
        screenHeight = ScreenUtils.getScreenHeight(this);
        // 为了让view都能能够这值属性值做的post操作
        rlCameraOption.post(new Runnable() {
            @Override
            public void run() {
                int height = rlCameraOption.getMeasuredHeight();
                // 动态设置相机的 宽 高 通过屏幕尺寸计算
                float cameraCropWidth = screenWidth * 0.8f;
                float cameraCropHeight = (screenHeight - height) * 0.84f;

                // 手动设置垂直居中
                float marginTop = (screenHeight - height - cameraCropHeight) / 2;

                // 相机区域 设置位置
                RelativeLayout.LayoutParams containerParamsNew = new RelativeLayout.LayoutParams((int) cameraCropWidth, (int) cameraCropHeight);
                containerParamsNew.addRule(RelativeLayout.CENTER_HORIZONTAL);
                containerParamsNew.topMargin = (int) marginTop;
                ivCameraCrop.setLayoutParams(containerParamsNew);

            }
        });
        initCameraPreview();
    }

    /**
     * 增加0.5秒过渡界面，解决个别手机首次申请权限导致预览界面启动慢的问题
     */
    private void initCameraPreview() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mCameraPreview.setVisibility(View.VISIBLE);
                    }
                });
            }
        }, 500);
    }

    /**
     * 处理请求权限的响应
     *
     * @param requestCode  请求码
     * @param permissions  权限数组
     * @param grantResults 请求权限结果数组
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean isPermissions = true;
        // 循环检查请求的权限是否给予
        for (int i = 0; i < permissions.length; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                isPermissions = false;
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])) { //用户选择了"不再询问"
                    if (isToast) {
                        Toast.makeText(this, "请手动打开该应用需要的权限", Toast.LENGTH_SHORT).show();
                        isToast = false;
                    }
                }
            }
        }
        isToast = true;
        if (isPermissions) {
            Log.d("onRequestPermission", "onRequestPermissionsResult: " + "允许所有权限");
            init();
        } else {
            Log.d("onRequestPermission", "onRequestPermissionsResult: " + "有权限不允许");
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.camera_preview) {
            mCameraPreview.focus();
        } else if (id == R.id.iv_camera_close) {
            finish();
        } else if (id == R.id.iv_camera_take) {
            takePhoto();
        } else if (id == R.id.iv_camera_flash) {
            boolean isFlashOn = mCameraPreview.switchFlashLight();
            ivCameraFlash.setImageResource(isFlashOn ? R.drawable.camera_flash_on : R.drawable.camera_flash_off);
        } else if (id == R.id.iv_camera_result_ok) {
            confirm();
        } else if (id == R.id.iv_camera_result_cancel) {
            cancle();
        }
    }

    /**
     * 不满意 重新拍摄
     */
    private void cancle() {
        mCameraPreview.setEnabled(true);
        mCameraPreview.addCallback();
        mCameraPreview.startPreview();
        ivCameraFlash.setImageResource(R.drawable.camera_flash_off);
        mImageView.setVisibility(View.GONE);
        setTakePhotoLayout();
    }

    /**
     * 设置拍照布局
     */
    private void setTakePhotoLayout() {
        // 区分显示和不显示拍照时的裁剪框
        if (isCropBox) {
            ivCameraCrop.setVisibility(View.VISIBLE);
            ivCameraCrop.setBackgroundResource(R.drawable.camera_portrait);
        }
        mCameraPreview.setVisibility(View.VISIBLE);
        rlCameraOption.setVisibility(View.VISIBLE);
        llCameraResult.setVisibility(View.GONE);
        mViewFocusingTips.setText(getString(R.string.touch_to_focus));
        mCameraPreview.focus();
    }

    /**
     * 主线程中更新UI,进行图片的确认和重拍操作
     *
     * @param bitmap
     */
    private void setTakeAfterPhotoLayout(final Bitmap bitmap) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                rlCameraOption.setVisibility(View.GONE);
                llCameraResult.setVisibility(View.VISIBLE);
                mViewFocusingTips.setVisibility(View.GONE);
                ivCameraCrop.setVisibility(View.GONE);
                mImageView.setVisibility(View.VISIBLE);
                mImageView.setImageBitmap(bitmap);
                mCameraPreview.setVisibility(View.GONE);
            }
        });
    }


    /**
     * 点击确认，返回图片路径
     */
    private void confirm() {
        /*手动裁剪图片*/
        if (mTakeCropBitmap == null) {
            Toast.makeText(getApplicationContext(), getString(R.string.crop_fail), Toast.LENGTH_SHORT).show();
            finish();
        }
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(new File(takePhotoSavePath));

            Bitmap.CompressFormat format = null;
            if (takePhotoSavePath.endsWith(InossemCustomCamera.TAKE_PHOTO_SUFFIX_PNG)) {
                format = Bitmap.CompressFormat.PNG;
            } else if (takePhotoSavePath.endsWith(InossemCustomCamera.TAKE_PHOTO_SUFFIX_WEBP)) {
                format = Bitmap.CompressFormat.WEBP;
            } else {
                format = Bitmap.CompressFormat.JPEG;
            }
            mTakeCropBitmap.compress(format, 100, out);
            // 生成新拍照片或视频对象 用于直接返回的情况
            setResult(RESULT_OK);
            finish();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 拍照
     */
    private void takePhoto() {
        mCameraPreview.setEnabled(false);
        CameraUtils.getCamera().setOneShotPreviewCallback(new Camera.PreviewCallback() {
            // 拍照后的回调 获取图片的byte流
            @Override
            public void onPreviewFrame(final byte[] bytes, Camera camera) {
                //获取预览大小 最佳大小
                final Camera.Size size = camera.getParameters().getPreviewSize();
                camera.stopPreview();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final int w = size.width;
                        final int h = size.height;
                        Bitmap bitmap = BitmapUtil.rotaingImageView(90, ImageUtils.getBitmapFromByte(bytes, w, h));
                        if (isCropBox) {
                            cropImage(bitmap);
                        } else {
                            mTakeCropBitmap = bitmap;
                            mResultBitmap = bitmap;
                            setTakeAfterPhotoLayout(bitmap);
                        }
                    }
                }).start();
            }
        });
    }

    /**
     * 根据裁剪框的位置截取裁剪图片
     *
     * @param bitmap 要进行裁剪的图片bitmap
     */
    private void cropImage(Bitmap bitmap) {
        /*计算扫描框的坐标点*/
        float left = screenWidth / 10;
        float top = ivCameraCrop.getTop();
        float width = ivCameraCrop.getRight() - left;
        float height = ivCameraCrop.getBottom() - top;

        /*自动裁剪*/
        mTakeCropBitmap = Bitmap.createBitmap(bitmap,
                (int) (left),
                (int) (top),
                (int) (width),
                (int) (height));
        setTakeAfterPhotoLayout(mTakeCropBitmap);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mCameraPreview != null) {
            mCameraPreview.onStart();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mCameraPreview != null) {
            mCameraPreview.onStop();
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        int i = seekBar.getId();
        if (i == R.id.lum_seek_bar) {
            mLum = progress * 1f / MID_VALUE;
        }
        mResultBitmap = BitmapUtil.getChangedBitmap(mTakeCropBitmap, mLum);
        mImageView.setImageBitmap(mResultBitmap);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}

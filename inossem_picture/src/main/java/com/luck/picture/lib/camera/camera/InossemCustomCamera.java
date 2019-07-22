package com.luck.picture.lib.camera.camera;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.luck.picture.lib.camera.exception.InossemCameraException;

import java.lang.ref.WeakReference;

/**
 * Desc	  自定义相机
 */
public class InossemCustomCamera {

    private static InossemCustomCamera instance;
    public final static int TYPE_IDCARD_FRONT = 1;//身份证正面
    public final static int TYPE_IDCARD_BACK = 2;//身份证反面
    public final static int RESULT_CODE = 0X11;//结果码
    public final static int PERMISSION_CODE_FIRST = 0x12;//权限请求码
    public final static String TAKE_TYPE = "take_type";//拍摄类型标记
    public final static String RESULT_IMAGE_PATH = "result_image_path";// onActivityResult 图片路径标记
    public final static String IS_CROP_BOX = "is_crop_box";// 是否显示自动裁剪框
    public final static String IS_CHANGE_LIGHT = "is_change_light";// 是否調整亮度
    public final static String TAKE_PHOTO_PATH = "take_photo_path";// 照片全路径  包括名字

    public final static String TAKE_PHOTO_SUFFIX_PNG = "PNG";// 照片 PNG
    public final static String TAKE_PHOTO_SUFFIX_JPEG = "JPEG";// 照片 JPEG
    public final static String TAKE_PHOTO_SUFFIX_WEBP = "WEBP";// 照片 WebP

    private WeakReference<Activity> mActivity = null;
    private WeakReference<Fragment> mFragment = null;

    private boolean isCropBox = false;
    private boolean isLight = false;
    private String takePhotoSavePath;
    private Fragment fragment;
    private Activity activity;

    private InossemCustomCamera() {

    }

    public static InossemCustomCamera getInstance() {
        if (instance == null) {
            instance = new InossemCustomCamera();
        }
        return instance;
    }

    public InossemCustomCamera initActivity(Activity mActivity, String takePhotoSavePath) {
        this.mActivity = new WeakReference<Activity>(mActivity);
        activity = this.mActivity.get();
        init(takePhotoSavePath);
        return this;
    }

    public InossemCustomCamera initFragment(Fragment mFragment, String takePhotoSavePath) {
        this.mFragment = new WeakReference<Fragment>(mFragment);
        fragment = this.mFragment.get();
        init(takePhotoSavePath);
        return this;
    }

    private void init(String takePhotoSavePath) {
        if (TextUtils.isEmpty(takePhotoSavePath)) {
            throw new InossemCameraException("照片存储路径不能为空");
        }
        this.isCropBox = false;
        this.isLight = false;
        this.takePhotoSavePath = takePhotoSavePath;
    }

    public InossemCustomCamera setLightCamera(boolean isLight) {
        this.isLight = isLight;
        return this;
    }

    public InossemCustomCamera setCropCamera(boolean isCropBox) {
        this.isCropBox = isCropBox;
        return this;
    }

    public InossemCustomCamera setTakePhotoSavePath(String takePhotoSavePath) {
        this.takePhotoSavePath = takePhotoSavePath;
        return this;
    }

    /**
     * 打开相机
     *
     * @param requestCode 请求码
     */
    public void openCamera(int requestCode) {
        /**
         * 上下文的判断
         */
        checkParamter(takePhotoSavePath, activity, fragment);
        Intent intent;
        if (activity != null) {
            intent = new Intent(activity, InossemCameraActivity.class);
        } else {
            intent = new Intent(fragment.getActivity(), InossemCameraActivity.class);
        }
        intent.putExtra(IS_CROP_BOX, isCropBox);
        intent.putExtra(IS_CHANGE_LIGHT, isLight);
        intent.putExtra(TAKE_PHOTO_PATH, takePhotoSavePath);
        if (fragment != null) {
            fragment.startActivityForResult(intent, requestCode);
        } else {
            activity.startActivityForResult(intent, requestCode);
        }
    }

    /**
     * 检查各个参数是否合法
     *
     * @param takePhotoSavePath 照片名字
     * @param activity          activity上下文
     * @param fragment          fragment上下文
     */
    private void checkParamter(String takePhotoSavePath, Activity activity, Fragment fragment) {
        if (activity == null && fragment == null) {
            throw new InossemCameraException("[Activity&&Fragment] null");
        }

        if (TextUtils.isEmpty(takePhotoSavePath)) {
            throw new InossemCameraException("photo save path null");
        }
    }

    /**
     * 获取图片路径
     *
     * @param data Intent
     * @return 图片路径
     */
    public static String getImagePath(Intent data) {
        if (data != null) {
            return data.getStringExtra(RESULT_IMAGE_PATH);
        }
        return "";
    }
}


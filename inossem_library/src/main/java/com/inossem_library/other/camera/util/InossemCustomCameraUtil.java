package com.inossem_library.other.camera.util;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.text.TextUtils;

import com.inossem_library.app.path.util.PathUtils;
import com.inossem_library.exception.InossemException;
import com.inossem_library.exception.constant.ExceptionEnum;
import com.inossem_library.other.camera.constant.CameraConstant;
import com.inossem_library.other.file.util.FileIOUtils;
import com.inossem_library.view.activity.InossemCameraActivity;
import com.inossem_library.view.activity.InossemExtra;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.UUID;

import androidx.fragment.app.Fragment;

/**
 * Desc	  自定义相机
 */
public class InossemCustomCameraUtil {

    private static InossemCustomCameraUtil instance;

    private WeakReference<Activity> mActivity = null;
    private WeakReference<Fragment> mFragment = null;

    // 后缀
    private Bitmap.CompressFormat suffix;
    // 是否裁剪
    private boolean isCropBox = false;
    // 拍照完成时候调整亮度
    private boolean isLight = false;
    // 照片保存路径
    private String saveDirPath;
    // fragment和activity对象  持有弱引用
    private Fragment fragment;
    private Activity activity;

    private InossemCustomCameraUtil() {
    }

    public static InossemCustomCameraUtil getInstance() {
        if (instance == null) {
            instance = new InossemCustomCameraUtil();
        }
        return instance;
    }

    public InossemCustomCameraUtil initActivity(Activity mActivity) {
        this.mActivity = new WeakReference<>(mActivity);
        activity = this.mActivity.get();
        init();
        return this;
    }

    public InossemCustomCameraUtil initFragment(Fragment mFragment) {
        this.mFragment = new WeakReference<>(mFragment);
        fragment = this.mFragment.get();
        init();
        return this;
    }

    private void init() {
        this.suffix = Bitmap.CompressFormat.JPEG;
        this.isCropBox = false;
        this.isLight = false;
        this.saveDirPath = PathUtils.getLegalPath(activity == null ? fragment.getActivity() : activity, Environment.DIRECTORY_PICTURES)
                + CameraConstant.DEFAULT_SAVE_PATH;
        FileIOUtils.judgeExistsMkdirs(this.saveDirPath);
    }

    public InossemCustomCameraUtil setLightCamera(boolean isLight) {
        this.isLight = isLight;
        return this;
    }

    public InossemCustomCameraUtil setCropCamera(boolean isCropBox) {
        this.isCropBox = isCropBox;
        return this;
    }

    public InossemCustomCameraUtil setSaveDirPath(String saveDirPath) {
        this.saveDirPath = saveDirPath;
        return this;
    }

    public InossemCustomCameraUtil setSuffix(Bitmap.CompressFormat suffix) {
        this.suffix = suffix;
        return this;
    }

    /**
     * 打开相机
     *
     * @param requestCode 请求码
     */
    public void openCamera(int requestCode) {
        Intent intent;
        if (activity != null) {
            intent = new Intent(activity, InossemCameraActivity.class);
        } else {
            intent = new Intent(fragment.getActivity(), InossemCameraActivity.class);
        }
        intent.putExtra(InossemExtra.IS_CROP_BOX, isCropBox);
        intent.putExtra(InossemExtra.IS_CHANGE_LIGHT, isLight);
        intent.putExtra(InossemExtra.TAKE_PHOTO_PATH, getPhotoPathName());
        if (fragment != null) {
            fragment.startActivityForResult(intent, requestCode);
        } else {
            activity.startActivityForResult(intent, requestCode);
        }
    }

    /**
     * 获取图片名称
     *
     * @return
     */
    private String getPhotoPathName() {
        String name = CameraConstant.PREFIX_PHOTO_NAME.concat(UUID.randomUUID().toString());
        return saveDirPath + File.separator + name + "." + this.suffix;
    }
}


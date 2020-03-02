package com.inossem_library.other.picture.config;

import android.app.Activity;
import android.text.TextUtils;

import com.inossem_library.callback.LibraryLinstener;
import com.inossem_library.exception.InossemException;
import com.inossem_library.exception.constant.ExceptionEnum;
import com.inossem_library.other.compress.constant.CompressConstant;
import com.inossem_library.other.picture.constant.PictureSelectContants;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;

/**
 * 图片选择配置默认值及set自定义值的类
 * Created by wen40 on 2019/7/10.
 */

public class InossemPictureConfig {

    private static InossemPictureConfig instance = null;

    private Activity activity;
    private Fragment fragment;
    // 请求code
    private int requestCode;
    // 最大选取张数
    private int maxSelect;
    // 是否裁剪
    private boolean enableCrop;
    // 是否压缩
    private boolean compress;
    // 同步true或异步false 压缩 默认同步
    private boolean synOrAsy;

    // 选取类别 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
    private int openGallery;
    // 多选:PictureConfig.MULTIPLE  单选:PictureConfig.SINGLE
    private int selectionMode;
    // 单选模式下是否直接返回，PictureConfig.SINGLE模式下有效
    private boolean singleDirectReturn;
    // 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
    private int theme;
    // 是否显示拍照按钮
    private boolean isCamera;
    // 拍照保存图片格式后缀,默认jpeg
    private String imageFormat;
    // 自定义拍照保存路径
    @Deprecated
    private String outputCameraPath;
    // 压缩图片保存地址
    private String compressSavePath;
    // 是否显示gif图片
    private boolean isGif;
    // 是否圆形裁剪
    private boolean circleDimmedLayer;
    // 是否开启点击声音 点击选中图片的时候是否有声音
    private boolean openClickSound;
    // 是否传入已选图片
    private List<LocalMedia> selectionMedia;
    // 压缩到多少kb
    private int compressToSize;
    // 裁剪压缩质量 默认100 质量压缩  改变位深大小改变  尺寸不变
    private int cropCompressQuality;
    // 0是activity对象  1是fragment对象
    private int jumpType = -1;
    // 是否启用自定义相机
    private boolean startCustomCamera;
    // 是否拍照的时候显示裁剪框
    private boolean cameraTakingCrop;
    // 拍照的时候显示裁剪框占屏幕宽度 (0,1) 左闭右闭
    private float cameraTakingCropWidth;
    // 拍照的时候显示裁剪框占屏幕高度 (0,1) 左闭右闭
    private float cameraTakingCropHeight;
    // 是否拍照后 调整亮度
    private boolean cameraAdjustLight;

    // 拍照后不再图片选择页面直接返回  不涉及压缩和裁剪  设置失效
    private boolean takedImmediatelyReturnBack;

    private LibraryLinstener libraryLinstener;

    /**
     * 私有构造
     */
    private InossemPictureConfig() {
    }

    /**
     * 获取配置类实例
     *
     * @return 返回实例
     */
    public static InossemPictureConfig getInstance() {
        if (instance == null) {
            instance = new InossemPictureConfig();
        }
        return instance;
    }

    /**
     * activity中调用的方法
     *
     * @param activity activity对象
     * @return 当前类
     */
    public InossemPictureConfig initActivity(Activity activity) {
        if (activity == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "activity null");
        }
        this.jumpType = 0;
        this.activity = activity;
        init();
        return this;
    }

    /**
     * fragment中调用的方法
     *
     * @param fragment fragment对象
     * @return 当前类
     */
    public InossemPictureConfig initFragment(Fragment fragment) {
        if (fragment == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "fragment null");
        }
        this.jumpType = 1;
        this.fragment = fragment;
        init();
        return this;
    }

    /**
     * 编辑配置项的默认值
     */
    private void init() {
        this.requestCode = PictureSelectContants.DEFAULT_PICTURE_REQUEST_CODE;
        this.maxSelect = PictureSelectContants.DEFAULT_PICTURE_MAX_SIZE;
        this.enableCrop = false;
        this.compress = true;
        this.startCustomCamera = false;
        this.cameraTakingCrop = false;
        this.cameraAdjustLight = false;
        this.takedImmediatelyReturnBack = false;
        this.openGallery = PictureMimeType.ofImage();
        this.selectionMode = PictureConfig.MULTIPLE;
        this.singleDirectReturn = false;
        this.synOrAsy = true;
        this.isCamera = true;
        this.imageFormat = PictureMimeType.JPEG;
        this.outputCameraPath = PictureSelectContants.DEFAULT_PICTURE_INOSSEM_CAMERA;
        this.compressSavePath = CompressConstant.INOSSEM_DEFAULT_COMPRESS_TAGPATH;
        this.isGif = false;
        this.circleDimmedLayer = false;
        this.openClickSound = false;
        this.selectionMedia = new ArrayList<>();
        this.compressToSize = PictureSelectContants.DEFAULT_PICTURE_MIN_NOT_COMPRESS_SIZE;
        this.cropCompressQuality = PictureSelectContants.DEFAULT_PICTURE_COMPRESS_QUALITY;
    }

    /**
     * 获取fragment实例
     *
     * @return
     */
    public Fragment getFragment() {
        return fragment;
    }

    /**
     * 获取是activity还是fragment跳转进来的
     *
     * @return 0-activity 1-fragment
     */
    public int getJumpType() {
        return jumpType;
    }

    /**
     * 请求code 默认0X001
     *
     * @param requestCode
     * @return
     */
    public InossemPictureConfig setRequestCode(int requestCode) {
        if (requestCode <= 0) {
            throw new InossemException(ExceptionEnum.ILLEGAL_PARAMS_RANGE, "requestCode must bigger than zero");
        }
        this.requestCode = requestCode;
        return this;
    }

    /**
     * 最大选取张数 默认四张
     *
     * @param maxSelect
     * @return
     */
    public InossemPictureConfig setMaxSelect(int maxSelect) {
        if (maxSelect <= 0) {
            throw new InossemException(ExceptionEnum.ILLEGAL_PARAMS_RANGE, "maxSelect must bigger than zero");
        }
        this.maxSelect = maxSelect;
        return this;
    }

    /**
     * 是否裁剪
     *
     * @param enableCrop
     * @return
     */
    public InossemPictureConfig setEnableCrop(boolean enableCrop) {
        this.enableCrop = enableCrop;
        return this;
    }

    /**
     * 是否压缩
     *
     * @param compress
     * @return
     */
    public InossemPictureConfig setCompress(boolean compress) {
        this.compress = compress;
        return this;
    }

    /**
     * 选取类别 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
     *
     * @param openGallery
     * @return this InossemPictureConfig
     */
    public InossemPictureConfig setOpenGallery(int openGallery) {
        if (!(openGallery == PictureMimeType.ofAll()
                || openGallery == PictureMimeType.ofImage()
                || openGallery == PictureMimeType.ofVideo()
                || openGallery == PictureMimeType.ofAudio())) {
            throw new InossemException(ExceptionEnum.ILLEGAL_PARAMS_RANGE, "openGallery must between 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()");
        }
        this.openGallery = openGallery;
        return this;
    }

    /**
     * 多选:PictureConfig.MULTIPLE  单选:PictureConfig.SINGLE
     *
     * @param selectionMode
     * @return
     */
    public InossemPictureConfig setSelectionMode(int selectionMode) {
        if (!(selectionMode == PictureConfig.MULTIPLE
                || selectionMode == PictureConfig.SINGLE)) {
            throw new InossemException(ExceptionEnum.ILLEGAL_PARAMS_RANGE, "selectionMode must between 多选:PictureConfig.MULTIPLE  单选:PictureConfig.SINGLE");
        }
        this.selectionMode = selectionMode;
        return this;
    }

    /**
     * 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
     *
     * @param theme
     * @return
     */
    public InossemPictureConfig setTheme(int theme) {
        this.theme = theme;
        return this;
    }

    /**
     * 是否显示拍照按钮(选择图片的列表页面)
     *
     * @param camera
     * @return
     */
    public InossemPictureConfig setCamera(boolean camera) {
        isCamera = camera;
        return this;
    }

    /**
     * 拍照保存图片格式后缀,默认jpeg
     *
     * @param imageFormat
     * @return 当前类
     */
    public InossemPictureConfig setImageFormat(String imageFormat) {
        if (TextUtils.isEmpty(imageFormat) || imageFormat.trim().length() == 0) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "imageFormat can not be null");
        }
        this.imageFormat = imageFormat;
        return this;
    }

    /**
     * 自定义拍照保存文件夹的名字
     *
     * @param outputCameraPath 保存路径的文件夹名  默认InossemCamera
     * @return
     */
    @Deprecated
    private InossemPictureConfig setOutputCameraPath(String outputCameraPath) {
        if (TextUtils.isEmpty(outputCameraPath) || outputCameraPath.trim().length() == 0) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "outputCameraPath can not be null");
        }
        this.outputCameraPath = outputCameraPath;
        return this;
    }

    /**
     * 压缩图片保存文件夹名称
     *
     * @param compressSavePath
     * @return
     */
    public InossemPictureConfig setCompressSavePath(String compressSavePath) {
        if (TextUtils.isEmpty(compressSavePath) || compressSavePath.trim().length() == 0) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "compressSavePath can not be null");
        }
        this.compressSavePath = compressSavePath;
        return this;
    }

    /**
     * 是否显示gif图片
     *
     * @param gif
     * @return
     */
//    public InossemPictureConfig setGif(boolean gif) {
//        isGif = gif;
//        return this;
//    }

    /**
     * 是否圆形裁剪
     *
     * @param circleDimmedLayer
     * @return
     */
    public InossemPictureConfig setCircleDimmedLayer(boolean circleDimmedLayer) {
        this.circleDimmedLayer = circleDimmedLayer;
        return this;
    }

    /**
     * 是否开启点击声音 点击选中图片的时候是否有声音
     *
     * @param openClickSound
     * @return
     */
    public InossemPictureConfig setOpenClickSound(boolean openClickSound) {
        this.openClickSound = openClickSound;
        return this;
    }

    /**
     * 是否传入已选图片
     *
     * @param selectionMedia
     * @return
     */
    public InossemPictureConfig setSelectionMedia(List<LocalMedia> selectionMedia) {
        this.selectionMedia = selectionMedia;
        return this;
    }

    /**
     * 小于 compressToSize kb的图片不压缩
     *
     * @param compressToSize
     * @return
     */
    public InossemPictureConfig setCompressToSize(int compressToSize) {
        if (compressToSize <= 0) {
            throw new InossemException(ExceptionEnum.ILLEGAL_PARAMS_RANGE, "compressToSize must bigger than zero");
        }
        this.compressToSize = compressToSize;
        return this;
    }

    /**
     * 裁剪压缩质量 默认100 质量压缩  改变位深大小改变  尺寸不变
     *
     * @param cropCompressQuality
     * @return
     */
    public InossemPictureConfig setCropCompressQuality(int cropCompressQuality) {
        if (cropCompressQuality <= 0 || cropCompressQuality > 100) {
            cropCompressQuality = CompressConstant.DEFAULT_QUALITY_SIZE;
        }
        this.cropCompressQuality = cropCompressQuality;
        return this;
    }

    /**
     * 拍照后不再图片选择页面直接返回  不涉及压缩和裁剪  设置失效
     *
     * @param takedImmediatelyReturnBack
     * @return
     */
    public InossemPictureConfig setTakedImmediatelyReturnBack(boolean takedImmediatelyReturnBack) {
        this.takedImmediatelyReturnBack = takedImmediatelyReturnBack;
        return this;
    }

    /**
     * 获取activity实例
     *
     * @return activity实例
     */
    public Activity getActivity() {
        return activity;
    }

    /**
     * 获取自定义requestcode
     *
     * @return自定义requestcode
     */
    public int getRequestCode() {
        return requestCode;
    }

    /**
     * 获取最大选取张数
     *
     * @return 最大张数
     */
    public int getMaxSelect() {
        return maxSelect;
    }

    /**
     * 获取是否裁剪
     *
     * @return true-是  false-不是
     */
    public boolean isEnableCrop() {
        return enableCrop;
    }

    /**
     * 获取是否压缩
     *
     * @return true-是  false-不是
     */
    public boolean isCompress() {
        return compress;
    }

    /**
     * 获取选取类型
     *
     * @return 类型对应值 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
     */
    public int getOpenGallery() {
        return openGallery;
    }

    /**
     * 多选:PictureConfig.MULTIPLE  单选:PictureConfig.SINGLE
     *
     * @return 多选:PictureConfig.MULTIPLE  单选:PictureConfig.SINGLE
     */
    public int getSelectionMode() {
        return selectionMode;
    }

    /**
     * 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
     *
     * @return 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
     */
    public int getTheme() {
        return theme;
    }

    /**
     * 是否显示拍照按钮
     *
     * @return true-是 false-不显示
     */
    public boolean isCamera() {
        return isCamera;
    }

    /**
     * 拍照保存图片格式后缀,默认jpeg
     *
     * @return 图片格式后缀
     */
    public String getImageFormat() {
        return imageFormat;
    }

    /**
     * 自定义拍照保存路径
     *
     * @return 拍照保存路径
     */
    public String getOutputCameraPath() {
        return outputCameraPath;
    }

    /**
     * 获取压缩图片保存地址
     *
     * @return 压缩图片保存地址
     */
    public String getCompressSavePath() {
        return compressSavePath;
    }

    /**
     * 是否显示gif图片
     *
     * @return
     */
    public boolean isGif() {
        return isGif;
    }

    /**
     * 是否返回圆形图片
     *
     * @return true-是  false-正常图片
     */
    public boolean isCircleDimmedLayer() {
        return circleDimmedLayer;
    }

    /**
     * 是否开启点击声音 点击选中图片的时候是否有声音
     *
     * @return 当前类
     */
    public boolean isOpenClickSound() {
        return openClickSound;
    }

    public List<LocalMedia> getSelectionMedia() {
        return selectionMedia;
    }

    public int getCompressToSize() {
        return compressToSize;
    }

    public int getCropCompressQuality() {
        return cropCompressQuality;
    }

    /**
     * 是否启用自定义相机
     *
     * @param startCustomCamera
     * @return 当前类
     */
    @Deprecated
    private InossemPictureConfig setStartCustomCamera(boolean startCustomCamera) {
        this.startCustomCamera = startCustomCamera;
        return this;
    }

    /**
     * 是否拍照的时候显示裁剪框
     *
     * @param cameraTakingCrop
     * @return 当前类
     */
    public InossemPictureConfig setCameraTakingCrop(boolean cameraTakingCrop) {
        this.cameraTakingCrop = cameraTakingCrop;
        return this;
    }

    /**
     * 是否拍照后 调整亮度
     *
     * @param cameraAdjustLight
     * @return 当前类
     */
    public InossemPictureConfig setCameraAdjustLight(boolean cameraAdjustLight) {
        this.cameraAdjustLight = cameraAdjustLight;
        return this;
    }

    /**
     * 获取是否是自定义相机
     *
     * @return true-是  false-不是
     */
    public boolean isStartCustomCamera() {
        return startCustomCamera;
    }

    /**
     * 获取是否在拍照的时候显示裁剪框
     *
     * @return true-是  false-不是
     */
    public boolean isCameraTakingCrop() {
        return cameraTakingCrop;
    }

    /**
     * 获取是否在拍照后调整亮度
     *
     * @return true-是  false-不是
     */
    public boolean isCameraAdjustLight() {
        return cameraAdjustLight;
    }

    /**
     * 获取是否在拍照后立即返回照片  不在选照片页面停留
     *
     * @return true-是  false-不是
     */
    public boolean isTakedImmediatelyReturnBack() {
        return takedImmediatelyReturnBack;
    }

    /**
     * 是否异步压缩
     *
     * @return 同步true或异步false
     */
    public boolean isSynOrAsy() {
        return synOrAsy;
    }

    /**
     * 是否异步压缩
     *
     * @param synOrAsy 同步true或异步false
     */
    public void setSynOrAsy(boolean synOrAsy) {
        this.synOrAsy = synOrAsy;
    }

    /**
     * 单选模式下是否直接返回，PictureConfig.SINGLE模式下有效
     *
     * @return true 直接返回
     */
    public boolean isSingleDirectReturn() {
        return singleDirectReturn;
    }

    /**
     * 单选模式下是否直接返回，PictureConfig.SINGLE模式下有效
     *
     * @param singleDirectReturn true 直接返回
     */
    public void setSingleDirectReturn(boolean singleDirectReturn) {
        this.singleDirectReturn = singleDirectReturn;
    }
}

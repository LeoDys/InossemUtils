package com.inossem_library.other.picture.util;

import android.content.Intent;
import android.os.Build;

import com.inossem_library.R;
import com.inossem_library.exception.InossemException;
import com.inossem_library.exception.constant.ExceptionEnum;
import com.inossem_library.other.picture.config.InossemPictureConfig;
import com.inossem_library.other.picture.constant.PictureSelectContants;
import com.luck.picture.lib.PictureSelectionModel;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 图片选择二层工具类
 *
 * @author LeoDys E-mail:changwen.sun@inossem.com 2020/3/2 15:51
 * @version 1.0.8
 * @since 1.0.8
 */

public class PicSelectUtil {

    /**
     * 将默认值赋予框架本身
     *
     * @param configBean 自定义参数配置类  有默认参数  activity
     */
    public static void activitySelectPictureActivity(InossemPictureConfig configBean, OnResultCallbackListener... callbackListener) {
        if (configBean == null) {
            new InossemException(ExceptionEnum.NULL_PARAMS, "InossemPictureConfig null,please check");
        }
        PictureSelector pictureSelector = createSelectionModle(configBean);
        PictureSelectionModel openTypeModle = pictureSelector
                // 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .openGallery(configBean.getOpenGallery());
        PictureSelectionModel sameConfigModle = getPictureSelectionModel(configBean, openTypeModle);
        resultCallBack(configBean, sameConfigModle, callbackListener);

    }

    /**
     * 回调给调用放
     *
     * @param configBean       参数配置类
     * @param sameConfigModle  统一配置config
     * @param callbackListener 回调接口
     */
    private static void resultCallBack(InossemPictureConfig configBean, PictureSelectionModel sameConfigModle, OnResultCallbackListener[] callbackListener) {
        // 是否启用了接口回调的模式
        if (callbackListener != null && callbackListener.length > 0) {
            // 接口回调返回结果
//            sameConfigModle.forResult(result -> {
//
//            });
            sameConfigModle.forResult(configBean.getRequestCode(), new OnResultCallbackListener() {
                @Override
                public void onResult(List result) {
                    callbackListener[0].onResult(result);
                }

                @Override
                public void onCancel() {

                }
            });
        } else {
            // 正常onAcyivityResult回调结果
            sameConfigModle.forResult(configBean.getRequestCode());
        }
    }

    /**
     * 仅拍照
     *
     * @param configBean 自定义参数配置类  有默认参数  activity
     */
    public static void takePhoto(InossemPictureConfig configBean, OnResultCallbackListener... callbackListener) {
        if (configBean == null) {
            new InossemException(ExceptionEnum.NULL_PARAMS, "InossemPictureConfig null,please check");
        }
        // configBean.getJumpType() 区分是fragment跳转进来的  还是activity
        PictureSelector pictureSelector = createSelectionModle(configBean);
        PictureSelectionModel openTypeModle = pictureSelector
                // 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .openCamera(configBean.getOpenGallery());
        PictureSelectionModel sameConfigModle = getPictureSelectionModel(configBean, openTypeModle);

        // 是否启用了接口回调的模式
        resultCallBack(configBean, sameConfigModle, callbackListener);
    }

    /**
     * 获取原始配置选项
     *
     * @param configBean 传入配置类参数
     * @return 配置类对象
     */
    private static PictureSelector createSelectionModle(InossemPictureConfig configBean) {
        // configBean.getJumpType() 区分是fragment跳转进来的  还是activity
        PictureSelector pictureSelector = null;
        if (configBean.getJumpType() == 0) {
            pictureSelector = PictureSelector.create(configBean.getActivity());
        } else {
            pictureSelector = PictureSelector.create(configBean.getFragment());
        }
        return pictureSelector;
    }

    /**
     * 相同的配置封住哪个
     *
     * @param configBean    配置传入类
     * @param openTypeModle 要打开的操作模式
     * @return 配置完成对象
     */
    private static PictureSelectionModel getPictureSelectionModel(InossemPictureConfig configBean, PictureSelectionModel openTypeModle) {
        return openTypeModle
                // 外部传入图片加载引擎，必传项
                .imageEngine(GlideEngine.createGlideEngine())
                // 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                .theme(R.style.picture_white_style)
                // 最大图片选择数量
                .maxSelectNum(configBean.getMaxSelect())
                // 最小选择数量
                .minSelectNum(0)
                // 每行显示个数
                .imageSpanCount(PictureSelectContants.DEFAULT_PICTURE_MAX_SIZE)
                // 多选 or 单选
                .selectionMode(configBean.getSelectionMode())
                // 是否可预览图片
                .isPreviewImage(true)
                // 是否可预览视频
                .isPreviewVideo(true)
                // 是否显示拍照按钮
                .isCamera(configBean.isCamera())
                // 图片列表点击 缩放效果 默认true TODO
                .isZoomAnim(true)
                // 拍照保存图片格式后缀,默认jpeg
                .imageFormat(configBean.getImageFormat())
                // 是否裁剪
                .isEnableCrop(configBean.isEnableCrop())
                // 是否压缩
                .isCompress(configBean.isCompress())
                // 同步true或异步false 压缩 默认同步
                .synOrAsy(configBean.isSynOrAsy())
                // 压缩图片保存地址
                .compressSavePath(configBean.getCompressSavePath())
                // 是否显示uCrop工具栏，默认不显示
                .hideBottomControls(true)
                // 是否显示gif图片
                .isGif(configBean.isGif())
                // 裁剪框是否可拖拽
                .freeStyleCropEnabled(false)
                // 是否圆形裁剪
                .circleDimmedLayer(configBean.isCircleDimmedLayer())
                // 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                .showCropFrame(configBean.isCircleDimmedLayer() ? false : true)
                // 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                .showCropGrid(configBean.isCircleDimmedLayer() ? false : true)
                // 是否开启点击声音
                .isOpenClickSound(configBean.isOpenClickSound())
                // 是否传入已选图片
                .selectionData(configBean.getSelectionMedia())
                // 是否可拖动裁剪框(固定) TODO
                // .isDragFrame(true)
                // .videoMaxSecond(3)
                // .videoMinSecond(10)
                // 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                .isPreviewEggs(true)
                // 裁剪压缩质量 默认100
                .cutOutQuality(configBean.getCropCompressQuality())
                // 小于compressToSize kb的图片不压缩
                .minimumCompressSize(configBean.getCompressToSize())
                // 裁剪是否可放大缩小图片
                .scaleEnabled(true)
                // 自定义相机的暂时注释
                // .startCustomCamera(configBean.isStartCustomCamera())
                // .cameraAdjustLight(configBean.isCameraAdjustLight())
                // .cameraCrop(configBean.isCameraTakingCrop())
                // 小于100kb的图片不压缩
                .minimumCompressSize(configBean.getCompressToSize());
    }

    /**
     * 获取原图的路径
     *
     * @param data onActivityResult 返回的Intent
     * @return 返回原图路径list
     */
    public static List<String> getPath(int requestCode, Intent data) {
        if (requestCode != PictureSelectContants.DEFAULT_PICTURE_REQUEST_CODE) {
            return null;
        }
        List<LocalMedia> localMedia = PictureSelector.obtainMultipleResult(data);
        if (localMedia == null || localMedia.isEmpty()) {
            return null;
        }
        List<String> pathList = new ArrayList<>();
        for (LocalMedia media : localMedia) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                pathList.add(media.getAndroidQToPath());
            } else {
                pathList.add(media.getPath());
            }
        }
        return pathList;
    }

    /**
     * 获取裁剪图的路径
     *
     * @param data onActivityResult 返回的Intent
     * @return 返回裁剪图路径list
     */
    public static List<String> getCutPath(int requestCode, Intent data) {
        if (requestCode != PictureSelectContants.DEFAULT_PICTURE_REQUEST_CODE) {
            return null;
        }
        List<LocalMedia> localMedia = PictureSelector.obtainMultipleResult(data);
        if (localMedia == null || localMedia.isEmpty()) {
            return null;
        }
        List<String> pathList = new ArrayList<>();
        for (LocalMedia media : localMedia) {
            pathList.add(media.getCutPath());
        }
        return pathList;
    }

    /**
     * 获取压缩图的路径
     *
     * @param data onActivityResult 返回的Intent
     * @return 返回压缩图路径list
     */
    public static List<String> getCompressPath(int requestCode, Intent data) {
        if (requestCode != PictureSelectContants.DEFAULT_PICTURE_REQUEST_CODE) {
            return null;
        }
        List<LocalMedia> localMedia = PictureSelector.obtainMultipleResult(data);
        if (localMedia == null || localMedia.isEmpty()) {
            return null;
        }
        List<String> pathList = new ArrayList<>();
        for (LocalMedia media : localMedia) {
            pathList.add(media.getCompressPath());
        }
        return pathList;
    }

}

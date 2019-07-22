package com.luck.picture.lib.tools;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.InossemPictureConfig;
import com.luck.picture.lib.constant.PictureSelectContants;
import com.luck.picture.lib.exception.InossemPictureSelectException;

import static com.luck.picture.lib.constant.PictureSelectContants.DEFAULT_PICTURE_MAX_SIZE;

/**
 * 图片选择二层工具类
 * Created by wen40 on 2019/7/10.
 */

public class PicSelectUtil {
    /**
     * 将默认值赋予框架本身
     *
     * @param configBean 自定义参数配置类  有默认参数  activity
     */
    public static void activitySelectPictureActivity(InossemPictureConfig configBean) {
        if (configBean == null) {
            new InossemPictureSelectException("InossemPictureConfig null,please check");
        }
        // configBean.getJumpType() 区分是fragment跳转进来的  还是activity
        PictureSelector pictureSelector = null;
        if (configBean.getJumpType() == 0) {
            pictureSelector = PictureSelector.create(configBean.getActivity());
        } else {
            pictureSelector = PictureSelector.create(configBean.getFragment());
        }
        pictureSelector
                // 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .openGallery(configBean.getOpenGallery())
                // 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                // .theme()
                // 最大图片选择数量
                .maxSelectNum(configBean.getMaxSelect())
                // 最小选择数量
                .minSelectNum(0)
                // 每行显示个数
                .imageSpanCount(PictureSelectContants.DEFAULT_PICTURE_MAX_SIZE)
                // 多选 or 单选
                .selectionMode(configBean.getSelectionMode())
                // 是否可预览图片
                .previewImage(true)
                // 是否可预览视频
                .previewVideo(true)
                // 是否可播放音频
                .enablePreviewAudio(true)
                // 是否显示拍照按钮
                .isCamera(configBean.isCamera())
                // 图片列表点击 缩放效果 默认true TODO
                .isZoomAnim(true)
                // 拍照保存图片格式后缀,默认jpeg
                .imageFormat(configBean.getImageFormat())
                // 自定义拍照保存路径
                .setOutputCameraPath(configBean.getOutputCameraPath())
                // 是否裁剪
                .enableCrop(configBean.isEnableCrop())
                // 是否压缩
                .compress(configBean.isCompress())
                //同步true或异步false 压缩 默认同步
//                    .synOrAsy(true)
                //压缩图片保存地址
                .compressSavePath(configBean.getCompressSavePath())
                // glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                // .sizeMultiplier(0.1f)
                // glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度 TODO
                .glideOverride(PictureSelectContants.DEFAULT_PICTURE_GLIDE_WIDTH, PictureSelectContants.DEFAULT_PICTURE_GLIDE_HEIGHT)
                // 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                // .withAspectRatio(aspect_ratio_x, aspect_ratio_y)
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
                .openClickSound(configBean.isOpenClickSound())
                // 是否传入已选图片
                .selectionMedia(configBean.getSelectionMedia())
                // 是否可拖动裁剪框(固定) TODO
                // .isDragFrame(true)
                // .videoMaxSecond(3)
                // .videoMinSecond(10)
                // 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                .previewEggs(true)
                // 裁剪压缩质量 默认100
                .cropCompressQuality(configBean.getCropCompressQuality())
                // 小于100kb的图片不压缩
                .minimumCompressSize(configBean.getMinimumCompressSize())
                // 裁剪宽高比，设置如果大于图片本身宽高则无效 TODO
                // .cropWH()
                // 裁剪是否可旋转图片
                //.rotateEnabled(true)
                // 裁剪是否可放大缩小图片
                .scaleEnabled(true)
                .startCustomCamera(configBean.isStartCustomCamera())
                .cameraAdjustLight(configBean.isCameraAdjustLight())
                .cameraCrop(configBean.isCameraTakingCrop())
                .takedImmediatelyReturnBack(configBean.isTakedImmediatelyReturnBack())
                // 视频录制质量 0 or 1
                //.videoQuality()
                //显示多少秒以内的视频or音频也可适用
                // .videoSecond()
                //录制视频秒数 默认60s
                // .recordVideoSecond()
                //结果回调onActivityResult code
                .forResult(configBean.getRequestCode());

    }
}

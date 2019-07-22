package com.luck.picture.lib;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;

import com.luck.picture.lib.config.InossemPictureConfig;
import com.luck.picture.lib.constant.PictureSelectContants;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.PicSelectUtil;

import java.util.List;

/**
 * 图片选择使用规范类
 * Created by wen40 on 2019/7/19.
 */

public class PictureSelectUseDemoActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        /**
         * 图片选择使用方法
         */
        InossemPictureConfig configBean = InossemPictureConfig
                .getInstance()
                .initActivity(PictureSelectUseDemoActivity.this)
                .setCompress(true)
                .setStartCustomCamera(true);

        PicSelectUtil.activitySelectPictureActivity(configBean);
    }

    // 照片回调
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureSelectContants.DEFAULT_PICTURE_REQUEST_CODE:
                    // 图片选择结果回调
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的
                    List<LocalMedia> localMedia = PictureSelector.obtainMultipleResult(data);
                    break;
                case 11:
                    //isdhfoishofihsi
                    break;

            }
        }
    }
}

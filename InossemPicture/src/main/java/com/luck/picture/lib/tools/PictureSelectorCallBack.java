package com.luck.picture.lib.tools;

import android.content.Intent;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.camera.exception.InossemCameraException;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;

/**
 * Created by wen40 on 2019/7/10.
 */

public class PictureSelectorCallBack {

    /**
     * @param onActivityResultIntent onActivityResult的Intent
     * @param type(1\2\3)            1.media.getPath(); 为原图path
     *                               2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
     *                               3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
     *                               如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的
     * @return
     */
    public static LocalMedia getSinglePicCallBack(Intent onActivityResultIntent, int type) {
        if (type != 1 && type != 2 && type != 3) {
            throw new InossemCameraException("请填写正确的类型");
        }
        List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(onActivityResultIntent);
        switch (type) {
            case 1:

                break;
            case 2:

                break;
            case 3:

                break;
        }
        return null;
    }
}

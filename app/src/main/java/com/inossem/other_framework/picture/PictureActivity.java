package com.inossem.other_framework.picture;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inossem.BaseActivity;
import com.inossem.R;
import com.inossem.util.Utils;
import com.inossem_library.callback.LibraryLinstener;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.InossemPictureConfig;
import com.luck.picture.lib.constant.PictureSelectContants;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.PicSelectUtil;
import com.zxy.util.CompressSyncUtils;
import com.zxy.util.TinyCompressUtils;
import com.zxy.util.TinyConfig;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 图片选择 压缩 裁剪
 *
 * @author Leo E-mail:changwen.sun@inossem.com 2019/7/25 20:20
 * @version 1.0.0
 * @since JDK-1.8
 */

public class PictureActivity extends BaseActivity {
    TextView description;
    private LinearLayout buttonLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        description = findViewById(R.id.description);
        buttonLayout = findViewById(R.id.buttonLayout);
        Utils.createButtons(PictureActivity.this, buttonLayout, 10, new Utils.ButtonListener() {
            @Override
            public void onCreated(Button button, int position) {
                switch (position) {
                    case 0:
                        button.setText("图片选择");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                /**
                                 * 图片选择使用方法
                                 */
                                InossemPictureConfig configBean = InossemPictureConfig
                                        .getInstance()
                                        .initActivity(PictureActivity.this)
                                        /*.setLibraryLinstener(new LibraryLinstener() {
                                            @Override
                                            public List<String> compressPicCallBack(List<File> files) {
                                                // 压缩
                                                return CompressSyncUtils.filesCallBack(files, new TinyConfig(PictureActivity.this));
                                            }
                                        })*/
                                        .setEnableCrop(true)
                                        .setStartCustomCamera(true);

                                PicSelectUtil.activitySelectPictureActivity(configBean);
                            }
                        });
                        break;
                }
            }
        });
    }

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
                    Log.e("LocalMedia-path", localMedia.toString());
                    List<File> files = new ArrayList<>();
                    for (LocalMedia media : localMedia) {
                        files.add(new File(media.getPath()));
                    }

                    Log.i("LocalMedia-compress", "内部：" + Thread.currentThread().getId());

                    TinyCompressUtils.filesCallBack(files, new TinyConfig(PictureActivity.this), new TinyCompressUtils.TidyCompressFilesListener() {
                        @Override
                        public void compressCallBack(List<String> outfiles) {
                            Log.i("LocalMedia-compress", "内部：" + Thread.currentThread().getId());
                            Log.e("LocalMedia-compress", outfiles.toString());
                        }
                    });

                    Log.e("LocalMedia-return", "11111111");

                    break;

            }
        }
    }
}

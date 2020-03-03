package com.inossem.other_framework.picture;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inossem.BaseActivity;
import com.inossem.R;
import com.inossem.util.Utils;
import com.inossem_library.app.path.util.PathUtils;
import com.inossem_library.other.compress.config.CompressConfig;
import com.inossem_library.other.compress.util.CompressUtils;
import com.inossem_library.other.picture.config.InossemPictureConfig;
import com.inossem_library.other.picture.constant.PictureSelectContants;
import com.inossem_library.other.picture.util.PicSelectUtil;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;

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
    private static final String TAG = "BaseActivity";
    TextView description;
    private LinearLayout buttonLayout;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
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
                                        .setCompress(true)
                                        .setCompressSavePath(PathUtils.getLegalPath(PictureActivity.this, Environment.DIRECTORY_PICTURES) + "InossemTest");
                                PicSelectUtil.activitySelectPictureActivity(configBean, new OnResultCallbackListener() {
                                    @Override
                                    public void onResult(List<LocalMedia> result) {
                                        for (LocalMedia media : result) {

                                            Log.i(TAG, "是否压缩:" + media.isCompressed());
                                            Log.i(TAG, "压缩:" + media.getCompressPath());
                                            Log.i(TAG, "原图:" + media.getPath());
                                            Log.i(TAG, "是否裁剪:" + media.isCut());
                                            Log.i(TAG, "裁剪:" + media.getCutPath());
                                            Log.i(TAG, "是否开启原图:" + media.isOriginal());
                                            Log.i(TAG, "原图路径:" + media.getOriginalPath());
                                            Log.i(TAG, "Android Q 特有Path:" + media.getAndroidQToPath());


                                            CompressConfig compressConfig = new CompressConfig(PictureActivity.this);
//                                            compressConfig.setCompressDirectory(PathUtils.getLegalPath(activity, Environment.DIRECTORY_PICTURES) + "InossemTest");
                                            compressConfig.setCompreeToSize(200);
                                            compressConfig.setArgbConfig(Bitmap.Config.RGB_565);
                                            compressConfig.setKeepSampling(true);
                                            compressConfig.setQuality(100);

                                            CompressUtils.fileCallBack(new File(media.getPath()), compressConfig, outfile ->
                                                    Log.i(TAG, "Tiny压缩:" + outfile));

                                        }
                                    }
                                });
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
                    List<String> compressPath = PicSelectUtil.getCompressPath(requestCode, data);
                    List<File> files = new ArrayList<>();
                    for (String media : compressPath) {
                        files.add(new File(media));
                        Log.e("LocalMedia-path", media);
                    }
                    ImageView imageView = new ImageView(PictureActivity.this);
                    Bitmap bitmap = BitmapFactory.decodeFile(compressPath.get(0));
                    imageView.setImageBitmap(bitmap);
                    buttonLayout.addView(imageView);
                    break;
            }
        }
    }

}

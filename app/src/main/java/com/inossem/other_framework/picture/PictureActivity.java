package com.inossem.other_framework.picture;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inossem.BaseActivity;
import com.inossem.MainActivity;
import com.inossem.R;
import com.inossem.util.Utils;
import com.inossem_library.app.path.util.PathUtils;
import com.inossem_library.other.picture.config.InossemPictureConfig;
import com.inossem_library.other.picture.constant.PictureSelectContants;
import com.inossem_library.other.picture.util.GlideEngineUtil;
import com.inossem_library.other.picture.util.PicSelectUtil;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
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
                                        .setCompress(true)
                                        .setCompressSavePath(getPath(PictureActivity.this, Environment.DIRECTORY_PICTURES));
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

    /**
     * 获取保存文件路径 （增加Q版本判断）
     */
    public static String getPath(Context context, String environmentType) {
        // Environment.DIRECTORY_DOCUMENTS  ， DIRECTORY_DCIM  ，DIRECTORY_DOWNLOADS ， DIRECTORY_MOVIES
        String path;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            path = context.getExternalFilesDir(environmentType) + File.separator + "InossemTest";
        } else {
            path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "InossemTest";
        }
        return path;
    }

}

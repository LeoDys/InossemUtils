package com.inossem.other_framework.pattern;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inossem.BaseActivity;
import com.inossem.R;
import com.inossem.other_framework.picture.PictureActivity;
import com.inossem.util.Utils;
import com.inossem_library.other.pattern.util.PatterUtils;
import com.inossem_library.tips.toast.util.ToastUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.InossemPictureConfig;
import com.luck.picture.lib.constant.PictureSelectContants;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.PicSelectUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 手势密码
 *
 * @author Leo E-mail:changwen.sun@inossem.com 2019/7/25 20:20
 * @version 1.0.0
 * @since JDK-1.8
 */

public class PatternActivity extends BaseActivity {
    TextView description;
    private LinearLayout buttonLayout;

    private String patternStr = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        description = findViewById(R.id.description);
        buttonLayout = findViewById(R.id.buttonLayout);
        Utils.createButtons(PatternActivity.this, buttonLayout, 10, new Utils.ButtonListener() {
            @Override
            public void onCreated(Button button, int position) {
                switch (position) {
                    case 0:
                        button.setText("设置手势密码");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                PatterUtils.setPattern(PatternActivity.this, null);
                            }
                        });
                        break;
                    case 1:
                        button.setText("校验手势密码");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                PatterUtils.checkPattern(PatternActivity.this, patternStr, null);
                            }
                        });
                        break;
                    case 2:
                        button.setText("重置手势密码");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                PatterUtils.resetPattern(PatternActivity.this, patternStr, null);
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
        String patternResult = PatterUtils.getPatternResult(resultCode, requestCode, data);
        if (!TextUtils.isEmpty(patternResult)) {
            patternStr = patternResult;
            ToastUtils.show(PatternActivity.this, "手势密码设置成功");
            return;
        }
        String resetPatternResult = PatterUtils.getResetPatternResult(resultCode, requestCode, data);
        if (!TextUtils.isEmpty(resetPatternResult)) {
            patternStr = resetPatternResult;
            ToastUtils.show(PatternActivity.this, "重置手势密码成功");
            return;
        }
        Boolean checkPatternResult = PatterUtils.getCheckPatternResult(resultCode, requestCode, data);
        if (checkPatternResult != null && checkPatternResult) {
            ToastUtils.show(PatternActivity.this, "校验手势密码成功");
            return;
        }
    }
}

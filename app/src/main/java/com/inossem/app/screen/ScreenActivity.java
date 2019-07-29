package com.inossem.app.screen;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inossem.BaseActivity;
import com.inossem.R;
import com.inossem.util.Utils;
import com.inossem_library.app.screen.util.ScreenUtils;
import com.inossem_library.tips.toast.util.ToastUtils;

/**
 * 屏幕相关
 *
 * @author LinH
 */
public class ScreenActivity extends BaseActivity {
    TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        description = findViewById(R.id.description);
        LinearLayout buttonLayout = findViewById(R.id.buttonLayout);
        Utils.createButtons(ScreenActivity.this, buttonLayout, 20, new Utils.ButtonListener() {
            @Override
            public void onCreated(final Button button, int position) {
                switch (position) {
                    case 0:
                        button.setText("获取屏幕的宽度(单位 px)" + ScreenUtils.getScreenWidth(ScreenActivity.this));
                        break;
                    case 1:
                        button.setText("获取屏幕的高度(单位 px)" + ScreenUtils.getScreenHeight(ScreenActivity.this));
                        break;
                    case 2:
                        button.setText("获取应用屏幕的宽度(单位 px)" + ScreenUtils.getAppScreenWidth(ScreenActivity.this));
                        break;
                    case 3:
                        button.setText("获取应用屏幕的高度(单位 px)" + ScreenUtils.getAppScreenHeight(ScreenActivity.this));
                        break;
                    case 4:
                        button.setText("获取屏幕密度(单位 px)" + ScreenUtils.getScreenDensity());
                        break;
                    case 5:
                        button.setText("获取屏幕密度 (单位 dpi)" + ScreenUtils.getScreenDensityDpi());
                        break;
                    case 6:
                        button.setText("设置屏幕为全屏");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ScreenUtils.setFullScreen(ScreenActivity.this);
                            }
                        });
                        break;
                    case 7:
                        button.setText("设置屏幕为非全屏");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ScreenUtils.setNonFullScreen(ScreenActivity.this);
                            }
                        });
                        break;
                    case 8:
                        button.setText("切换屏幕为全屏与非全屏状态");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ScreenUtils.toggleFullScreen(ScreenActivity.this);
                            }
                        });
                        break;
                    case 9:
                        button.setText("判断屏幕是否为全屏");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                boolean fullScreen = ScreenUtils.isFullScreen(ScreenActivity.this);
                                if (fullScreen) {
                                    ToastUtils.show(ScreenActivity.this, "全屏");
                                } else {
                                    ToastUtils.show(ScreenActivity.this, "非全屏");
                                }
                            }
                        });
                        break;
                    case 10:
                        button.setText("设置屏幕为横屏");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ScreenUtils.setLandscape(ScreenActivity.this);
                            }
                        });
                        break;
                    case 11:
                        button.setText("设置屏幕为竖屏");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ScreenUtils.setPortrait(ScreenActivity.this);
                            }
                        });
                        break;
                    case 12:
                        button.setText("判断屏幕方向");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                boolean landscape = ScreenUtils.isLandscape(ScreenActivity.this);
                                if (landscape) {
                                    ToastUtils.show(ScreenActivity.this, "横屏");
                                } else {
                                    ToastUtils.show(ScreenActivity.this, "竖屏");
                                }
                            }
                        });
                        break;
                    case 13:
                        button.setText("获取屏幕旋转角度");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int screenRotation = ScreenUtils.getScreenRotation(ScreenActivity.this);
                                ToastUtils.show(ScreenActivity.this, String.valueOf(screenRotation));
                            }
                        });
                        break;
                    case 14:
                        button.setText("截屏 不删除状态栏");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Bitmap bitmap = ScreenUtils.screenShot(ScreenActivity.this);
                                Drawable drawable = new BitmapDrawable(bitmap);
                                button.setBackground(drawable);
                            }
                        });
                        break;
                    case 15:
                        button.setText("判断是否锁屏");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                boolean screenLock = ScreenUtils.isScreenLock(ScreenActivity.this);
                                if (screenLock) {
                                    ToastUtils.show(ScreenActivity.this, "锁屏");
                                } else {
                                    ToastUtils.show(ScreenActivity.this, "未锁屏");
                                }
                            }
                        });
                        break;
                    case 16:
                        button.setText("设置进入休眠时长 15s");
                        button.setOnClickListener(new View.OnClickListener() {
                            @SuppressLint("MissingPermission")
                            @Override
                            public void onClick(View v) {
                                ScreenUtils.setSleepDuration(ScreenActivity.this, 1500);
                            }
                        });
                        break;
                    case 17:
                        button.setText("获取进入休眠时长");
                        button.setOnClickListener(new View.OnClickListener() {
                            @SuppressLint("MissingPermission")
                            @Override
                            public void onClick(View v) {
                                try {
                                    int sleepDuration = ScreenUtils.getSleepDuration(ScreenActivity.this);
                                    ToastUtils.show(ScreenActivity.this, String.valueOf(sleepDuration));
                                } catch (Settings.SettingNotFoundException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        break;
                }
            }
        });
    }
}
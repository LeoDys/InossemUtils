package com.inossem.app.keyboard;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inossem.BaseActivity;
import com.inossem.R;
import com.inossem.util.Utils;
import com.inossem_library.app.keyboard.util.KeyboardUtils;
import com.inossem_library.tips.toast.util.ToastUtils;

public class KeyboardActivity extends BaseActivity {
    TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        description = findViewById(R.id.description);
        LinearLayout buttonLayout = findViewById(R.id.buttonLayout);
        Utils.createButtons(KeyboardActivity.this, buttonLayout, 20, new Utils.ButtonListener() {
            @Override
            public void onCreated(final Button button, int position) {
                switch (position) {
                    case 0:
                        button.setText("显示软键盘");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                KeyboardUtils.showSoftInput(KeyboardActivity.this);
                            }
                        });
                        break;
                    case 1:
                        button.setText("隐藏软键盘");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                KeyboardUtils.hideSoftInput(KeyboardActivity.this);
                            }
                        });
                        break;
                    case 2:
                        button.setText("切换键盘显示与否状态");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                KeyboardUtils.toggleSoftInput(KeyboardActivity.this);
                            }
                        });
                        break;
                    case 3:
                        button.setText("判断软键盘是否可见");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                boolean softInputVisible = KeyboardUtils.isSoftInputVisible(KeyboardActivity.this);
                                if (softInputVisible) {
                                    ToastUtils.show(KeyboardActivity.this, "可见");
                                } else {
                                    ToastUtils.show(KeyboardActivity.this, "不可见");
                                }
                            }
                        });
                        break;
                    case 4:
                        button.setText("软键盘可视区域大小");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int decorViewInvisibleHeight = KeyboardUtils.getDecorViewInvisibleHeight(KeyboardActivity.this.getWindow());
                                ToastUtils.show(KeyboardActivity.this, String.valueOf(decorViewInvisibleHeight));
                            }
                        });
                        break;
                    case 5:
                        button.setText("修复安卓 5497 BUG");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                KeyboardUtils.fixAndroidBug5497(KeyboardActivity.this);
                            }
                        });
                        break;
                    case 6:
                        button.setText("修复软键盘内存泄漏");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    KeyboardUtils.fixSoftInputLeaks(KeyboardActivity.this);
                                } catch (Throwable throwable) {
                                    throwable.printStackTrace();
                                }
                            }
                        });
                        break;
                    case 7:
                        button.setText("状态栏的高度");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int statusBarHeight = KeyboardUtils.getStatusBarHeight();
                                ToastUtils.show(KeyboardActivity.this, String.valueOf(statusBarHeight));
                            }
                        });
                        break;
                    case 8:
                        button.setText("导航栏的高度");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int statusBarHeight = KeyboardUtils.getNavBarHeight();
                                ToastUtils.show(KeyboardActivity.this, String.valueOf(statusBarHeight));
                            }
                        });
                        break;
                }
            }
        });
    }
}

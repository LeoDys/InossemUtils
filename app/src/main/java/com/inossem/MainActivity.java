package com.inossem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inossem.other.OtherActivity;
import com.inossem.phone.PhoneRelatedActivity;
import com.inossem.other_framework.OtherFrameworkActivity;
import com.inossem.request.RequestActivity;
import com.inossem.tips.Toast.ToastActivity;
import com.inossem.tips.dialog.DialogActivity;
import com.inossem.tips.logcat.LogcatActivity;
import com.inossem.util.Utils;

/**
 * 首页介绍
 *
 * @author Leo E-mail:changwen.sun@inossem.com 2019/7/23 14:59
 * @version Java-1.8
 * @since 1.0.0
 */
public class MainActivity extends BaseActivity {

    TextView description;

    private LinearLayout buttonLayout;

    @Override
    protected void onCreateImpl(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        description = findViewById(R.id.description);
        buttonLayout = findViewById(R.id.buttonLayout);
        Utils.createButtons(MainActivity.this, buttonLayout, 20, new Utils.ButtonListener() {
            @Override
            public void onCreated(Button button, int position) {
                switch (position) {
                    case 0:
                        button.setText("请求");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(MainActivity.this, RequestActivity.class));
                            }
                        });
                        break;

                    case 1:
                        button.setText("提示");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
//                                  startActivity(new Intent(MainActivity.this, RequestActivity.class));
                            }
                        });
                        break;

                    case 2:
                        button.setText("内容解析");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
//                                startActivity(new Intent(MainActivity.this, null));
                            }
                        });
                        break;
                    case 3:
                        button.setText("加密");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
//                                startActivity(new Intent(MainActivity.this, null));
                            }
                        });
                        break;
                    case 4:
                        button.setText("手机相关");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(MainActivity.this, PhoneRelatedActivity.class));
                            }
                        });
                        break;
                    case 5:
                        button.setText("其他");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(MainActivity.this, OtherActivity.class));
                            }
                        });
                        break;
                    case 6:
                        button.setText("其他框架");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(MainActivity.this, OtherFrameworkActivity.class));
                            }
                        });
                        break;
                    case 7:
                        button.setText("Dialog");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(MainActivity.this, DialogActivity.class));
                            }
                        });
                        break;
                    case 8:
                        button.setText("logcat");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(MainActivity.this, LogcatActivity.class));
                            }
                        });
                        break;
                    case 9:
                        button.setText("Toast");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(MainActivity.this, ToastActivity.class));
                            }
                        });
                        break;
                }
            }
        });
    }
}

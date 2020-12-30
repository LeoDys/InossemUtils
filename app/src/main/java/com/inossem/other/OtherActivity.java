package com.inossem.other;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inossem.BaseActivity;
import com.inossem.R;
import com.inossem.other.clean.CleanActivity;
import com.inossem.other.convert.ConvertActivity;
import com.inossem.other.mmkv.InossemMMKVActivity;
import com.inossem.other.permission.PermissionActivity;
import com.inossem.other.push.PushActivity;
import com.inossem.other.regex.RegexActivity;
import com.inossem.other.sp.SPActivity;
import com.inossem.other.string.StringActivity;
import com.inossem.other.time.TimeActivity;
import com.inossem.util.Utils;

/**
 * @author 郭晓龙
 * @time on 2019/7/25
 * @email xiaolong.guo@inossem.com
 * @describe Other
 */
public class OtherActivity extends BaseActivity {

    TextView description;

    private LinearLayout buttonLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        description = findViewById(R.id.description);
        buttonLayout = findViewById(R.id.buttonLayout);
        Utils.createButtons(OtherActivity.this, buttonLayout, 20, new Utils.ButtonListener() {
            @Override
            public void onCreated(Button button, int position) {
                switch (position) {
                    case 0:
                        button.setText("推送");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(OtherActivity.this, PushActivity.class));
                            }
                        });
                        break;
                    case 1:
                        button.setText("转换");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(OtherActivity.this, ConvertActivity.class));
                            }
                        });
                        break;
                    case 2:
                        button.setText("时间");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(OtherActivity.this, TimeActivity.class));
                            }
                        });
                        break;
                    case 3:
                        button.setText("String");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(OtherActivity.this, StringActivity.class));
                            }
                        });
                        break;
                    case 4:
                        button.setText("正则表达式");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(OtherActivity.this, RegexActivity.class));
                            }
                        });
                        break;
                    case 5:
                        button.setText("SP");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(OtherActivity.this, SPActivity.class));
                            }
                        });
                        break;
                    case 6:
                        button.setText("权限");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(OtherActivity.this, PermissionActivity.class));
                            }
                        });
                        break;
                    case 7:
                        button.setText("清理");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(OtherActivity.this, CleanActivity.class));
                            }
                        });
                        break;
                    case 8:
                        button.setText("图片相关");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
//                                startActivity(new Intent(OtherActivity.this, CleanActivity.class));
                            }
                        });
                        break;
                    case 9:
                        button.setText("MMKV");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(OtherActivity.this, InossemMMKVActivity.class));
                            }
                        });
                        break;
                }
            }
        });
    }
}

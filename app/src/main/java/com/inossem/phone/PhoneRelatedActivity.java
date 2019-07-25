package com.inossem.phone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inossem.BaseActivity;
import com.inossem.R;
import com.inossem.app.app.AppActivity;
import com.inossem.app.device.DeviceActivity;
import com.inossem.app.keyboard.KeyboardActivity;
import com.inossem.app.language.SupportLanguageActivity;
import com.inossem.app.light.FlashlightActivity;
import com.inossem.app.network.NetworkActivity;
import com.inossem.app.path.PathActivity;
import com.inossem.app.screen.ScreenActivity;
import com.inossem.util.Utils;

public class PhoneRelatedActivity extends BaseActivity {
    TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        description = findViewById(R.id.description);
        LinearLayout buttonLayout = findViewById(R.id.buttonLayout);
        Utils.createButtons(PhoneRelatedActivity.this, buttonLayout, 20, new Utils.ButtonListener() {
            @Override
            public void onCreated(Button button, int position) {
                switch (position) {
                    case 0:
                        button.setText("App相关");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(PhoneRelatedActivity.this, AppActivity.class));
                            }
                        });
                        break;
                    case 1:
                        button.setText("设备相关");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(PhoneRelatedActivity.this, DeviceActivity.class));
                            }
                        });
                    case 2:
                        button.setText("键盘相关");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(PhoneRelatedActivity.this, KeyboardActivity.class));
                            }
                        });
                    case 3:
                        button.setText("语言相关");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(PhoneRelatedActivity.this, SupportLanguageActivity.class));
                            }
                        });
                    case 4:
                        button.setText("闪光灯相关");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(PhoneRelatedActivity.this, FlashlightActivity.class));
                            }
                        });
                    case 5:
                        button.setText("网络相关");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(PhoneRelatedActivity.this, NetworkActivity.class));
                            }
                        });
                        break;
                    case 6:
                        button.setText("路径相关");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(PhoneRelatedActivity.this, PathActivity.class));
                            }
                        });
                        break;
                    case 7:
                        button.setText("屏幕相关");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(PhoneRelatedActivity.this, ScreenActivity.class));
                            }
                        });
                }
            }
        });
    }
}

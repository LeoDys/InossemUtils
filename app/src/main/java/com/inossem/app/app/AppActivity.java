package com.inossem.app.app;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.inossem.BaseActivity;
import com.inossem.R;
import com.inossem.util.Utils;
import com.inossem_library.app.app.util.AppUtils;

public class AppActivity extends BaseActivity {
    TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        description = findViewById(R.id.description);
        LinearLayout buttonLayout = findViewById(R.id.buttonLayout);
        Utils.createButtons(AppActivity.this, buttonLayout, 20, new Utils.ButtonListener() {
            @Override
            public void onCreated(Button button, int position) {
                switch (position) {
                    case 0:
                        button.setText("判断App是否安装 packageName (com.inossem)");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    boolean appInstalled = AppUtils.isAppInstalled(AppActivity.this, "com.inossem");
                                    if (appInstalled) {
                                        Toast.makeText(AppActivity.this, "已经安装", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(AppActivity.this, "尚未安装", Toast.LENGTH_LONG).show();
                                    }
                                } catch (PackageManager.NameNotFoundException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        break;
                    case 1:
                        button.setText("判断App是否是Debug版本 packageName (com.inossem)");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    boolean appInstalled = AppUtils.isAppDebug(AppActivity.this, "com.inossem");
                                    if (appInstalled) {
                                        Toast.makeText(AppActivity.this, "已经安装", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(AppActivity.this, "尚未安装", Toast.LENGTH_LONG).show();
                                    }
                                } catch (PackageManager.NameNotFoundException e) {
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
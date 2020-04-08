package com.inossem.other_framework.version;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inossem.BaseActivity;
import com.inossem.R;
import com.inossem.util.Utils;
import com.inossem_library.other.version.AppUpdate;
import com.inossem_library.tips.toast.util.ToastUtils;

/**
 * 版本升级
 *
 * @author Leo E-mail:changwen.sun@inossem.com 2019/7/25 20:20
 * @version 1.0.0
 * @since JDK-1.8
 */

public class VersionActivity extends BaseActivity {
    TextView description;
    private LinearLayout buttonLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        description = findViewById(R.id.description);
        buttonLayout = findViewById(R.id.buttonLayout);
        Utils.createButtons(VersionActivity.this, buttonLayout, 10, new Utils.ButtonListener() {
            @Override
            public void onCreated(Button button, int position) {
                switch (position) {
                    case 0:
                        button.setText("升级");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AppUpdate.defaultUpgradeVersionDialog(VersionActivity.this, "2736128", "1.0.0.3",
                                        "versionRemark", "http://test-1251233192.coscd.myqcloud.com/1_1.apk",
                                        "token", "ef6d5c1105e6945a3867e2812fbc4b17",
                                        true, true, new AppUpdate.ExitListener() {
                                            @Override
                                            public void exit() {
                                                ToastUtils.show(VersionActivity.this,"退出");
                                            }
                                        }).show();
                            }
                        });
                        break;
                }
            }
        });
    }
}

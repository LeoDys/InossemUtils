package com.inossem.app.light;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inossem.BaseActivity;
import com.inossem.R;
import com.inossem.util.Utils;
import com.inossem_library.app.light.util.FlashlightUtils;
import com.inossem_library.tips.toast.util.ToastUtils;

/**
 * 闪光灯相关
 *
 * @author Lin
 */
public class FlashlightActivity extends BaseActivity {
    TextView description;
    boolean isFlashlightStatus = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        description = findViewById(R.id.description);
        LinearLayout buttonLayout = findViewById(R.id.buttonLayout);
        Utils.createButtons(FlashlightActivity.this, buttonLayout, 20, new Utils.ButtonListener() {
            @Override
            public void onCreated(final Button button, int position) {
                switch (position) {
                    case 0:
                        button.setText("判断设备是否支持闪光灯");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                boolean flashlightEnable = FlashlightUtils.isFlashlightEnable(FlashlightActivity.this);
                                if (flashlightEnable) {
                                    ToastUtils.show(FlashlightActivity.this, "支持");
                                } else {
                                    ToastUtils.show(FlashlightActivity.this, "不支持");
                                }
                            }
                        });
                        break;
                    case 1:
                        button.setText("判断闪光灯是否打开");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                boolean flashlightEnable = FlashlightUtils.isFlashlightOn();
                                if (flashlightEnable) {
                                    ToastUtils.show(FlashlightActivity.this, "是");
                                } else {
                                    ToastUtils.show(FlashlightActivity.this, "否");
                                }
                            }
                        });
                        break;
                    case 2:
                        button.setText("设置闪光灯状态");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    if (isFlashlightStatus) {
                                        FlashlightUtils.setFlashlightStatus(true);
                                        isFlashlightStatus = false;
                                    } else {
                                        FlashlightUtils.setFlashlightStatus(false);
                                        isFlashlightStatus = true;
                                    }
                                } catch (Throwable throwable) {
                                    throwable.printStackTrace();
                                }
                            }
                        });
                        break;
                }
            }
        });
    }
}
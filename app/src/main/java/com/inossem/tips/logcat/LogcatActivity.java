package com.inossem.tips.logcat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inossem.BaseActivity;
import com.inossem.R;
import com.inossem.util.Utils;
import com.inossem_library.tips.logcat.util.LogUtils;

/**
 * @author 王斯宇
 * @time on 2019/7/25 15:28
 * @email siyu.wang@inossem.com
 */
public class LogcatActivity extends BaseActivity {
    private static final String COMMON_SYMBOL = "系统自带log-------------------------";
    private static final String LOGGER_SYMBOL = "logger-------------------------";
    TextView description;

    private LinearLayout buttonLayout;

    @Override
    protected void onCreateImpl(Bundle savedInstanceState) {
        super.onCreateImpl(savedInstanceState);
        setContentView(R.layout.activity_main);
        description = findViewById(R.id.description);
        buttonLayout = findViewById(R.id.buttonLayout);
        Utils.createButtons(LogcatActivity.this, buttonLayout, 5, new Utils.ButtonListener() {
            @Override
            public void onCreated(Button button, int position) {
                switch (position) {
                    case 0:
                        button.setText("Info级别日志");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                LogUtils.i(COMMON_SYMBOL + "Info级别日志");
                                LogUtils.loggerI(LOGGER_SYMBOL + "Info级别日志");
                            }
                        });
                        break;
                    case 1:
                        button.setText("Verbose级别日志");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                LogUtils.v(COMMON_SYMBOL + "Verbose级别日志");
                                LogUtils.loggerV(LOGGER_SYMBOL + "Verbose级别日志");
                            }
                        });
                        break;
                    case 2:
                        button.setText("Debug级别日志");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                LogUtils.d(COMMON_SYMBOL + "Debug级别日志");
                                LogUtils.loggerD(COMMON_SYMBOL + "Debug级别日志");
                            }
                        });
                        break;
                    case 3:
                        button.setText("Warn级别日志");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                LogUtils.w(COMMON_SYMBOL + "Warn级别日志");
                                LogUtils.loggerW(COMMON_SYMBOL + "Warn级别日志");
                            }
                        });
                        break;
                    case 4:
                        button.setText("Error级别日志");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                LogUtils.e(COMMON_SYMBOL + "Error级别日志");
                                LogUtils.loggerE(COMMON_SYMBOL + "Error级别日志");
                            }
                        });
                        break;
                }
            }
        });
    }
}

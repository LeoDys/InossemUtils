package com.inossem.app.language;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inossem.BaseActivity;
import com.inossem.R;
import com.inossem.util.Utils;
import com.inossem_library.app.language.util.SupportLanguageUtil;
import com.inossem_library.tips.toast.util.ToastUtils;

import java.util.Locale;

public class SupportLanguageActivity extends BaseActivity {
    TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        description = findViewById(R.id.description);
        LinearLayout buttonLayout = findViewById(R.id.buttonLayout);
        Utils.createButtons(SupportLanguageActivity.this, buttonLayout, 20, new Utils.ButtonListener() {
            @Override
            public void onCreated(final Button button, int position) {
                switch (position) {
                    case 0:
                        button.setText("是否支持此语言 language (zh) 语言");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                boolean supportLanguage = SupportLanguageUtil.isSupportLanguage("zh");
                                if (supportLanguage) {
                                    ToastUtils.show(SupportLanguageActivity.this, "支持");
                                } else {
                                    ToastUtils.show(SupportLanguageActivity.this, "不支持");
                                }
                            }
                        });
                        break;
                    case 1:
                        button.setText("获取系统首选语言 API需高于24");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                    Locale systemPreferredLanguage = SupportLanguageUtil.getSystemPreferredLanguage();
                                    ToastUtils.show(SupportLanguageActivity.this, String.valueOf(systemPreferredLanguage));
                                } else {
                                    ToastUtils.show(SupportLanguageActivity.this, "API过低");
                                }
                            }
                        });
                        break;
                }
            }
        });
    }
}

package com.inossem.other.mmkv;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inossem.BaseActivity;
import com.inossem.R;
import com.inossem.util.Utils;
import com.inossem_library.other.mmkv.util.InossemMMKV;
import com.inossem_library.tips.toast.util.ToastUtils;

public class InossemMMKVActivity extends BaseActivity {
    TextView description;
    private int mPosition = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        description = findViewById(R.id.description);
        LinearLayout buttonLayout = findViewById(R.id.buttonLayout);
        Utils.createButtons(InossemMMKVActivity.this, buttonLayout, 20, new Utils.ButtonListener() {
            @Override
            public void onCreated(Button button, int position) {
                switch (position) {
                    case 0:
                        button.setText("设置数据");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                InossemMMKV.get().put("TEST_DATA", "I am a piece of test data" + mPosition++);
                            }
                        });
                        break;
                    case 1:
                        button.setText("读取数据");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ToastUtils.show(InossemMMKVActivity.this, InossemMMKV.get().get("TEST_DATA", String.class));
                            }
                        });
                        break;
                }
            }
        });
    }
}
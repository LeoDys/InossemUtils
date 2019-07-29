package com.inossem.other.clean;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inossem.BaseActivity;
import com.inossem.R;
import com.inossem.util.Utils;
import com.inossem_library.other.clean.utils.CleanUtils;
import com.inossem_library.tips.toast.util.ToastUtils;

public class CleanActivity extends BaseActivity {
    TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        description = findViewById(R.id.description);
        LinearLayout buttonLayout = findViewById(R.id.buttonLayout);
        Utils.createButtons(CleanActivity.this, buttonLayout, 20, new Utils.ButtonListener() {
            @Override
            public void onCreated(Button button, int position) {
                switch (position) {
                    case 0:
                        button.setText("清除内部缓存");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                boolean b = CleanUtils.cleanInternalCache(CleanActivity.this);
                                if (b) {
                                    ToastUtils.show(CleanActivity.this, "清除成功");
                                } else {
                                    ToastUtils.show(CleanActivity.this, "清除失败");
                                }
                            }
                        });
                        break;
                    case 1:
                        button.setText("清理内部文件");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                boolean b = CleanUtils.cleanInternalFiles(CleanActivity.this);
                                if (b) {
                                    ToastUtils.show(CleanActivity.this, "清除成功");
                                } else {
                                    ToastUtils.show(CleanActivity.this, "清除失败");
                                }
                            }
                        });
                        break;
                    case 2:
                        button.setText("清理内部数据库");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                boolean b = CleanUtils.cleanInternalDbs(CleanActivity.this);
                                if (b) {
                                    ToastUtils.show(CleanActivity.this, "清除成功");
                                } else {
                                    ToastUtils.show(CleanActivity.this, "清除失败");
                                }
                            }
                        });
                        break;
                    case 3:
                        button.setText("清除内部SP");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                boolean b = CleanUtils.cleanInternalSp(CleanActivity.this);
                                if (b) {
                                    ToastUtils.show(CleanActivity.this, "清除成功");
                                } else {
                                    ToastUtils.show(CleanActivity.this, "清除失败");
                                }
                            }
                        });
                        break;
                    case 4:
                        button.setText("清除外部缓存");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                boolean b = CleanUtils.cleanExternalCache(CleanActivity.this);
                                if (b) {
                                    ToastUtils.show(CleanActivity.this, "清除成功");
                                } else {
                                    ToastUtils.show(CleanActivity.this, "清除失败");
                                }
                            }
                        });
                        break;
                }
            }
        });
    }
}
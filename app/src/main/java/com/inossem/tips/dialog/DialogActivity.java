package com.inossem.tips.dialog;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inossem.BaseActivity;
import com.inossem.R;
import com.inossem.util.Utils;
import com.inossem_library.tips.dialog.util.DialogUtils;

/**
 * @author 王斯宇
 * @time on 2019/7/25 15:22
 * @email siyu.wang@inossem.com
 */
public class DialogActivity extends BaseActivity {
    TextView description;

    private LinearLayout buttonLayout;
    @Override
    protected void onCreateImpl(Bundle savedInstanceState) {
        super.onCreateImpl(savedInstanceState);
        setContentView(R.layout.activity_main);
        description = findViewById(R.id.description);
        buttonLayout = findViewById(R.id.buttonLayout);
        Utils.createButtons(DialogActivity.this, buttonLayout, 4, new Utils.ButtonListener() {
            @Override
            public void onCreated(Button button, int position) {
                switch (position) {
                    case 0:
                        button.setText("提示信息dialog");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                DialogUtils.getInstance().showInfoDialog(DialogActivity.this , "提示信息");
                            }
                        });
                        break;
                    case 1:
                        button.setText("警告信息dialog");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                DialogUtils.getInstance().showErrorDialog(DialogActivity.this , "警告信息");
                            }
                        });
                        break;
                    case 2:
                        button.setText("错误信息dialog");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                DialogUtils.getInstance().showErrorDialog(DialogActivity.this , "错误信息");
                            }
                        });
                        break;
                    case 3:
                        button.setText("判定信息dialog");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                DialogUtils.getInstance().showJudgeDialog(DialogActivity.this , "判定信息");
                            }
                        });
                        break;
                }
            }
        });
    }
}

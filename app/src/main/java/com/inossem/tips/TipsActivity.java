package com.inossem.tips;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inossem.BaseActivity;
import com.inossem.R;
import com.inossem.tips.dialog.DialogActivity;
import com.inossem.tips.logcat.LogcatActivity;
import com.inossem.tips.toast.ToastActivity;
import com.inossem.util.Utils;

/**
 * @author 王斯宇
 * @time on 2019/7/26 10:41
 * @email siyu.wang@inossem.com
 */
public class TipsActivity extends BaseActivity {

    TextView description;

    private LinearLayout buttonLayout;

    @Override
    protected void onCreateImpl(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        description = findViewById(R.id.description);
        buttonLayout = findViewById(R.id.buttonLayout);
        Utils.createButtons(TipsActivity.this, buttonLayout, 3, new Utils.ButtonListener() {
            @Override
            public void onCreated(Button button, int position) {
                switch (position) {
                    case 0:
                        button.setText("Dialog");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(TipsActivity.this, DialogActivity.class));
                            }
                        });
                        break;
                    case 1:
                        button.setText("logcat");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(TipsActivity.this, LogcatActivity.class));
                            }
                        });
                        break;
                    case 2:
                        button.setText("Toast");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(TipsActivity.this, ToastActivity.class));
                            }
                        });
                        break;
                }
            }
        });
    }

}

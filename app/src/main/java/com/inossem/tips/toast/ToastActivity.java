package com.inossem.tips.toast;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inossem.BaseActivity;
import com.inossem.R;
import com.inossem.util.Utils;
import com.inossem_library.tips.toast.util.ToastUtils;

/**
 * @author 王斯宇
 * @time on 2019/7/25 15:40
 * @email siyu.wang@inossem.com
 */
public class ToastActivity extends BaseActivity {
    TextView description;

    private LinearLayout buttonLayout;

    @Override
    protected void onCreateImpl(Bundle savedInstanceState) {
        super.onCreateImpl(savedInstanceState);
        setContentView(R.layout.activity_main);
        description = findViewById(R.id.description);
        buttonLayout = findViewById(R.id.buttonLayout);
        Utils.createButtons(ToastActivity.this, buttonLayout, 2, new Utils.ButtonListener() {
            @Override
            public void onCreated(Button button, int position) {
                switch (position) {
                    case 0:
                        button.setText("默认toast");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ToastUtils.show(ToastActivity.this , "默认toast");
                            }
                        });
                        break;
                    case 1:
                        button.setText("警告toast");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ToastUtils.showError(ToastActivity.this , "默认图标的警告toast" , null);
                            }
                        });
                        break;
                }
            }
        });
    }

}

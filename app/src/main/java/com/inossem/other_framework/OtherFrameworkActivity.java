package com.inossem.other_framework;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inossem.BaseActivity;
import com.inossem.R;
import com.inossem.other_framework.picture.PictureActivity;
import com.inossem.util.Utils;

/**
 * 其他框架
 *
 * @author Leo E-mail:changwen.sun@inossem.com 2019/7/25 20:17
 * @version 1.0.0
 * @since JDK-1.8
 */

public class OtherFrameworkActivity extends BaseActivity {

    TextView description;

    private LinearLayout buttonLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        description = findViewById(R.id.description);
        buttonLayout = findViewById(R.id.buttonLayout);
        Utils.createButtons(OtherFrameworkActivity.this, buttonLayout, 10, new Utils.ButtonListener() {
            @Override
            public void onCreated(Button button, int position) {
                switch (position) {
                    case 0:
                        button.setText("图片选择");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(OtherFrameworkActivity.this, PictureActivity.class));
                            }
                        });
                        break;
                }
            }
        });
    }
}

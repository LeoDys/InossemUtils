package com.inossem.encrypt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inossem.BaseActivity;
import com.inossem.R;
import com.inossem.encrypt.en_decrypt.DecryptActivity;
import com.inossem.util.Utils;

public class EncryptActivity extends BaseActivity {
    TextView description;

    @Override
    protected void onCreateImpl(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        description = findViewById(R.id.description);
        LinearLayout buttonLayout = findViewById(R.id.buttonLayout);
        Utils.createButtons(EncryptActivity.this, buttonLayout, 20, new Utils.ButtonListener() {
            @Override
            public void onCreated(Button button, int position) {
                switch (position) {
                    case 0:
                        button.setText("编码解码?");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
//                                startActivity(new Intent(EncryptActivity.this, null));
                            }
                        });
                        break;

                    case 1:
                        button.setText("加密解密");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(EncryptActivity.this, DecryptActivity.class));
                            }
                        });
                        break;
                }
            }
        });
    }
}
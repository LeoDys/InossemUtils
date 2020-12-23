package com.inossem.security;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inossem.BaseActivity;
import com.inossem.R;
import com.inossem.security.en_decrypt.DecryptActivity;
import com.inossem.security.en_decrypt.NewDecryptActivity;
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
                        button.setText("新版加解密");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(EncryptActivity.this, NewDecryptActivity.class));
                            }
                        });
                        break;

                    case 1:
                        button.setText("加解密");
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
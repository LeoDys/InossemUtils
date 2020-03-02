package com.inossem.other.sp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inossem.BaseActivity;
import com.inossem.R;
import com.inossem.util.Utils;
import com.inossem_library.other.sp.util.SPStaticUtils;
import com.inossem_library.other.sp.util.SPUtils;
/**
 * @author 郭晓龙
 * @time on 2019/7/25
 * @email xiaolong.guo@inossem.com
 * @describe SP
 */
public class SPActivity extends BaseActivity {

    TextView description;

    private LinearLayout buttonLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        description = findViewById(R.id.description);
        buttonLayout = findViewById(R.id.buttonLayout);
        Utils.createButtons(SPActivity.this, buttonLayout, 1, new Utils.ButtonListener() {
            @Override
            public void onCreated(Button button, int position) {
                switch (position) {
                    case 0:
                        button.setText("SP");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                SPStaticUtils.setDefaultSPUtils(new SPUtils(SPActivity.this, "name"));
                                SPStaticUtils.put(SPActivity.this, "key", "value");
                                Log.e("TAG", "getString-->" + SPStaticUtils.getString(SPActivity.this, "key"));
                                SPStaticUtils.put(SPActivity.this, "key", "value");
                                Log.e("TAG", "getString-->" + SPStaticUtils.getString(SPActivity.this, "key"));
                                SPStaticUtils.put(SPActivity.this, "key", "value");
                                Log.e("TAG", "getString-->" + SPStaticUtils.getString(SPActivity.this, "key"));
                            }
                        });
                        break;
                }
            }
        });
    }
}

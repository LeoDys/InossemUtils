package com.inossem.other.string;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inossem.BaseActivity;
import com.inossem.R;
import com.inossem.util.Utils;
import com.inossem_library.other.string.util.StringUtils;
/**
 * @author 郭晓龙
 * @time on 2019/7/25
 * @email xiaolong.guo@inossem.com
 * @describe String
 */
public class StringActivity extends BaseActivity {

    TextView description;

    private LinearLayout buttonLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        description = findViewById(R.id.description);
        buttonLayout = findViewById(R.id.buttonLayout);
        Utils.createButtons(StringActivity.this, buttonLayout, 1, new Utils.ButtonListener() {
            @Override
            public void onCreated(Button button, int position) {
                switch (position) {
                    case 0:
                        button.setText("字符串");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Log.e("TAG", "isEmpty-->" + StringUtils.isEmpty("   ") + "");
                                Log.e("TAG", "isSpace-->" + StringUtils.isSpace("   ") + "");
                                Log.e("TAG", "equals-->" + StringUtils.equals("18642690025", "18642690025") + "");
                                Log.e("TAG", "equalsIgnoreCase-->" + StringUtils.equalsIgnoreCase("abc", "ABC") + "");
                                Log.e("TAG", "null2Length0-->" + StringUtils.null2Length0(null));
                                Log.e("TAG", "length-->" + StringUtils.length("18642690025") + "");
                                Log.e("TAG", "upperFirstLetter-->" + StringUtils.upperFirstLetter("abc") + "");
                                Log.e("TAG", "lowerFirstLetter-->" + StringUtils.lowerFirstLetter("ABC") + "");
                                Log.e("TAG", "toDBC-->" + StringUtils.toDBC(",，,，,，") + "");
                                Log.e("TAG", "toSBC-->" + StringUtils.toSBC(",，,，,，") + "");
                                Log.e("TAG", "getString-->" + StringUtils.getString(StringActivity.this, R.string.app_name) + "");
                            }
                        });
                        break;
                }
            }
        });
    }
}

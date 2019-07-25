package com.inossem.other.regex;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inossem.BaseActivity;
import com.inossem.R;
import com.inossem.other.OtherActivity;
import com.inossem.other.sp.SPActivity;
import com.inossem.util.Utils;
import com.inossem_library.other.convert.constant.ConvertConstants;
import com.inossem_library.other.convert.util.ConvertUtils;
import com.inossem_library.other.push.constant.PushConstant;
import com.inossem_library.other.push.util.PushUtils;
import com.inossem_library.other.regex.util.RegexUtils;
/**
 * @author 郭晓龙
 * @time on 2019/7/25
 * @email xiaolong.guo@inossem.com
 * @describe 正则
 */
public class RegexActivity extends BaseActivity {

    TextView description;

    private LinearLayout buttonLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        description = findViewById(R.id.description);
        buttonLayout = findViewById(R.id.buttonLayout);
        Utils.createButtons(RegexActivity.this, buttonLayout, 1, new Utils.ButtonListener() {
            @Override
            public void onCreated(Button button, int position) {
                switch (position) {
                    case 0:
                        button.setText("正则表达式");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Log.e("TAG", "isMobileSimple-->" + RegexUtils.isMobileSimple("18642690025") + "");
                                Log.e("TAG", "isMobileExact-->" + RegexUtils.isMobileExact("18642690025") + "");
                                Log.e("TAG", "isTel-->" + RegexUtils.isTel("18642690025") + "");
                                Log.e("TAG", "isIDCard18Exact-->" + RegexUtils.isIDCard18Exact("210111198812153038") + "");
                                Log.e("TAG", "isEmail-->" + RegexUtils.isEmail("210111198812153038") + "");
                                Log.e("TAG", "isURL-->" + RegexUtils.isURL("210111198812153038") + "");
                                Log.e("TAG", "isZh-->" + RegexUtils.isZh("郭郭郭郭郭郭") + "");
                                Log.e("TAG", "isUsername-->" + RegexUtils.isUsername("郭郭郭郭郭郭") + "");
                                Log.e("TAG", "isDate-->" + RegexUtils.isDate("2010-11-11") + "");
                                Log.e("TAG", "isIP-->" + RegexUtils.isIP("郭郭郭郭郭郭") + "");
                            }
                        });
                        break;
                }
            }
        });
    }
}

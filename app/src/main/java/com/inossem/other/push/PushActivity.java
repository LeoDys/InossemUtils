package com.inossem.other.push;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.inossem.BaseActivity;
import com.inossem.R;
import com.inossem.other.OtherActivity;
import com.inossem.util.Utils;
import com.inossem_library.other.push.constant.PushConstant;
import com.inossem_library.other.push.util.PushUtils;
/**
 * @author 郭晓龙
 * @time on 2019/7/25
 * @email xiaolong.guo@inossem.com
 * @describe 推送
 */
public class PushActivity extends BaseActivity {

    TextView description;

    private LinearLayout buttonLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        description = findViewById(R.id.description);
        buttonLayout = findViewById(R.id.buttonLayout);
        Utils.createButtons(PushActivity.this, buttonLayout, 1, new Utils.ButtonListener() {
            @Override
            public void onCreated(Button button, int position) {
                switch (position) {
                    case 0:
                        button.setText("推送");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
//                                PushUtils.getInstance(getApplicationContext()).push(PushConstant.PUSH_TYPE_SYSTEM_NOTICES, "测试标题", "测试内容", R.mipmap.ic_launcher, OtherActivity.class, R.mipmap.ic_launcher);
                                PushUtils.getInstance(getApplicationContext()).push(PushConstant.PUSH_TYPE_SYSTEM_NOTICES, "测试标题", "测试内容", OtherActivity.class, R.mipmap.ic_launcher);
                            }
                        });
                        break;
                }
            }
        });
        PushUtils.getInstance(this).registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Toast.makeText(getApplicationContext(), intent.getStringExtra(PushConstant.PUSH_MESSAGE), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        PushUtils.getInstance(this).unregisterReceiver();
        super.onDestroy();
    }
}
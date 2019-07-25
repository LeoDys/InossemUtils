package com.inossem.request;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inossem.BaseActivity;
import com.inossem.R;
import com.inossem.other.OtherActivity;
import com.inossem.other.convert.ConvertActivity;
import com.inossem.other.push.PushActivity;
import com.inossem.other.regex.RegexActivity;
import com.inossem.other.sp.SPActivity;
import com.inossem.other.string.StringActivity;
import com.inossem.other.time.TimeActivity;
import com.inossem.request.http.HttpActivity;
import com.inossem.request.socket.SocketActivity;
import com.inossem.util.Utils;

/**
 * @author 郭晓龙
 * @time on 2019/7/25
 * @email xiaolong.guo@inossem.com
 * @describe Request
 */
public class RequestActivity extends BaseActivity {

    TextView description;

    private LinearLayout buttonLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        description = findViewById(R.id.description);
        buttonLayout = findViewById(R.id.buttonLayout);
        Utils.createButtons(RequestActivity.this, buttonLayout, 20, new Utils.ButtonListener() {
            @Override
            public void onCreated(Button button, int position) {
                switch (position) {
                    case 0:
                        button.setText("Socket");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(RequestActivity.this, SocketActivity.class));
                            }
                        });
                        break;
                    case 1:
                        button.setText("Http");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(RequestActivity.this, HttpActivity.class));
                            }
                        });
                        break;
                }
            }
        });
    }
}

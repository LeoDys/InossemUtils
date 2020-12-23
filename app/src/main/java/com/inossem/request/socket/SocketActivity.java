package com.inossem.request.socket;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.inossem.BaseActivity;
import com.inossem.R;
import com.inossem.util.Utils;
import com.inossem_library.other.push.util.BroadcastUtils;
import com.inossem_library.other.sp.util.SPStaticUtils;
import com.inossem_library.request.socket.constant.SocketConstants;
import com.inossem_library.request.socket.util.MinaCallBack;
import com.inossem_library.request.socket.util.SocketService;
import com.inossem_library.request.socket.util.SocketUtils;

/**
 * @author 郭晓龙
 * @time on 2019/7/25
 * @email xiaolong.guo@inossem.com
 * @describe Socket
 */
public class SocketActivity extends BaseActivity {

    TextView description;

    private LinearLayout buttonLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        description = findViewById(R.id.description);
        buttonLayout = findViewById(R.id.buttonLayout);
        Utils.createButtons(SocketActivity.this, buttonLayout, 4, new Utils.ButtonListener() {
            @Override
            public void onCreated(Button button, int position) {
                switch (position) {
                    case 0:
                        button.setText("Socket");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
//                                initSocket();
                                //启动socket服务
                                Intent intent = new Intent(SocketActivity.this, SocketService.class);
                                SPStaticUtils.put(SocketActivity.this, SocketConstants.SOCKET_URL, "192.168.3.102");
                                SPStaticUtils.put(SocketActivity.this, SocketConstants.SOCKET_PORT, 60000);
                                startService(intent);
                            }
                        });
                        break;
                    case 1:
                        button.setText("断开连接");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                SocketUtils.stopNetConnect();
                            }
                        });
                        break;
                    case 2:
                        button.setText("连接");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                SocketUtils.getConnect("192.168.3.102", 60000, new MinaCallBack() {
                                    @Override
                                    public void messageReceived(String msg) {
                                        BroadcastUtils.getInstance(SocketActivity.this).sendBroadcast(SocketConstants.SOCKET_ACTION, new String[]{SocketConstants.SOCKET_MESSAGE}, new Object[]{msg});
                                    }

                                    @Override
                                    public void sessionClosed(boolean isCon) {

                                    }
                                });
                            }
                        });
                        break;
                    case 3:
                        button.setText("崩溃");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int a = 0 / 0;
                            }
                        });
                        break;
                }
            }
        });

        BroadcastUtils.getInstance(this).registerReceiver(SocketConstants.SOCKET_ACTION, new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Toast.makeText(SocketActivity.this, intent.getStringExtra(SocketConstants.SOCKET_MESSAGE), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BroadcastUtils.getInstance(this).unregisterReceiver(SocketConstants.SOCKET_ACTION);
    }
}

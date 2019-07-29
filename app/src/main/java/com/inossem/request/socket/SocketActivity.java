package com.inossem.request.socket;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.inossem.BaseActivity;
import com.inossem.R;
import com.inossem.util.Utils;
import com.inossem_library.other.push.util.BroadcastUtils;
import com.inossem_library.request.socket.constant.SocketConstants;
import com.inossem_library.request.socket.util.MinaCallBack;
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
        Utils.createButtons(SocketActivity.this, buttonLayout, 1, new Utils.ButtonListener() {
            @Override
            public void onCreated(Button button, int position) {
                switch (position) {
                    case 0:
                        button.setText("Socket");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                initSocket();
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

    /**
     * Socket初始化
     */
    private void initSocket() {
        //打开TCP连接
        boolean result = SocketUtils.getConnect("192.168.3.59", 60000, new MySocketCallBack());
        if (result) {
            //
//            SocketUtils.send("123");发送数据
        } else {
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    initSocket();
                }
            }, SocketConstants.RECONNECT_TIME);
        }
    }

    /**
     * Socket回调接口
     * messageReceived 收到消息回调
     * sessionClosed 连接断开回调
     */
    private class MySocketCallBack implements MinaCallBack {

        @Override
        public void messageReceived(String msg) {
            BroadcastUtils.getInstance(SocketActivity.this).sendBroadcast(SocketConstants.SOCKET_ACTION, new String[]{SocketConstants.SOCKET_MESSAGE}, new Object[]{msg});
        }

        @Override
        public void sessionClosed(boolean isCon) {
            //掉线10秒重连
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    initSocket();
                }
            }, SocketConstants.RECONNECT_TIME);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BroadcastUtils.getInstance(this).unregisterReceiver(SocketConstants.SOCKET_ACTION);
    }
}

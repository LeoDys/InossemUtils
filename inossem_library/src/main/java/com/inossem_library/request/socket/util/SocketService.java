package com.inossem_library.request.socket.util;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.StrictMode;
import android.util.Log;

import com.inossem_library.other.push.util.BroadcastUtils;
import com.inossem_library.other.sp.util.SPStaticUtils;
import com.inossem_library.request.http.util.RetrofitLogWriter;
import com.inossem_library.request.socket.constant.SocketConstants;
import com.inossem_library.tips.toast.util.ToastUtils;

/**
 * @author 郭晓龙
 * @time on 2020-12-07 17:52:30
 * @email xiaolong.guo@inossem.com
 * @describe socket服务
 */
public class SocketService extends Service implements MinaCallBack {

    private String socketUrl;
    private int socketPort;
    private Handler mHandler;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        socketUrl = SPStaticUtils.getString(SocketService.this, SocketConstants.SOCKET_URL, SocketConstants.DEFULE_SOCKET_URL);
        socketPort = SPStaticUtils.getInt(SocketService.this, SocketConstants.SOCKET_PORT, SocketConstants.DEFULE_SOCKET_PORT);
        initSocket();
        mHandler = new Handler();
        mHandler.postDelayed(runnable, SocketConstants.AUTO_TIME);
        Log.i("PRETTY_LOGGER", "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("PRETTY_LOGGER", "onStartCommand");
        return START_STICKY;
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            //10分钟主动断开连接 会自动执行断线重连   后台配合心跳检测后不需要自动重连
            String requestLog = RetrofitLogWriter.getRequestLogMsg("郭晓龙", "石峰", "Socket", "",
                    socketUrl + ":" + socketPort, "Socket自动重连", "", "");
            RetrofitLogWriter.log(SocketService.this, "Socket", requestLog);
            SocketUtils.stopNetConnect();
            mHandler.postDelayed(runnable, SocketConstants.AUTO_TIME);
        }
    };

    private void initSocket() {
        //打开TCP连接
        boolean result = SocketUtils.getConnect(socketUrl, socketPort, this);
        if (result) {
            ToastUtils.show(this, "Socket" + socketUrl + ":" + socketPort + "连接成功！");
            String requestLog = RetrofitLogWriter.getRequestLogMsg("郭晓龙", "石峰", "Socket", "",
                    socketUrl + ":" + socketPort, "Socket连接成功", "", "");
            RetrofitLogWriter.log(this, "Socket", requestLog);
        } else {
            ToastUtils.show(this, "Socket" + socketUrl + ":" + socketPort + "连接失败！");
            String requestLog = RetrofitLogWriter.getRequestLogMsg("郭晓龙", "石峰", "Socket", "",
                    socketUrl + ":" + socketPort, "Socket连接失败", "", "");
            RetrofitLogWriter.log(this, "Socket", requestLog);
            new Handler(Looper.getMainLooper()).postDelayed(() -> initSocket(), SocketConstants.RECONNECT_TIME);
        }
    }

    @Override
    public void messageReceived(final String msg) {
        String requestLog = RetrofitLogWriter.getRequestLogMsg("郭晓龙", "石峰", "Socket", "",
                socketUrl + ":" + socketPort, "Socket收到信息", "", msg);
        RetrofitLogWriter.log(this, "Socket", requestLog);
        BroadcastUtils.getInstance(SocketService.this).sendBroadcast(SocketConstants.SOCKET_ACTION, new String[]{SocketConstants.SOCKET_MESSAGE}, new Object[]{msg});
    }

    @Override
    public void sessionClosed(boolean isCon) {
        String requestLog = RetrofitLogWriter.getRequestLogMsg("郭晓龙", "石峰", "Socket", "",
                socketUrl + ":" + socketPort, "Socket断线重连", "", "");
        RetrofitLogWriter.log(this, "Socket", requestLog);
        new Handler(Looper.getMainLooper()).postDelayed(() -> initSocket(), SocketConstants.RECONNECT_TIME);
    }

    @Override
    public ComponentName startService(Intent service) {
        return super.startService(service);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            Intent intent = new Intent(this, SocketService.class);
            startService(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
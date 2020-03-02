package com.inossem_library.request.netty.util;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import android.text.TextUtils;

import com.inossem_library.request.netty.constant.NettyConstant;

/**
 * @author 詹建宇
 * @time on 2019/7/25
 * @email jianyu.zhan@inossem.com
 * @describe Netty推送工具类(暂未调试 慎用!!!)
 */
@Deprecated
public class NettyUtils {

    /**
     * 获取Retrofit配置项SP
     *
     * @param context 上下文
     * @return SP
     */
    public static SharedPreferences getSettingSp(Context context) {
        return context.getSharedPreferences(NettyConstant.SP, Context.MODE_PRIVATE);
    }

    /**
     * 设置Netty基本参数
     *
     * @param context        *上下文(必填)
     * @param host           *域名(必填)
     * @param port           *端口号(必填)
     * @param readerIdleTime 读闲置时间
     * @param writerIdleTime 写闲置时间
     * @param allIdleTime    所有类型的超时时间
     * @param isPrintLog     是否打印日志
     * @param isSaveLog      是否保存日志
     * @throws Exception 异常
     */
    public static void set(@NonNull Context context, @NonNull String host, @NonNull Integer port, Long readerIdleTime, Long writerIdleTime,
                           Long allIdleTime, Boolean isPrintLog, Boolean isSaveLog) throws Exception {
        SharedPreferences.Editor editor = getSettingSp(context).edit();
        if (!TextUtils.isEmpty(host)) {
            editor.putString(NettyConstant.HOST, host);
        } else {
            throw new Exception("host can`t null");
        }
        if (null != port) {
            editor.putInt(NettyConstant.PORT, port);
        } else {
            throw new Exception("port can`t null");
        }
        if (null != readerIdleTime) {
            editor.putLong(NettyConstant.IDLE_READ, readerIdleTime);
        } else {
            editor.putLong(NettyConstant.IDLE_READ, 0);
        }
        if (null != writerIdleTime) {
            editor.putLong(NettyConstant.IDLE_WRITE, writerIdleTime);
        } else {
            editor.putLong(NettyConstant.IDLE_WRITE, 0);
        }
        if (null != allIdleTime) {
            editor.putLong(NettyConstant.IDLE_ALL, allIdleTime);
        } else {
            editor.putLong(NettyConstant.IDLE_ALL, 0);
        }
        if (null != isPrintLog) {
            editor.putBoolean(NettyConstant.IS_PRINT_LOG, isPrintLog);
        } else {
            editor.putBoolean(NettyConstant.IS_PRINT_LOG, false);
        }
        if (null != isSaveLog) {
            editor.putBoolean(NettyConstant.IS_SAVE_LOG, isSaveLog);
        } else {
            editor.putBoolean(NettyConstant.IS_SAVE_LOG, false);
        }
        editor.apply();
    }

    /**
     * 连接Socket服务
     *
     * @param context         上下文
     * @param connectCallBack 是否连接成功回调
     * @throws Exception 异常
     */
    public static void connect(Context context, ConnectCallBack connectCallBack) throws Exception {
        if (null == context) throw new Exception("context can`t null");
        NettyService service = NettyCache.getService();
        if (service != null) {
            service.connect(connectCallBack);
        } else {
            connectCallBack.onFailure();
        }
    }

    /**
     * 设置接收信息回调
     *
     * @param context         上下文
     * @param receiveCallBack 接收信息回调
     * @throws Exception 异常
     */
    public static void setReceiveCallback(Context context, ReceiveCallBack receiveCallBack) throws Exception {
        if (null == context) throw new Exception("context can`t null");
        NettyService service = NettyCache.getService();
        if (service != null) {
            service.setReceiveCallback(receiveCallBack);
        }
    }

    /**
     * 发送消息
     *
     * @param message      消息实体
     * @param sendCallBack 是否发送成功的回调(可以为null)
     */
    public static void send(String message, SendCallBack sendCallBack) {
        NettyService service = NettyCache.getService();
        if (service != null) {
            service.send(message, sendCallBack);
        }
    }

    /**
     * 断开连接
     */
    public static void disConnect() {
        NettyService service = NettyCache.getService();
        if (service != null) {
            service.disConnect();
        }
    }

}

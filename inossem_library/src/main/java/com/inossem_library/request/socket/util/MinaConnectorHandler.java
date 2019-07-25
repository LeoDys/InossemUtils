package com.inossem_library.request.socket.util;

import android.util.Log;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import java.net.PortUnreachableException;

/**
 * @author 郭晓龙
 * @time on 2019/7/16
 * @email xiaolong.guo@inossem.com
 * @describe Socket消息处理器
 */
public class MinaConnectorHandler extends IoHandlerAdapter {

    //Socket回调
    private MinaCallBack mCallBack;

    /**
     * 构造方法
     *
     * @param callBack Socket回调
     */
    public MinaConnectorHandler(MinaCallBack callBack) {
        mCallBack = callBack;
    }

    /**
     * 服务器与客户端创建连接
     */
    @Override
    public void sessionCreated(IoSession session) throws Exception {
        super.sessionCreated(session);
    }

    /**
     * 从端口接受消息，会响应此方法来对消息进行处理
     */
    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        String msg = message.toString();
        Log.i("MinaConnectorHandler", "接收到网关发送指令,指令信息为:" + msg);
        super.messageReceived(session, message);
        mCallBack.messageReceived(msg);
    }

    /**
     * 关闭与客户端的连接时会调用此方法
     */
    @Override
    public void sessionClosed(IoSession session) throws Exception {
        Log.i("MinaConnectorHandler", "网络层关闭session连接,停止网络服务");
        super.sessionClosed(session);
        MinaConnector.isCon = false;
        mCallBack.sessionClosed(false);
    }

    /**
     * 向客服端发送消息后会调用此方法
     */
    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        Log.i("MinaConnectorHandler", "检测到正在向网关发送字符串：" + message);
        super.messageSent(session, message);
    }

    /**
     * 服务器与客户端连接打开
     */
    @Override
    public void sessionOpened(IoSession session) throws Exception {
        Log.i("MinaConnectorHandler", "网络层已开启session");
        super.sessionOpened(session);
    }

    /**
     * 服务器发送异常
     */
    @Override
    public void exceptionCaught(IoSession session, Throwable exception) throws Exception {
        super.exceptionCaught(session, exception);
        if (exception instanceof PortUnreachableException) {
            MinaConnector.isCon = false;
            mCallBack.sessionClosed(false);
            Log.e("MinaConnectorHandler", "网络故障！！！！！！！！！");
        }
        Log.e("MinaConnectorHandler", "session出现异常", exception);
    }

    /**
     * 服务器进入空闲状态
     */
    @Override
    public void sessionIdle(IoSession session, IdleStatus idle) throws Exception {
        Log.i("MinaConnectorHandler", "服务器进入空闲状态");
        super.sessionIdle(session, idle);
    }
}

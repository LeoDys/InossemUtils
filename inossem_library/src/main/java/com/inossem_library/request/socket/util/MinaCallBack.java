package com.inossem_library.request.socket.util;


/**
 * @author 郭晓龙
 * @time on 2019/7/16
 * @email xiaolong.guo@inossem.com
 * @describe Socket回调接口 处理收到消息和连接断开
 */
public interface MinaCallBack {
    /**
     * 收到消息回调
     *
     * @param msg
     */
    void messageReceived(String msg);

    /**
     * 连接断开回调
     *
     * @param isCon
     */
    void sessionClosed(boolean isCon);
}

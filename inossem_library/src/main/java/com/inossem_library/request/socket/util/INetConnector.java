package com.inossem_library.request.socket.util;

/**
 * @author 郭晓龙
 * @time on 2019/7/16
 * @email xiaolong.guo@inossem.com
 * @describe Socket连接接口
 */
public interface INetConnector {
    /**
     * 连接服务
     *
     * @param host ip地址
     * @param port 端口号
     * @return 返回连接状态true、false
     */
    boolean connectServer(String host, int port);

    /**
     * 断开连接
     */
    void disconnect();

    /**
     * 发送数据
     *
     * @param msg 发送内容
     */
    void write(String msg);

    /**
     * 连接状态
     *
     * @return 返回连接状态true、false
     */
    boolean isConnect();
}

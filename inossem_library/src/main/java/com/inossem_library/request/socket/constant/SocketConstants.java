package com.inossem_library.request.socket.constant;

/**
 * @author 郭晓龙
 * @time on 2019/7/18
 * @email xiaolong.guo@inossem.com
 * @describe Socket工具类常量
 */

public class SocketConstants {
    //超时时间
    public static final int TIMEOUTMILLIS = 3000;
    //休眠时间
    public static final int SLEEPTIME = 500;
    //断线重连时间10秒
    public static final int RECONNECT_TIME = 1000*10;
    //10分钟自动重连
    public static final int AUTO_TIME = 1000 * 60 * 10;
    //mina拦截器类型
    public static final String CODEC = "codec";
    //数据分发广播Action
    public final static String SOCKET_ACTION = "com.inossem.smartstorage.socket";
    //消息
    public final static String SOCKET_MESSAGE = "message";
    //ip地址
    public final static String SOCKET_URL = "socketUrl";
    //端口号
    public final static String SOCKET_PORT = "socketPort";
    //默认ip地址
    public final static String DEFULE_SOCKET_URL = "192.168.3.102";
    //默认端口号
    public final static int DEFULE_SOCKET_PORT = 60000;
}

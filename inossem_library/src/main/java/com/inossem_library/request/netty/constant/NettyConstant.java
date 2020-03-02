package com.inossem_library.request.netty.constant;

/**
 * @author 詹建宇
 * @time on 2019/7/25
 * @email jianyu.zhan@inossem.com
 * @describe Netty常量
 */
public class NettyConstant {

    /*
    Netty框架需要的一些参数
    */
    //SharedPreferences命名
    public final static String SP = "netty_sp";
    //基础服务器地址(类型-String)
    public final static String HOST = "host";
    //基础服务器地址(类型-String)
    public final static String PORT = "port";
    //读闲置时间 readerIdleTime(多长时间没有接受到服务器发送数据之后触发心跳)
    public final static String IDLE_READ = "idle_read";
    //写闲置时间 writerIdleTime(多长时间没有向客户端发送数据之后触发心跳)
    public final static String IDLE_WRITE = "idle_write";
    //所有类型的超时时间 allIdleTime
    public final static String IDLE_ALL = "idle_all";
    //是否打印日志
    public final static String IS_PRINT_LOG = "is_print_log";
    //是否保存日志
    public final static String IS_SAVE_LOG = "is_save_log";
}

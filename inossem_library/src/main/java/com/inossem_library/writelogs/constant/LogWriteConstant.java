package com.inossem_library.writelogs.constant;

/**
 * 本地日志写入常量类
 *
 * @author Leo E-mail:changwen.sun@inossem.com 2019/8/2 17:20
 * @version 1.0.7
 * @since 1.0.7
 */

public class LogWriteConstant {
    // 日志根目录名称
    public static final String LOG_ROOT_PATH = "Inossem_Log";
    // SD卡日志
    public static final String SD_FILE_NAME = "SD";
    // 日志文件后缀名
    public static final String FILE_SUFFIX = ".txt";
    // 文件名日期格式
    public static final String FILE_DATE_TEMPLATE = "yyyyMMddHHmmss";
    // 日志日期格式
    public static final String LOG_DATE_TEMPLATE = "yyyy-MM-dd HH:mm:ss";
    // 日志文件名SP
    public static final String LOG_SPNAME = "Inossem_Log";

    public static final String TOP_MESSAGE = "\n*************";

    public static final String BOTTOM_MESSAGE = "\n*************\n\n";
    // 单个文件最大允许3MB
    public static final long MAX_FILE_SIZE = 3L * 1024L * 1024L;
    // 文件夹下最大允许30MB
    public static final long MAX_TOTAL_FILE_SIZE = 30L * 1024L * 1024L;
    // 文件保存最多30天
    public static final long MAX_FILE_SAVE_TIME = 30L * 24L * 60L * 60L * 1000L;


    // Log.ERROR=6
    // Log.WARN=5
    // Log.INFO=4
    // Log.DEBUG=3
    // Log.VERBOSE=2
    public static final int LOG_LEVEL = 8; // 当LOG_LEVEL等于0时所有日志将不再输出

    public static final String TAG_INOSSEM = "INOSSEM";

    public static final String TAG_LOG = "LOG";

    public static final String NEW_LINE = "\n\n";
    /**
     * 以下为日志类型
     */
    // 崩溃日志名称
    public static final String LOG_NAME_CRASHEXCEPTION = "CrashException";
    // 异常捕获日志名称
    public static final String LOG_NAME_THROWABLE = "Throwable";
    // 请求
    public static final String LOG_NAME_REQUEST = "Request";
    // 数据库
    public static final String LOG_NAME_DATABASE = "Database";
    // 二维码
    public static final String LOG_NAME_QRCODE = "QRCode";
    //剪切板lebel值
    public static final String CLIPBOARD_LABEL = "Inossem";

}

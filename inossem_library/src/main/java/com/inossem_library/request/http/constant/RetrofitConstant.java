package com.inossem_library.request.http.constant;

import java.nio.charset.Charset;

/**
 * @author 詹建宇
 * @time on 2019/7/25
 * @email jianyu.zhan@inossem.com
 * @describe Retrofit请求框架 常量
 */
public class RetrofitConstant {

    /*
    Retrofit框架需要的一些参数
     */
    //SharedPreferences命名
    public final static String SP = "retrofit_sp";
    //基础服务器地址(类型-String)
    public final static String URL = "retrofit_url";
    //请求头(类型-Map)
    public final static String HEADER = "retrofit_header";
    //连接超时时间
    public final static String CONT_TIMEOUT = "connect_timeout";
    //读取超时时间
    public final static String READ_TIMEOUT = "read_timeout";
    //写超时时间
    public final static String WRITE_TIMEOUT = "write_timeout";
    //是否打印日志
    public final static String IS_PRINT_LOG = "is_print_log";
    //是否保存日志
    public final static String IS_SAVE_LOG = "is_save_log";

    /*
    时间格式相关
     */
    //时间格式 年-月-日 时-分-秒
    public final static String FORMAT_LONG = "yyyy-MM-dd HH:mm:ss";
    //时间格式 年-月-日
    public final static String FORMAT_SHORT = "yyyy-MM-dd";

    /*
    网络框架默认配置
     */
    //默认连接时间(单位:毫秒)
    public final static Long DEFAULT_CONNECT = 20 * 1000L;
    //默认读取时间(单位:毫秒)
    public final static Long DEFAULT_READ = 20 * 1000L;
    //默认写时间(单位:毫秒)
    public final static Long DEFAULT_WRITE = 20 * 10000L;
    //默认是否打印日志
    public final static Boolean DEFAULT_PRINT_LOG = true;
    //默认是否保存日志
    public final static Boolean DEFAULT_SAVE_LOG = true;

    /*
    可能会用到的字符集
    一般默认字符集为utf-8 但是如果采用GZIp压缩 必须使用ISO-8859-1
     */
    public final static String CHARSET_UTF8 = "utf-8";
    public final static String CHARSET_ISO88591 = "iso-8859-1";
    public final static String CHARSET_CAPITAL_UTF8 = "UTF-8";
    public final static String CHARSET_CAPITAL_ISO88591 = "ISO-8859-1";
    public final static Charset UTF8 = Charset.forName(CHARSET_CAPITAL_UTF8);
    public final static Charset ISO88591 = Charset.forName(CHARSET_CAPITAL_ISO88591);
}

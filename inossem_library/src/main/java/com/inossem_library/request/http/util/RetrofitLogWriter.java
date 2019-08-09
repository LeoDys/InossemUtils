package com.inossem_library.request.http.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.inossem_library.other.time.util.TimeUtils;
import com.inossem_library.request.http.constant.RetrofitConstant;
import com.inossem_library.request.http.constant.RetrofitLogConstant;
import com.inossem_library.tips.logcat.util.LogUtils;
import com.inossem_library.writelogs.util.LogWriteUtils;

/**
 * 写日志
 *
 * @author 詹建宇
 * @time on 2018/11/20 16:11
 * @email jianyu.zhan@inossem.com
 */
public class RetrofitLogWriter {

    /**
     * 拼接请求日志
     *
     * @param android  android开发人员
     * @param java     java开发人员
     * @param module   模块名称
     * @param function 接口功能
     * @param url      请求地址
     * @param method   请求方式
     * @param headers  请求头
     * @param body     请求体
     * @return 请求日志
     */
    public static String getRequestLogMsg(String android, String java, String module, String function,
                                          String url, String method, String headers, String body) {

        return RetrofitLogConstant.MODULE_LINE + "\n"
                + RetrofitLogConstant.LOG_MODULE + module + "\n"
                + RetrofitLogConstant.LOG_FUNCTION + function + "\n"
                + RetrofitLogConstant.LOG_TIME + TimeUtils.getNowString() + "\n"
                + RetrofitLogConstant.LOG_TYPE + RetrofitLogConstant.REQUEST + "\n"
                + RetrofitLogConstant.LOG_ANDROID + android + "\n"
                + RetrofitLogConstant.LOG_JAVA + java + "\n"
                + RetrofitLogConstant.LOG_URL + url + "\n"
                + RetrofitLogConstant.LOG_METHOD + method + "\n"
                + RetrofitLogConstant.LOG_HEADER + headers + "\n"
                + RetrofitLogConstant.LOG_BODY + body + "\n"
                + RetrofitLogConstant.MODULE_LINE + "\n" + "\n" + "\n";
    }

    /**
     * 拼接响应日志
     *
     * @param android  android开发人员
     * @param java     java开发人员
     * @param module   模块名称
     * @param function 接口功能
     * @param url      请求地址
     * @param res_code 服务器请求码
     * @param res_info 响应体
     * @return 响应日志
     */
    public static String getResponseLogMsg(String android, String java, String module, String function,
                                           String url, String res_code, String res_info) {

        return RetrofitLogConstant.MODULE_LINE + "\n"
                + RetrofitLogConstant.LOG_MODULE + module + "\n"
                + RetrofitLogConstant.LOG_FUNCTION + function + "\n"
                + RetrofitLogConstant.LOG_TIME + TimeUtils.getNowString() + "\n"
                + RetrofitLogConstant.LOG_TYPE + RetrofitLogConstant.RESPONSE + "\n"
                + RetrofitLogConstant.LOG_ANDROID + android + "\n"
                + RetrofitLogConstant.LOG_JAVA + java + "\n"
                + RetrofitLogConstant.LOG_URL + url + "\n"
                + RetrofitLogConstant.LOG_RES_CODE + res_code + "\n"
                + RetrofitLogConstant.LOG_RES_INFO + res_info + "\n"
                + RetrofitLogConstant.MODULE_LINE + "\n" + "\n" + "\n";
    }

    /**
     * 对日志进行处理
     *
     * @param context        上下文
     * @param type           类型(请求/响应)
     * @param logInformation 日志信息
     */
    public static void log(Context context, String type, String logInformation) {
        if (null == context) return;
        SharedPreferences sp = RetrofitUtils.getSettingSp(context);
        Boolean isPrintLog = sp.getBoolean(RetrofitConstant.IS_PRINT_LOG, RetrofitConstant.DEFAULT_PRINT_LOG);
        Boolean isSaveLog = sp.getBoolean(RetrofitConstant.IS_SAVE_LOG, RetrofitConstant.DEFAULT_SAVE_LOG);

        if (isPrintLog != null && isPrintLog) {
            //打印日志
            LogUtils.loggerI(logInformation);
        }

        if (isSaveLog != null && isSaveLog) {
            //保存日志
            LogWriteUtils.logRequestI(context, logInformation);
        }

    }

}

package com.inossem_library.exception.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 异常转换工具类
 * 将异常转换成string
 *
 * @author Leo E-mail:changwen.sun@inossem.com 2019/8/2 17:27
 * @version 1.0.7
 * @since 1.0.7
 */

public class ExceptionUtil {
    /**
     * 将Throwable 转为 String
     * 目的：存储到本地
     *
     * @param throwable 要转换的异常
     * @return
     */
    public static String exceptionToString(Throwable throwable) {
        if (throwable == null) {
            return "";
        }
        String exceptionMessage = "";
        try {
            StringWriter sw = new StringWriter();
            throwable.printStackTrace(new PrintWriter(sw, true));
            exceptionMessage = sw.toString();
            sw.flush();
            sw.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return exceptionMessage;
    }
}

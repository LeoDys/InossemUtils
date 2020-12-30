package com.inossem_library.crash_exception;

import android.content.Context;

/**
 * @author 郭晓龙
 * @time on 2020-12-25 10:09:38
 * @email xiaolong.guo@inossem.com
 * @describe 捕获异常工具类
 */
public class CrashExceptionUtils {
    /**
     * 初始化全局异常捕获
     *
     * @param context                上下文
     * @param crashExceptionResponse 异常捕获响应监听
     */
    public static void initCrashException(Context context, CrashExceptionResponse crashExceptionResponse) {
        CrashExceptionHandler ceh = CrashExceptionHandler.getInstance();
        ceh.init(context, crashExceptionResponse);
    }

    public interface CrashExceptionResponse {
        void onResponse(Context context);
    }
}

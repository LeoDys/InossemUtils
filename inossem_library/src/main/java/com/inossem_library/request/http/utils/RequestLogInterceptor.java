package com.inossem_library.request.http.utils;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.inossem_library.request.http.constant.RetrofitConstant;
import com.inossem_library.request.http.constant.RetrofitLogConstant;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * @author 詹建宇
 * @time on 2019/7/25
 * @email jianyu.zhan@inossem.com
 * @describe Retrofit拦截器(请求)
 */
@SuppressWarnings("unchecked")
public class RequestLogInterceptor implements Interceptor {

    //上下文
    private Context context;
    //android开发人员
    private String android;
    //java开发人员
    private String java;
    //功能模块
    private String module;
    //接口描述
    private String function;

    /**
     * 构造方法
     *
     * @param context  上下文
     * @param android  android开发人员
     * @param java     java开发人员
     * @param module   功能模块
     * @param function 接口描述
     */
    RequestLogInterceptor(Context context, String android, String java, String module, String function) {
        this.context = context;
        this.android = android;
        this.java = java;
        this.module = module;
        this.function = function;
    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {

        Request request = chain.request();
        RequestBody requestBody = request.body();

        String body = "";
        if (requestBody != null) {
            Buffer buffer = new Buffer();
            requestBody.writeTo(buffer);

            Charset charset = RetrofitConstant.UTF8;
            MediaType contentType = requestBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(RetrofitConstant.UTF8);
            }
            if (charset != null) {
                //请求体
                body = buffer.readString(charset);
            }
        }

        //请求类别
        String method = request.method();
        //请求地址
        String urls = request.url().toString();
        //请求头
        String headers = new Gson().toJson(request.headers());

        //拼接日志
        String requestLog = RetrofitLogWriter.getRequestLogMsg(android, java, module, function,
                urls, method, headers, body);
        //处理日志
        RetrofitLogWriter.log(context, RetrofitLogConstant.REQUEST, requestLog);

        return chain.proceed(request);
    }

}

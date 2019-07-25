package com.inossem_library.request.http.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.inossem_library.request.http.constant.RetrofitConstant;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * Retrofit拦截器(接收)
 *
 * @author 詹建宇
 * @time on 2018/11/20 11:13
 * @email jianyu.zhan@inossem.com
 */
@SuppressWarnings("unchecked")
public class ReceiveLogInterceptor implements Interceptor {

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
    ReceiveLogInterceptor(Context context, String android, String java, String module, String function) {
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
        Response response = chain.proceed(request);
        ResponseBody responseBody = response.body();

        if (null != responseBody) {
            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE);
            Buffer buffer = source.buffer();

            Charset charset = RetrofitConstant.UTF8;
            MediaType contentType = responseBody.contentType();
            if (contentType != null) {
                try {
                    charset = contentType.charset(RetrofitConstant.UTF8);
                } catch (UnsupportedCharsetException e) {
                    e.printStackTrace();
                }
            }

            //HTTP状态码
            String responseCode = String.valueOf(response.code());
            //请求地址
            String url = response.request().url().toString();
            //返回Json
            String body = buffer.clone().readString(charset);

            //TODO 打印日志
            Log.i("ReceiveLogInterceptor", responseCode + "/" + url + "/" + body);
        }

        return response;
    }

}

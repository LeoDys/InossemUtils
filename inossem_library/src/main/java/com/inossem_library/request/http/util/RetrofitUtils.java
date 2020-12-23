package com.inossem_library.request.http.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.inossem_library.request.http.constant.RetrofitConstant;
import com.inossem_library.request.http.util.dealWithData.InossemConverterFactory;
import com.inossem_library.request.http.util.dealWithData.InossemRequestConverterListener;
import com.inossem_library.request.http.util.dealWithData.InossemResponseConverterListener;

import java.io.IOException;
import java.lang.reflect.Field;
import java.security.SecureRandom;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import androidx.annotation.NonNull;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author 詹建宇
 * @time on 2019/7/25
 * @email jianyu.zhan@inossem.com
 * @describe Retrofit网络框架工具类
 */
public class RetrofitUtils {

    /**
     * 设置Retrofit基本参数
     *
     * @param context        上下文
     * @param url            地址
     * @param header         请求头
     * @param connectTimeout 连接时间
     * @param readTimeout    读取时间
     * @param writeTimeout   写入时间
     * @param isPrintLog     是否打印日志
     * @param isSaveLog      是否保存日志
     * @throws Exception 异常
     */
    public static void set(Context context, String url, Map<String, String> header, Long connectTimeout,
                           Long readTimeout, Long writeTimeout, Boolean isPrintLog, Boolean isSaveLog, Boolean isSSL) throws Exception {
        if (null == context) throw new Exception("context can`t null");
        SharedPreferences.Editor editor = getSettingSp(context).edit();
        if (!TextUtils.isEmpty(url)) {
            //[请求地址]不为空时 保存[请求地址]
            editor.putString(RetrofitConstant.URL, url);
        }
        if (null != header) {
            //[请求头]不为空时 保存请求头 且根据key值 对value值进行覆盖或新增
            Map<String, String> oldHeader = getHeaderMap(context);
            for (Map.Entry<String, String> entryNew : header.entrySet()) {
                oldHeader.put(entryNew.getKey(), entryNew.getValue());
            }
            editor.putString(RetrofitConstant.HEADER, new Gson().toJson(oldHeader));
        }
        if (null != connectTimeout) {
            //[连接时间]不为空时 保存[连接时间]
            editor.putLong(RetrofitConstant.CONT_TIMEOUT, connectTimeout);
        }
        if (null != readTimeout) {
            //[读取时间]不为空时 保存[读取时间]
            editor.putLong(RetrofitConstant.READ_TIMEOUT, readTimeout);
        }
        if (null != writeTimeout) {
            //[写入时间]不为空时 保存[写入时间]
            editor.putLong(RetrofitConstant.WRITE_TIMEOUT, writeTimeout);
        }
        if (null != isPrintLog) {
            //[是否打印日志]不为空时 保存[是否打印日志]
            editor.putBoolean(RetrofitConstant.IS_PRINT_LOG, isPrintLog);
        }
        if (null != isSaveLog) {
            //[是否保存日志]不为空时 保存[是否保存日志]
            editor.putBoolean(RetrofitConstant.IS_SAVE_LOG, isSaveLog);
        }
        if (null != isSSL) {
            //[是否启用SSL]不为空时 保存[是否启用SSL]
            editor.putBoolean(RetrofitConstant.IS_SSL, isSSL);
        }
        editor.apply();
    }

    /**
     * 设置地址
     *
     * @param context 上下文
     * @param url     地址
     * @throws Exception 异常
     */
    public static void setUrl(Context context, String url) throws Exception {
        set(context, url, null, null, null, null, null, null, false);
    }

    /**
     * 设置请求头
     *
     * @param context 上下文
     * @param header  请求头
     * @throws Exception
     */
    public static void setHeader(Context context, Map<String, String> header) throws Exception {
        set(context, null, header, null, null, null, null, null, false);
    }

    /**
     * 设置连接时间
     *
     * @param context        上下文
     * @param connectTimeout 连接时间
     * @throws Exception 异常
     */
    public static void setConnectTimeout(Context context, Long connectTimeout) throws Exception {
        set(context, null, null, connectTimeout, null, null, null, null, false);
    }

    /**
     * 设置读取时间
     *
     * @param context     上下文
     * @param readTimeout 读取时间
     * @throws Exception 异常
     */
    public static void setRadTimeout(Context context, Long readTimeout) throws Exception {
        set(context, null, null, null, readTimeout, null, null, null, false);
    }

    /**
     * 设置写入时间
     *
     * @param context      上下文
     * @param writeTimeout 写入时间
     * @throws Exception 异常
     */
    public static void setWriteTimeout(Context context, Long writeTimeout) throws Exception {
        set(context, null, null, null, null, writeTimeout, null, null, false);
    }

    /**
     * 设置是否打印日志
     *
     * @param context    上下文
     * @param isPrintLog 是否打印日志
     * @throws Exception 异常
     */
    public static void setIsPrintLog(Context context, Boolean isPrintLog) throws Exception {
        set(context, null, null, null, null, null, isPrintLog, null, false);
    }

    /**
     * 设置是否保存日志
     *
     * @param context   上下文
     * @param isSaveLog 是否打印日志
     * @throws Exception 异常
     */
    public static void setIsSaveLog(Context context, Boolean isSaveLog) throws Exception {
        set(context, null, null, null, null, null, isSaveLog, null, false);
    }

    /**
     * 获取Retrofit实例
     *
     * @param context  上下文
     * @param android  android开发人员
     * @param java     java开发人员
     * @param module   功能模块
     * @param function 接口描述
     * @return Retrofit实例
     */
    public static Retrofit getRetrofit(Context context, String android, String java, String module, String function) {
        return getRetrofit(context, android, java, module, function, null, null, null);
    }

    /**
     * 获取Retrofit实例
     *
     * @param context  上下文
     * @param android  android开发人员
     * @param java     java开发人员
     * @param module   功能模块
     * @param function 接口描述
     * @param url      地址
     * @return Retrofit实例
     */
    public static Retrofit getRetrofit(Context context, String android, String java, String module, String function, String url) {
        return getRetrofit(context, android, java, module, function, url, null, null);
    }

    /**
     * 获取Retrofit实例
     *
     * @param context                   上下文
     * @param android                   android开发人员
     * @param java                      java开发人员
     * @param module                    功能模块
     * @param function                  接口描述
     * @param requestConverterListener  处理请求的接口(传空则不处理)
     * @param responseConverterListener 处理响应的接口(传空则不处理)
     * @return Retrofit实例
     */
    public static Retrofit getRetrofit(Context context, String android, String java, String module, String function,
                                       InossemRequestConverterListener requestConverterListener, InossemResponseConverterListener responseConverterListener) {
        return getRetrofit(context, android, java, module, function, null, requestConverterListener, responseConverterListener);
    }

    /**
     * 获取Retrofit实例
     *
     * @param context                   上下文
     * @param android                   android开发人员
     * @param java                      java开发人员
     * @param module                    功能模块
     * @param function                  接口描述
     * @param requestConverterListener  处理请求的接口(传空则不处理)
     * @param responseConverterListener 处理响应的接口(传空则不处理)
     * @param url                       地址
     * @return Retrofit实例
     */
    public static Retrofit getRetrofit(Context context, String android, String java, String module, String function, String url,
                                       InossemRequestConverterListener requestConverterListener, InossemResponseConverterListener responseConverterListener) {
        //Retrofit建造工厂
        Retrofit.Builder builder = new Retrofit.Builder();
        if (TextUtils.isEmpty(url)) {
            builder = builder.baseUrl(getSettingSp(context).getString(RetrofitConstant.URL, ""));
        } else {
            builder = builder.baseUrl(url);
        }
        builder = builder.client(getClient(context, android, java, module, function));

        //判断是否需要数据处理(压缩 加密)
        if (null != requestConverterListener || null != responseConverterListener) {
            //自定义数据处理
            builder = builder.addConverterFactory(InossemConverterFactory.create(getGson(), requestConverterListener, responseConverterListener));
        } else {
            //默认处理方式
            builder = builder.addConverterFactory(GsonConverterFactory.create(getGson()));
        }

        return builder.build();
    }

    /**
     * 获取Retrofit配置项SP
     *
     * @param context 上下文
     * @return SP
     */
    public static SharedPreferences getSettingSp(Context context) {
        return context.getSharedPreferences(RetrofitConstant.SP, Context.MODE_PRIVATE);
    }

    /**
     * 获取Gson实例(解决String解析成Date的问题)
     *
     * @return Gson实例
     */
    private static Gson getGson() {
        return new GsonBuilder().setDateFormat(RetrofitConstant.FORMAT_LONG).
                registerTypeAdapter(Date.class, GsonDateErrorAnalysis.getInstance())
                .create();
    }

    /**
     * 设置OkHttp参数
     *
     * @param context  上下文
     * @param android  android开发人员
     * @param java     java开发人员
     * @param module   功能模块
     * @param function 接口描述
     * @return OkHttpClient
     */
    private static OkHttpClient getClient(Context context, String android, String java, String module, String function) {
        final SharedPreferences sp = getSettingSp(context);
        if (sp.getBoolean(RetrofitConstant.IS_SSL, false)) {

            OkHttpClient client = new OkHttpClient.Builder()
                    //连接时间
                    .connectTimeout(sp.getLong(RetrofitConstant.CONT_TIMEOUT, RetrofitConstant.DEFAULT_CONNECT), TimeUnit.MILLISECONDS)
                    //读取时间
                    .readTimeout(sp.getLong(RetrofitConstant.READ_TIMEOUT, RetrofitConstant.DEFAULT_READ), TimeUnit.MILLISECONDS)
                    //写入时间
                    .writeTimeout(sp.getLong(RetrofitConstant.WRITE_TIMEOUT, RetrofitConstant.DEFAULT_WRITE), TimeUnit.MILLISECONDS)
                    //设置请求头
                    .addInterceptor(setHeader(context))
                    //设置请求拦截器
                    .addInterceptor(new RequestLogInterceptor(context, android, java, module, function))
                    //设置接收拦截器
                    .addInterceptor(new ReceiveLogInterceptor(context, android, java, module, function))
                    //再原有分装添加
                    .sslSocketFactory(HttpsUtils.createSSLSocketFactory(HttpsUtils.createTrustCustomTrustManager(HttpsUtils.getInputStreamFromAsset(context, sp.getString(RetrofitConstant.SSL_NAME, "instock.cer"))))
                            , HttpsUtils.createTrustCustomTrustManager(HttpsUtils.getInputStreamFromAsset(context, sp.getString(RetrofitConstant.SSL_NAME, "instock.cer"))))
                    .hostnameVerifier(new HttpsUtils.TrustAllHostnameVerifier())
                    //创建
                    .build();

            return getSSLClient(client);
        }

        return new OkHttpClient.Builder()
                //连接时间
                .connectTimeout(sp.getLong(RetrofitConstant.CONT_TIMEOUT, RetrofitConstant.DEFAULT_CONNECT), TimeUnit.MILLISECONDS)
                //读取时间
                .readTimeout(sp.getLong(RetrofitConstant.READ_TIMEOUT, RetrofitConstant.DEFAULT_READ), TimeUnit.MILLISECONDS)
                //写入时间
                .writeTimeout(sp.getLong(RetrofitConstant.WRITE_TIMEOUT, RetrofitConstant.DEFAULT_WRITE), TimeUnit.MILLISECONDS)
                //设置请求头
                .addInterceptor(setHeader(context))
                //设置请求拦截器
                .addInterceptor(new RequestLogInterceptor(context, android, java, module, function))
                //设置接收拦截器
                .addInterceptor(new ReceiveLogInterceptor(context, android, java, module, function))
                //创建
                .build();
    }

    private static OkHttpClient getSSLClient(OkHttpClient sClient) {
        SSLContext sc = null;
        try {
            sc = SSLContext.getInstance("SSL");
            sc.init(null, new TrustManager[]{new X509TrustManager() {
                @Override
                public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {

                }

                @Override
                public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {

                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            }}, new SecureRandom());
        } catch (Exception e) {
            e.printStackTrace();
        }
        HostnameVerifier hv1 = (hostname, session) -> true;

        String workerClassName = "okhttp3.OkHttpClient";
        try {
            Class workerClass = Class.forName(workerClassName);
            Field hostnameVerifier = workerClass.getDeclaredField("hostnameVerifier");
            hostnameVerifier.setAccessible(true);
            hostnameVerifier.set(sClient, hv1);

            Field sslSocketFactory = workerClass.getDeclaredField("sslSocketFactory");
            sslSocketFactory.setAccessible(true);
            sslSocketFactory.set(sClient, sc.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sClient;
    }

    /**
     * 设置请求头
     *
     * @param context 上下文
     * @return 请求头
     */
    private static Interceptor setHeader(final Context context) {
        return new Interceptor() {
            @NonNull
            @Override
            public Response intercept(@NonNull Chain chain) throws IOException {
                Map<String, String> headerMap = getHeaderMap(context);
                //Request建造工厂
                Request.Builder builder = chain.request().newBuilder();
                //遍历headerMap 设置请求头
                for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                    builder.addHeader(entry.getKey(), entry.getValue());
                }
                //建造Request
                Request request = builder.build();
                return chain.proceed(request);
            }
        };
    }

    /**
     * 获取请求头
     *
     * @param context 上下文
     * @return 请求头
     */
    private static Map<String, String> getHeaderMap(Context context) {
        //获取配置SP
        SharedPreferences sp = getSettingSp(context);
        String headerStr = sp.getString(RetrofitConstant.HEADER, "");
        java.lang.reflect.Type type = new TypeToken<HashMap<String, String>>() {
        }.getType();
        //解析出请求头
        Map<String, String> headerMap;
        if (TextUtils.isEmpty(headerStr)) {
            headerMap = new HashMap<>();
        } else {
            headerMap = new Gson().fromJson(headerStr, type);
        }
        return headerMap;
    }

}

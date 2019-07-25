package com.inossem_library.request.http.utils.dealWithData;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * @author 詹建宇
 * @time on 2019/7/20
 * @email jianyu.zhan@inossem.com
 * @describe 转换器工厂(处理加密 压缩)
 */
public class InossemConverterFactory extends Converter.Factory {

    //解析Json工具
    private final Gson gson;
    //处理请求的接口
    private InossemRequestConverterListener requestConverterListener;
    //处理返回的接口
    private InossemResponseConverterListener responseConverterListener;

    /**
     * 自定义Gson获取EncryptConverterFactory
     *
     * @param gson 自定义Gson
     * @return InossemConverterFactory
     */
    public static InossemConverterFactory create(Gson gson,InossemRequestConverterListener requestConverterListener, InossemResponseConverterListener responseConverterListener) {
        return new InossemConverterFactory(gson,  requestConverterListener,  responseConverterListener);
    }

    /**
     * 构造方法
     *
     * @param gson 解析Json工具
     */
    private InossemConverterFactory(Gson gson, InossemRequestConverterListener requestConverterListener, InossemResponseConverterListener responseConverterListener) {
        if (null == gson) {
            gson = new Gson();
        }
        this.gson = gson;
        this.requestConverterListener = requestConverterListener;
        this.responseConverterListener = responseConverterListener;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        try {
            //处理响应
            return new InossemResponseConverter<>(adapter, responseConverterListener);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        try {
            //处理请求
            return new InossemRequestConverter(adapter,requestConverterListener);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}

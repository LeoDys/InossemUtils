package com.inossem_library.request.http.util.dealWithData;

import android.support.annotation.NonNull;

import com.google.gson.TypeAdapter;

import java.io.IOException;

import okhttp3.RequestBody;
import retrofit2.Converter;

/**
 * @author 詹建宇
 * @time on 2019/7/25
 * @email jianyu.zhan@inossem.com
 * @describe 请求转换器
 */
public class InossemRequestConverter<T> implements Converter<T, RequestBody> {

    //类型适配器
    private TypeAdapter<T> adapter;

    //处理请求的接口
    private InossemRequestConverterListener requestConverterListener;

    /**
     * 构造方法
     *
     * @param adapter 类型适配器
     */
    public InossemRequestConverter(TypeAdapter<T> adapter, InossemRequestConverterListener requestConverterListener) throws Exception {
        if (null == adapter) {
            throw new Exception("adapter don`t be null");
        }
        this.adapter = adapter;
        this.requestConverterListener = requestConverterListener;
    }

    @Override
    public RequestBody convert(@NonNull T o) throws IOException {
        if (null != requestConverterListener) {
            return requestConverterListener.onListener(o,adapter);
        } else {
            return null;
        }
    }

}

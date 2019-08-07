package com.inossem_library.request.http.util.dealWithData;

import android.support.annotation.NonNull;

import com.google.gson.TypeAdapter;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * @author 詹建宇
 * @time on 2019/7/25
 * @email jianyu.zhan@inossem.com
 * @describe 响应转换器
 */
public class InossemResponseConverter<T> implements Converter<ResponseBody, T> {

    //类型适配器
    private TypeAdapter<T> adapter;

    //处理响应的接口
    private InossemResponseConverterListener responseConverterListener;

    /**
     * 构造方法
     *
     * @param adapter 类型适配器
     */
    public InossemResponseConverter(TypeAdapter<T> adapter, InossemResponseConverterListener responseConverterListener) throws Exception {
        if (null == adapter) {
            throw new Exception("adapter don`t be null");
        }
        this.adapter = adapter;
        this.responseConverterListener = responseConverterListener;
    }

    @Override
    public T convert(@NonNull ResponseBody responseBody) throws IOException {
        if (null != responseConverterListener) {
            return (T) responseConverterListener.onListener(responseBody, adapter);
        } else {
            return null;
        }
    }

}

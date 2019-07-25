package com.inossem_library.request.http.utils.dealWithData;

import android.support.annotation.NonNull;

import com.google.gson.TypeAdapter;

import java.io.IOException;

import okhttp3.RequestBody;

/**
 * @author 詹建宇
 * @time on 2019/7/25
 * @email jianyu.zhan@inossem.com
 * @describe 处理请求的接口(压缩 加密等对数据进行处理)
 */
public interface InossemRequestConverterListener<T> {
    RequestBody onListener(@NonNull T o, TypeAdapter<T> adapter) throws IOException;
}

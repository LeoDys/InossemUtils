package com.inossem_library.request.http.utils.dealWithData;

import android.support.annotation.NonNull;

import com.google.gson.TypeAdapter;

import java.io.IOException;

import okhttp3.ResponseBody;

/**
 * @author 詹建宇
 * @time on 2019/7/25
 * @email jianyu.zhan@inossem.com
 * @describe 处理响应的接口(解压 解密等对数据进行处理)
 */
public interface InossemResponseConverterListener<T> {
    T onListener(@NonNull ResponseBody responseBody, TypeAdapter<T> adapter) throws IOException;
}

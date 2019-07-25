package com.inossem_library.request.http.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * @author 詹建宇
 * @time on 2019/7/25
 * @email jianyu.zhan@inossem.com
 * @describe 网络请求管理
 */
public class RetrofitManager {

    //网络请求集合
    private final List<Call> mCallList;

    /**
     * 构造方法 并初始化网络请求集合
     */
    public RetrofitManager() {
        mCallList = new ArrayList<>();
    }

    /**
     * 发送请求
     *
     * @param call 网络请求
     * @param callback 请求回调
     * @param <T> 泛型
     */
    public <T> void call(Call<T> call, Callback<T> callback) {
        add(call);
        call.enqueue(callback);
    }

    /**
     * 取消所有请求
     */
    public void cancelAll() {
        /*
        加同步锁 遍历所有请求 取消请求
         */
        synchronized (mCallList) {
            Iterator<Call> iterator = mCallList.iterator();
            while (iterator.hasNext()) {
                Call call = iterator.next();
                if (call == null || call.isCanceled()) {
                    continue;
                }
                call.cancel();
                iterator.remove();
            }
        }
    }

    /**
     * 保存请求实体
     *
     * @param call 请求实体
     */
    private void add(Call call) {
        /*
        加同步锁 增加请求
         */
        synchronized (mCallList) {
            mCallList.add(call);
        }
    }
}

package com.inossem_library.request.http.utils;

import android.app.Dialog;
import android.support.annotation.NonNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author 詹建宇
 * @time on 2019/7/25
 * @email jianyu.zhan@inossem.com
 * @describe 请求响应
 */
public abstract class RetrofitCallback<T> implements Callback<T> {

    //请求网络时的弹窗(可不传)
    private Dialog dialog;

    //请求成功
    public abstract void success(Response<T> response);

    //404 500等HTTP错误
    public abstract void httpError(Response<T> response);

    //连接超时等连接错误
    //TODO 错误
    public abstract void failure(Throwable t);

    /**
     * 构造方法1
     *
     * 用此方法没有请求时的加载框
     */
    public RetrofitCallback() {
    }

    /**
     * 构造方法2
     *
     * @param dialog 用此方法有请求时的加载框
     *               1. 需要自己封装 网络框架不参与UI封装 降低耦合
     *               2. 建议用单例
     * @throws Exception 当设置的弹窗为空时 报异常
     */
    public RetrofitCallback(Dialog dialog) throws Exception {
        this.dialog = dialog;

        if (null == this.dialog) {
            throw new Exception("Dialog不能为空");
        }

        //如果dialog没有显示的话 显示dialog
        if (!this.dialog.isShowing()) {
            this.dialog.show();
        }
    }

    /**
     * 请求成功
     *
     * @param call     请求
     * @param response 返回值
     */
    @Override
    public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
        //关闭dialog
        dialogDismiss();
        if (response.isSuccessful()) {
            //请求成功
            success(response);
        } else {
            //404 500等HTTP错误
            httpError(response);
        }
    }

    /**
     * 请求失败
     *
     * @param call 请求
     * @param t    错误信息
     */
    @Override
    public void onFailure(@NonNull Call call, @NonNull Throwable t) {
        //关闭dialog
        dialogDismiss();
        //连接超时 等连接错误
        failure(t);
    }

    /**
     * 使弹窗消失
     */
    private void dialogDismiss() {
        if (null != dialog && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

}

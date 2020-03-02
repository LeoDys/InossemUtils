package com.inossem_library.request.netty.util;

/**
 * @author 詹建宇
 * @time on 2019/7/25
 * @email jianyu.zhan@inossem.com
 * @describe 连接是否成功回调
 */
public interface ConnectCallBack {
    void onSuccess();

    void onFailure();
}

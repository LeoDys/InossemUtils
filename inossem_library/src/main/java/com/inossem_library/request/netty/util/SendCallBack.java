package com.inossem_library.request.netty.util;

/**
 * @author 詹建宇
 * @time on 2019/7/25
 * @email jianyu.zhan@inossem.com
 * @describe 发送信息回调
 */
public interface SendCallBack {
    void onSuccess();

    void onFailure();
}

package com.inossem_library.request.netty.util;

/**
 * @author 詹建宇
 * @time on 2019/7/25
 * @email jianyu.zhan@inossem.com
 * @describe 服务器推送回调
 */
public class NettyCache {
    private static NettyService service;

    public static NettyService getService() {
        return service;
    }

    public static void setService(NettyService service) {
        NettyCache.service = service;
    }

}

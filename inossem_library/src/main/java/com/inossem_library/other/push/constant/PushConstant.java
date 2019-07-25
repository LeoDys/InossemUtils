package com.inossem_library.other.push.constant;

/**
 * @author 郭晓龙
 * @time on 2019/7/10
 * @email xiaolong.guo@inossem.com
 * @describe 推送工具类常量
 */
public class PushConstant {
    //推送广播Action
    public final static String PUSH_ACTION = "com.inossem.smartstorage.push";
    //读取raw文件路径
    public final static String ANDROID_RESOURCE = "android.resource://";
    //推送后发送广播类型
    public final static String PUSH_TYPE = "type";
    //推送后发送广播消息
    public final static String PUSH_MESSAGE = "message";
    //间接推送类型需要请求接口
    public final static String PUSH_TYPE_INDIRECT = "push_type_indirect";
    //系统公告
    public final static String PUSH_TYPE_SYSTEM_NOTICES = "push_type_system_notices";
}

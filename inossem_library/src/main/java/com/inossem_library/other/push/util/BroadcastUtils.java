package com.inossem_library.other.push.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.inossem_library.exception.constant.ExceptionEnum;
import com.inossem_library.exception.InossemException;
import com.inossem_library.other.string.util.StringUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 郭晓龙
 * @time on 2019/7/12
 * @email xiaolong.guo@inossem.com
 * @describe 广播封装类 注册、取消注册、发送
 */
public class BroadcastUtils {

    //上下文
    private Context mContext;
    //BroadcastUtils实例
    private static BroadcastUtils instance;
    //广播接收器集合
    private Map<String, BroadcastReceiver> receiverMap;

    /**
     * 构造方法
     *
     * @param context 上下文
     */
    private BroadcastUtils(Context context) {
        this.mContext = context;
        receiverMap = new HashMap<>();
    }

    /**
     * 单例模式实现
     *
     * @param context 上下文
     * @return 返回BroadcastUtils实例
     */
    public static BroadcastUtils getInstance(Context context) {
        if (instance == null) {
            synchronized (BroadcastUtils.class) {
                if (instance == null) {
                    instance = new BroadcastUtils(context);
                }
            }
        }
        return instance;
    }

    /**
     * 添加Action,做广播的初始化
     *
     * @param action   广播Action
     * @param receiver 广播接收器
     */
    public void registerReceiver(String action, BroadcastReceiver receiver) {
        if (StringUtils.isEmpty(action)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "action不能为空！");
        }
        if (receiver == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "receiver不能为空！");
        }
        IntentFilter filter = new IntentFilter();
        filter.addAction(action);
        LocalBroadcastManager.getInstance(mContext).registerReceiver(receiver, filter);
        receiverMap.put(action, receiver);
    }

    /**
     * 添加Action,做广播的初始化
     *
     * @param actions  广播Action
     * @param receiver 广播接收器
     */
    public void registerReceiver(String[] actions, BroadcastReceiver receiver) {
        if (actions == null || actions.length == 0) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "action不能为空！");
        }
        if (receiver == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "receiver不能为空！");
        }
        IntentFilter filter = new IntentFilter();
        for (String action : actions) {
            filter.addAction(action);
        }
        LocalBroadcastManager.getInstance(mContext).registerReceiver(receiver, filter);
        for (String action : actions) {
            receiverMap.put(action, receiver);
        }
    }

    /**
     * 发送广播
     *
     * @param action   广播Action
     * @param types    类型
     * @param messages 消息
     */
    public void sendBroadcast(String action, String[] types, Object[] messages) {
        if (StringUtils.isEmpty(action)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "action不能为空！");
        }
        if (types == null || types.length == 0) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "types不能为空！");
        }
        if (messages == null || messages.length == 0) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "messages不能为空！");
        }
        Intent intent = new Intent();
        intent.setAction(action);
        for (int i = 0; i < types.length; i++) {
            String type = messages[i].getClass().getSimpleName();
            if ("String".equals(type)) {
                intent.putExtra(types[i], (String) messages[i]);
            } else if ("Integer".equals(type)) {
                intent.putExtra(types[i], (Integer) messages[i]);
            } else if ("Boolean".equals(type)) {
                intent.putExtra(types[i], (Boolean) messages[i]);
            } else if ("Float".equals(type)) {
                intent.putExtra(types[i], (Float) messages[i]);
            } else if ("Long".equals(type)) {
                intent.putExtra(types[i], (Long) messages[i]);
            } else {
                intent.putExtra(types[i], (Serializable) messages[i]);
            }
        }
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
    }

    /**
     * 销毁广播
     *
     * @param actions 广播Action
     */
    public void unregisterReceiver(String... actions) {
        if (actions == null || actions.length == 0) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "actions不能为空！");
        }
        if (receiverMap != null) {
            for (String action : actions) {
                BroadcastReceiver receiver = receiverMap.get(action);
                if (receiver != null) {
                    LocalBroadcastManager.getInstance(mContext).unregisterReceiver(receiver);
                }
            }
        }
    }
}
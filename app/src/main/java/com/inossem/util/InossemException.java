package com.inossem.util;

import android.text.TextUtils;

/**
 * 异常封装
 *
 * @author 雷嘉
 * @time on 2018/3/6 15:34
 * @email jia.lei@inossem.com
 */

public class InossemException extends RuntimeException {

    /**
     * 实例化一个错误对象，调用错误对象的toString方法转成错误信息打印出来
     *
     * @param object        错误对象
     * @param extendMessage 扩展信息
     */
    public InossemException(Object object, String... extendMessage) {
        super(object.toString() + (extendMessage == null || extendMessage.length != 1 || TextUtils.isEmpty(extendMessage[0]) ? "" : extendMessage[0]));
    }

    /**
     * 实例化一个错误对象，将错误信息打印出来
     *
     * @param message 错误信息
     */
    public InossemException(String message) {
        super(message);
    }

}

package com.inossem_library.exception;

import android.text.TextUtils;

import com.inossem_library.exception.constant.ExceptionEnum;

/**
 * 异常封装
 *
 * @author Leo E-mail:changwen.sun@inossem.com 2019/7/24 15:56
 * @version Java-1.8
 * @since 1.0.0
 */
public class InossemException extends RuntimeException {

    /**
     * 实例化一个错误对象，调用错误对象的toString方法转成错误信息打印出来
     *
     * @param exceptionEnum 错误对象
     * @param extendMessage 扩展信息
     */
    public InossemException(ExceptionEnum exceptionEnum, String... extendMessage) {
        super(exceptionEnum.toString() + (extendMessage == null || extendMessage.length != 1 || TextUtils.isEmpty(extendMessage[0]) ? "" : extendMessage[0]));
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

package com.inossem_library.other.pattern.constant;

/**
 * 收拾锁屏常量类
 *
 * @author LeoDys E-mail:changwen.sun@inossem.com 2020/2/27 15:13
 * @version 1.0.8
 * @since 1.0.8
 */
public class PatternConstant {
    //打开密码页面类型
    public static final String KEY_TYPE = "type";
    //图片资源文件
    public static final String KEY_LOGO = "logo";
    //正确密码
    public static final String KEY_CORRECT_PASSWORD = "correct_password";
    //正确密码
    public static final String KEY_NEW_PASSWORD = "new_password";
    //返回前页面的类型
    public static final String KEY_RESULT_TYPE = "result_type";
    //设置密码
    public static final String TYPE_SET_PASSWORD = "set";
    //修改密码
    public static final String TYPE_CHANGE_PASSWORD = "change";
    //校验密码
    public static final String TYPE_CHECK_PASSWORD = "check";
    //点击确定按钮的返回类型
    public static final String RESULT_TYPE_CONFIRM = "confirm";
    //点击忘记密码按钮的返回类型
    public static final String RESULT_TYPE_FORGET = "forget";
    //校验密码成功的返回类型
    public static final String RESULT_TYPE_CHECK_SUCCESS = "check_success";
    //设置密码的请求码
    public static final int REQUEST_CODE_SET_PASSWORD = 10010;
    //修改密码的请求码
    public static final int REQUEST_CODE_CHANGE_PASSWORD = 10011;
    //校验密码的请求码
    public static final int REQUEST_CODE_CHECK_PASSWORD = 10012;
    //收拾密码最小长度
    public static final int PASSWORD_LENGTH = 4;
}

package com.inossem_library.request.http.constant;

/**
 * @author 詹建宇
 * @time on 2019/7/25
 * @email jianyu.zhan@inossem.com
 * @describe 请求错误类别的枚举类
 */
public enum RetrofitCallBackErrorEnum {

    TIME_OUT(991, "超时"),
    CONNECT_FAIL(992, "连接错误"),
    FIND_NULL(993, "未找到主机"),
    PARSE_ERROR(994, "json解析错误"),
    OTHER(999, "其它未知错误");

    //错误码
    private int errorCode;
    //错误信息(中文)
    private String errorMSgCN;

    /**
     * 构造方法
     *
     * @param errorCode  错误码
     * @param errorMSgCN 错误信息(中文)
     */
    RetrofitCallBackErrorEnum(int errorCode, String errorMSgCN) {
        this.errorCode = errorCode;
        this.errorMSgCN = errorMSgCN;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMSgCN() {
        return errorMSgCN;
    }
}

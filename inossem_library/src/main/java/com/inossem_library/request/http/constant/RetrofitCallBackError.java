package com.inossem_library.request.http.constant;

/**
 * @author 詹建宇
 * @time on 2019/7/25
 * @email jianyu.zhan@inossem.com
 * @describe 请求错误类别
 */
public class RetrofitCallBackError {

    //异常
    private Throwable throwable;
    //错误码
    private int errorCode;
    //错误信息(中文)
    private String errorMSgCN;
    //错误信息(英文)
    private String errorMSgUS;

    /**
     * 构造方法
     *
     * @param throwable  异常
     * @param errorCode  错误码(错误码列表,请查看枚举类--> RetrofitCallBackErrorEnum )
     * @param errorMSgCN 错误信息(中文)
     * @param errorMSgUS 错误信息(英文)
     */
    public RetrofitCallBackError(Throwable throwable, int errorCode, String errorMSgCN, String errorMSgUS) {
        this.throwable = throwable;
        this.errorCode = errorCode;
        this.errorMSgCN = errorMSgCN;
        this.errorMSgUS = errorMSgUS;
    }

    /**
     * 获取异常
     *
     * @return 异常
     */
    public Throwable getThrowable() {
        return throwable;
    }

    /**
     * 设置异常
     *
     * @param throwable 异常
     */
    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    /**
     * 获取错误码
     *
     * @return 错误码
     */
    public int getErrorCode() {
        return errorCode;
    }

    /**
     * 设置错误码
     *
     * @param errorCode 错误码
     */
    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * 获取错误信息(中文)
     *
     * @return 错误信息(中文)
     */
    public String getErrorMSgCN() {
        return errorMSgCN;
    }

    /**
     * 设置错误信息(中文)
     *
     * @param errorMSgCN 错误信息(中文)
     */
    public void setErrorMSgCN(String errorMSgCN) {
        this.errorMSgCN = errorMSgCN;
    }

    /**
     * 获取错误信息(英文)
     *
     * @return 错误信息(英文)
     */
    public String getErrorMSgUS() {
        return errorMSgUS;
    }

    /**
     * 设置错误信息(英文)
     *
     * @param errorMSgUS 错误信息(英文)
     */
    public void setErrorMSgUS(String errorMSgUS) {
        this.errorMSgUS = errorMSgUS;
    }

}

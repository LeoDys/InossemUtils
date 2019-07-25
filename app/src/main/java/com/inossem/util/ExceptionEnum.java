package com.inossem.util;

/**
 * 异常枚举集合
 *
 * @author 雷嘉
 * @time on 2018/3/6 15:27
 * @email jia.lei@inossem.com
 */

public enum ExceptionEnum {

    NULL_PARAMS("9001", "参数不可以为空"),
    ILLEGAL_PARAMS("9002", "参数非法");

    private String code;
    private String description;

    ExceptionEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Code:【" + this.code + "】 --- Reason:【" + this.description + "】 ";
    }
}

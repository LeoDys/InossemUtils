package com.inossem_library.other.regex.constant;

/**
 * @author 郭晓龙
 * @time on 2019/7/15
 * @email xiaolong.guo@inossem.com
 * @describe 正则表达式工具类常量
 */
public class RegexConstants {

    /**
     * 简单移动正则表达式
     */
    public static final String REGEX_MOBILE_SIMPLE = "^[1]\\d{10}$";
    /**
     * 精确移动的正则表达式
     * <p>中国移动: 134(0-8), 135, 136, 137, 138, 139, 147, 150, 151, 152, 157, 158, 159, 178, 182, 183, 184, 187, 188, 198</p>
     * <p>中古联通: 130, 131, 132, 145, 155, 156, 166, 171, 175, 176, 185, 186</p>
     * <p>中国电信: 133, 153, 173, 177, 180, 181, 189, 199, 191</p>
     * <p>全球通: 1349</p>
     * <p>虚拟操作员: 170</p>
     */
    public static final String REGEX_MOBILE_EXACT = "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(16[6])|(17[0,1,3,5-8])|(18[0-9])|(19[1,8,9]))\\d{8}$";
    /**
     * 电话号码的正则表达式
     */
    public static final String REGEX_TEL = "^0\\d{2,3}[- ]?\\d{7,8}";
    /**
     * 身份证号码的正则表达式，长度为15。
     */
    public static final String REGEX_ID_CARD15 = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$";
    /**
     * 身份证号码的正则表达式，长度为18。
     */
    public static final String REGEX_ID_CARD18 = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9Xx])$";
    /**
     * 电子邮件的正则表达式
     */
    public static final String REGEX_EMAIL = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
    /**
     * URL的正则表达式
     */
    public static final String REGEX_URL = "[a-zA-z]+://[^\\s]*";
    /**
     * 汉字正则表达式
     */
    public static final String REGEX_ZH = "^[\\u4e00-\\u9fa5]+$";
    /**
     * 用户名的正则表达式
     * <p>由"a-z", "A-Z", "0-9", "_", "中文"组成</p>
     * <p>不能以"_"结尾</p>
     * <p>长度6到20</p>.
     */
    public static final String REGEX_USERNAME = "^[\\w\\u4e00-\\u9fa5]{6,20}(?<!_)$";
    /**
     * 格式为“yyyy-MM-dd”的日期正则表达式
     */
    public static final String REGEX_DATE = "^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)$";
    /**
     * IP地址的正则表达式
     */
    public static final String REGEX_IP = "((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)";

    /**
     * 双字节字符的正则表达式
     */
    public static final String REGEX_DOUBLE_BYTE_CHAR = "[^\\x00-\\xff]";
    /**
     * 空白行的正则表达式
     */
    public static final String REGEX_BLANK_LINE = "\\n\\s*\\r";
    /**
     * QQ号码的正则表达式
     */
    public static final String REGEX_QQ_NUM = "[1-9][0-9]{4,}";
    /**
     * 中国邮政编码法规
     */
    public static final String REGEX_CHINA_POSTAL_CODE = "[1-9]\\d{5}(?!\\d)";
    /**
     * 正整数的正则表达式
     */
    public static final String REGEX_POSITIVE_INTEGER = "^[1-9]\\d*$";
    /**
     * 负整数的正则表达式
     */
    public static final String REGEX_NEGATIVE_INTEGER = "^-[1-9]\\d*$";
    /**
     * 整数的正则表达式
     */
    public static final String REGEX_INTEGER = "^-?[1-9]\\d*$";
    /**
     * 非负整数的正则表达式
     */
    public static final String REGEX_NOT_NEGATIVE_INTEGER = "^[1-9]\\d*|0$";
    /**
     * 非正整数的正则表达式
     */
    public static final String REGEX_NOT_POSITIVE_INTEGER = "^-[1-9]\\d*|0$";
    /**
     * 正浮点数的正则表达式
     */
    public static final String REGEX_POSITIVE_FLOAT = "^[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*$";
    /**
     * 负浮点数的正则表达式
     */
    public static final String REGEX_NEGATIVE_FLOAT = "^-[1-9]\\d*\\.\\d*|-0\\.\\d*[1-9]\\d*$";
}
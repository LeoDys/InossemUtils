package com.inossem_library.other.regex.util;

import androidx.annotation.NonNull;
import androidx.collection.SimpleArrayMap;

import com.inossem_library.exception.constant.ExceptionEnum;
import com.inossem_library.exception.InossemException;
import com.inossem_library.other.regex.constant.RegexConstants;
import com.inossem_library.other.string.util.StringUtils;

import java.util.regex.Pattern;

/**
 * @author 郭晓龙
 * @time on 2019/7/15
 * @email xiaolong.guo@inossem.com
 * @describe 正则表达式封装 判断手机号、身份证、邮箱等
 */

public class RegexUtils {

    //城市对应集合
    private final static SimpleArrayMap<String, String> CITY_MAP = new SimpleArrayMap<>();

    /**
     * 不能被实例化
     */
    private RegexUtils() {
        throw new UnsupportedOperationException("不能被实例化");
    }

    /**
     * 简单验证手机号
     *
     * @param phone 手机号
     * @return {@code true}: 是<br>{@code false}: 不是
     */
    public static boolean isMobileSimple(@NonNull final CharSequence phone) {
        if (StringUtils.isEmpty(phone)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "phone不能为空！");
        }
        return isMatch(RegexConstants.REGEX_MOBILE_SIMPLE, phone);
    }

    /**
     * 精确验证手机号
     *
     * @param phone 手机号
     * @return {@code true}: 是<br>{@code false}: 不是
     */
    public static boolean isMobileExact(@NonNull final CharSequence phone) {
        if (StringUtils.isEmpty(phone)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "phone不能为空！");
        }
        return isMatch(RegexConstants.REGEX_MOBILE_EXACT, phone);
    }

    /**
     * 验证电话号码
     *
     * @param phone 电话号
     * @return {@code true}: 是<br>{@code false}: 不是
     */
    public static boolean isTel(@NonNull final CharSequence phone) {
        if (StringUtils.isEmpty(phone)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "phone不能为空！");
        }
        return isMatch(RegexConstants.REGEX_TEL, phone);
    }

    /**
     * 验证身份证号码 15 位
     *
     * @param idCard15 15位身份证号
     * @return {@code true}: 是<br>{@code false}: 不是
     */
    public static boolean isIDCard15(@NonNull final CharSequence idCard15) {
        if (StringUtils.isEmpty(idCard15)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "idCard15不能为空！");
        }
        return isMatch(RegexConstants.REGEX_ID_CARD15, idCard15);
    }

    /**
     * 简单验证身份证号码 18 位
     *
     * @param idCard18 18位身份证号
     * @return {@code true}: 是<br>{@code false}: 不是
     */
    public static boolean isIDCard18(@NonNull final CharSequence idCard18) {
        if (StringUtils.isEmpty(idCard18)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "idCard18不能为空！");
        }
        return isMatch(RegexConstants.REGEX_ID_CARD18, idCard18);
    }

    /**
     * 精确验证身份证号码 18 位
     *
     * @param idCard18 18位身份证号
     * @return {@code true}: 是<br>{@code false}: 不是
     */
    public static boolean isIDCard18Exact(@NonNull final CharSequence idCard18) {
        if (StringUtils.isEmpty(idCard18)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "idCard18不能为空！");
        }
        if (isIDCard18(idCard18)) {
            int[] factor = new int[]{7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
            char[] suffix = new char[]{'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};
            if (CITY_MAP.isEmpty()) {
                CITY_MAP.put("11", "北京");
                CITY_MAP.put("12", "天津");
                CITY_MAP.put("13", "河北");
                CITY_MAP.put("14", "山西");
                CITY_MAP.put("15", "内蒙古");

                CITY_MAP.put("21", "辽宁");
                CITY_MAP.put("22", "吉林");
                CITY_MAP.put("23", "黑龙江");

                CITY_MAP.put("31", "上海");
                CITY_MAP.put("32", "江苏");
                CITY_MAP.put("33", "浙江");
                CITY_MAP.put("34", "安徽");
                CITY_MAP.put("35", "福建");
                CITY_MAP.put("36", "江西");
                CITY_MAP.put("37", "山东");

                CITY_MAP.put("41", "河南");
                CITY_MAP.put("42", "湖北");
                CITY_MAP.put("43", "湖南");
                CITY_MAP.put("44", "广东");
                CITY_MAP.put("45", "广西");
                CITY_MAP.put("46", "海南");

                CITY_MAP.put("50", "重庆");
                CITY_MAP.put("51", "四川");
                CITY_MAP.put("52", "贵州");
                CITY_MAP.put("53", "云南");
                CITY_MAP.put("54", "西藏");

                CITY_MAP.put("61", "陕西");
                CITY_MAP.put("62", "甘肃");
                CITY_MAP.put("63", "青海");
                CITY_MAP.put("64", "宁夏");
                CITY_MAP.put("65", "新疆");

                CITY_MAP.put("71", "台湾");
                CITY_MAP.put("81", "香港");
                CITY_MAP.put("82", "澳门");
                CITY_MAP.put("91", "国外");
            }
            if (CITY_MAP.get(idCard18.subSequence(0, 2).toString()) != null) {
                int weightSum = 0;
                for (int i = 0; i < 17; ++i) {
                    weightSum += (idCard18.charAt(i) - '0') * factor[i];
                }
                int idCardMod = weightSum % 11;
                char idCardLast = idCard18.charAt(17);
                return idCardLast == suffix[idCardMod];
            }
        }
        return false;
    }

    /**
     * 验证邮箱
     *
     * @param email 邮箱
     * @return {@code true}: 是<br>{@code false}: 不是
     */
    public static boolean isEmail(@NonNull final CharSequence email) {
        if (StringUtils.isEmpty(email)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "email不能为空！");
        }
        return isMatch(RegexConstants.REGEX_EMAIL, email);
    }

    /**
     * 验证 URL
     *
     * @param url URL
     * @return {@code true}: 是<br>{@code false}: 不是
     */
    public static boolean isURL(@NonNull final CharSequence url) {
        if (StringUtils.isEmpty(url)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "url不能为空！");
        }
        return isMatch(RegexConstants.REGEX_URL, url);
    }

    /**
     * 验证汉字
     *
     * @param zh 汉字
     * @return {@code true}: 是<br>{@code false}: 不是
     */
    public static boolean isZh(@NonNull final CharSequence zh) {
        if (StringUtils.isEmpty(zh)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "输入不能为空！");
        }
        return isMatch(RegexConstants.REGEX_ZH, zh);
    }

    /**
     * 验证用户名
     * <p>由"a-z", "A-Z", "0-9", "_", "中文"组成</p>
     * <p>不能以"_"结尾</p>
     * <p>长度6到20</p>.
     *
     * @param username 用户名
     * @return {@code true}: 是<br>{@code false}: 不是
     */
    public static boolean isUsername(@NonNull final CharSequence username) {
        if (StringUtils.isEmpty(username)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "username不能为空！");
        }
        return isMatch(RegexConstants.REGEX_USERNAME, username);
    }

    /**
     * 验证 yyyy-MM-dd 格式的日期校验
     *
     * @param date 日期
     * @return {@code true}: 是<br>{@code false}: 不是
     */
    public static boolean isDate(@NonNull final CharSequence date) {
        if (StringUtils.isEmpty(date)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "date不能为空！");
        }
        return isMatch(RegexConstants.REGEX_DATE, date);
    }

    /**
     * 验证 IP 地址
     *
     * @param ip ip地址
     * @return {@code true}: 是<br>{@code false}: 不是
     */
    public static boolean isIP(@NonNull final CharSequence ip) {
        if (StringUtils.isEmpty(ip)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "ip不能为空！");
        }
        return isMatch(RegexConstants.REGEX_IP, ip);
    }

    /**
     * 判断是否匹配正则
     *
     * @param regex 正则表达式
     * @param input 输入字符串
     * @return {@code true}: 是<br>{@code false}: 不是
     */
    public static boolean isMatch(@NonNull final String regex, @NonNull final CharSequence input) {
        if (StringUtils.isEmpty(regex)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "regex不能为空！");
        }
        if (StringUtils.isEmpty(input)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "输入字符串不能为空！");
        }
        return Pattern.matches(regex, input);
    }
}
package com.inossem_library.other.string.util;

import android.content.Context;
import android.support.annotation.ArrayRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import com.inossem_library.exception.ExceptionEnum;
import com.inossem_library.exception.InossemException;

/**
 * @author 郭晓龙
 * @time on 2019/7/15
 * @email xiaolong.guo@inossem.com
 * @describe 字符串工具类 判断字符串是否为空、字符串反转、转全角、转半角
 */

public class StringUtils {
    /**
     * 不能被实例化
     */
    private StringUtils() {
        throw new UnsupportedOperationException("不能被实例化");
    }

    /**
     * 判断字符串是否为null或长度为0
     *
     * @param string 字符串
     * @return {@code true}: 是<br> {@code false}: 不是
     */
    public static boolean isEmpty(final CharSequence string) {
        return string == null || string.length() == 0;
    }

    /**
     * 判断字符串是否为null或全为空格
     *
     * @param string 字符串
     * @return {@code true}: 是<br> {@code false}: 不是
     */
    public static boolean isSpace(final CharSequence string) {
        if (string == null) {
            return true;
        }
        for (int i = 0, len = string.length(); i < len; ++i) {
            if (!Character.isWhitespace(string.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断两字符串是否相等
     *
     * @param s1 第一个字符串
     * @param s2 第二个字符串
     * @return {@code true}: 是<br> {@code false}: 不是
     */
    public static boolean equals(final CharSequence s1, final CharSequence s2) {
        if (s1 == s2) {
            return true;
        }
        int length;
        if (s1 != null && s2 != null && (length = s1.length()) == s2.length()) {
            if (s1 instanceof String && s2 instanceof String) {
                return s1.equals(s2);
            } else {
                for (int i = 0; i < length; i++) {
                    if (s1.charAt(i) != s2.charAt(i)) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 判断两字符串忽略大小写是否相等
     *
     * @param s1 第一个字符串
     * @param s2 第二个字符串
     * @return {@code true}: 是<br> {@code false}: 不是
     */
    public static boolean equalsIgnoreCase(final String s1, final String s2) {
        return s1 == null ? s2 == null : s1.equalsIgnoreCase(s2);
    }

    /**
     * null转为长度为0的字符串
     *
     * @param string 字符串
     * @return {@code ""} 如果字符串为空返回""
     */
    public static String null2Length0(final String string) {
        return string == null ? "" : string;
    }

    /**
     * 返回字符串长度
     *
     * @param string 字符串
     * @return 字符串长度
     */
    public static int length(@NonNull final CharSequence string) {
        if (StringUtils.isEmpty(string)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "string不能为空！");
        }
        return string == null ? 0 : string.length();
    }

    /**
     * 首字母大写
     *
     * @param string 字符串
     * @return 返回首字母大写字符串
     */
    public static String upperFirstLetter(@NonNull final String string) {
        if (StringUtils.isEmpty(string)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "string不能为空！");
        }
        if (!Character.isLowerCase(string.charAt(0))) {
            return string;
        }
        return String.valueOf((char) (string.charAt(0) - 32)) + string.substring(1);
    }

    /**
     * 首字母小写
     *
     * @param string 字符串
     * @return 返回首字母小写字符串
     */
    public static String lowerFirstLetter(@NonNull final String string) {
        if (StringUtils.isEmpty(string)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "string不能为空！");
        }
        if (!Character.isUpperCase(string.charAt(0))) return string;
        return String.valueOf((char) (string.charAt(0) + 32)) + string.substring(1);
    }

    /**
     * 反转字符串
     *
     * @param string 字符串
     * @return 返回反转字符串
     */
    public static String reverse(@NonNull final String string) {
        if (StringUtils.isEmpty(string)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "string不能为空！");
        }
        int len = string.length();
        int mid = len >> 1;
        char[] chars = string.toCharArray();
        char c;
        for (int i = 0; i < mid; ++i) {
            c = chars[i];
            chars[i] = chars[len - i - 1];
            chars[len - i - 1] = c;
        }
        return new String(chars);
    }

    /**
     * 转化为半角字符串
     *
     * @param string 字符串
     * @return 返回半角字符串
     */
    public static String toDBC(@NonNull final String string) {
        if (StringUtils.isEmpty(string)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "string不能为空！");
        }
        char[] chars = string.toCharArray();
        for (int i = 0, len = chars.length; i < len; i++) {
            if (chars[i] == 12288) {
                chars[i] = ' ';
            } else if (65281 <= chars[i] && chars[i] <= 65374) {
                chars[i] = (char) (chars[i] - 65248);
            } else {
                chars[i] = chars[i];
            }
        }
        return new String(chars);
    }

    /**
     * 转化为全角字符串
     *
     * @param string 字符串
     * @return 返回全角字符串
     */
    public static String toSBC(@NonNull final String string) {
        if (StringUtils.isEmpty(string)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "string不能为空！");
        }
        char[] chars = string.toCharArray();
        for (int i = 0, len = chars.length; i < len; i++) {
            if (chars[i] == ' ') {
                chars[i] = (char) 12288;
            } else if (33 <= chars[i] && chars[i] <= 126) {
                chars[i] = (char) (chars[i] + 65248);
            } else {
                chars[i] = chars[i];
            }
        }
        return new String(chars);
    }

    /**
     * 通过资源ID获取字符串
     *
     * @param context 上下文
     * @param id      字符串资源ID
     * @return 返回资源ID对应的字符串
     */
    public static String getString(@NonNull Context context, @StringRes int id) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context不能为空！");
        }
        return context.getResources().getString(id);
    }

    /**
     * 通过资源ID获取字符串集合
     *
     * @param context 上下文
     * @param id      字符串资源ID
     * @return 返回资源ID对应的字符串数组
     */
    public static String[] getStringArray(@NonNull Context context, @ArrayRes int id) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context不能为空！");
        }
        return context.getResources().getStringArray(id);
    }
}
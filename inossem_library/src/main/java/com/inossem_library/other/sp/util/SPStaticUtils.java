package com.inossem_library.other.sp.util;

import android.content.Context;
import android.support.annotation.NonNull;

import com.inossem_library.exception.ExceptionEnum;
import com.inossem_library.exception.InossemException;
import com.inossem_library.other.string.util.StringUtils;

import java.util.Map;
import java.util.Set;

/**
 * @author 郭晓龙
 * @time on 2019/7/15
 * @email xiaolong.guo@inossem.com
 * @describe SP静态类封装 SP存取数据操作
 */
public class SPStaticUtils {

    //SPUtils默认实例
    private static SPUtils sDefaultSPUtils;

    /**
     * 设置SPUtils默认实例
     *
     * @param spUtils SPUtils实例
     */
    public static void setDefaultSPUtils(final SPUtils spUtils) {
        if (spUtils == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "spUtils不能为空！");
        }
        sDefaultSPUtils = spUtils;
    }

    /**
     * SP中写入字符串
     *
     * @param context 上下文
     * @param key     sp钥匙
     * @param value   sp值
     */
    public static void put(@NonNull Context context, @NonNull final String key, final String value) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context不能为空！");
        }
        if (StringUtils.isEmpty(key)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "key不能为空！");
        }
        put(key, value, getDefaultSPUtils(context));
    }

    /**
     * SP中写入字符串
     *
     * @param context  上下文
     * @param key      sp钥匙
     * @param value    sp值
     * @param isCommit true提交方式为commit，false提交方式为apply
     */
    public static void put(@NonNull Context context, @NonNull final String key, final String value, final boolean isCommit) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context不能为空！");
        }
        if (StringUtils.isEmpty(key)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "key不能为空！");
        }
        put(key, value, isCommit, getDefaultSPUtils(context));
    }


    /**
     * SP中获取字符串
     *
     * @param context 上下文
     * @param key     sp钥匙
     * @return 钥匙对应的值 如果不存在为""
     */
    public static String getString(@NonNull Context context, @NonNull final String key) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context不能为空！");
        }
        if (StringUtils.isEmpty(key)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "key不能为空！");
        }
        return getString(key, getDefaultSPUtils(context));
    }

    /**
     * SP中获取字符串
     *
     * @param context      上下文
     * @param key          sp钥匙
     * @param defaultValue 默认值
     * @return 钥匙对应的值 如果不存在为默认值
     */
    public static String getString(@NonNull Context context, @NonNull final String key, final String defaultValue) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context不能为空！");
        }
        if (StringUtils.isEmpty(key)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "key不能为空！");
        }
        return getString(key, defaultValue, getDefaultSPUtils(context));
    }


    /**
     * SP中写入整型
     *
     * @param context 上下文
     * @param key     sp钥匙
     * @param value   sp值
     */
    public static void put(@NonNull Context context, @NonNull final String key, final int value) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context不能为空！");
        }
        if (StringUtils.isEmpty(key)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "key不能为空！");
        }
        put(key, value, getDefaultSPUtils(context));
    }

    /**
     * SP中写入整型
     *
     * @param context  上下文
     * @param key      sp钥匙
     * @param value    sp值
     * @param isCommit true提交方式为commit，false提交方式为apply
     */
    public static void put(@NonNull Context context, @NonNull final String key, final int value, final boolean isCommit) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context不能为空！");
        }
        if (StringUtils.isEmpty(key)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "key不能为空！");
        }
        put(key, value, isCommit, getDefaultSPUtils(context));
    }

    /**
     * SP中获取整型
     *
     * @param context 上下文
     * @param key     sp钥匙
     * @return 钥匙对应的值 如果不存在为-1
     */
    public static int getInt(@NonNull Context context, @NonNull final String key) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context不能为空！");
        }
        if (StringUtils.isEmpty(key)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "key不能为空！");
        }
        return getInt(key, getDefaultSPUtils(context));
    }

    /**
     * SP中获取整型
     *
     * @param context      上下文
     * @param key          sp钥匙
     * @param defaultValue 默认值
     * @return 钥匙对应的值 如果不存在为默认值
     */
    public static int getInt(@NonNull Context context, @NonNull final String key, final int defaultValue) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context不能为空！");
        }
        if (StringUtils.isEmpty(key)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "key不能为空！");
        }
        return getInt(key, defaultValue, getDefaultSPUtils(context));
    }

    /**
     * SP中写入长整型
     *
     * @param context 上下文
     * @param key     sp钥匙
     * @param value   sp值
     */
    public static void put(@NonNull Context context, @NonNull final String key, final long value) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context不能为空！");
        }
        if (StringUtils.isEmpty(key)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "key不能为空！");
        }
        put(key, value, getDefaultSPUtils(context));
    }

    /**
     * SP中写入长整型
     *
     * @param context  上下文
     * @param key      sp钥匙
     * @param value    sp值
     * @param isCommit true提交方式为commit，false提交方式为apply
     */
    public static void put(@NonNull Context context, @NonNull final String key, final long value, final boolean isCommit) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context不能为空！");
        }
        if (StringUtils.isEmpty(key)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "key不能为空！");
        }
        put(key, value, isCommit, getDefaultSPUtils(context));
    }

    /**
     * SP中获取长整型
     *
     * @param context 上下文
     * @param key     sp钥匙
     * @return 钥匙对应的值 如果不存在为-1
     */
    public static long getLong(@NonNull Context context, @NonNull final String key) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context不能为空！");
        }
        if (StringUtils.isEmpty(key)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "key不能为空！");
        }
        return getLong(key, getDefaultSPUtils(context));
    }

    /**
     * SP中获取长整型
     *
     * @param context      上下文
     * @param key          sp钥匙
     * @param defaultValue 默认值
     * @return 钥匙对应的值 如果不存在为默认值
     */
    public static long getLong(@NonNull Context context, @NonNull final String key, final long defaultValue) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context不能为空！");
        }
        if (StringUtils.isEmpty(key)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "key不能为空！");
        }
        return getLong(key, defaultValue, getDefaultSPUtils(context));
    }

    /**
     * SP中写入浮点型
     *
     * @param context 上下文
     * @param key     sp钥匙
     * @param value   sp值
     */
    public static void put(@NonNull Context context, @NonNull final String key, final float value) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context不能为空！");
        }
        if (StringUtils.isEmpty(key)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "key不能为空！");
        }
        put(key, value, getDefaultSPUtils(context));
    }

    /**
     * SP中写入浮点型
     *
     * @param context  上下文
     * @param key      sp钥匙
     * @param value    sp值
     * @param isCommit true提交方式为commit，false提交方式为apply
     */
    public static void put(@NonNull Context context, @NonNull final String key, final float value, final boolean isCommit) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context不能为空！");
        }
        if (StringUtils.isEmpty(key)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "key不能为空！");
        }
        put(key, value, isCommit, getDefaultSPUtils(context));
    }

    /**
     * SP中获取浮点型
     *
     * @param context 上下文
     * @param key     sp钥匙
     * @return 钥匙对应的值 如果不存在为-1
     */
    public static float getFloat(@NonNull Context context, @NonNull final String key) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context不能为空！");
        }
        if (StringUtils.isEmpty(key)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "key不能为空！");
        }
        return getFloat(key, getDefaultSPUtils(context));
    }

    /**
     * SP中获取浮点型
     *
     * @param context      上下文
     * @param key          sp钥匙
     * @param defaultValue 默认值
     * @return 钥匙对应的值 如果不存在为默认值
     */
    public static float getFloat(@NonNull Context context, @NonNull final String key, final float defaultValue) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context不能为空！");
        }
        if (StringUtils.isEmpty(key)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "key不能为空！");
        }
        return getFloat(key, defaultValue, getDefaultSPUtils(context));
    }

    /**
     * SP中写入布尔型
     *
     * @param context 上下文
     * @param key     sp钥匙
     * @param value   sp值
     */
    public static void put(@NonNull Context context, @NonNull final String key, final boolean value) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context不能为空！");
        }
        if (StringUtils.isEmpty(key)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "key不能为空！");
        }
        put(key, value, getDefaultSPUtils(context));
    }

    /**
     * SP中写入布尔型
     *
     * @param context  上下文
     * @param key      sp钥匙
     * @param value    sp值
     * @param isCommit true提交方式为commit，false提交方式为apply
     */
    public static void put(@NonNull Context context, @NonNull final String key, final boolean value, final boolean isCommit) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context不能为空！");
        }
        if (StringUtils.isEmpty(key)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "key不能为空！");
        }
        put(key, value, isCommit, getDefaultSPUtils(context));
    }

    /**
     * SP中读取布尔型
     *
     * @param context 上下文
     * @param key     sp钥匙
     * @return 钥匙对应的值 如果不存在为false
     */
    public static boolean getBoolean(@NonNull Context context, @NonNull final String key) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context不能为空！");
        }
        if (StringUtils.isEmpty(key)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "key不能为空！");
        }
        return getBoolean(key, getDefaultSPUtils(context));
    }

    /**
     * SP中读取布尔型
     *
     * @param context      上下文
     * @param key          sp钥匙
     * @param defaultValue 默认值
     * @return 钥匙对应的值 如果不存在为默认值
     */
    public static boolean getBoolean(@NonNull Context context, @NonNull final String key, final boolean defaultValue) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context不能为空！");
        }
        if (StringUtils.isEmpty(key)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "key不能为空！");
        }
        return getBoolean(key, defaultValue, getDefaultSPUtils(context));
    }

    /**
     * SP中写入字符串集合
     *
     * @param context 上下文
     * @param key     sp钥匙
     * @param value   sp值
     */
    public static void put(@NonNull Context context, @NonNull final String key, final Set<String> value) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context不能为空！");
        }
        if (StringUtils.isEmpty(key)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "key不能为空！");
        }
        put(key, value, getDefaultSPUtils(context));
    }

    /**
     * SP中写入字符串集合
     *
     * @param context  上下文
     * @param key      sp钥匙
     * @param value    sp值
     * @param isCommit true提交方式为commit，false提交方式为apply
     */
    public static void put(@NonNull Context context, @NonNull final String key,
                           final Set<String> value,
                           final boolean isCommit) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context不能为空！");
        }
        if (StringUtils.isEmpty(key)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "key不能为空！");
        }
        put(key, value, isCommit, getDefaultSPUtils(context));
    }

    /**
     * SP中读取字符串集合
     *
     * @param context 上下文
     * @param key     sp钥匙
     * @return 钥匙对应的值 如果不存在为空集合
     */
    public static Set<String> getStringSet(@NonNull Context context, @NonNull final String key) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context不能为空！");
        }
        if (StringUtils.isEmpty(key)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "key不能为空！");
        }
        return getStringSet(key, getDefaultSPUtils(context));
    }

    /**
     * SP中读取字符串集合
     *
     * @param context      上下文
     * @param key          sp钥匙
     * @param defaultValue 默认值
     * @return 钥匙对应的值 如果不存在为默认值
     */
    public static Set<String> getStringSet(@NonNull Context context, @NonNull final String key,
                                           final Set<String> defaultValue) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context不能为空！");
        }
        if (StringUtils.isEmpty(key)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "key不能为空！");
        }
        return getStringSet(key, defaultValue, getDefaultSPUtils(context));
    }

    /**
     * SP中读取所有数据
     *
     * @param context 上下文
     * @return 所有值
     */
    public static Map<String, ?> getAll(@NonNull Context context) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context不能为空！");
        }
        return getAll(getDefaultSPUtils(context));
    }

    /**
     * SP中是否存在该key
     *
     * @param context 上下文
     * @param key     sp钥匙
     * @return {@code true}: 是<br>{@code false}: 不是
     */
    public static boolean contains(@NonNull Context context, @NonNull final String key) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context不能为空！");
        }
        if (StringUtils.isEmpty(key)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "key不能为空！");
        }
        return contains(key, getDefaultSPUtils(context));
    }

    /**
     * SP中移除该key
     *
     * @param context 上下文
     * @param key     sp钥匙
     */
    public static void remove(@NonNull Context context, @NonNull final String key) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context不能为空！");
        }
        if (StringUtils.isEmpty(key)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "key不能为空！");
        }
        remove(key, getDefaultSPUtils(context));
    }

    /**
     * SP中移除该key
     *
     * @param context  上下文
     * @param key      sp钥匙
     * @param isCommit true提交方式为commit，false提交方式为apply
     */
    public static void remove(@NonNull Context context, @NonNull final String key, final boolean isCommit) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context不能为空！");
        }
        if (StringUtils.isEmpty(key)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "key不能为空！");
        }
        remove(key, isCommit, getDefaultSPUtils(context));
    }

    /**
     * SP中清除所有数据
     *
     * @param context 上下文
     */
    public static void clear(@NonNull Context context) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context不能为空！");
        }
        clear(getDefaultSPUtils(context));
    }

    /**
     * SP中清除所有数据
     *
     * @param context  上下文
     * @param isCommit true提交方式为commit，false提交方式为apply
     */
    public static void clear(@NonNull Context context, final boolean isCommit) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context不能为空！");
        }
        clear(isCommit, getDefaultSPUtils(context));
    }

    ///////////////////////////////////////////////////////////////////////////
    // 上面使用默认SPUtils
    // 分割线
    // 下面需要传入SPUtils实例
    ///////////////////////////////////////////////////////////////////////////

    /**
     * SP中写入字符串
     *
     * @param key     sp钥匙
     * @param value   sp值
     * @param spUtils SPUtils实例
     */
    public static void put(@NonNull final String key, final String value, @NonNull final SPUtils spUtils) {
        if (StringUtils.isEmpty(key)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "key不能为空！");
        }
        if (spUtils == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "spUtils不能为空！");
        }
        spUtils.put(key, value);
    }

    /**
     * SP中写入字符串
     *
     * @param key      sp钥匙
     * @param value    sp值
     * @param isCommit true提交方式为commit，false提交方式为apply
     * @param spUtils  SPUtils实例
     */
    public static void put(@NonNull final String key,
                           final String value,
                           final boolean isCommit,
                           @NonNull final SPUtils spUtils) {
        if (StringUtils.isEmpty(key)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "key不能为空！");
        }
        if (spUtils == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "spUtils不能为空！");
        }
        spUtils.put(key, value, isCommit);
    }


    /**
     * SP中获取字符串
     *
     * @param key     sp钥匙
     * @param spUtils SPUtils实例
     * @return 钥匙对应的值 如果不存在为""
     */
    public static String getString(@NonNull final String key, @NonNull final SPUtils spUtils) {
        if (StringUtils.isEmpty(key)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "key不能为空！");
        }
        if (spUtils == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "spUtils不能为空！");
        }
        return spUtils.getString(key);
    }

    /**
     * SP中获取字符串
     *
     * @param key          sp钥匙
     * @param defaultValue 默认值
     * @param spUtils      SPUtils实例
     * @return 钥匙对应的值 如果不存在为默认值
     */
    public static String getString(@NonNull final String key,
                                   final String defaultValue,
                                   @NonNull final SPUtils spUtils) {
        if (StringUtils.isEmpty(key)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "key不能为空！");
        }
        if (spUtils == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "spUtils不能为空！");
        }
        return spUtils.getString(key, defaultValue);
    }


    /**
     * SP中写入整型
     *
     * @param key     sp钥匙
     * @param value   sp值
     * @param spUtils SPUtils实例
     */
    public static void put(@NonNull final String key, final int value, @NonNull final SPUtils spUtils) {
        if (StringUtils.isEmpty(key)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "key不能为空！");
        }
        if (spUtils == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "spUtils不能为空！");
        }
        spUtils.put(key, value);
    }

    /**
     * SP中写入整型
     *
     * @param key      sp钥匙
     * @param value    sp值
     * @param isCommit true提交方式为commit，false提交方式为apply
     * @param spUtils  SPUtils实例
     */
    public static void put(@NonNull final String key,
                           final int value,
                           final boolean isCommit,
                           @NonNull final SPUtils spUtils) {
        if (StringUtils.isEmpty(key)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "key不能为空！");
        }
        if (spUtils == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "spUtils不能为空！");
        }
        spUtils.put(key, value, isCommit);
    }

    /**
     * SP中获取整型
     *
     * @param key     sp钥匙
     * @param spUtils SPUtils实例
     * @return 钥匙对应的值 如果不存在为-1
     */
    public static int getInt(@NonNull final String key, @NonNull final SPUtils spUtils) {
        if (StringUtils.isEmpty(key)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "key不能为空！");
        }
        if (spUtils == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "spUtils不能为空！");
        }
        return spUtils.getInt(key);
    }

    /**
     * SP中获取整型
     *
     * @param key          sp钥匙
     * @param defaultValue 默认值
     * @param spUtils      SPUtils实例
     * @return 钥匙对应的值 如果不存在为默认值
     */
    public static int getInt(@NonNull final String key, final int defaultValue, @NonNull final SPUtils spUtils) {
        if (StringUtils.isEmpty(key)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "key不能为空！");
        }
        if (spUtils == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "spUtils不能为空！");
        }
        return spUtils.getInt(key, defaultValue);
    }

    /**
     * SP中写入长整型
     *
     * @param key     sp钥匙
     * @param value   sp值
     * @param spUtils SPUtils实例
     */
    public static void put(@NonNull final String key, final long value, @NonNull final SPUtils spUtils) {
        if (StringUtils.isEmpty(key)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "key不能为空！");
        }
        if (spUtils == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "spUtils不能为空！");
        }
        spUtils.put(key, value);
    }

    /**
     * SP中写入长整型
     *
     * @param key      sp钥匙
     * @param value    sp值
     * @param isCommit true提交方式为commit，false提交方式为apply
     * @param spUtils  SPUtils实例
     */
    public static void put(@NonNull final String key,
                           final long value,
                           final boolean isCommit,
                           @NonNull final SPUtils spUtils) {
        if (StringUtils.isEmpty(key)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "key不能为空！");
        }
        if (spUtils == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "spUtils不能为空！");
        }
        spUtils.put(key, value, isCommit);
    }

    /**
     * SP中获取长整型
     *
     * @param key     sp钥匙
     * @param spUtils SPUtils实例
     * @return 钥匙对应的值 如果不存在为默认值-1
     */
    public static long getLong(@NonNull final String key, @NonNull final SPUtils spUtils) {
        if (StringUtils.isEmpty(key)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "key不能为空！");
        }
        if (spUtils == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "spUtils不能为空！");
        }
        return spUtils.getLong(key);
    }

    /**
     * SP中获取长整型
     *
     * @param key          sp钥匙
     * @param defaultValue 默认值
     * @param spUtils      SPUtils实例
     * @return 钥匙对应的值 如果不存在为默认值
     */
    public static long getLong(@NonNull final String key, final long defaultValue, @NonNull final SPUtils spUtils) {
        if (StringUtils.isEmpty(key)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "key不能为空！");
        }
        if (spUtils == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "spUtils不能为空！");
        }
        return spUtils.getLong(key, defaultValue);
    }

    /**
     * SP中写入浮点型
     *
     * @param key     sp钥匙
     * @param value   sp值
     * @param spUtils SPUtils实例
     */
    public static void put(@NonNull final String key, final float value, @NonNull final SPUtils spUtils) {
        if (StringUtils.isEmpty(key)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "key不能为空！");
        }
        if (spUtils == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "spUtils不能为空！");
        }
        spUtils.put(key, value);
    }

    /**
     * SP中写入浮点型
     *
     * @param key      sp钥匙
     * @param value    sp值
     * @param isCommit true提交方式为commit，false提交方式为apply
     * @param spUtils  SPUtils实例
     */
    public static void put(@NonNull final String key,
                           final float value,
                           final boolean isCommit,
                           @NonNull final SPUtils spUtils) {
        if (StringUtils.isEmpty(key)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "key不能为空！");
        }
        if (spUtils == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "spUtils不能为空！");
        }
        spUtils.put(key, value, isCommit);
    }

    /**
     * SP中获取浮点型
     *
     * @param key     sp钥匙
     * @param spUtils SPUtils实例
     * @return 钥匙对应的值 如果不存在为默认值-1
     */
    public static float getFloat(@NonNull final String key, @NonNull final SPUtils spUtils) {
        if (StringUtils.isEmpty(key)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "key不能为空！");
        }
        if (spUtils == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "spUtils不能为空！");
        }
        return spUtils.getFloat(key);
    }

    /**
     * SP中获取浮点型
     *
     * @param key          sp钥匙
     * @param defaultValue 默认值
     * @param spUtils      SPUtils实例
     * @return 钥匙对应的值 如果不存在为默认值
     */
    public static float getFloat(@NonNull final String key, final float defaultValue, @NonNull final SPUtils spUtils) {
        if (StringUtils.isEmpty(key)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "key不能为空！");
        }
        if (spUtils == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "spUtils不能为空！");
        }
        return spUtils.getFloat(key, defaultValue);
    }

    /**
     * SP中写入布尔型
     *
     * @param key     sp钥匙
     * @param value   sp值
     * @param spUtils SPUtils实例
     */
    public static void put(@NonNull final String key, final boolean value, @NonNull final SPUtils spUtils) {
        if (StringUtils.isEmpty(key)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "key不能为空！");
        }
        if (spUtils == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "spUtils不能为空！");
        }
        spUtils.put(key, value);
    }

    /**
     * SP中写入布尔型
     *
     * @param key      sp钥匙
     * @param value    sp值
     * @param isCommit true提交方式为commit，false提交方式为apply
     * @param spUtils  SPUtils实例
     */
    public static void put(@NonNull final String key,
                           final boolean value,
                           final boolean isCommit,
                           @NonNull final SPUtils spUtils) {
        if (StringUtils.isEmpty(key)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "key不能为空！");
        }
        if (spUtils == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "spUtils不能为空！");
        }
        spUtils.put(key, value, isCommit);
    }

    /**
     * SP中读取布尔型
     *
     * @param key     sp钥匙
     * @param spUtils SPUtils实例
     * @return 钥匙对应的值 如果不存在为false
     */
    public static boolean getBoolean(@NonNull final String key, @NonNull final SPUtils spUtils) {
        if (StringUtils.isEmpty(key)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "key不能为空！");
        }
        if (spUtils == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "spUtils不能为空！");
        }
        return spUtils.getBoolean(key);
    }

    /**
     * SP中读取布尔型
     *
     * @param key          sp钥匙
     * @param defaultValue 默认值
     * @param spUtils      SPUtils实例
     * @return 钥匙对应的值 如果不存在为默认值
     */
    public static boolean getBoolean(@NonNull final String key,
                                     final boolean defaultValue,
                                     @NonNull final SPUtils spUtils) {
        if (StringUtils.isEmpty(key)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "key不能为空！");
        }
        if (spUtils == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "spUtils不能为空！");
        }
        return spUtils.getBoolean(key, defaultValue);
    }

    /**
     * SP中写入字符串集合
     *
     * @param key     sp钥匙
     * @param value   sp值
     * @param spUtils SPUtils实例
     */
    public static void put(@NonNull final String key, final Set<String> value, @NonNull final SPUtils spUtils) {
        if (StringUtils.isEmpty(key)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "key不能为空！");
        }
        if (spUtils == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "spUtils不能为空！");
        }
        spUtils.put(key, value);
    }

    /**
     * SP中写入字符串集合
     *
     * @param key      sp钥匙
     * @param value    sp值
     * @param isCommit true提交方式为commit，false提交方式为apply
     * @param spUtils  SPUtils实例
     */
    public static void put(@NonNull final String key,
                           final Set<String> value,
                           final boolean isCommit,
                           @NonNull final SPUtils spUtils) {
        if (StringUtils.isEmpty(key)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "key不能为空！");
        }
        if (spUtils == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "spUtils不能为空！");
        }
        spUtils.put(key, value, isCommit);
    }

    /**
     * SP中读取字符串集合
     *
     * @param key     sp钥匙
     * @param spUtils SPUtils实例
     * @return 钥匙对应的值 如果不存在为空集合
     */
    public static Set<String> getStringSet(@NonNull final String key, @NonNull final SPUtils spUtils) {
        if (StringUtils.isEmpty(key)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "key不能为空！");
        }
        if (spUtils == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "spUtils不能为空！");
        }
        return spUtils.getStringSet(key);
    }

    /**
     * SP中读取字符串集合
     *
     * @param key          sp钥匙
     * @param defaultValue 默认值
     * @param spUtils      SPUtils实例
     * @return 钥匙对应的值 如果不存在为默认值
     */
    public static Set<String> getStringSet(@NonNull final String key,
                                           final Set<String> defaultValue,
                                           @NonNull final SPUtils spUtils) {
        if (StringUtils.isEmpty(key)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "key不能为空！");
        }
        if (spUtils == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "spUtils不能为空！");
        }
        return spUtils.getStringSet(key, defaultValue);
    }

    /**
     * SP中读取所有数据
     *
     * @param spUtils SPUtils实例
     * @return sp所有值
     */
    public static Map<String, ?> getAll(@NonNull final SPUtils spUtils) {
        if (spUtils == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "spUtils不能为空！");
        }
        return spUtils.getAll();
    }

    /**
     * SP中是否存在该key
     *
     * @param key     sp钥匙
     * @param spUtils SPUtils实例
     * @return {@code true}: 是<br>{@code false}: 不是
     */
    public static boolean contains(@NonNull final String key, @NonNull final SPUtils spUtils) {
        if (StringUtils.isEmpty(key)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "key不能为空！");
        }
        if (spUtils == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "spUtils不能为空！");
        }
        return spUtils.contains(key);
    }

    /**
     * SP中移除该key
     *
     * @param key     sp钥匙
     * @param spUtils SPUtils实例
     */
    public static void remove(@NonNull final String key, @NonNull final SPUtils spUtils) {
        if (StringUtils.isEmpty(key)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "key不能为空！");
        }
        if (spUtils == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "spUtils不能为空！");
        }
        spUtils.remove(key);
    }

    /**
     * SP中移除该key
     *
     * @param key      sp钥匙
     * @param isCommit true提交方式为commit，false提交方式为apply
     * @param spUtils  SPUtils实例
     */
    public static void remove(@NonNull final String key, final boolean isCommit, @NonNull final SPUtils spUtils) {
        if (StringUtils.isEmpty(key)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "key不能为空！");
        }
        if (spUtils == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "spUtils不能为空！");
        }
        spUtils.remove(key, isCommit);
    }

    /**
     * SP中清除所有数据
     *
     * @param spUtils SPUtils实例
     */
    public static void clear(@NonNull final SPUtils spUtils) {
        if (spUtils == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "spUtils不能为空！");
        }
        spUtils.clear();
    }

    /**
     * SP中清除所有数据
     *
     * @param isCommit true提交方式为commit，false提交方式为apply
     * @param spUtils  SPUtils实例
     */
    public static void clear(final boolean isCommit, @NonNull final SPUtils spUtils) {
        if (spUtils == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "spUtils不能为空！");
        }
        spUtils.clear(isCommit);
    }

    /**
     * 获取默认SPUtils实例
     *
     * @param context 上下文
     * @return SPUtils实例
     */
    private static SPUtils getDefaultSPUtils(@NonNull Context context) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context不能为空！");
        }
        return sDefaultSPUtils != null ? sDefaultSPUtils : SPUtils.getInstance(context);
    }
}
package com.inossem_library.other.sp.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;

import com.inossem_library.exception.constant.ExceptionEnum;
import com.inossem_library.exception.InossemException;
import com.inossem_library.other.sp.constant.SPConstants;
import com.inossem_library.other.string.util.StringUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author 郭晓龙
 * @time on 2019/7/15
 * @email xiaolong.guo@inossem.com
 * @describe SP封装 SP存取数据操作https://www.cnblogs.com/wushanmanong/p/6273027.html
 */
@SuppressLint("ApplySharedPref")
public final class SPUtils {

    //SPUtils实例集合
    private static final Map<String, SPUtils> SP_UTILS_MAP = new HashMap<>();
    //SharedPreferences对象
    private SharedPreferences sp;

    /**
     * 获取SPUtils实例
     *
     * @param context 上下文
     * @return SPUtils实例
     */
    public static SPUtils getInstance(@NonNull Context context) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context不能为空！");
        }
        return getInstance(context, "", Context.MODE_PRIVATE);
    }

    /**
     * 获取SPUtils实例
     *
     * @param context 上下文
     * @param mode    sp模式
     * @return SPUtils实例
     */
    public static SPUtils getInstance(@NonNull Context context, final int mode) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context不能为空！");
        }
        return getInstance(context, "", mode);
    }

    /**
     * 获取SPUtils实例
     *
     * @param context 上下文
     * @param spName  sp名字
     * @return SPUtils实例
     */
    public static SPUtils getInstance(@NonNull Context context, @NonNull String spName) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context不能为空！");
        }
        if (StringUtils.isEmpty(spName)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "spName不能为空！");
        }
        return getInstance(context, spName, Context.MODE_PRIVATE);
    }

    /**
     * 获取SPUtils实例
     *
     * @param context 上下文
     * @param spName  sp名字
     * @param mode    sp模式
     * @return SPUtils实例
     */
    public static SPUtils getInstance(@NonNull Context context, @NonNull String spName, final int mode) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context不能为空！");
        }
        if (StringUtils.isEmpty(spName)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "spName不能为空！");
        }
        if (StringUtils.isSpace(spName)) {
            spName = SPConstants.DEFUALT_NAME;
        }
        SPUtils spUtils = SP_UTILS_MAP.get(spName);
        if (spUtils == null) {
            synchronized (SPUtils.class) {
                spUtils = SP_UTILS_MAP.get(spName);
                if (spUtils == null) {
                    spUtils = new SPUtils(context, spName, mode);
                    SP_UTILS_MAP.put(spName, spUtils);
                }
            }
        }
        return spUtils;
    }

    /**
     * 构造方法
     *
     * @param context 上下文
     * @param spName  sp名字
     */
    public SPUtils(@NonNull Context context, @NonNull final String spName) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context不能为空！");
        }
        if (StringUtils.isEmpty(spName)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "spName不能为空！");
        }
        sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
    }

    /**
     * 构造方法
     *
     * @param context 上下文
     * @param spName  sp名字
     * @param mode    sp模式
     */
    public SPUtils(@NonNull Context context, @NonNull final String spName, final int mode) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context不能为空！");
        }
        if (StringUtils.isEmpty(spName)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "spName不能为空！");
        }
        sp = context.getSharedPreferences(spName, mode);
    }

    /**
     * SP中写入字符串
     *
     * @param key   sp钥匙
     * @param value sp值
     */
    public void put(@NonNull final String key, final String value) {
        if (StringUtils.isEmpty(key)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "key不能为空！");
        }
        put(key, value, false);
    }

    /**
     * SP中写入字符串
     *
     * @param key      sp钥匙
     * @param value    sp值
     * @param isCommit true提交方式为commit，false提交方式为apply
     */
    public void put(@NonNull final String key, final String value, final boolean isCommit) {
        if (StringUtils.isEmpty(key)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "key不能为空！");
        }
        if (isCommit) {
            sp.edit().putString(key, value).commit();
        } else {
            sp.edit().putString(key, value).apply();
        }
    }

    /**
     * SP中获取字符串
     *
     * @param key sp钥匙
     * @return 钥匙对应的值 如果不存在为""
     */
    public String getString(@NonNull final String key) {
        if (StringUtils.isEmpty(key)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "key不能为空！");
        }
        return getString(key, "");
    }

    /**
     * SP中获取字符串
     *
     * @param key          sp钥匙
     * @param defaultValue 默认值
     * @return 钥匙对应的值 如果不存在为默认值
     */
    public String getString(@NonNull final String key, final String defaultValue) {
        if (StringUtils.isEmpty(key)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "key不能为空！");
        }
        return sp.getString(key, defaultValue);
    }

    /**
     * SP中写入整型
     *
     * @param key   sp钥匙
     * @param value sp值
     */
    public void put(@NonNull final String key, final int value) {
        if (StringUtils.isEmpty(key)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "key不能为空！");
        }
        put(key, value, false);
    }

    /**
     * SP中写入整型
     *
     * @param key      sp钥匙
     * @param value    sp值
     * @param isCommit true提交方式为commit，false提交方式为apply
     */
    public void put(@NonNull final String key, final int value, final boolean isCommit) {
        if (StringUtils.isEmpty(key)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "key不能为空！");
        }
        if (isCommit) {
            sp.edit().putInt(key, value).commit();
        } else {
            sp.edit().putInt(key, value).apply();
        }
    }

    /**
     * SP中获取整型
     *
     * @param key sp钥匙
     * @return 钥匙对应的值 如果不存在为-1
     */
    public int getInt(@NonNull final String key) {
        if (StringUtils.isEmpty(key)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "key不能为空！");
        }
        return getInt(key, -1);
    }

    /**
     * SP中获取整型
     *
     * @param key          sp钥匙
     * @param defaultValue 默认值
     * @return 钥匙对应的值 如果不存在为默认值
     */
    public int getInt(@NonNull final String key, final int defaultValue) {
        if (StringUtils.isEmpty(key)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "key不能为空！");
        }
        return sp.getInt(key, defaultValue);
    }

    /**
     * SP中写入长整型
     *
     * @param key   sp钥匙
     * @param value sp值
     */
    public void put(@NonNull final String key, final long value) {
        if (StringUtils.isEmpty(key)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "key不能为空！");
        }
        put(key, value, false);
    }

    /**
     * SP中写入长整型
     *
     * @param key      sp钥匙
     * @param value    sp值
     * @param isCommit true提交方式为commit，false提交方式为apply
     */
    public void put(@NonNull final String key, final long value, final boolean isCommit) {
        if (StringUtils.isEmpty(key)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "key不能为空！");
        }
        if (isCommit) {
            sp.edit().putLong(key, value).commit();
        } else {
            sp.edit().putLong(key, value).apply();
        }
    }

    /**
     * SP中获取长整型
     *
     * @param key sp钥匙
     * @return 钥匙对应的值 如果不存在为-1
     */
    public long getLong(@NonNull final String key) {
        if (StringUtils.isEmpty(key)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "key不能为空！");
        }
        return getLong(key, -1L);
    }

    /**
     * SP中获取长整型
     *
     * @param key          sp钥匙
     * @param defaultValue 默认值
     * @return 钥匙对应的值 如果不存在为默认值
     */
    public long getLong(@NonNull final String key, final long defaultValue) {
        if (StringUtils.isEmpty(key)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "key不能为空！");
        }
        return sp.getLong(key, defaultValue);
    }

    /**
     * SP中写入浮点型
     *
     * @param key   sp钥匙
     * @param value sp值
     */
    public void put(@NonNull final String key, final float value) {
        if (StringUtils.isEmpty(key)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "key不能为空！");
        }
        put(key, value, false);
    }

    /**
     * SP中写入浮点型
     *
     * @param key      sp钥匙
     * @param value    sp值
     * @param isCommit true提交方式为commit，false提交方式为apply
     */
    public void put(@NonNull final String key, final float value, final boolean isCommit) {
        if (StringUtils.isEmpty(key)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "key不能为空！");
        }
        if (isCommit) {
            sp.edit().putFloat(key, value).commit();
        } else {
            sp.edit().putFloat(key, value).apply();
        }
    }

    /**
     * SP中获取浮点型
     *
     * @param key sp钥匙
     * @return 钥匙对应的值 如果不存在为-1
     */
    public float getFloat(@NonNull final String key) {
        if (StringUtils.isEmpty(key)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "key不能为空！");
        }
        return getFloat(key, -1f);
    }

    /**
     * SP中获取浮点型
     *
     * @param key          sp钥匙
     * @param defaultValue 默认值
     * @return 钥匙对应的值 如果不存在为默认值
     */
    public float getFloat(@NonNull final String key, final float defaultValue) {
        if (StringUtils.isEmpty(key)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "key不能为空！");
        }
        return sp.getFloat(key, defaultValue);
    }

    /**
     * SP中写入布尔型
     *
     * @param key   sp钥匙
     * @param value sp值
     */
    public void put(@NonNull final String key, final boolean value) {
        if (StringUtils.isEmpty(key)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "key不能为空！");
        }
        put(key, value, false);
    }

    /**
     * SP中写入布尔型
     *
     * @param key      sp钥匙
     * @param value    sp值
     * @param isCommit true提交方式为commit，false提交方式为apply
     */
    public void put(@NonNull final String key, final boolean value, final boolean isCommit) {
        if (StringUtils.isEmpty(key)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "key不能为空！");
        }
        if (isCommit) {
            sp.edit().putBoolean(key, value).commit();
        } else {
            sp.edit().putBoolean(key, value).apply();
        }
    }

    /**
     * SP中读取布尔型
     *
     * @param key sp钥匙
     * @return 钥匙对应的值 如果不存在为false
     */
    public boolean getBoolean(@NonNull final String key) {
        if (StringUtils.isEmpty(key)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "key不能为空！");
        }
        return getBoolean(key, false);
    }

    /**
     * SP中读取布尔型
     *
     * @param key          sp钥匙
     * @param defaultValue 默认值
     * @return 钥匙对应的值 如果不存在为默认值
     */
    public boolean getBoolean(@NonNull final String key, final boolean defaultValue) {
        if (StringUtils.isEmpty(key)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "key不能为空！");
        }
        return sp.getBoolean(key, defaultValue);
    }

    /**
     * SP中写入字符串集合
     *
     * @param key   sp钥匙
     * @param value sp值
     */
    public void put(@NonNull final String key, final Set<String> value) {
        if (StringUtils.isEmpty(key)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "key不能为空！");
        }
        put(key, value, false);
    }

    /**
     * SP中写入字符串集合
     *
     * @param key      sp钥匙
     * @param value    sp值
     * @param isCommit true提交方式为commit，false提交方式为apply
     */
    public void put(@NonNull final String key,
                    final Set<String> value,
                    final boolean isCommit) {
        if (StringUtils.isEmpty(key)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "key不能为空！");
        }
        if (isCommit) {
            sp.edit().putStringSet(key, value).commit();
        } else {
            sp.edit().putStringSet(key, value).apply();
        }
    }

    /**
     * SP中读取字符串集合
     *
     * @param key sp钥匙
     * @return 钥匙对应的值 如果不存在为空集合
     */
    public Set<String> getStringSet(@NonNull final String key) {
        if (StringUtils.isEmpty(key)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "key不能为空！");
        }
        return getStringSet(key, Collections.<String>emptySet());
    }

    /**
     * SP中读取字符串集合
     *
     * @param key          sp钥匙
     * @param defaultValue 默认值
     * @return 钥匙对应的值 如果不存在为默认值
     */
    public Set<String> getStringSet(@NonNull final String key,
                                    final Set<String> defaultValue) {
        if (StringUtils.isEmpty(key)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "key不能为空！");
        }
        return sp.getStringSet(key, defaultValue);
    }

    /**
     * SP中读取所有数据
     *
     * @return sp所有值
     */
    public Map<String, ?> getAll() {
        return sp.getAll();
    }

    /**
     * SP中是否存在该key
     *
     * @param key sp钥匙
     * @return {@code true}: 是<br>{@code false}: 不是
     */
    public boolean contains(@NonNull final String key) {
        if (StringUtils.isEmpty(key)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "key不能为空！");
        }
        return sp.contains(key);
    }

    /**
     * SP中移除该key
     *
     * @param key sp钥匙
     */
    public void remove(@NonNull final String key) {
        if (StringUtils.isEmpty(key)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "key不能为空！");
        }
        remove(key, false);
    }

    /**
     * SP中移除该key
     *
     * @param key      sp钥匙
     * @param isCommit true提交方式为commit，false提交方式为apply
     */
    public void remove(@NonNull final String key, final boolean isCommit) {
        if (StringUtils.isEmpty(key)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "key不能为空！");
        }
        if (isCommit) {
            sp.edit().remove(key).commit();
        } else {
            sp.edit().remove(key).apply();
        }
    }

    /**
     * SP中清除所有数据
     */
    public void clear() {
        clear(false);
    }

    /**
     * SP中清除所有数据
     *
     * @param isCommit true提交方式为commit，false提交方式为apply
     */
    public void clear(final boolean isCommit) {
        if (isCommit) {
            sp.edit().clear().commit();
        } else {
            sp.edit().clear().apply();
        }
    }
}
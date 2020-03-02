package com.inossem_library.app.language.util;

import android.os.Build;
import android.os.LocaleList;
import androidx.annotation.RequiresApi;

import com.inossem_library.app.language.constant.SupportLanguageConstant;

import java.util.Locale;

/**
 * 语言相关
 *
 * @author Lin
 */
public class SupportLanguageUtil {
    /**
     * 是否支持此语言
     *
     * @param language 语言
     * @return true:支持 false:不支持
     */
    public static boolean isSupportLanguage(String language) {
        return SupportLanguageConstant.mSupportLanguages.containsKey(language);
    }

    /**
     * 获取系统首选语言
     *
     * @return 语言
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static Locale getSystemPreferredLanguage() {
        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = LocaleList.getDefault().get(0);
        } else {
            locale = Locale.getDefault();
        }
        return locale;
    }
}
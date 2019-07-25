package com.inossem_library.app.language.constant;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class SupportLanguageConstant {
    /**
     * 各国的命名规范
     * <a>https://blog.csdn.net/jacksinrow/article/details/80667143</a>
     */
    // 中文简体,中国
    public static final String SIMPLIFIED_CHINESE = "zh";
    // 英语,美国
    public static final String ENGLISH = "en";
    // 法语,法国
    public static final String FRANCE = "fr";
    public static Map<String, Locale> mSupportLanguages = new HashMap<String, Locale>(3) {{
        put(SIMPLIFIED_CHINESE, Locale.SIMPLIFIED_CHINESE);
        put(ENGLISH, Locale.ENGLISH);
        put(FRANCE, Locale.FRANCE);
    }};
}
package com.inossem_library.other.pattern.config;

import com.andrognito.patternlockview.PatternLockView;

/**
 * 手势密码配置类
 *
 * @author LeoDys E-mail:changwen.sun@inossem.com 2020/2/28 14:20
 * @version 1.0.8
 * @since 1.0.8
 */
public class PatternConfig {
    /**
     * mPatternLockView.setViewMode(PatternLockView.PatternViewMode.CORRECT);       // Set the current viee more
     * mPatternLockView.setInStealthMode(true);                                     // Set the pattern in stealth mode (pattern drawing is hidden)
     * mPatternLockView.setTactileFeedbackEnabled(true);                            // Enables vibration feedback when the pattern is drawn
     * mPatternLockView.setInputEnabled(false);                                     // Disables any input from the pattern lock view completely
     * mPatternLockView.setDotCount(3);
     * mPatternLockView.setDotNormalSize((int) ResourceUtils.getDimensionInPx(this, R.dimen.pattern_lock_dot_size));
     * mPatternLockView.setDotSelectedSize((int) ResourceUtils.getDimensionInPx(this, R.dimen.pattern_lock_dot_selected_size));
     * mPatternLockView.setPathWidth((int) ResourceUtils.getDimensionInPx(this, R.dimen.pattern_lock_path_width));
     * mPatternLockView.setAspectRatioEnabled(true);
     * mPatternLockView.setAspectRatio(PatternLockView.AspectRatio.ASPECT_RATIO_HEIGHT_BIAS);
     * mPatternLockView.setNormalStateColor(ResourceUtils.getColor(this, R.color.white));
     * mPatternLockView.setCorrectStateColor(ResourceUtils.getColor(this, R.color.primary));
     * mPatternLockView.setWrongStateColor(ResourceUtils.getColor(this, R.color.pomegranate));
     * mPatternLockView.setDotAnimationDuration(150);
     * mPatternLockView.setPathEndAnimationDuration(100);
     */
    /**
     * PatternLockView.PatternViewMode.CORRECT此状态表示用户正确绘制的模式。道路的颜色和两个点都会变成这种颜色。
     */
    // 是否隐藏绘制路径
    private boolean inStealthMode;


}

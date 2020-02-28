package com.inossem_library.other.pattern.util;

import android.app.Activity;
import android.content.Intent;

import com.inossem_library.other.pattern.constant.PatternConstant;
import com.inossem_library.view.activity.PatternActivity;

import java.util.logging.LoggingMXBean;

/**
 * 手势锁屏工具类
 *
 * @author LeoDys E-mail:changwen.sun@inossem.com 2020/2/27 15:52
 * @version 1.0.8
 * @since 1.0.8
 */
public class PatterUtils {
    /**
     * 设置手势密码
     *
     * @param mActivity 上下文对象
     */
    public static void setPattern(Activity mActivity, Integer icon) {
        Intent intent = new Intent(mActivity, PatternActivity.class);
        intent.putExtra(PatternConstant.KEY_TYPE, PatternConstant.TYPE_SET_PASSWORD);
        intent.putExtra(PatternConstant.KEY_LOGO, icon);
        mActivity.startActivityForResult(intent, PatternConstant.REQUEST_CODE_SET_PASSWORD);
    }

    /**
     * 重置手势密码
     *
     * @param mActivity       上下文对象
     * @param patternPassword 旧的手势密码
     */
    public static void resetPattern(Activity mActivity, String patternPassword, Integer icon) {
        Intent intent = new Intent(mActivity, PatternActivity.class);
        intent.putExtra(PatternConstant.KEY_TYPE, PatternConstant.TYPE_CHANGE_PASSWORD);
        intent.putExtra(PatternConstant.KEY_CORRECT_PASSWORD, patternPassword);
        intent.putExtra(PatternConstant.KEY_LOGO, icon);
        mActivity.startActivityForResult(intent, PatternConstant.REQUEST_CODE_CHANGE_PASSWORD);
    }

    /**
     * 校验手势密码
     *
     * @param mActivity       上下文对象
     * @param patternPassword 当前手势密码
     */
    public static void checkPattern(Activity mActivity, String patternPassword, Integer icon) {
        Intent intent = new Intent(mActivity, PatternActivity.class);
        intent.putExtra(PatternConstant.KEY_TYPE, PatternConstant.TYPE_CHECK_PASSWORD);
        intent.putExtra(PatternConstant.KEY_CORRECT_PASSWORD, patternPassword);
        intent.putExtra(PatternConstant.KEY_LOGO, icon);
        mActivity.startActivityForResult(intent, PatternConstant.REQUEST_CODE_CHECK_PASSWORD);
    }

    /**
     * 在onActivityResult中获取 设置的手势密码
     *
     * @param resultCode  结果码
     * @param requestCode 请求码
     * @param data        intent数据
     * @return 当前设置的手势密码
     */
    public static String getPatternResult(int resultCode, int requestCode, Intent data) {
        if (resultCode == Activity.RESULT_OK &&
                requestCode == PatternConstant.REQUEST_CODE_SET_PASSWORD) {
            /**
             * 返回类型type
             * PatternConstant.RESULT_TYPE_CONFIRM         点击确认按钮的
             * PatternConstant.RESULT_TYPE_CHECK_SUCCESS   校验密码成功的返回类型
             */
            return data.getStringExtra(PatternConstant.KEY_NEW_PASSWORD);
        } else {
            return null;
        }
    }

    /**
     * 在onActivityResult中获取 重置的手势密码
     *
     * @param resultCode  结果码
     * @param requestCode 请求码
     * @param data        intent数据
     * @return 当前设置的手势密码
     */
    public static String getResetPatternResult(int resultCode, int requestCode, Intent data) {
        if (resultCode == Activity.RESULT_OK &&
                requestCode == PatternConstant.REQUEST_CODE_CHANGE_PASSWORD) {
            /**
             * 返回类型type
             * PatternConstant.RESULT_TYPE_CONFIRM         点击确认按钮的
             * PatternConstant.RESULT_TYPE_CHECK_SUCCESS   校验密码成功的返回类型
             */
            return data.getStringExtra(PatternConstant.KEY_NEW_PASSWORD);
        } else {
            return null;
        }
    }

    /**
     * 在onActivityResult中获取校验手势密码的返回结果
     *
     * @param resultCode  结果码
     * @param requestCode 请求码
     * @param data        intent数据
     * @return 是否校验成功
     */
    public static Boolean getCheckPatternResult(int resultCode, int requestCode, Intent data) {
        if (resultCode == Activity.RESULT_OK &&
                requestCode == PatternConstant.REQUEST_CODE_CHECK_PASSWORD) {
            /**
             * 返回类型type
             * PatternConstant.RESULT_TYPE_CONFIRM         点击确认按钮的
             * PatternConstant.RESULT_TYPE_CHECK_SUCCESS   校验密码成功的返回类型
             */
            String type = data.getStringExtra(PatternConstant.KEY_RESULT_TYPE);
            if (PatternConstant.RESULT_TYPE_CHECK_SUCCESS.equals(type)) {
                return true;
            } else {
                return false;
            }
        } else {
            return null;
        }
    }
}

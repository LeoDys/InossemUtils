package com.inossem_library.tips.toast.util;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.Gravity;

import com.github.johnpersano.supertoasts.library.Style;
import com.github.johnpersano.supertoasts.library.SuperActivityToast;
import com.inossem_library.R;
import com.inossem_library.exception.constant.ExceptionEnum;
import com.inossem_library.exception.InossemException;
import com.inossem_library.tips.toast.constant.ToastConstants;

/**
 * @author 王斯宇
 * @time on 2019/7/16 9:44
 * @email siyu.wang@inossem.com
 */
public class ToastUtils {

    /**
     * 默认toast，在屏幕底部，背景灰色
     * @param context  上下文
     * @param msg  提示信息id
     */
    public static void show(Context context , Integer msg){
        show(context , context.getResources().getString(msg));
    }
    /**
     * 默认toast，在屏幕底部，背景灰色
     * @param context  上下文
     * @param msg  提示信息
     */
    public static void show(Context context , String msg){
        show(context , msg , null , null , null ,null , null , null , null);
    }
    /**
     * 默认toast，在屏幕底部，背景灰色
     * @param context  上下文
     * @param msg  提示信息id
     * @param icon  图标
     */
    public static void showError(Context context , Integer msg , Integer icon){
        showError(context , context.getResources().getString(msg) , icon);
    }
    /**
     * 警告toast
     * @param context  上下文
     * @param msg  提示信息
     * @param icon  图标 默认:R.drawable.warn
     */
    public static void showError(Context context , String msg , Integer icon){
        show(context , msg , icon == null ? R.drawable.warn : icon , Color.RED , null , null , null , null , null);
    }

    /***
     * 可以设置背景颜色、字体颜色、样式、显示时长
     * @param context 上下文
     * @param msg  提示信息
     * @param icon  图标 默认没有图标
     * @param color  背景颜色 默认灰色
     * @param textColor  字体颜色  默认白色
     * @param frame  样式  Style.FRAME_KITKAT  Style.FRAME_STANDARD  Style.FRAME_LOLLIPOP
     * @param duration  显示时长
     * @param gravity 位置  默认中间
     *                @since
     *                @see
     *
     */
    public static void show(Context context , Integer msg , Integer icon , Integer color , Integer textColor , @Style.Frame Integer frame , Integer duration , Integer gravity , @Style.Animations Integer animations){
        show(context , context.getResources().getString(msg) , icon ,  color , textColor , frame ,duration , gravity , animations);
    }

    /***
     * 可以设置背景颜色、字体颜色、样式、显示时长
     * @param context 上下文
     * @param msg  提示信息
     * @param icon  图标 默认没有图标
     * @param color  背景颜色 默认灰色
     * @param textColor  字体颜色  默认白色
     * @param frame  样式:Style.FRAME_KITKAT(两边为半圆形)  Style.FRAME_STANDARD(两边带圆角的方形)  Style.FRAME_LOLLIPOP(横向全屏显示)
     * @param duration  显示时长
     * @param gravity 位置  默认中间
     * @param animations  动画  Style.ANIMATIONS_FADE(普通效果)   Style.ANIMATIONS_FLY(左侧划入，右侧划出)   Style.ANIMATIONS_SCALE(缩放)   Style.ANIMATIONS_POP(从下往上划入，从上往下划出)
     */
    public static void show(@NonNull Context context , String msg , Integer icon , Integer color , Integer textColor , @Style.Frame Integer frame , Integer duration , Integer gravity , @Style.Animations Integer animations){
        if (context == null){
            throw new InossemException(ExceptionEnum.NULL_PARAMS , ToastConstants.CONTEST_EXCEPTION_INFO);
        }
        if (msg == null){
            throw new InossemException(ExceptionEnum.NULL_PARAMS , ToastConstants.MSG_EXCEPTION_INFO);
        }
        SuperActivityToast toast = SuperActivityToast.create(context , msg , duration == null ? Style.DURATION_SHORT : duration);
        toast.setFrame(frame == null ? Style.FRAME_STANDARD : frame);
        toast.setColor(color == null ? Color.GRAY : color);
        if (icon != null) {
            toast.setIconResource(icon);
        }
        toast.setTextColor(textColor == null ? Color.WHITE : textColor);
        toast.setGravity(gravity == null ? Gravity.CENTER : gravity);
        toast.setAnimations(animations == null ? Style.ANIMATIONS_FADE : animations);
        toast.show();
    }

}

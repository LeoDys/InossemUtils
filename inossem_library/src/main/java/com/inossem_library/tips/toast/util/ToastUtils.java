package com.inossem_library.tips.toast.util;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import androidx.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inossem_library.R;
import com.inossem_library.exception.InossemException;
import com.inossem_library.exception.constant.ExceptionEnum;
import com.inossem_library.tips.toast.constant.ToastConstants;

/**
 * @author 王斯宇
 * @time on 2019/7/16 9:44
 * @email siyu.wang@inossem.com
 */
public class ToastUtils {

    /**
     * 默认toast，在屏幕底部，背景灰色
     *
     * @param context 上下文
     * @param msg     提示信息id
     */
    public static void show(Context context, Integer msg) {
        show(context, context.getResources().getString(msg));
    }

    /**
     * 默认toast，在屏幕底部，背景灰色
     *
     * @param context 上下文
     * @param msg     提示信息
     */
    public static void show(Context context, String msg) {
        show(context, msg, null, null, null, null, null);
    }

    /**
     * 默认toast，在屏幕底部，背景灰色
     *
     * @param context 上下文
     * @param msg     提示信息id
     * @param icon    图标
     */
    public static void showError(Context context, Integer msg, Integer icon) {
        showError(context, context.getResources().getString(msg), icon);
    }

    /**
     * 警告toast
     *
     * @param context 上下文
     * @param msg     提示信息
     * @param icon    图标 默认:R.drawable.dialog_warn
     */
    public static void showError(Context context, String msg, Integer icon) {
        show(context, msg, icon == null ? R.drawable.dialog_warn : icon, Color.RED, null, null, null);
    }

    /***
     * 可以设置背景颜色、字体颜色、样式、显示时长
     * @param context 上下文
     * @param msg  提示信息
     * @param icon  图标 默认没有图标
     * @param color  背景颜色 默认灰色
     * @param textColor  字体颜色  默认白色
     * @param radius  圓角
     * @param gravity 位置  默认中间
     *                @since
     *                @see
     *
     */
    public static void show(Context context, Integer msg, Integer icon, Integer color, Integer textColor, Integer radius, Integer gravity) {
        show(context, context.getResources().getString(msg), icon, color, textColor, radius, gravity);
    }

    /***
     * 可以设置背景颜色、字体颜色、样式、显示时长
     * @param context 上下文
     * @param msg  提示信息
     * @param icon  图标 默认没有图标
     * @param color  背景颜色 默认灰色
     * @param textColor  字体颜色  默认白色
     * @param radius  圓角
     * @param gravity 位置  默认中间
     */
    public static void show(@NonNull Context context, String msg, Integer icon, Integer color, Integer textColor, Integer radius, Integer gravity) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, ToastConstants.CONTEST_EXCEPTION_INFO);
        }
        if (msg == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, ToastConstants.MSG_EXCEPTION_INFO);
        }
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(color == null ? Color.GRAY : color);
        gd.setCornerRadius(radius == null ? 30 : radius);
        ToastUtil_BlankJ.setGravity(gravity == null ? Gravity.CENTER : gravity, 0, 0);
        View view = LayoutInflater.from(context).inflate(R.layout.toasty_custom, null);
        LinearLayout ll = view.findViewById(R.id.ll);
        ll.setBackground(gd);
        ImageView imageView = view.findViewById(R.id.img);
        if (icon == null) {
            imageView.setVisibility(View.GONE);
        } else {
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageResource(icon);
        }
        TextView textView = view.findViewById(R.id.tv_msg);
        textView.setText(msg);
        textView.setTextColor(textColor == null ? Color.WHITE : textColor);
        ToastUtil_BlankJ.showCustomShort(view);
    }

    /**
     * 初始化，放在application里
     *
     * @param context
     */
    public static void init(final Context context) {
        Utils_BlankJ.init(context);
    }

}

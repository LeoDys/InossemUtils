package com.inossem_library.other.time.util;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;

import com.inossem_library.R;
import com.inossem_library.bean.timer.TimerConfig;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.jzxiang.pickerview.utils.Utils;

import java.sql.Time;

/**
 * 时间选择
 *
 * @author Leo E-mail:changwen.sun@inossem.com 2019/8/1 17:34
 * @version 1.0.7
 * @since 1.0.7
 */

public class TimePickUtils {

    /**
     * 显示时间选择Dialog的方法
     *
     * @param context  上下文对象
     * @param manager  fragment管理器
     * @param config   配置config bean
     * @param listener 时间回调
     */
    public static void getTimePickerDialog(Context context, FragmentManager manager, TimerConfig config, OnDateSetListener listener) {
        TimePickerDialog timeDialog = new TimePickerDialog.Builder()
                .setCallBack(listener)
                .setCancelStringId(context.getString(config.getCancelStringId()))
                .setSureStringId(context.getString(config.getSureStringId()))
                .setTitleStringId(context.getString(config.getTitleStringId()))
                .setYearText(context.getString(config.getYearText()))
                .setMonthText(context.getString(config.getMonthText()))
                .setDayText(context.getString(config.getDayText()))
                .setHourText(context.getString(config.getHourText()))
                .setMinuteText(context.getString(config.getMinuteText()))
                .setCyclic(config.getCyclic())
                .setMinMillseconds(config.getMinMillseconds())
                .setMaxMillseconds(config.getMaxMillseconds())
                .setCurrentMillseconds(config.getCurrentMillseconds())
                .setThemeColor(context.getResources().getColor(config.getThemeColor()))
                .setType(config.getType())
                .setWheelItemTextNormalColor(context.getResources().getColor(config.getWheelItemTextNormalColor()))
                .setWheelItemTextSelectorColor(context.getResources().getColor(config.getWheelItemTextSelectorColor()))
                .setWheelItemTextSize(config.getWheelItemTextSize())
                .build();
        timeDialog.show(manager, "ALL");
    }
}

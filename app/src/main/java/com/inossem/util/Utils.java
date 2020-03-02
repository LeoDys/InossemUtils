package com.inossem.util;

import android.content.Context;

import androidx.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;

import com.inossem.R;

/**
 * 工具类
 *
 * @author 雷嘉
 * @time on 2018/3/6 14:55
 * @email jia.lei @inossem.com
 */
public class Utils {

    /**
     * 日志级别，控制是否输出日志，等于0时不输出日志
     */
    private static final int LOG_LEVEL = 8;

    /**
     * 日志标识
     */
    private static final String TAG = "TECHNOLOGY";

    /**
     * KEY 描述页的来源
     */
    public static final String DESCRIPTION_SOURCES = "Description_Sources";

    /**
     * KEY 描述页的描述
     */
    public static final String DESCRIPTION_DESCRIPTION = "Description_Description";


    /**
     * 动态创建多个button
     *
     * @param context    上下文
     * @param parentView 父控件
     * @param count      创建数量
     * @param listener   创建成功的响应回调
     */
    public static void createButtons(@NonNull Context context, @NonNull ViewGroup parentView, int count, @NonNull ButtonListener listener) {
        checkNullParams(context, parentView, listener);
        if (count <= 0) {
            throw new InossemException(ExceptionEnum.ILLEGAL_PARAMS, "count参数必须大于0");
        }
        for (int i = 0; i < count; i++) {
            Button button = (Button) LayoutInflater.from(context).inflate(R.layout.view_button, null);
            parentView.addView(button);
            listener.onCreated(button, i);
        }
    }

    /**
     * 检测参数是否为空，为空时抛出异常，index是指调用该方法中传的第几个参数为null，计数从0开始
     *
     * @param objectList 参数数组
     */
    public static void checkNullParams(Object... objectList) {
        if (objectList != null) {
            for (int i = 0; i < objectList.length; i++) {
//            if (objectList[i] == null || (objectList[i] instanceof String && TextUtils.isEmpty((String) objectList[i]))) {
                if (objectList[i] == null) {
                    throw new InossemException(ExceptionEnum.NULL_PARAMS, "--- Index:【" + i + "】");
                }
            }
        }
    }

    /**
     * info级别日志，输出日志信息
     *
     * @param message 日志内容信息
     */
    public static void i(String message) {
        if (LOG_LEVEL > Log.INFO) {
            Log.i(TAG, message);
        }
    }

    /**
     * error级别日志，输出日志信息及错误信息
     *
     * @param message   日志内容信息
     * @param throwable 错误信息
     */
    public static void e(String message, Throwable... throwable) {
        if (LOG_LEVEL > Log.ERROR) {
            if (throwable == null) {
                Log.e(TAG, message);
            } else {
                Log.e(TAG, message, throwable[0]);
            }
        }
    }


    /**
     * 按钮响应回调
     *
     * @author 雷嘉
     * @time on 2018/3/6 17:14
     * @email jia.lei @inossem.com
     */
    public interface ButtonListener {
        /**
         * 按钮控件创建后回调
         *
         * @param button   按钮控件
         * @param position 按钮位置
         */
        void onCreated(Button button, int position);
    }

}

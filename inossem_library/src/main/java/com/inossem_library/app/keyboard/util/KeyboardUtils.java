package com.inossem_library.app.keyboard.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.inossem_library.app.keyboard.constant.KeyboardConstant;
import com.inossem_library.exception.constant.ExceptionEnum;
import com.inossem_library.exception.InossemException;

import java.lang.reflect.Field;

/**
 * 键盘相关
 *
 * @author LinH
 */
public final class KeyboardUtils {
    private static int sDecorViewDelta = 0;

    /**
     * 显示软键盘
     */
    public static void showSoftInput(@NonNull Context context) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * 显示软键盘
     *
     * @param view 点击的view
     */
    public static void showSoftInput(@NonNull Context context, final View view) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        showSoftInput(context, view, InputMethodManager.SHOW_FORCED);
    }

    /**
     * 显示软键盘
     *
     * @param view  点击的view
     * @param flags 提供其他操作标志 {@link InputMethodManager#SHOW_IMPLICIT} 代表强行打开,表示用户已强制执行
     */
    public static void showSoftInput(@NonNull final Context context, final View view, final int flags) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        imm.showSoftInput(view, flags, new ResultReceiver(new Handler()) {
            @Override
            protected void onReceiveResult(int resultCode, Bundle resultData) {
                if (resultCode == InputMethodManager.RESULT_UNCHANGED_HIDDEN || resultCode == InputMethodManager.RESULT_HIDDEN) {
                    toggleSoftInput(context);
                }
            }
        });
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * 隐藏软键盘
     *
     * @param activity 当前activity
     */
    public static void hideSoftInput(@NonNull final Activity activity) {
        View view = activity.getCurrentFocus();
        if (view == null) {
            View focusView = activity.getWindow().getDecorView().findViewWithTag("keyboardTagView");
            if (focusView == null) {
                view = new EditText(activity);
                view.setTag("keyboardTagView");
            } else {
                view = focusView;
            }
            ((ViewGroup) activity.getWindow().getDecorView()).addView(view, 0, 0);
            view.requestFocus();
        }
        hideSoftInput(activity, view);
    }

    /**
     * 隐藏软键盘
     *
     * @param view 点击的view
     */
    public static void hideSoftInput(@NonNull final Activity activity, final View view) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) return;
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0, new ResultReceiver(new Handler()) {
            @Override
            protected void onReceiveResult(int resultCode, Bundle resultData) {
                if (resultCode == InputMethodManager.RESULT_UNCHANGED_SHOWN || resultCode == InputMethodManager.RESULT_SHOWN) {
                    toggleSoftInput(activity);
                }
            }
        });
    }

    /**
     * 切换键盘显示与否状态
     */
    public static void toggleSoftInput(@NonNull Context context) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    /**
     * 判断软键盘是否可见
     *
     * @param activity 当前activity
     */
    public static boolean isSoftInputVisible(@NonNull final Activity activity) {
        if (activity == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "activity can not null");
        }
        return getDecorViewInvisibleHeight(activity.getWindow()) > 0;
    }

    /**
     * 软键盘可视区域大小
     */
    public static int getDecorViewInvisibleHeight(@NonNull final Window window) {
        final View decorView = window.getDecorView();
        if (decorView == null) {
            return 0;
        }
        final Rect outRect = new Rect();
        decorView.getWindowVisibleDisplayFrame(outRect);
        int delta = Math.abs(decorView.getBottom() - outRect.bottom);
        if (delta <= getNavBarHeight()) {
            sDecorViewDelta = delta;
            return 0;
        }
        return delta - sDecorViewDelta;
    }

    /**
     * 修复安卓 5497 BUG
     * {@link Activity#onCreate(Bundle)} ()}在此方法中调用
     * <a>https://www.jianshu.com/p/a95a1b84da11</a>
     *
     * @param activity 当前activity
     */
    public static void fixAndroidBug5497(@NonNull final Activity activity) {
        if (activity == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "activity can not null");
        }
        fixAndroidBug5497(activity.getWindow());
    }

    /**
     * 修复安卓 5497 BUG
     * <a>https://www.jianshu.com/p/a95a1b84da11</a>
     * <p>兼容性问题 代表为华为，小米，三星</p>
     *
     * @param window 当前屏幕 activity.getWindow()
     */
    public static void fixAndroidBug5497(@NonNull final Window window) {
        final FrameLayout contentView = window.findViewById(android.R.id.content);
        final View contentViewChild = contentView.getChildAt(0);
        final int paddingBottom = contentViewChild.getPaddingBottom();
        final int[] contentViewInvisibleHeightPre5497 = {getContentViewInvisibleHeight(window)};
        contentView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int height = getContentViewInvisibleHeight(window);
                if (contentViewInvisibleHeightPre5497[0] != height) {
                    contentViewChild.setPadding(contentViewChild.getPaddingLeft(), contentViewChild.getPaddingTop(), contentViewChild.getPaddingRight(), paddingBottom + getDecorViewInvisibleHeight(window));
                    contentViewInvisibleHeightPre5497[0] = height;
                }
            }
        });
    }

    /**
     * 获取内容视图的遮挡高度(系统屏幕内导航)
     *
     * @param window 当前屏幕 activity.getWindow()
     * @return 遮挡高度
     */
    public static int getContentViewInvisibleHeight(final Window window) {
        final View contentView = window.findViewById(android.R.id.content);
        if (contentView == null) {
            return 0;
        }
        final Rect outRect = new Rect();
        contentView.getWindowVisibleDisplayFrame(outRect);
        /*屏幕矩形包含一个矩形的四个整数坐标*/
        /*outRect.bottom 返回屏幕下部分的基准点(不包含系统屏幕内导航导航)*/
        int delta = Math.abs(contentView.getBottom() - outRect.bottom);
        if (delta <= getStatusBarHeight() + getNavBarHeight()) {
            return 0;
        }
        return delta;
    }

    /**
     * 修复软键盘内存泄漏
     * {@link Activity#onDestroy()}在此方法中调用
     *
     * @param activity 调用的activity
     */
    public static void fixSoftInputLeaks(@NonNull final Activity activity) throws Throwable {
        if (activity == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "activity can not null");
        }
        fixSoftInputLeaks(activity, activity.getWindow());
    }

    /**
     * 修复软键盘内存泄漏
     * {@link Activity#onDestroy()}在此方法中调用
     *
     * @param window 当前屏幕 activity.getWindow()
     */
    public static void fixSoftInputLeaks(@NonNull Context context, @NonNull final Window window) throws Throwable {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }
        String[] leakViews = new String[]{KeyboardConstant.M_LAST_SRV_VIEW, KeyboardConstant.M_CUR_ROOT_VIEW, KeyboardConstant.M_SERVED_VIEW, KeyboardConstant.M_NEXT_SERVED_VIEW};
        for (String leakView : leakViews) {
            Field leakViewField = InputMethodManager.class.getDeclaredField(leakView);
            if (leakViewField == null) {
                continue;
            }
            if (!leakViewField.isAccessible()) {
                leakViewField.setAccessible(true);
            }
            Object obj = leakViewField.get(imm);
            if (!(obj instanceof View)) {
                continue;
            }
            View view = (View) obj;
            if (view.getRootView() == window.getDecorView().getRootView()) {
                leakViewField.set(imm, null);
            }
        }
    }

    /**
     * 点击屏幕空白区域(软键盘以外区域)隐藏软键盘
     * TODO 在Activity中复制以下代码。
     */
    public static void clickBlankArea2HideSoftInput() {
        /*
        // <a>https://www.jianshu.com/p/6d3f603b5d0b</a>
        // <p>事件分发 点击软键盘以外区域,默认为操作当前页面,不会自动关闭键盘</p>
        @Override
        public boolean dispatchTouchEvent(MotionEvent ev) {
            // ACTION_DOWN:按下的手势的起始位置
            if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                // getCurrentFocus 得到当前的焦点
                View v = getCurrentFocus();
                if (isShouldHideKeyboard(v, ev)) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    // 隐藏软键盘
                    imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
            return super.dispatchTouchEvent(ev);
        }

        // 是否可以获取输入焦点(EditText可触摸)
        private boolean isShouldHideKeyboard(View v, MotionEvent event) {
            if (v != null && (v instanceof EditText)) {
                int[] l = {0, 0};
                v.getLocationInWindow(l);
                int left = l[0],top = l[1],bottom = top + v.getHeight(),right = left + v.getWidth();
                return !(event.getX() > left && event.getX() < right&& event.getY() > top && event.getY() < bottom);
            }
            return false;
        }
        */
    }

    /**
     * 状态栏的高度
     *
     * @return 状态栏的高度
     */
    public static int getStatusBarHeight() {
        Resources resources = Resources.getSystem();
        // status_bar_height 状态栏的高度
        int resourceId = resources.getIdentifier(KeyboardConstant.STATUS_BAR_HEIGHT, KeyboardConstant.DIMEN, KeyboardConstant.ANDROID);
        return resources.getDimensionPixelSize(resourceId);
    }

    /**
     * 导航栏的高度
     *
     * @return 导航栏的高度
     */
    public static int getNavBarHeight() {
        Resources res = Resources.getSystem();
        // navigation_bar_height 导航栏的高度
        int resourceId = res.getIdentifier(KeyboardConstant.NAVIGATION_BAR_HEIGHT, KeyboardConstant.DIMEN, KeyboardConstant.ANDROID);
        if (resourceId != 0) {
            return res.getDimensionPixelSize(resourceId);
        } else {
            return 0;
        }
    }
}
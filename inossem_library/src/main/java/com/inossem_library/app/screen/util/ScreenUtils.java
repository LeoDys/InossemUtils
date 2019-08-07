package com.inossem_library.app.screen.util;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresPermission;
import android.util.DisplayMetrics;
import android.view.Surface;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.inossem_library.exception.constant.ExceptionEnum;
import com.inossem_library.exception.InossemException;

import static android.Manifest.permission.WRITE_SETTINGS;

/**
 * 屏幕相关
 *
 * @author LinH
 */
public final class ScreenUtils {
    /**
     * 获取屏幕的宽度(单位 px)
     *
     * @return 屏幕的宽度(单位 px)
     */
    public static int getScreenWidth(@NonNull Context context) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (wm == null) {
            return -1;
        }
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wm.getDefaultDisplay().getRealSize(point);
        } else {
            wm.getDefaultDisplay().getSize(point);
        }
        return point.x;
    }

    /**
     * 获取屏幕的高度(单位 px)
     *
     * @return 屏幕的高度(单位 px)
     */
    public static int getScreenHeight(@NonNull Context context) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (wm == null) {
            return -1;
        }
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wm.getDefaultDisplay().getRealSize(point);
        } else {
            wm.getDefaultDisplay().getSize(point);
        }
        return point.y;
    }

    /**
     * 获取应用屏幕的宽度(单位 px)
     *
     * @return 应用屏幕的宽度(单位 px)
     */
    public static int getAppScreenWidth(@NonNull Context context) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (wm == null) {
            return -1;
        }
        Point point = new Point();
        wm.getDefaultDisplay().getSize(point);
        return point.x;
    }

    /**
     * 获取应用屏幕的高度(单位 px)
     *
     * @return 应用屏幕的高度(单位 px)
     */
    public static int getAppScreenHeight(@NonNull Context context) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (wm == null) {
            return -1;
        }
        Point point = new Point();
        wm.getDefaultDisplay().getSize(point);
        return point.y;
    }

    /**
     * 获取屏幕密度
     *
     * @return 屏幕密度
     */
    public static float getScreenDensity() {
        return Resources.getSystem().getDisplayMetrics().density;
    }

    /**
     * 获取屏幕密度DPI
     *
     * @return 屏幕密度 DPI
     */
    public static int getScreenDensityDpi() {
        return Resources.getSystem().getDisplayMetrics().densityDpi;
    }

    /**
     * 设置屏幕为全屏
     *
     * @param activity 当前activity
     */
    public static void setFullScreen(@NonNull final Activity activity) {
        if (activity == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "activity can not null");
        }
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * 设置屏幕为非全屏
     *
     * @param activity 当前activity
     */
    public static void setNonFullScreen(@NonNull final Activity activity) {
        if (activity == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "activity can not null");
        }
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * 切换屏幕为全屏与非全屏状态
     *
     * @param activity 当前activity
     */
    public static void toggleFullScreen(@NonNull final Activity activity) {
        if (activity == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "activity can not null");
        }
        boolean isFullScreen = isFullScreen(activity);
        Window window = activity.getWindow();
        if (isFullScreen) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    /**
     * 判断屏幕是否为全屏
     *
     * @param activity 当前activity
     * @return True全屏 False非全屏
     */
    public static boolean isFullScreen(@NonNull final Activity activity) {
        if (activity == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "activity can not null");
        }
        int fullScreenFlag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        return (activity.getWindow().getAttributes().flags & fullScreenFlag) == fullScreenFlag;
    }

    /**
     * 设置屏幕为横屏
     *
     * @param activity 当前activity
     */
    public static void setLandscape(@NonNull final Activity activity) {
        if (activity == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "activity can not null");
        }
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    /**
     * 设置屏幕为竖屏
     *
     * @param activity 当前activity
     */
    public static void setPortrait(@NonNull final Activity activity) {
        if (activity == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "activity can not null");
        }
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    /**
     * 判断是否横屏
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isLandscape(@NonNull Context context) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    /**
     * 判断是否竖屏
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isPortrait(@NonNull Context context) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    /**
     * 获取屏幕旋转角度
     *
     * @param activity 当前activity
     * @return 屏幕旋转角度
     */
    public static int getScreenRotation(@NonNull final Activity activity) {
        if (activity == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "activity can not null");
        }
        switch (activity.getWindowManager().getDefaultDisplay().getRotation()) {
            case Surface.ROTATION_0:
                return 0;
            case Surface.ROTATION_90:
                return 90;
            case Surface.ROTATION_180:
                return 180;
            case Surface.ROTATION_270:
                return 270;
            default:
                return 0;
        }
    }

    /**
     * 截屏 不删除状态栏
     *
     * @param activity 当前activity
     * @return 屏幕Bitmap
     */
    public static Bitmap screenShot(@NonNull final Activity activity) {
        if (activity == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "activity can not null");
        }
        return screenShot(activity, false);
    }

    /**
     * 截屏
     *
     * @param activity          当前activity
     * @param isDeleteStatusBar True删除状态栏,false不删除状态栏
     * @return 屏幕Bitmap
     */
    public static Bitmap screenShot(@NonNull final Activity activity, boolean isDeleteStatusBar) {
        // 获得Window视图
        View decorView = activity.getWindow().getDecorView();
        // 启用绘图缓存
        decorView.setDrawingCacheEnabled(true);
        // 使用缓存
        decorView.setWillNotCacheDrawing(false);
        // 获取绘图缓存
        Bitmap bmp = decorView.getDrawingCache();
        if (bmp == null) {
            return null;
        }
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        Bitmap ret;
        if (isDeleteStatusBar) {
            Resources resources = activity.getResources();
            // status_bar_height 状态栏高度
            int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
            int statusBarHeight = resources.getDimensionPixelSize(resourceId);
            ret = Bitmap.createBitmap(bmp, 0, statusBarHeight, dm.widthPixels, dm.heightPixels - statusBarHeight);
        } else {
            ret = Bitmap.createBitmap(bmp, 0, 0, dm.widthPixels, dm.heightPixels);
        }
        decorView.destroyDrawingCache();
        return ret;
    }

    /**
     * 判断是否锁屏
     *
     * @return True锁屏 False不锁屏
     */
    public static boolean isScreenLock(@NonNull Context context) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        KeyguardManager km = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        return km != null && km.inKeyguardRestrictedInputMode();
    }

    /**
     * 设置进入休眠时长
     * {@code <uses-permission android:name="android.permission.WRITE_SETTINGS" />}需要权限
     *
     * @param duration 持续时间
     */
    @RequiresPermission(WRITE_SETTINGS)
    public static void setSleepDuration(@NonNull Context context, final int duration) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, duration);
    }

    /**
     * 获取进入休眠时长
     *
     * @return 持续时间
     */
    public static int getSleepDuration(@NonNull Context context) throws Settings.SettingNotFoundException {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        return Settings.System.getInt(context.getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT);
    }
}
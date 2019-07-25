package com.inossem_library.app.device.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Debug;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresPermission;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.inossem_library.exception.ExceptionEnum;
import com.inossem_library.exception.InossemException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static android.Manifest.permission.ACCESS_WIFI_STATE;
import static android.Manifest.permission.INTERNET;

/**
 * 设备相关
 *
 * @author Lin
 */
public final class DeviceUtils {

    /**
     * 判断设备是否ROOT
     * <a>https://blog.csdn.net/applek_Case/article/details/79491166</a>
     */
    public static boolean isDeviceRooted() {
        // root权限是安卓最高的操作权限，俗称superuser，系统简称su
        String su = "su";
        // root过的手机，系统目录会有su目录
        String[] locations = {"/system/bin/", "/system/xbin/", "/sbin/", "/system/sd/xbin/", "/system/bin/failsafe/", "/data/local/xbin/", "/data/local/bin/", "/data/local/", "/system/sbin/", "/usr/bin/", "/vendor/bin/"};
        for (String location : locations) {
            if (new File(location + su).exists()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断设备ADB是否可用
     */
    public static boolean isAdbEnabled(@NonNull Context context) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        // ADB_ENABLED ADB授权
        return Settings.Secure.getInt(context.getContentResolver(), Settings.Global.ADB_ENABLED, 0) > 0;
    }

    /**
     * 获取设备系统版本号
     */
    public static String getSDKVersionName() {
        // RELEASE 用户可见的版本字符串
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取设备系统版本码
     */
    public static int getSDKVersionCode() {
        // SDK_INT 当前在此硬件设备上运行的软件的SDK版本
        return Build.VERSION.SDK_INT;
    }

    /**
     * 获取设备 AndroidID,但是如果返厂的手机,或者被 root 的手机,可能会变
     */
    @SuppressLint("HardwareIds")
    public static String getAndroidID(@NonNull Context context) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        String id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        return id == null ? "" : id;
    }

    /**
     * 获取设备 MAC 地址
     * 必须的权限 <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"></uses-permission>
     */
    @RequiresPermission(allOf = {ACCESS_WIFI_STATE, INTERNET})
    public static String getMacAddress(@NonNull Context context) throws Throwable {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        String mac = "02:00:00:00:00:00";
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            mac = getMacDefault(context);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            mac = getMacAddres();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mac = getMacFromHardware();
        }
        return mac;
    }

    /**
     * Android 6.0 之前（不包括6.0）获取mac地址
     * 必须的权限 <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"></uses-permission>
     */
    @SuppressLint("HardwareIds")
    @RequiresPermission(allOf = {ACCESS_WIFI_STATE, INTERNET})
    private static String getMacDefault(@NonNull Context context) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        String mac = "02:00:00:00:00:00";
        // 根据WIFI获取MAC地址
        WifiManager wifi = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wifi == null) {
            return mac;
        }
        WifiInfo info = wifi.getConnectionInfo();
        if (info == null) {
            return null;
        }
        mac = info.getMacAddress();
        if (!TextUtils.isEmpty(mac)) {
            mac = mac.toUpperCase(Locale.ENGLISH);
        }
        return mac;
    }

    /**
     * Android 6.0-Android 7.0 获取mac地址
     */
    private static String getMacAddres() throws Throwable {
        // 获取文件中的MAC地址
        return new BufferedReader(new FileReader(new File("/sys/class/net/wlan0/address"))).readLine();
    }

    /**
     * Android 7.0之后获取Mac地址
     * 遍历循环所有的网络接口,找到接口是 wlan0
     * 必须的权限 <uses-permission android:name="android.permission.INTERNET"></uses-permission>
     */
    private static String getMacFromHardware() throws Throwable {
        // getNetworkInterfaces 返回此机器上的所有接口
        List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
        for (NetworkInterface nif : all) {
            if (!nif.getName().equalsIgnoreCase("wlan0")) {
                continue;
            }
            byte[] macBytes = nif.getHardwareAddress();
            if (macBytes == null) {
                return "";
            }
            StringBuilder res1 = new StringBuilder();
            for (byte b : macBytes) {
                res1.append(String.format("%02X:", b));
            }
            if (res1.length() > 0) {
                res1.deleteCharAt(res1.length() - 1);
            }
            return res1.toString();
        }
        return "02:00:00:00:00:00";
    }

    /**
     * 获取设备厂商
     */
    public static String getManufacturer() {
        // MANUFACTURER 产品/硬件的制造商
        return Build.MANUFACTURER;
    }

    /**
     * 获取设备型号
     */
    public static String getModel() {
        String model = Build.MODEL;
        if (model != null) {
            model = model.trim().replaceAll("\\s*", "");
        } else {
            model = "";
        }
        return model;
    }

    /**
     * 获取设备ABIs(CPU)
     */
    public static String[] getABIs() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return Build.SUPPORTED_ABIS;
        } else {
            if (!TextUtils.isEmpty(Build.CPU_ABI2)) {
                return new String[]{Build.CPU_ABI, Build.CPU_ABI2};
            }
            return new String[]{Build.CPU_ABI};
        }
    }

    /**
     * 判断是否是平板
     * screenLayout 屏幕布局
     * <p>
     * 位掩码的屏幕的总体布局。目前有四个
     * SCREENLAYOUT_SIZE_MASK      大小
     * SCREENLAYOUT_LONG_MASK      长宽比
     * SCREENLAYOUT_LAYOUTDIR_MASK 布局方向
     * SCREENLAYOUT_ROUND_MASK     曲面屏
     * <p>
     * SCREENLAYOUT_SIZE_LARGE 代表屏幕为大规模及平板
     */
    public static boolean isTablet(@NonNull Context context) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    /**
     * 判断是否是模拟器
     */
    public static boolean isEmulator(@NonNull Context context) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        @SuppressLint("HardwareIds") boolean checkProperty =
                //指纹
                Build.FINGERPRINT.startsWith("generic")// generic 通用
                        || Build.FINGERPRINT.toLowerCase().contains("vbox")// vbox 视频盒
                        || Build.FINGERPRINT.toLowerCase().contains("test-keys")// test-keys 测试键
                        // 最终产品的最终用户可见名称
                        || Build.MODEL.contains("google_sdk")// google_sdk google 开放SDK
                        || Build.MODEL.contains("Emulator")// Emulator 模拟器
                        || Build.MODEL.contains("Android SDK built for x86")// Android SDK built for x86 Android SDK为x86构建
                        // 串行端口
                        || Build.SERIAL.equalsIgnoreCase("unknown")// unknown 未知的
                        || Build.SERIAL.equalsIgnoreCase("android")// android 默认的
                        // 制造商
                        || Build.MANUFACTURER.contains("Genymotion")// 安卓模拟器
                        // 商标
                        // 设备
                        || (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))// generic 通用
                        // 产品
                        || Build.PRODUCT.equals("google_sdk");// google_sdk google 开放SDK
        if (checkProperty) {
            return true;
        }
        boolean checkDebuggerConnected = Debug.isDebuggerConnected();
        if (checkDebuggerConnected) {
            return true;
        }
        String operatorName = "";
        // 电话Manager
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (tm != null) {
            // getNetworkOperatorName 当前注册操作符 可用性:只有当用户注册到网络时才可用
            String name = tm.getNetworkOperatorName();
            if (name != null) {
                operatorName = name;
            }
        }
        boolean checkOperatorName = "android".equals(operatorName.toLowerCase());
        if (checkOperatorName) {
            return true;
        }
        // 隐式跳转尝试电话
        String url = "tel:" + "123456";
        Intent intent = new Intent();
        intent.setData(Uri.parse(url));
        intent.setAction(Intent.ACTION_DIAL);
        return intent.resolveActivity(context.getPackageManager()) != null;
    }
}
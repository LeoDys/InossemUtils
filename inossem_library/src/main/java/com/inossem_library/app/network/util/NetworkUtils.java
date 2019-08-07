package com.inossem_library.app.network.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresPermission;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;

import com.inossem_library.app.network.constant.NetworkTypeEnum;
import com.inossem_library.exception.constant.ExceptionEnum;
import com.inossem_library.exception.InossemException;

import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.LinkedList;

import static android.Manifest.permission.ACCESS_NETWORK_STATE;
import static android.Manifest.permission.ACCESS_WIFI_STATE;
import static android.Manifest.permission.CHANGE_WIFI_STATE;
import static android.Manifest.permission.INTERNET;
import static android.content.Context.WIFI_SERVICE;

/**
 * 网络相关
 *
 * @author LinH
 */
public final class NetworkUtils {
    /**
     * 打开网络设置界面
     * ACTION_WIRELESS_SETTINGS 网络设置
     */
    public static void openWirelessSettings(@NonNull Context context) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        context.startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    /**
     * 判断网络是否连接
     * {@code <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />}访问网络状态权限
     *
     * @return True连接 False断开
     */
    @RequiresPermission(ACCESS_NETWORK_STATE)
    public static boolean isConnected(@NonNull Context context) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        NetworkInfo info = getActiveNetworkInfo(context);
        /*isConnected 判断网络连接是否存在，是否可以建立连接并传递数据*/
        return info != null && info.isConnected();
    }

    /**
     * 判断移动数据是否打开
     *
     * @return True连接 False断开
     */
    public static boolean getMobileDataEnabled(@NonNull Context context) throws Throwable {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        // TELEPHONY_SERVICE 电话服务
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (tm == null) {
            return false;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return tm.isDataEnabled();
        }
        @SuppressLint("PrivateApi")
        Method getMobileDataEnabledMethod = tm.getClass().getDeclaredMethod("getDataEnabled");// 获取数据启用
        if (null != getMobileDataEnabledMethod) {
            return (boolean) getMobileDataEnabledMethod.invoke(tm);
        }
        return false;
    }

    /**
     * 判断网络是否是移动数据
     * {@code <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />}访问网络状态权限
     *
     * @return True连接 False断开
     */
    @RequiresPermission(ACCESS_NETWORK_STATE)
    public static boolean isMobileData(@NonNull Context context) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        NetworkInfo info = getActiveNetworkInfo(context);
        // isAvailable是否可以进行网络连接.当持久性或半持久性条件阻止连接到该网络时，网络不可用
        // 例子包括
        // 1. 该设备不在任何此类网络的覆盖范围内
        // 2. 该设备位于家庭网络以外的网络上(数据漫游已被禁用)
        // getType 方法所指向的网络的类型
        // TYPE_MOBILE 移动网络
        return null != info && info.isAvailable() && info.getType() == ConnectivityManager.TYPE_MOBILE;
    }

    /**
     * 判断网络是否是4G
     * {@code <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />}访问网络状态权限
     *
     * @return True连接 False断开
     */
    @RequiresPermission(ACCESS_NETWORK_STATE)
    public static boolean is4G(@NonNull Context context) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        NetworkInfo info = getActiveNetworkInfo(context);
        // isAvailable是否可以进行网络连接.当持久性或半持久性条件阻止连接到该网络时，网络不可用
        // 例子包括
        // 1. 该设备不在任何此类网络的覆盖范围内
        // 2. 该设备位于家庭网络以外的网络上(数据漫游已被禁用)
        // getType 网络类型
        // NETWORK_TYPE_LTE LTE网络类型
        return info != null && info.isAvailable() && info.getSubtype() == TelephonyManager.NETWORK_TYPE_LTE;
    }

    /**
     * 判断WIFI是否打开
     * {@code <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />}访问WIFI状态权限
     *
     * @return True连接 False断开
     */
    @RequiresPermission(ACCESS_WIFI_STATE)
    public static boolean getWifiEnabled(@NonNull Context context) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        @SuppressLint("WifiManagerLeak")
        WifiManager manager = (WifiManager) context.getSystemService(WIFI_SERVICE);
        // isWifiEnabled 是否启用或禁用
        return manager != null && manager.isWifiEnabled();
    }

    /**
     * 更改WIFI状态
     * {@code <uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />}更改WIFI状态
     *
     * @param enabled True连接 False断开
     */
    @RequiresPermission(CHANGE_WIFI_STATE)
    public static void setWifiEnabled(@NonNull Context context, final boolean enabled) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        @SuppressLint("WifiManagerLeak")
        WifiManager manager = (WifiManager) context.getSystemService(WIFI_SERVICE);
        if (manager == null) {
            return;
        }
        // isWifiEnabled 是否启用或禁用
        if (enabled == manager.isWifiEnabled()) {
            return;
        }
        manager.setWifiEnabled(enabled);
    }

    /**
     * 判断WIFI是否连接状态
     * {@code <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />}访问网络状态权限
     *
     * @return True连接 False断开
     */
    @RequiresPermission(ACCESS_NETWORK_STATE)
    public static boolean isWifiConnected(@NonNull Context context) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        // CONNECTIVITY_SERVICE 连接服务
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
            return false;
        }
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.getType() == ConnectivityManager.TYPE_WIFI;
    }

    /**
     * 获取移动网络运营商名称
     *
     * @return 移动网络运营商名称
     */
    public static String getNetworkOperatorName(@NonNull Context context) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        // TELEPHONY_SERVICE 电话服务
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (tm == null) {
            return "";
        }
        return tm.getNetworkOperatorName();
    }

    /**
     * 获取当前网络类型
     * {@code <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />}访问网络状态权限
     *
     * @return 网络类型
     */
    @RequiresPermission(ACCESS_NETWORK_STATE)
    public static NetworkTypeEnum getNetworkType(@NonNull Context context) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        if (isEthernet(context)) {
            return NetworkTypeEnum.NETWORK_ETHERNET;
        }
        NetworkInfo info = getActiveNetworkInfo(context);
        if (info != null && info.isAvailable()) {
            if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                return NetworkTypeEnum.NETWORK_WIFI;
            } else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                switch (info.getSubtype()) {
                    /* 返回网络类型的一般类，如“3G”或“4G”*/
                    case TelephonyManager.NETWORK_TYPE_GSM:
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN:
                        return NetworkTypeEnum.NETWORK_2G;
                    case TelephonyManager.NETWORK_TYPE_TD_SCDMA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B:
                    case TelephonyManager.NETWORK_TYPE_EHRPD:
                    case TelephonyManager.NETWORK_TYPE_HSPAP:
                        return NetworkTypeEnum.NETWORK_3G;
                    case TelephonyManager.NETWORK_TYPE_IWLAN:
                    case TelephonyManager.NETWORK_TYPE_LTE:
                        return NetworkTypeEnum.NETWORK_4G;
                    default:
                        /*获得类型名称*/
                        String subtypeName = info.getSubtypeName();
                        if ("TD-SCDMA".equalsIgnoreCase(subtypeName)) {
                            return NetworkTypeEnum.NETWORK_3G;
                        } else if ("CDMA2000".equalsIgnoreCase(subtypeName)) {
                            return NetworkTypeEnum.NETWORK_3G;
                        } else if ("WCDMA".equalsIgnoreCase(subtypeName)) {
                            return NetworkTypeEnum.NETWORK_3G;
                        }
                }
            }
        }
        return NetworkTypeEnum.NETWORK_UNKNOWN;
    }

    /**
     * 是否使用以太网
     * {@code <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />}访问网络状态
     *
     * @return True连接 False断开
     */
    @RequiresPermission(ACCESS_NETWORK_STATE)
    public static boolean isEthernet(@NonNull Context context) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        final ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
            return false;
        }
        final NetworkInfo info = cm.getNetworkInfo(ConnectivityManager.TYPE_ETHERNET);
        if (info == null) {
            return false;
        }
        NetworkInfo.State state = info.getState();
        return null != state && (state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING);
    }

    /**
     * 获取网络信息
     *
     * @return 网络信息
     */
    @RequiresPermission(ACCESS_NETWORK_STATE)
    public static NetworkInfo getActiveNetworkInfo(@NonNull Context context) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        // CONNECTIVITY_SERVICE 网络连接
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
            return null;
        }
        return cm.getActiveNetworkInfo();
    }

    /**
     * 获取IP地址
     * {@code <uses-permission android:name="android.permission.INTERNET" />}因特网权限
     *
     * @param useIPv4 True使用ipv4 False不使用ipv4
     * @return IP地址
     */
    @RequiresPermission(INTERNET)
    public static String getIPAddress(final boolean useIPv4) throws Throwable {
        Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces();
        LinkedList<InetAddress> adds = new LinkedList<>();
        while (nis.hasMoreElements()) {
            NetworkInterface ni = nis.nextElement();
            // 防止小米手机返回"10.0.2.15"
            if (!ni.isUp() || ni.isLoopback()) {
                continue;
            }
            Enumeration<InetAddress> addresses = ni.getInetAddresses();
            while (addresses.hasMoreElements()) {
                adds.addFirst(addresses.nextElement());
            }
        }
        for (InetAddress add : adds) {
            // 判断是否是loopback地址(本机127.0.0.0 ~ 127.255.255.255)
            if (!add.isLoopbackAddress()) {
                // getHostAddress 以文本形式返回IP地址字符串
                String hostAddress = add.getHostAddress();
                boolean isIPv4 = hostAddress.indexOf(':') < 0;
                if (useIPv4) {
                    if (isIPv4) {
                        return hostAddress;
                    }
                } else {
                    if (!isIPv4) {
                        int index = hostAddress.indexOf('%');
                        // toUpperCase 将字符串小写字符转换为大写 toLowerCase 将字符串大写字符转换为小写
                        return index < 0 ? hostAddress.toUpperCase() : hostAddress.substring(0, index).toUpperCase();
                    }
                }
            }
        }
        return "";
    }

    /**
     * 根据WIFI获取网络IP地址
     *
     * @return IP地址
     */
    @RequiresPermission(ACCESS_WIFI_STATE)
    public static String getIpAddressByWifi(@NonNull Context context) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        @SuppressLint("WifiManagerLeak")
        WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (wm == null) {
            return "";
        }
        // ipAddress IP地址
        return Formatter.formatIpAddress(wm.getDhcpInfo().ipAddress);
    }

    /**
     * 根据WIFI获取网关
     *
     * @return 网关
     */
    @RequiresPermission(ACCESS_WIFI_STATE)
    public static String getGatewayByWifi(@NonNull Context context) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        @SuppressLint("WifiManagerLeak")
        WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (wm == null) {
            return "";
        }
        // gateway 网关
        return Formatter.formatIpAddress(wm.getDhcpInfo().gateway);
    }

    /**
     * 根据WIFI获取子网掩码
     *
     * @return 子网掩码
     */
    @RequiresPermission(ACCESS_WIFI_STATE)
    public static String getNetMaskByWifi(@NonNull Context context) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        @SuppressLint("WifiManagerLeak")
        WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (wm == null) {
            return "";
        }
        return Formatter.formatIpAddress(wm.getDhcpInfo().netmask);
    }

    /**
     * 根据WIFI获取服务端(DHCP服务器)
     *
     * @return 服务端
     */
    @RequiresPermission(ACCESS_WIFI_STATE)
    public static String getServerAddressByWifi(@NonNull Context context) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        @SuppressLint("WifiManagerLeak")
        WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (wm == null) {
            return "";
        }
        return Formatter.formatIpAddress(wm.getDhcpInfo().serverAddress);
    }
}
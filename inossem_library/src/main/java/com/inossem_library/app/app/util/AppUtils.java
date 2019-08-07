package com.inossem_library.app.app.util;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.inossem_library.app.app.constant.AppConstant;
import com.inossem_library.exception.constant.ExceptionEnum;
import com.inossem_library.exception.InossemException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * App相关
 *
 * @author LinH
 */
public final class AppUtils {

    /**
     * 判断App是否安装
     *
     * @param context     上下文
     * @param packageName 包名
     * @return True存在 False不存在
     * @throws PackageManager.NameNotFoundException 无法找到给定的包
     */
    public static boolean isAppInstalled(@NonNull Context context, @NonNull final String packageName) throws PackageManager.NameNotFoundException {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        if (TextUtils.isEmpty(packageName)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        return getApplicationInfo(context, packageName, PackageManager.GET_META_DATA) != null;
    }

    /**
     * 判断App是否是Debug版本
     *
     * @param context 上下文
     * @return True是 False不是
     * @throws PackageManager.NameNotFoundException 无法找到给定的包
     */
    public static boolean isAppDebug(@NonNull Context context) throws PackageManager.NameNotFoundException {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        return isAppDebug(context, context.getPackageName());
    }

    /**
     * 判断App是否是Debug版本
     *
     * @param context     上下文
     * @param packageName 包名
     * @return True是 False不是
     * @throws PackageManager.NameNotFoundException 无法找到给定的包
     * @see ApplicationInfo#flags 与应用程序关联的标志
     */
    public static boolean isAppDebug(@NonNull Context context, @NonNull final String packageName) throws PackageManager.NameNotFoundException {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        if (TextUtils.isEmpty(packageName)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        ApplicationInfo applicationInfo = getApplicationInfo(context, packageName, PackageManager.GET_META_DATA);
        // FLAG_DEBUGGABLE 允许调试
        return (applicationInfo.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
    }

    /**
     * 判断 App 是否是系统应用
     *
     * @param context 上下文
     * @return True是 False不是
     * @throws PackageManager.NameNotFoundException 无法找到给定的包
     */
    public static boolean isAppSystem(@NonNull Context context) throws PackageManager.NameNotFoundException {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        return isAppDebug(context, context.getPackageName());
    }

    /**
     * 判断 App 是否是系统应用
     *
     * @param context     上下文
     * @param packageName 包名
     * @return True是 False不是
     * @throws PackageManager.NameNotFoundException 无法找到给定的包
     */
    public static boolean isAppSystem(@NonNull Context context, @NonNull final String packageName) throws PackageManager.NameNotFoundException {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        if (TextUtils.isEmpty(packageName)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        ApplicationInfo applicationInfo = getApplicationInfo(context, packageName, PackageManager.GET_META_DATA);
        return (applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0;
    }

    /**
     * 判断 App 是否运行
     *
     * @param context     上下文
     * @param packageName 包名
     * @return True是 False不是
     * @throws PackageManager.NameNotFoundException 无法找到给定的包
     */
    public static boolean isAppRunning(@NonNull Context context, @NonNull final String packageName) throws PackageManager.NameNotFoundException {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        if (TextUtils.isEmpty(packageName)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (activityManager != null) {
            List<ActivityManager.RunningTaskInfo> taskInfo = activityManager.getRunningTasks(Integer.MAX_VALUE);
            if (taskInfo != null && taskInfo.size() > 0) {
                // 判断 运行 中是否有相同的 包名
                for (ActivityManager.RunningTaskInfo applicationInfonfo : taskInfo) {
                    // applicationInfonfo.uid 运行进程的 包名
                    if (packageName.equals(applicationInfonfo.baseActivity.getPackageName())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 判断 App 是否在后台运行
     *
     * @param context     上下文
     * @param packageName 包名
     * @return True是 False不是
     * @throws PackageManager.NameNotFoundException 无法找到给定的包
     * @throws PackageManager.NameNotFoundException 无法找到给定的包
     */
    public static boolean isAppKernelRunning(@NonNull Context context, @NonNull final String packageName) throws PackageManager.NameNotFoundException {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        if (TextUtils.isEmpty(packageName)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        int uid;
        ApplicationInfo applicationInfo = getApplicationInfo(context, packageName, PackageManager.GET_META_DATA);
        // uid 内核的用户id(多应用可能有相同uid)
        uid = applicationInfo.uid;
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (activityManager != null) {
            List<ActivityManager.RunningServiceInfo> serviceInfo = activityManager.getRunningServices(Integer.MAX_VALUE);
            if (serviceInfo != null && serviceInfo.size() > 0) {
                // 判断 内核 中是否有相同的用户ID
                for (ActivityManager.RunningServiceInfo applicationInfonfo : serviceInfo) {
                    // applicationInfonfo.uid 运行进程的用户id
                    if (uid == applicationInfonfo.uid) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 打开 App
     *
     * @param context     上下文
     * @param packageName 包名
     * @throws PackageManager.NameNotFoundException 无法找到给定的包
     */
    public static void launchApp(@NonNull Context context, @NonNull final String packageName) throws PackageManager.NameNotFoundException {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        if (TextUtils.isEmpty(packageName)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        context.startActivity(getLaunchAppIntent(context, packageName, true));
    }

    /**
     * 重启 App
     *
     * @param context 上下文
     */
    public static void relaunchApp(@NonNull Context context) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        relaunchApp(context, false);
    }

    /**
     * 重启 App
     *
     * @param isKillProcess True杀死进程 False不死进程
     * @see Intent#FLAG_ACTIVITY_NEW_TASK
     */
    public static void relaunchApp(@NonNull Context context, final boolean isKillProcess) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        PackageManager packageManager = context.getPackageManager();
        // 包名跳转
        Intent intent = packageManager.getLaunchIntentForPackage(context.getPackageName());
        // 添加标记
        // FLAG_ACTIVITY_NEW_TASK 新任务
        // FLAG_ACTIVITY_CLEAR_TOP 清除当前栈顶
        // FLAG_ACTIVITY_CLEAR_TASK 清除任务
        if (intent != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(intent);
            if (isKillProcess) {
                // killProcess 杀进程工具
                // myPid 进程标识符
                android.os.Process.killProcess(android.os.Process.myPid());
                // exit 结束当前正在运行中的java虚拟机
                System.exit(0);
            }
        }
    }

    /**
     * 打开 App 具体设置
     *
     * @param context 上下文
     * @throws PackageManager.NameNotFoundException 无法找到给定的包
     */
    public static void launchAppDetapplicationInfolsSettings(@NonNull Context context) throws PackageManager.NameNotFoundException {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        launchAppDetapplicationInfolsSettings(context, context.getPackageName());
    }

    /**
     * 打开 App 具体设置
     *
     * @param context     上下文
     * @param packageName 包名
     * @throws PackageManager.NameNotFoundException 无法找到给定的包
     */
    public static void launchAppDetapplicationInfolsSettings(@NonNull Context context, @NonNull final String packageName) throws PackageManager.NameNotFoundException {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        if (TextUtils.isEmpty(packageName)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        // ACTION_APPLICATION_DETAILS_SETTINGS 应用程序详细信息设置
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + packageName));
        // 添加标记
        // FLAG_ACTIVITY_NEW_TASK标志活动新任务
        context.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    /**
     * 获取 App 图标
     *
     * @param context 上下文
     * @return App 图标
     * @throws PackageManager.NameNotFoundException 无法找到给定的包
     */
    public static Drawable getAppIcon(@NonNull Context context) throws PackageManager.NameNotFoundException {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        return getAppIcon(context, context.getPackageName());
    }

    /**
     * 获取 App 图标
     *
     * @param context     上下文
     * @param packageName 包名
     * @return App 图标
     * @throws PackageManager.NameNotFoundException 无法找到给定的包
     */
    public static Drawable getAppIcon(@NonNull Context context, @NonNull final String packageName) throws PackageManager.NameNotFoundException {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        if (TextUtils.isEmpty(packageName)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        PackageManager packageManager = getPackageManager(context);
        // Package信息
        PackageInfo packageInfo = getPackageInfo(context, packageName, PackageManager.GET_META_DATA);
        // packageInfo.applicationInfo 应用程序的信息标签，如果没有，则为空
        // loadIcon 检索与此项关联的当前图形图标
        if (packageInfo != null) {
            return packageInfo.applicationInfo.loadIcon(packageManager);
        } else {
            return null;
        }
    }

    /**
     * 获取 App 包名
     *
     * @param context 上下文
     * @return App 包名
     */
    public static String getAppPackageName(@NonNull Context context) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        return context.getPackageName();
    }

    /**
     * 获取 App 名称
     *
     * @param context 上下文
     * @return App 名称
     * @throws PackageManager.NameNotFoundException 无法找到给定的包
     */
    public static String getAppName(@NonNull Context context) throws PackageManager.NameNotFoundException {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        return getAppName(context, context.getPackageName());
    }

    /**
     * 获取 App 名称
     *
     * @param context     上下文
     * @param packageName 包名
     * @return App 名称
     * @throws PackageManager.NameNotFoundException 无法找到给定的包
     */
    public static String getAppName(@NonNull Context context, @NonNull final String packageName) throws PackageManager.NameNotFoundException {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        if (TextUtils.isEmpty(packageName)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        PackageManager packageManager = getPackageManager(context);
        // Package信息
        PackageInfo packageInfo = getPackageInfo(context, packageName, PackageManager.GET_META_DATA);
        // packageInfo.applicationInfo 应用程序的信息标签，如果没有，则为空
        // loadLabel 返回包含项目标签的CharSequence
        if (packageInfo != null) {
            return packageInfo.applicationInfo.loadLabel(packageManager).toString();
        } else {
            return null;
        }
    }

    /**
     * 获取 App 路径
     *
     * @param context 上下文
     * @return App 路径
     * @throws PackageManager.NameNotFoundException 无法找到给定的包
     */
    public static String getAppPath(@NonNull Context context) throws PackageManager.NameNotFoundException {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        return getAppPath(context, context.getPackageName());
    }

    /**
     * 获取 App 路径
     *
     * @param context     上下文
     * @param packageName 包名
     * @return App 路径
     * @throws PackageManager.NameNotFoundException 无法找到给定的包
     */
    public static String getAppPath(@NonNull Context context, @NonNull final String packageName) throws PackageManager.NameNotFoundException {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        if (TextUtils.isEmpty(packageName)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        // Package信息
        PackageInfo packageInfo = getPackageInfo(context, packageName, PackageManager.GET_META_DATA);
        // packageInfo.applicationInfo 应用程序的信息标签，如果没有，则为空
        // sourceDir 此应用程序到基本APK的完整路径
        if (packageInfo != null) {
            return packageInfo.applicationInfo.sourceDir;
        } else {
            return null;
        }
    }

    /**
     * 获取 App 版本名称
     *
     * @param context 上下文
     * @return App 版本名称
     * @throws PackageManager.NameNotFoundException 无法找到给定的包
     */
    public static String getAppVersionName(@NonNull Context context) throws PackageManager.NameNotFoundException {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        return getAppVersionName(context, context.getPackageName());
    }

    /**
     * 获取 App 版本名称
     *
     * @param context     上下文
     * @param packageName 包名
     * @return App 版本名称
     * @throws PackageManager.NameNotFoundException 无法找到给定的包
     */
    public static String getAppVersionName(@NonNull Context context, @NonNull final String packageName) throws PackageManager.NameNotFoundException {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        if (TextUtils.isEmpty(packageName)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        // Package信息
        PackageInfo packageInfo = getPackageInfo(context, packageName, PackageManager.GET_META_DATA);
        // versionName 版本名称
        if (packageInfo != null) {
            return packageInfo.versionName;
        } else {
            return null;
        }
    }

    /**
     * 获取 App 版本号
     *
     * @param context 上下文
     * @return App 版本号
     * @throws PackageManager.NameNotFoundException 无法找到给定的包
     */
    public static int getAppVersionCode(@NonNull Context context) throws PackageManager.NameNotFoundException {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        return getAppVersionCode(context, context.getPackageName());
    }

    /**
     * 获取 App 版本号
     *
     * @param context     上下文
     * @param packageName 包名
     * @return App 版本号
     * @throws PackageManager.NameNotFoundException 无法找到给定的包
     */
    public static int getAppVersionCode(@NonNull Context context, @NonNull final String packageName) throws PackageManager.NameNotFoundException {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        if (TextUtils.isEmpty(packageName)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        // Package信息
        PackageInfo packageInfo = getPackageInfo(context, packageName, PackageManager.GET_META_DATA);
        // versionName 版本号
        if (packageInfo != null) {
            return packageInfo.versionCode;
        } else {
            return -1;
        }
    }

    /**
     * 获取 App 签名
     *
     * @param context 上下文
     * @return App 签名
     * @throws PackageManager.NameNotFoundException 无法找到给定的包
     */
    public static Signature[] getAppSignature(@NonNull Context context) throws PackageManager.NameNotFoundException {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        return getAppSignature(context, context.getPackageName());
    }

    /**
     * 获取 App 签名
     *
     * @param context     上下文
     * @param packageName 包名
     * @return App 签名
     * @throws PackageManager.NameNotFoundException 无法找到给定的包
     */
    public static Signature[] getAppSignature(@NonNull Context context, @NonNull final String packageName) throws PackageManager.NameNotFoundException {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        if (TextUtils.isEmpty(packageName)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        PackageManager packageManager = context.getPackageManager();
        // GET_SIGNATURES 得到签名
        PackageInfo packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES); /* 可能会导致恶意应用程序使用其伪造证书,向Play商店提交 */
        if (packageInfo != null) {
            return packageInfo.signatures;
        } else {
            return null;
        }
    }

    /**
     * 获取应用签名的的 SHA1 值
     *
     * @param context 上下文
     * @return 应用签名的的 SHA1 值
     * @throws PackageManager.NameNotFoundException 无法找到给定的包
     */
    public static String getAppSignatureSHA1(@NonNull Context context) throws PackageManager.NameNotFoundException {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        return getAppSignatureSHA1(context, context.getPackageName());
    }

    /**
     * 获取应用签名的的 SHA1 值
     *
     * @param context     上下文
     * @param packageName 包名
     * @return 应用签名的的 SHA1 值
     * @throws PackageManager.NameNotFoundException 无法找到给定的包
     */
    public static String getAppSignatureSHA1(@NonNull Context context, @NonNull final String packageName) throws PackageManager.NameNotFoundException {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        if (TextUtils.isEmpty(packageName)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        return getAppSignatureHash(context, packageName, "SHA1");
    }

    /**
     * 获取应用签名的的 SHA256 值
     *
     * @param context 上下文
     * @return 应用签名的的 SHA256 值
     * @throws PackageManager.NameNotFoundException 无法找到给定的包
     */
    public static String getAppSignatureSHA256(@NonNull Context context) throws PackageManager.NameNotFoundException {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        return getAppSignatureSHA256(context, context.getPackageName());
    }

    /**
     * 获取应用签名的的 SHA256 值
     *
     * @param context     上下文
     * @param packageName 包名
     * @return 应用签名的的 SHA256 值
     * @throws PackageManager.NameNotFoundException 无法找到给定的包
     */
    public static String getAppSignatureSHA256(@NonNull Context context, @NonNull final String packageName) throws PackageManager.NameNotFoundException {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        if (TextUtils.isEmpty(packageName)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        return getAppSignatureHash(context, packageName, "SHA256");
    }

    /**
     * 获取应用签名的的 MD5 值
     *
     * @param context 上下文
     * @return 应用签名的的 MD5 值
     * @throws PackageManager.NameNotFoundException 无法找到给定的包
     */
    public static String getAppSignatureMD5(@NonNull Context context) throws PackageManager.NameNotFoundException {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        return getAppSignatureMD5(context, context.getPackageName());
    }

    /**
     * 获取应用签名的的 MD5 值
     *
     * @param context     上下文
     * @param packageName 包名
     * @return 应用签名的的 MD5 值
     * @throws PackageManager.NameNotFoundException 无法找到给定的包
     */
    public static String getAppSignatureMD5(@NonNull Context context, @NonNull final String packageName) throws PackageManager.NameNotFoundException {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        if (TextUtils.isEmpty(packageName)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        return getAppSignatureHash(context, packageName, "MD5");
    }

    /**
     * 获取应用程序签名Hash
     *
     * @param context     上下文
     * @param packageName 包名
     * @param algorithm   算法名称
     * @throws PackageManager.NameNotFoundException 无法找到给定的包
     */
    private static String getAppSignatureHash(@NonNull Context context, final String packageName, final String algorithm) throws PackageManager.NameNotFoundException {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        if (TextUtils.isEmpty(packageName)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        Signature[] signature = getAppSignature(context, packageName);
        if (signature == null || signature.length <= 0) {
            return "";
        }
        // 十六进制解密 的哈希
        return bytes2HexString(hashTemplate(signature[0].toByteArray(), algorithm)).replaceAll("(?<=[0-9A-F]{2})[0-9A-F]{2}", ":$0");
    }

    /**
     * 获取 App 信息
     *
     * @param context 上下文
     * @return 获取 App 信息
     * @throws PackageManager.NameNotFoundException 无法找到给定的包
     */
    public static AppInfo getAppInfo(@NonNull Context context) throws PackageManager.NameNotFoundException {
        return getAppInfo(context, context.getPackageName());
    }

    /**
     * 获取 App 信息
     *
     * @param context     上下文
     * @param packageName 包名
     * @return 获取 App 信息
     * @throws PackageManager.NameNotFoundException 无法找到给定的包
     */
    public static AppInfo getAppInfo(@NonNull Context context, @NonNull final String packageName) throws PackageManager.NameNotFoundException {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        if (TextUtils.isEmpty(packageName)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        PackageManager packageManager = getPackageManager(context);
        if (packageManager == null) {
            return null;
        }
        // Package信息
        PackageInfo packageInfo = getPackageInfo(context, packageName, PackageManager.GET_META_DATA);
        return getBean(packageManager, packageInfo);
    }

    /**
     * 获取所有已安装 App 信息
     *
     * @param context 上下文
     * @return 获取所有已安装 App 信息
     */
    public static List<AppInfo> getAppsInfo(@NonNull Context context) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        List<AppInfo> list = new ArrayList<>();
        PackageManager packageManager = context.getPackageManager();
        if (packageManager == null) {
            return list;
        }
        // GET_PERMISSIONS 返回包中有关的信息
        List<PackageInfo> installedPackages = packageManager.getInstalledPackages(PackageManager.GET_PERMISSIONS);
        for (PackageInfo packageInfo : installedPackages) {
            AppInfo applicationInfo = getBean(packageManager, packageInfo);
            list.add(applicationInfo);
        }
        return list;
    }

    private static AppInfo getBean(@NonNull final PackageManager packageManager, @NonNull final PackageInfo packageInfo) {
        ApplicationInfo applicationInfo = packageInfo.applicationInfo;
        String packageName = packageInfo.packageName;
        String name = applicationInfo.loadLabel(packageManager).toString();
        Drawable icon = applicationInfo.loadIcon(packageManager);
        String packagePath = applicationInfo.sourceDir;
        String versionName = packageInfo.versionName;
        int versionCode = packageInfo.versionCode;
        boolean isSystem = (ApplicationInfo.FLAG_SYSTEM & applicationInfo.flags) != 0;
        return new AppInfo(packageName, name, icon, packagePath, versionName, versionCode, isSystem);
    }

    /**
     * 获取哈希
     *
     * @param data      数据
     * @param algorithm 获取哈希值名称
     * @return 哈希加密的byte[]
     */
    private static byte[] hashTemplate(@NonNull final byte[] data, @NonNull final String algorithm) {
        if (data.length <= 0) {
            return null;
        }
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            md.update(data);
            return md.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 十六进制解密
     *
     * @param bytes 数据
     * @return 解密数据
     */
    private static String bytes2HexString(@NonNull final byte[] bytes) {
        int len = bytes.length;
        if (len <= 0) {
            return "";
        }
        char[] ret = new char[len << 1];
        for (int i = 0, j = 0; i < len; i++) {
            ret[j++] = AppConstant.HEX_DIGITS[bytes[i] >> 4 & 0x0f];
            ret[j++] = AppConstant.HEX_DIGITS[bytes[i] & 0x0f];
        }
        return new String(ret);
    }

    /**
     * 跳转
     *
     * @param context     上下文
     * @param packageName 包名
     * @param isNewTask   是否重新打开
     * @throws PackageManager.NameNotFoundException 无法找到给定的包
     */
    private static Intent getLaunchAppIntent(@NonNull Context context, @NonNull final String packageName, final boolean isNewTask) throws PackageManager.NameNotFoundException {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        if (TextUtils.isEmpty(packageName)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        if (intent == null) {
            return null;
        }
        if (isNewTask) {
            return intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        } else {
            return intent;
        }
    }

    /**
     * 获取PackageManager
     *
     * @param context 上下文
     * @return PackageManager
     * @throws PackageManager.NameNotFoundException 无法找到给定的包
     */
    public static PackageManager getPackageManager(@NonNull Context context) throws PackageManager.NameNotFoundException {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        return context.getPackageManager();
    }

    /**
     * 获取ApplicationInfo
     *
     * @param context     上下文
     * @param packageName PackageManager
     * @param flags       附加选项标志来修改返回的数据
     * @return ApplicationInfo
     * @throws PackageManager.NameNotFoundException 无法找到给定的包
     */
    public static ApplicationInfo getApplicationInfo(@NonNull Context context, @NonNull final String packageName, int flags) throws PackageManager.NameNotFoundException {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        if (TextUtils.isEmpty(packageName)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        PackageManager packageManager = context.getPackageManager();
        return packageManager.getApplicationInfo(packageName, flags);
    }

    /**
     * 获取PackageInfo
     *
     * @param context     上下文
     * @param packageName PackageManager
     * @param flags       附加选项标志来修改返回的数据
     * @return PackageInfo
     * @throws PackageManager.NameNotFoundException 无法找到给定的包
     */
    @SuppressLint("PackageManagerGetSignatures")
    public static PackageInfo getPackageInfo(@NonNull Context context, @NonNull final String packageName, int flags) throws PackageManager.NameNotFoundException {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        if (TextUtils.isEmpty(packageName)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        PackageManager packageManager = context.getPackageManager();
        return packageManager.getPackageInfo(packageName, flags);
    }

    /**
     * App 信息 Bean
     */
    public static class AppInfo {
        private String packageName;
        private String name;
        private Drawable icon;
        private String packagePath;
        private String versionName;
        private int versionCode;
        private boolean isSystem;

        public AppInfo(final String packageName, String name, Drawable icon, String packagePath, String versionName, int versionCode, boolean isSystem) {
            this.setName(name);
            this.setIcon(icon);
            this.setPackageName(packageName);
            this.setPackagePath(packagePath);
            this.setVersionName(versionName);
            this.setVersionCode(versionCode);
            this.setSystem(isSystem);
        }

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Drawable getIcon() {
            return icon;
        }

        public void setIcon(Drawable icon) {
            this.icon = icon;
        }

        public String getPackagePath() {
            return packagePath;
        }

        public void setPackagePath(String packagePath) {
            this.packagePath = packagePath;
        }

        public String getVersionName() {
            return versionName;
        }

        public void setVersionName(String versionName) {
            this.versionName = versionName;
        }

        public int getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(int versionCode) {
            this.versionCode = versionCode;
        }

        public boolean isSystem() {
            return isSystem;
        }

        public void setSystem(boolean system) {
            isSystem = system;
        }

        @Override
        public String toString() {
            return "AppInfo{" +
                    "packageName='" + packageName + '\'' +
                    ", name='" + name + '\'' +
                    ", icon=" + icon +
                    ", packagePath='" + packagePath + '\'' +
                    ", versionName='" + versionName + '\'' +
                    ", versionCode=" + versionCode +
                    ", isSystem=" + isSystem +
                    '}';
        }
    }
}
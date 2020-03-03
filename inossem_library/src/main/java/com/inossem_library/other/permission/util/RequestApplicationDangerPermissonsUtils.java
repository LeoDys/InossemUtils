package com.inossem_library.other.permission.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;

import com.inossem_library.exception.InossemException;
import com.inossem_library.exception.constant.ExceptionEnum;

import java.util.ArrayList;
import java.util.List;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

/**
 * 获取应用在Manifest中申请的危险权限
 *
 * @author Leo E-mail:changwen.sun@inossem.com 2019/7/23 15:18
 * @version Java-1.8
 * @since 1.0.0
 */

public class RequestApplicationDangerPermissonsUtils {

    // 对应应用的包管理器
    private static PackageManager pManager;

    /**
     * 获取应用在Manifest中申请的危险权限
     *
     * @param context     上下文
     * @param packageName 应用包名
     * @return 申请的危险权限集合
     * @throws PackageManager.NameNotFoundException 没有找到这个包名的应用
     * @version 1.0.0
     * @since 1.0.0
     */
    public static List<String> getManifestDangerPermission(Context context, String packageName) {
        // 上下文或者包名是null的情况 直接抛出异常给开发使用者
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        if (TextUtils.isEmpty(packageName)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "packageName can not null");
        }
        // 获取包管理器对象
        pManager = context.getPackageManager();
        List<String> list = new ArrayList<>();
        try {
            // 利用包管理器 获取该应用的信息
            PackageInfo pack = pManager.getPackageInfo(packageName, PackageManager.GET_PERMISSIONS);
            // 获取该应用下的所有权限
            String[] permissionStrings = pack.requestedPermissions;
            // 获取危险权限列表
            List<String> dangerPermissionList = dangerPermission();
            for (int i = 0; i < permissionStrings.length; i++) {
                // 区分危险权限
                if (dangerPermissionList.contains(permissionStrings[i])) {
                    list.add(permissionStrings[i]);
                } else {
                    continue;
                }
            }
            return list;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取哪些权限没有获取
     *
     * @param checkList
     * @return
     */
    public static List<String> noHavePermission(Context mContexts, String packName, List<String> checkList) {
        List<String> noHavePermission = new ArrayList<>();
        for (String per : checkList) {
            if (!isHasPermission(mContexts, packName, per)) {
                noHavePermission.add(per);
            }
        }
        return noHavePermission;
    }

    /**
     * Android 所有的危险权限
     *
     * @return 所有的危险权限集合
     */
    private static List<String> dangerPermission() {
        List<String> dangerPermissionList = new ArrayList<>();
        // 写入联系人
        dangerPermissionList.add("android.permission.WRITE_CONTACTS");
        // 访问GMail账户列表
        dangerPermissionList.add("android.permission.GET_ACCOUNTS");
        // 允许程序读取用户联系人数据
        dangerPermissionList.add("android.permission.READ_CONTACTS");
        //
        dangerPermissionList.add("android.permission.READ_CALL_LOG");
        // 手机状态
        dangerPermissionList.add("android.permission.READ_PHONE_STATE");
        // 直接打电话
        dangerPermissionList.add("android.permission.CALL_PHONE");
        //
        dangerPermissionList.add("android.permission.WRITE_CALL_LOG");
        //
        dangerPermissionList.add("android.permission.USE_SIP");
        // 允许程序监视、修改有关播出电话
        dangerPermissionList.add("android.permission.PROCESS_OUTGOING_CALLS");
        //
        dangerPermissionList.add("com.android.voicemail.permission.ADD_VOICEMAIL");
        // 允许程序读取用户日历数据
        dangerPermissionList.add("android.permission.READ_CALENDAR");
        // 允许一个程序写入但不读取用户日历数据
        dangerPermissionList.add("android.permission.WRITE_CALENDAR");
        // 相机
        dangerPermissionList.add("android.permission.CAMERA");
        // 传感器
        dangerPermissionList.add("android.permission.BODY_SENSORS");
        // 通过GPS芯片接收卫星的定位信息，定位精度达10米以内
        dangerPermissionList.add("android.permission.ACCESS_FINE_LOCATION");
        // 允许一个程序访问CellID或WiFi热点来获取粗略的位置
        dangerPermissionList.add("android.permission.ACCESS_COARSE_LOCATION");
        // 读sd卡
        dangerPermissionList.add("android.permission.READ_EXTERNAL_STORAGE");
        // 写入sd卡
        dangerPermissionList.add("android.permission.WRITE_EXTERNAL_STORAGE");
        // 录音
        dangerPermissionList.add("android.permission.RECORD_AUDIO");
        // 允许程序读取短信息
        dangerPermissionList.add("android.permission.READ_SMS");
        // 允许程序监控将收到WAP PUSH信息
        dangerPermissionList.add("android.permission.RECEIVE_WAP_PUSH");
        // 允许一个程序监控将收到MMS彩信,记录或处理
        dangerPermissionList.add("android.permission.RECEIVE_MMS");
        // 发短信
        dangerPermissionList.add("android.permission.SEND_SMS");
        //
        dangerPermissionList.add("android.permission.READ_CELL_BROADCASTS");
        return dangerPermissionList;
    }

    /**
     * 判断应用是否有某个权限
     *
     * @param mContexts  上下文
     * @param permission 具体权限
     * @return true-有  false-没有
     */
    private static boolean isHasPermission(Context mContexts, String packName, String permission) {
        return lackPermission1(mContexts, permission) && lackPermission2(mContexts, packName, permission);
    }


    /**
     * 判断应用是否有某个权限
     *
     * @param mContexts  上下文
     * @param permission 具体权限
     * @return true-有  false-没有
     */
    private static boolean lackPermission1(Context mContexts, String permission) {
        return !(ContextCompat.checkSelfPermission(mContexts, permission) ==
                PackageManager.PERMISSION_DENIED);
    }

    /**
     * 判断应用是否有某个权限
     *
     * @param mContexts  上下文
     * @param packName   应用包名
     * @param permission 具体权限
     * @return true-有  false-没有
     */
    private static boolean lackPermission2(Context mContexts, String packName, String permission) {
        PackageManager pm = mContexts.getPackageManager();
        return (PackageManager.PERMISSION_GRANTED ==
                pm.checkPermission(permission, packName));
    }

    /**
     * 第一次检查权限，用在打开应用的时候请求应用需要的所有权限
     *
     * @param context
     * @param requestCode 请求码
     * @param permission  权限数组
     * @return
     */
    public static boolean checkPermissionFirst(Context context, int requestCode, String[] permission) {
        List<String> permissions = new ArrayList<String>();
        for (String per : permission) {
            int permissionCode = ActivityCompat.checkSelfPermission(context, per);
            if (permissionCode != PackageManager.PERMISSION_GRANTED) {
                permissions.add(per);
            }
        }
        if (!permissions.isEmpty()) {
            ActivityCompat.requestPermissions((Activity) context, permissions.toArray(new String[permissions.size()]), requestCode);
            return false;
        } else {
            return true;
        }
    }

    /**
     * 第二次检查权限，用在某个操作需要某个权限的时候调用
     *
     * @param context
     * @param requestCode 请求码
     * @param permission  权限数组
     * @return
     */
    public static boolean checkPermissionSecond(Context context, int requestCode, String[] permission) {

        List<String> permissions = new ArrayList<String>();
        for (String per : permission) {
            int permissionCode = ActivityCompat.checkSelfPermission(context, per);
            if (permissionCode != PackageManager.PERMISSION_GRANTED) {
                permissions.add(per);
            }
        }
        if (!permissions.isEmpty()) {
            ActivityCompat.requestPermissions((Activity) context, permissions.toArray(new String[permissions.size()]), requestCode);

            /*跳转到应用详情，让用户去打开权限*/
            Intent localIntent = new Intent();
            localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (Build.VERSION.SDK_INT >= 9) {
                localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
            } else if (Build.VERSION.SDK_INT <= 8) {
                localIntent.setAction(Intent.ACTION_VIEW);
                localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
                localIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
            }
            context.startActivity(localIntent);
            return false;
        } else {
            return true;
        }
    }


}

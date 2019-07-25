package com.inossem_library.other.permission.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

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
     */
    public static List<String> getManifestDangerPermission(Context context, String packageName) {
        // 获取包管理器对象
        pManager = context.getPackageManager();
        List<String> list = new ArrayList<>();
        try {
            // 利用包管理器 获取该应用的信息
            PackageInfo pack = pManager.getPackageInfo(packageName, PackageManager.GET_PERMISSIONS);
            // 获取该应用下的所有权限
            String[] permissionStrings = pack.requestedPermissions;
            for (int i = 0; i < permissionStrings.length; i++) {
                // 区分危险权限
                for (int j = 0; j < dangerPermission().size(); j++) {
                    if (permissionStrings[i].equals(dangerPermission().get(j))) {
                        list.add(permissionStrings[i]);
                    }
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
    public static List<String> noHavePermission(Context mContexts, String packName, String permission, List<String> checkList) {
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
    public static List<String> dangerPermission() {
        List<String> dangerPermissionList = new ArrayList<>();
        dangerPermissionList.add("android.permission.WRITE_CONTACTS");
        dangerPermissionList.add("android.permission.GET_ACCOUNTS");
        dangerPermissionList.add("android.permission.READ_CONTACTS");
        dangerPermissionList.add("android.permission.READ_CALL_LOG");
        dangerPermissionList.add("android.permission.READ_PHONE_STATE");
        dangerPermissionList.add("android.permission.CALL_PHONE");
        dangerPermissionList.add("android.permission.WRITE_CALL_LOG");
        dangerPermissionList.add("android.permission.USE_SIP");
        dangerPermissionList.add("android.permission.PROCESS_OUTGOING_CALLS");
        dangerPermissionList.add("com.android.voicemail.permission.ADD_VOICEMAIL");
        dangerPermissionList.add("android.permission.READ_CALENDAR");
        dangerPermissionList.add("android.permission.WRITE_CALENDAR");
        dangerPermissionList.add("android.permission.CAMERA");
        dangerPermissionList.add("android.permission.BODY_SENSORS");
        dangerPermissionList.add("android.permission.ACCESS_FINE_LOCATION");
        dangerPermissionList.add("android.permission.ACCESS_COARSE_LOCATION");
        dangerPermissionList.add("android.permission.READ_EXTERNAL_STORAGE");
        dangerPermissionList.add("android.permission.WRITE_EXTERNAL_STORAGE");
        dangerPermissionList.add("android.permission.RECORD_AUDIO");
        dangerPermissionList.add("android.permission.READ_SMS");
        dangerPermissionList.add("android.permission.RECEIVE_WAP_PUSH");
        dangerPermissionList.add("android.permission.RECEIVE_MMS");
        dangerPermissionList.add("android.permission.RECEIVE_MMS");
        dangerPermissionList.add("android.permission.SEND_SMS");
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
        return ContextCompat.checkSelfPermission(mContexts, permission) ==
                PackageManager.PERMISSION_DENIED;
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

}

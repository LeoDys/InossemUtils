package com.luck.picture.lib.permissions;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.luck.picture.lib.exception.InossemPermissionException;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * 权限工具类
 * Created by wen40 on 2019/7/18.
 */

public class PermissionUtil {

    public interface PermissionCallBackListener {
        /**
         * 是否全部请求成功
         *
         * @param isSuccess
         */
        void permissionListener(boolean isSuccess);
    }

//    public interface PermissionArrayCallBackListener {
//        /**
//         * 是否全部请求成功
//         *
//         * @param isSuccess
//         */
//        void permissionListener(boolean isSuccess, List<String> unGrantedPermissions);
//    }

    /**
     * 单个或者多个权限的请求 拒绝后不需要调用系统的设置开启权限直接退出
     *
     * @param activity    上下文
     * @param listener    权限申请回调接口对象
     * @param permissions 要请求的权限  一个或多个
     */
    public static void requestPermission(final Activity activity, final PermissionCallBackListener listener, String... permissions) {
        // 6.0一下的系统不需要申请权限
        if (Build.VERSION.SDK_INT < 23) {
            listener.permissionListener(true);
            return;
        }
        // 权限集合若是空的直接抛异常
        if (permissions == null || permissions.length == 0) {
            throw new InossemPermissionException("permissions null,please check");
        }
        RxPermissions rxPermissions = new RxPermissions(activity);
        rxPermissions.request(permissions)
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (aBoolean) {// 校验
                            listener.permissionListener(true);
                        } else {
                            goIntentSetting(activity);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void requestPermissionToSetting(final Activity activity, final PermissionArrayCallBackListener listener
            , final String... permissions) {
        if (permissions == null || permissions.length == 0) {
            return;
        }
        if (Build.VERSION.SDK_INT < 23) {
            listener.permissionListener(true, null);
            return;
        }
        RxPermissions rxPermissions = new RxPermissions(activity);
        rxPermissions.requestEach(permissions)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                            // 用户已经同意该权限
                            Log.d(TAG, permission.name + " is granted.");
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                            Log.d(TAG, permission.name + " is denied. More info should be provided.");
                        } else {
                            // 用户拒绝了该权限，并且选中『不再询问』
                            Log.d(TAG, permission.name + " is denied.");
                        }
                    }
                });
    }

    /**
     * 点选不在询问  跳转设置页面手动设置
     *
     * @param activity 上下文对象
     */
    public static void goIntentSetting(final Activity activity) {

        new AlertDialog.Builder(activity).setTitle("设置").setMessage("请到设置添加相应权限,否则应用将无法工作").setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
                intent.setData(uri);
                try {
                    activity.startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).create().show();
    }

    /**
     * 是否拥有该权限
     *
     * @param activity   上下文
     * @param permission 要检查的权限
     * @return ture 有  false 没有
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private static boolean isHasThisPermission(Activity activity, String permission) {
        return activity.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

}

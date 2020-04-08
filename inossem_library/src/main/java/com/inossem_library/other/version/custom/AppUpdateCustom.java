package com.inossem_library.other.version.custom;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.inossem_library.R;
import com.inossem_library.other.version.utils.AppUpdateUtils;
import com.inossem_library.other.version.utils.HttpManager;
import com.inossem_library.other.version.utils.OkGoUpdateHttpUtil;
import com.inossem_library.other.version.utils.VersionUtils;
import com.inossem_library.tips.toast.util.ToastUtils;

import java.io.File;
import java.util.UUID;

/**
 * ----------
 * Author: Leo
 * Date: 2019/6/6
 * Email: 18304097708@163.com
 * Description:
 * ----------
 */

public class AppUpdateCustom {
    /**
     * 获取更新的布局文件
     *
     * @param mContext
     * @param layoutId
     * @return
     */
    public static View getUpdateView(Context mContext, int layoutId) {
        return LayoutInflater.from(mContext).inflate(layoutId, null);
    }

    /**
     * 下载方法
     *
     * @param context
     * @param apkSavePath apk存储路径
     * @param apkName     apk命名 不填写自动生成UUID
     * @param url         apk下载路径 必填
     * @param isCheckMD5  是否启用md5
     * @param md5         启用必填
     * @param callback    下载回调
     */
    public static void download(final Context context, String apkSavePath, String apkName, String url
            , String token, boolean isCheckMD5, String md5, @NonNull final HttpManager.FileCallback callback) {
        if (isCheckMD5) {
            if (TextUtils.isEmpty(md5)) {
                ToastUtils.show(context, context.getString(R.string.update_module_md5_not_null));
                return;
            }
        }
        if (TextUtils.isEmpty(url)) {
            ToastUtils.show(context, context.getString(R.string.update_module_url_not_null));
            return;
        }
        if (TextUtils.isEmpty(apkName)) {
            apkName = UUID.randomUUID().toString() + ".apk";
        }
        OkGoUpdateHttpUtil okGoUpdateHttpUtil = new OkGoUpdateHttpUtil();
        okGoUpdateHttpUtil.download(url, token, apkSavePath, apkName, callback);
    }

    /**
     * apk 安装
     *
     * @param context
     * @param apkSavePath apk路径 绝对路径
     * @param apkName     apk名字
     */
    public static void installApk(final Context context, String apkSavePath, String apkName) {
        AppUpdateUtils.installApp(context, new File(apkSavePath, apkName));
    }

    /**
     * 校验md5
     *
     * @param context
     * @param apkSavePath
     * @param apkName
     * @param md5
     * @return
     */
    public static boolean checkMD5(final Context context, String apkSavePath, String apkName, String md5) {
        File appFile = new File(apkSavePath, apkName);
        return !TextUtils.isEmpty(md5)
                && appFile.exists()
                && VersionUtils.encryptFile(appFile).equalsIgnoreCase(md5);
    }

}

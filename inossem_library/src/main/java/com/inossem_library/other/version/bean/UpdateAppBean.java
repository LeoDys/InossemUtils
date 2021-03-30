package com.inossem_library.other.version.bean;

import com.inossem_library.other.version.utils.HttpManager;

import java.io.Serializable;

/**
 * 版本信息 更新需要的各种信息
 */
public class UpdateAppBean implements Serializable {
    private static final long serialVersionUID = 1L;
    //新版本号
    private String versionCode;
    private String versionName;
    //新app下载地址
    private String url;
    //更新日志
    private String versionRemark;
    //配置默认更新dialog 的title
    private String dialogTitle;
    //新app大小
    private String apkSize;
    //是否强制更新
    private boolean isForce;
    //md5
    private String versionMD5;

    private String token;

    /**********以下是内部使用的数据**********/

    //网络工具，内部使用
    private HttpManager httpManager;
    private String targetPath; // 下载目标路径
    private String fileName;
    private boolean mDismissNotificationProgress;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public HttpManager getHttpManager() {
        return httpManager;
    }

    public void setHttpManager(HttpManager httpManager) {
        this.httpManager = httpManager;
    }

    public String getTargetPath() {
        return targetPath;
    }

    public void setTargetPath(String targetPath) {
        this.targetPath = targetPath;
    }

    public void dismissNotificationProgress(boolean dismissNotificationProgress) {
        mDismissNotificationProgress = dismissNotificationProgress;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVersionRemark() {
        return versionRemark;
    }

    public void setVersionRemark(String versionRemark) {
        this.versionRemark = versionRemark;
    }

    public String getDialogTitle() {
        return dialogTitle;
    }

    public void setDialogTitle(String dialogTitle) {
        this.dialogTitle = dialogTitle;
    }

    public String getApkSize() {
        return apkSize;
    }

    public void setApkSize(String apkSize) {
        this.apkSize = apkSize;
    }

    public boolean isForce() {
        return isForce;
    }

    public void setForce(boolean force) {
        isForce = force;
    }

    public String getVersionMD5() {
        return versionMD5;
    }

    public void setVersionMD5(String versionMD5) {
        this.versionMD5 = versionMD5;
    }

    public boolean isDismissNotificationProgress() {
        return mDismissNotificationProgress;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

package com.inossem_library.other.version.constant;

/**
 * 版本更新常量
 *
 * @author LeoDys E-mail:changwen.sun@inossem.com 2020/4/8 11:05
 * @version 1.0.8
 * @since 1.0.8
 */
public class VersionConstant {
    // 下载之前 显示更新信息
    public static final int ERROR_UPDATE_FORWORD_MD5_EXIST = -1;
    // 下载之前 显示更新信息
    public static final int ERROR_UPDATE_FORWORD = 0;
    // 正在下载
    public static final int ERROR_UPDATE_DOWNLOADING = 1;
    // 下载完成
    public static final int ERROR_UPDATE_DOWNLOADED = 2;
    // 安装
    public static final int ERROR_UPDATE_INSTALL = 3;
    // 下载失败
    public static final int ERROR_UPDATE_ERROR = 4;
    // 拼接的apk名称
    public static final String APK_FILE_NAME = "InossemAPK_";
    // 后缀名
    public static final String APK_SUFFIX_NAME = ".apk";
    // 下载根目录名称
    public static final String DOWNLOAD_ROOT_PATH = "Inossem_Download";
    // 安装包目录名称
    public static final String DOWNLOAD_APK = "APK";


}

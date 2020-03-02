package com.inossem_library.app.path.util;

import android.content.Context;
import android.os.Build;
import android.os.Environment;

import androidx.annotation.NonNull;

import com.inossem_library.app.path.constant.PathConstant;
import com.inossem_library.exception.constant.ExceptionEnum;
import com.inossem_library.exception.InossemException;

import java.io.File;

/**
 * 路径相关
 *
 * @author Lin
 */
public class PathUtils {

    /**
     * 获取根路径 /system.
     *
     * @return 根路径
     */
    public static String getRootPath() {
        return getAbsolutePath(Environment.getRootDirectory());
    }

    /**
     * 获取数据路径 /data.
     *
     * @return 数据路径
     */
    public static String getDataPath() {
        return getAbsolutePath(Environment.getDataDirectory());
    }

    /**
     * 获取下载缓存路径 /cache.
     *
     * @return 下载缓存路径 /cache
     */
    public static String getDownloadCachePath() {
        return getAbsolutePath(Environment.getDownloadCacheDirectory());
    }

    /**
     * 获取内存应用数据路径 /data/data/package.
     *
     * @return 内存应用数据路径 /data/data/package
     */
    public static String getInternalAppDataPath(@NonNull Context context) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            return context.getApplicationInfo().dataDir;
        }
        return getAbsolutePath(context.getDataDir());
    }

    /**
     * 获取内存应用代码缓存路径 /data/data/package/code_cache.
     *
     * @return 内存应用代码缓存路径 /data/data/package/code_cache
     */
    public static String getInternalAppCodeCacheDir(@NonNull Context context) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return context.getApplicationInfo().dataDir + PathConstant.codeCache;
        }
        return getAbsolutePath(context.getCodeCacheDir());
    }

    /**
     * 获取内存应用缓存路径 /data/data/package/cache.
     *
     * @return 内存应用缓存路径 /data/data/package/cache
     */
    public static String getInternalAppCachePath(@NonNull Context context) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        return getAbsolutePath(context.getCacheDir());
    }

    /**
     * 获取内存应用数据库路径 /data/data/package/databases.
     *
     * @return 内存应用数据库路径 /data/data/package/databases
     */
    public static String getInternalAppDbsPath(@NonNull Context context) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        return context.getApplicationInfo().dataDir + PathConstant.databases;
    }

    /**
     * 获取内存应用数据库路径 /data/data/package/databases/name.
     *
     * @param name 数据库的名称
     * @return 内存应用数据库路径 /data/data/package/databases/name
     */
    public static String getInternalAppDbPath(@NonNull Context context, String name) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        return getAbsolutePath(context.getDatabasePath(name));
    }

    /**
     * 获取内存应用文件路径 /data/data/package/files.
     *
     * @return 内存应用文件路径 /data/data/package/files
     */
    public static String getInternalAppFilesPath(@NonNull Context context) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        return getAbsolutePath(context.getFilesDir());
    }

    /**
     * 获取内存应用SP路径 /data/data/package/shared_prefs.
     *
     * @return 内存应用SP路径
     */
    public static String getInternalAppSpPath(@NonNull Context context) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        return context.getApplicationInfo().dataDir + PathConstant.sharedPrefs;
    }

    /**
     * 获取内存应用未备份文件路径 /data/data/package/no_backup.
     *
     * @return 内存应用未备份文件路径 /data/data/package/no_backup
     */
    public static String getInternalAppNoBackupFilesPath(@NonNull Context context) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return context.getApplicationInfo().dataDir + PathConstant.noBackup;
        }
        return getAbsolutePath(context.getNoBackupFilesDir());
    }

    /**
     * 获取SD路径 /storage/emulated/0.
     *
     * @return SD路径 /storage/emulated/0
     */
    public static String getExternalStoragePath() {
        if (isExternalStorageDisable()) return "";
        return getAbsolutePath(Environment.getExternalStorageDirectory());
    }

    /**
     * 获取外存音乐路径 /storage/emulated/0/Music.
     *
     * @return 外存音乐路径 /storage/emulated/0/Music
     */
    public static String getExternalMusicPath() {
        if (isExternalStorageDisable()) return "";
        return getAbsolutePath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC));
    }

    /**
     * 获取外存播客路径 /storage/emulated/0/Podcasts.
     *
     * @return 外存播客路径 /storage/emulated/0/Podcasts
     */
    public static String getExternalPodcastsPath() {
        if (isExternalStorageDisable()) return "";
        return getAbsolutePath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PODCASTS));
    }

    /**
     * 获取外存铃声路径 /storage/emulated/0/Ringtones.
     *
     * @return 外存铃声路径 /storage/emulated/0/Ringtones
     */
    public static String getExternalRingtonesPath() {
        if (isExternalStorageDisable()) return "";
        return getAbsolutePath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_RINGTONES));
    }

    /**
     * 获取外存闹铃路径 /storage/emulated/0/Alarms.
     *
     * @return 外存闹铃路径 /storage/emulated/0/Alarms
     */
    public static String getExternalAlarmsPath() {
        if (isExternalStorageDisable()) return "";
        return getAbsolutePath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_ALARMS));
    }

    /**
     * 获取外存通知路径 /storage/emulated/0/Notifications
     *
     * @return 外存通知路径 /storage/emulated/0/Notifications
     */
    public static String getExternalNotificationsPath() {
        if (isExternalStorageDisable()) return "";
        return getAbsolutePath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_NOTIFICATIONS));
    }

    /**
     * 获取外存图片路径 /storage/emulated/0/Pictures
     *
     * @return 外存图片路径 /storage/emulated/0/Pictures
     */
    public static String getExternalPicturesPath() {
        if (isExternalStorageDisable()) return "";
        return getAbsolutePath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES));
    }

    /**
     * 获取外存影片路径 /storage/emulated/0/Movies
     *
     * @return 外存影片路径 /storage/emulated/0/Movies
     */
    public static String getExternalMoviesPath() {
        if (isExternalStorageDisable()) return "";
        return getAbsolutePath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES));
    }

    /**
     * 获取外存下载路径 /storage/emulated/0/Download.
     *
     * @return 外存下载路径 /storage/emulated/0/Download
     */
    public static String getExternalDownloadsPath() {
        if (isExternalStorageDisable()) return "";
        return getAbsolutePath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS));
    }

    /**
     * 获取外存数码相机图片路径 /storage/emulated/0/DCIM.
     *
     * @return 外存数码相机图片路径 /storage/emulated/0/DCIM
     */
    public static String getExternalDcimPath() {
        if (isExternalStorageDisable()) return "";
        return getAbsolutePath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM));
    }

    /**
     * 获取外存文档路径 /storage/emulated/0/documents.
     *
     * @return 外存文档路径 /storage/emulated/0/documents
     */
    public static String getExternalDocumentsPath() {
        if (isExternalStorageDisable()) return "";
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return getAbsolutePath(Environment.getExternalStorageDirectory()) + PathConstant.documents;
        }
        return getAbsolutePath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS));
    }

    /**
     * 获取外存应用数据路径 /storage/emulated/0/Android/data/package.
     *
     * @return 外存应用数据路径 /storage/emulated/0/Android/data/package
     */
    public static String getExternalAppDataPath(@NonNull Context context) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        if (isExternalStorageDisable()) return "";
        File externalCacheDir = context.getExternalCacheDir();
        if (externalCacheDir == null) return "";
        return getAbsolutePath(externalCacheDir.getParentFile());
    }

    /**
     * 获取外存应用缓存路径 /storage/emulated/0/Android/data/package/cache.
     *
     * @return 外存应用缓存路径 /storage/emulated/0/Android/data/package/cache
     */
    public static String getExternalAppCachePath(@NonNull Context context) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        if (isExternalStorageDisable()) return "";
        return getAbsolutePath(context.getExternalCacheDir());
    }

    /**
     * 获取外存应用文件路径 /storage/emulated/0/Android/data/package/files.
     *
     * @return 外存应用文件路径 /storage/emulated/0/Android/data/package/files
     */
    public static String getExternalAppFilesPath(@NonNull Context context) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        if (isExternalStorageDisable()) return "";
        return getAbsolutePath(context.getExternalFilesDir(null));
    }

    /**
     * 获取外存应用音乐路径 /storage/emulated/0/Android/data/package/files/Music.
     *
     * @return 外存应用音乐路径 /storage/emulated/0/Android/data/package/files/Music
     */
    public static String getExternalAppMusicPath(@NonNull Context context) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        if (isExternalStorageDisable()) return "";
        return getAbsolutePath(context.getExternalFilesDir(Environment.DIRECTORY_MUSIC));
    }

    /**
     * 获取外存应用播客路径 /storage/emulated/0/Android/data/package/files/Podcasts.
     *
     * @return 外存应用播客路径 /storage/emulated/0/Android/data/package/files/Podcasts
     */
    public static String getExternalAppPodcastsPath(@NonNull Context context) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        if (isExternalStorageDisable()) return "";
        return getAbsolutePath(context.getExternalFilesDir(Environment.DIRECTORY_PODCASTS));
    }

    /**
     * 获取外存应用铃声路径 /storage/emulated/0/Android/data/package/files/Ringtones.
     *
     * @return 外存应用铃声路径 /storage/emulated/0/Android/data/package/files/Ringtones
     */
    public static String getExternalAppRingtonesPath(@NonNull Context context) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        if (isExternalStorageDisable()) return "";
        return getAbsolutePath(context.getExternalFilesDir(Environment.DIRECTORY_RINGTONES));
    }

    /**
     * 获取外存应用闹铃路径 /storage/emulated/0/Android/data/package/files/Alarms.
     *
     * @return 外存应用闹铃路径 /storage/emulated/0/Android/data/package/files/Alarms
     */
    public static String getExternalAppAlarmsPath(@NonNull Context context) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        if (isExternalStorageDisable()) return "";
        return getAbsolutePath(context.getExternalFilesDir(Environment.DIRECTORY_ALARMS));
    }

    /**
     * 获取外存应用通知路径 /storage/emulated/0/Android/data/package/files/Notifications.
     *
     * @return 外存应用通知路径 /storage/emulated/0/Android/data/package/files/Notifications
     */
    public static String getExternalAppNotificationsPath(@NonNull Context context) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        if (isExternalStorageDisable()) return "";
        return getAbsolutePath(context.getExternalFilesDir(Environment.DIRECTORY_NOTIFICATIONS));
    }

    /**
     * 获取外存应用图片路径 /storage/emulated/0/Android/data/package/files/Pictures.
     *
     * @return 外存应用图片路径 /storage/emulated/0/Android/data/package/files/Pictures
     */
    public static String getExternalAppPicturesPath(@NonNull Context context) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        if (isExternalStorageDisable()) return "";
        return getAbsolutePath(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES));
    }

    /**
     * 获取外存应用影片路径 /storage/emulated/0/Android/data/package/files/Movies.
     *
     * @return 外存应用影片路径 /storage/emulated/0/Android/data/package/files/Movies
     */
    public static String getExternalAppMoviesPath(@NonNull Context context) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        if (isExternalStorageDisable()) return "";
        return getAbsolutePath(context.getExternalFilesDir(Environment.DIRECTORY_MOVIES));
    }

    /**
     * 获取外存应用下载路径 /storage/emulated/0/Android/data/package/files/Download.
     *
     * @return 外存应用下载路径 /storage/emulated/0/Android/data/package/files/Download
     */
    public static String getExternalAppDownloadPath(@NonNull Context context) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        if (isExternalStorageDisable()) return "";
        return getAbsolutePath(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS));
    }

    /**
     * 获取外存应用数码相机图片路径 /storage/emulated/0/Android/data/package/files/DCIM.
     *
     * @return 外存应用数码相机图片路径 /storage/emulated/0/Android/data/package/files/DCIM
     */
    public static String getExternalAppDcimPath(@NonNull Context context) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        if (isExternalStorageDisable()) return "";
        return getAbsolutePath(context.getExternalFilesDir(Environment.DIRECTORY_DCIM));
    }

    /**
     * 获取外存应用文档路径 /storage/emulated/0/Android/data/package/files/documents.
     *
     * @return 外存应用文档路径 /storage/emulated/0/Android/data/package/files/documents
     */
    public static String getExternalAppDocumentsPath(@NonNull Context context) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        if (isExternalStorageDisable()) return "";
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return getAbsolutePath(context.getExternalFilesDir(null)) + PathConstant.documents;
        }
        return getAbsolutePath(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS));
    }

    /**
     * 获取外存应用OBB路径 /storage/emulated/0/Android/obb/package.
     *
     * @return 外存应用OBB路径 /storage/emulated/0/Android/obb/package
     */
    public static String getExternalAppObbPath(@NonNull Context context) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        if (isExternalStorageDisable()) return "";
        return getAbsolutePath(context.getObbDir());
    }

    /**
     * 是否挂在外存储
     *
     * @return true 未挂载 false挂载
     */
    private static boolean isExternalStorageDisable() {
        return !Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    private static String getAbsolutePath(final File file) {
        if (file == null) return "";
        return file.getAbsolutePath();
    }
}
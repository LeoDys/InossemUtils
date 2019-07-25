package com.inossem_library.other.clean;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;

import com.inossem_library.exception.ExceptionEnum;
import com.inossem_library.exception.InossemException;

import java.io.File;

/**
 * 清理相关
 *
 * @author LinH
 */
public class CleanUtils {
    /**
     * 内部SP路径
     */
    public static final String SHARED_PREFS = "shared_prefs";
    /**
     * 内部数据库路径
     */
    public static final String DATABASES = "databases";

    /**
     * 清除内部缓存
     */
    public static boolean cleanInternalCache(@NonNull Context context) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        return deleteFilesInDir(context.getCacheDir());
    }

    /**
     * 清理内部文件
     */
    public static boolean cleanInternalFiles(@NonNull Context context) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        return deleteFilesInDir(context.getFilesDir());
    }

    /**
     * 清理内部数据库
     */
    public static boolean cleanInternalDbs(@NonNull Context context) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        return deleteFilesInDir(new File(context.getFilesDir().getParent(), DATABASES));
    }

    /**
     * 清除内部SP
     */
    public static boolean cleanInternalSp(@NonNull Context context) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        return deleteFilesInDir(new File(context.getFilesDir().getParent(), SHARED_PREFS));
    }

    /**
     * 清除外部缓存
     * Environment.MEDIA_MOUNTED 存储媒体(包含卡与内存储存)已经挂载，并且挂载点可读/写
     * Environment.getExternalStorageState()返回主共享/外部存储媒体的当前状态。
     */
    public static boolean cleanExternalCache(@NonNull Context context) {
        if (context == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "context can not null");
        }
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) && deleteFilesInDir(context.getExternalCacheDir());
    }

    /**
     * 删除目录中的文件
     */
    private static boolean deleteFilesInDir(final File dir) {
        // File为空，则返回false
        if (dir == null) {
            return false;
        }
        // File不存在，则返回true
        if (!dir.exists()) {
            return !dir.exists();
        }
        // File不是目录，则返回false
        if (!dir.isDirectory()) {
            return dir.isDirectory();
        }
        //listFiles()方法是返回某个目录下所有文件和目录的绝对路径
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (file.isFile()) {
                    return file.delete();
                } else if (file.isDirectory()) {
                    return deleteDir(file);
                }
            }
        }
        return true;
    }

    /**
     * 全部删除
     */
    private static boolean deleteDir(final File dir) {
        if (dir == null) {
            return false;
        }
        // File不存在，则返回true
        if (!dir.exists()) {
            return !dir.exists();
        }
        // File不是目录，则返回false
        if (!dir.isDirectory()) {
            return dir.isDirectory();
        }
        //listFiles()方法是返回某个目录下所有文件和目录的绝对路径
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (file.isFile()) {
                    if (!file.delete()) {
                        return false;
                    }
                } else if (file.isDirectory()) {
                    if (!deleteDir(file)) {
                        return false;
                    }
                }
            }
        }
        return dir.delete();
    }
}
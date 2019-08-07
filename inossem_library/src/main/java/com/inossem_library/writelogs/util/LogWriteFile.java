package com.inossem_library.writelogs.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;

import com.inossem_library.writelogs.constant.LogWriteConstant;
import com.jzxiang.pickerview.utils.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LogWriteFile {

    /**
     * 获取日志根目录
     *
     * @return 日志根目录
     * @author leij
     */
    static String getRootPath() {
        String rootPath = Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator + LogWriteConstant.LOG_ROOT_PATH;
        return rootPath;
    }

    /**
     * 获取指定类型日志根目录
     *
     * @param type 日志类型
     * @return 指定类型日志根目录
     * @author leij
     */
    private static String getTypePath(String type) {
        String typePath = getRootPath() + File.separator + type + File.separator;
        return typePath;
    }

    /**
     * 保存指定类型日志
     *
     * @param context     上下文
     * @param type        日志类型
     * @param information 日志信息
     * @return 日志保存结果，true成功，false失败
     * @author leij
     */
    static boolean saveLog(Context context, String type, String information) {
        boolean result = false;
        FileOutputStream fos = null;
        String fileName = null;
        String path = null;
        String formatType = type.replace("/", "-");
        try {
            // 外置sd卡是否存在
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                // 向SD卡中存日志 获取指定类型日志根目录
                path = getTypePath(type);
                // 目录不存在就新建立文件夹
                File dir = new File(path);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                // 从sp中取出要写入的文件的名字
                fileName = LogWriteAloneUtil.getString(context, LogWriteConstant.LOG_SPNAME, formatType, "");
                // 如果文件名是空的   新建文件名并保存到sp中
                if ("".equals(fileName)) {
                    fileName = LogWriteConstant.SD_FILE_NAME + "-" + formatType + "-"
                            + LogWriteAloneUtil.getCurrentStringDate(LogWriteConstant.FILE_DATE_TEMPLATE) + "-" + LogWriteConstant.FILE_SUFFIX;
                    initLog(context, path, fileName);
                    LogWriteAloneUtil.putString(context, LogWriteConstant.LOG_SPNAME, formatType, fileName);
                }
                // 整理目录下文件，策略：超过最大范围，超出最长时间，清除最早日志
                clearFile(path);
                // 检查当前日志文件，策略超过单个日志最大保存大小，重命名，并指定新的log日志文件名保存到SharedPreferences中
                boolean flag = checkFile(context, path, fileName);
                if (flag) {
                    fileName = LogWriteConstant.SD_FILE_NAME + "-" + formatType + "-"
                            + LogWriteAloneUtil.getCurrentStringDate(LogWriteConstant.FILE_DATE_TEMPLATE) + "-" + LogWriteConstant.FILE_SUFFIX;
                    initLog(context, path, fileName);
                    LogWriteAloneUtil.putString(context, LogWriteConstant.LOG_SPNAME, formatType, fileName);
                }
                fos = new FileOutputStream(path + fileName, true);
                String top = LogWriteConstant.TOP_MESSAGE + LogWriteAloneUtil.getCurrentStringDate(LogWriteConstant.LOG_DATE_TEMPLATE)
                        + "\n**********************************\n";
                fos.write(top.getBytes());
                fos.write(information.getBytes());
                fos.write(LogWriteConstant.BOTTOM_MESSAGE.getBytes());
                fos.flush();
                fos.close();
                result = true;
            } else {
                result = false;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            result = false;
        } catch (IOException e) {
            e.printStackTrace();
            result = false;
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 初始化日志文件内的设备信息
     *
     * @param context  上下文
     * @param path     文件路径
     * @param fileName 文件名称
     * @return 手机的设备信息
     * @author leij
     */
    private static void initLog(Context context, String path, String fileName) throws IOException {
        String information = collectDeviceInfo(context);
        saveInitLog(path, fileName, information);
    }

    /**
     * 收集设备信息
     *
     * @param context 上下文
     * @return 手机的设备信息
     * @author leij
     */
    private static String collectDeviceInfo(Context context) {
        StringBuffer sb = new StringBuffer();// 保存异常信息
        Map<String, String> infos = new HashMap<String, String>();
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";
                infos.put("versionName", versionName);
                infos.put("versionCode", versionCode);
            }
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        Field[] fields = Build.class.getDeclaredFields(); // 获取当前类所有静态属性
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (Map.Entry<String, String> entry : infos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key + "=" + value + "\n");
        }
        return sb.toString();
    }

    /**
     * 保存log日志的初的初始化信息（手机信息）
     *
     * @param path        文件路径
     * @param fileName    文件名称
     * @param information 保存的信息
     * @author leij
     */
    private static void saveInitLog(String path, String fileName, String information)
            throws IOException {
        FileOutputStream fos = new FileOutputStream(path + fileName, true);
        String top = LogWriteConstant.TOP_MESSAGE + "CreateTime:" + LogWriteAloneUtil.getCurrentStringDate(LogWriteConstant.LOG_DATE_TEMPLATE)
                + "\n***********************************************\n";
        fos.write(top.getBytes());
        fos.write(information.getBytes());
        fos.write(LogWriteConstant.BOTTOM_MESSAGE.getBytes());
        fos.flush();
        fos.close();
    }

    /**
     * 检查日志文件是否超过指定单个文件大小
     *
     * @param context  上下文
     * @param path     文件路径
     * @param fileName 文件名称
     * @return 是否超过指定单个文件大小，true超过大小，false没超过
     * @author leij
     */
    private static boolean checkFile(Context context, String path, String fileName) {
        boolean flag = false;
        File file = new File(path, fileName);
        if (file.exists()) {
            if (file.length() > LogWriteConstant.MAX_FILE_SIZE) {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 清理日志文件，超过总大小，超过指定保存时间，删除最早的日志文件
     *
     * @param path 文件路径
     * @return 返回删除的文件全路径，返回null时表示没有超总大小和超过指定保存时间的日志文件
     * @author leij
     */
    private static String clearFile(String path) {
        String absolutePath = null;
        long totalSize = 0l;
        File dir = new File(path);
        if (dir.exists() && dir.isDirectory()) {
            File[] listFiles = dir.listFiles();
            List<String> list = new ArrayList<String>();
            for (File file : listFiles) {
                // 累加算出该目录下的所有文件大小
                totalSize = totalSize + file.length();
                list.add(file.getName());
            }
            // 获取最早的log文件名称
            String earlistFileName = getEarliestFileName(list);
            if (earlistFileName != null) {
                // 超过目录指定最大范围后删除最早日志文件，返回删除日志文件的全路径
                absolutePath = overRangeDeleteFile(totalSize, LogWriteConstant.MAX_TOTAL_FILE_SIZE, path,
                        earlistFileName);
                if (absolutePath == null) {
                    // 超过指定保存最长时间后删除最早日志文件，返回删除日志文件的全路径
                    absolutePath = overTimeDeleteFile(LogWriteConstant.MAX_FILE_SAVE_TIME, path, earlistFileName);
                }
            }
        }

        return absolutePath;
    }

    /**
     * 总大小超过指定大小后删除指定log日志文件
     *
     * @param totalSize   文件总大小
     * @param limitedSize 文件限制的大小
     * @param path        文件路径
     * @param fileName    文件名称
     * @return 返回删除的文件全路径，返回null时表示没有超指定大小日志文件
     * @author leij
     */
    private static String overRangeDeleteFile(long totalSize, long limitedSize, String path,
                                              String fileName) {
        String deleteFileName = null;
        if (totalSize > limitedSize) {
            File file = new File(path, fileName);
            deleteFileName = file.getAbsolutePath();
            if (file.exists() && file.isFile()) {
                boolean flag = file.delete();
                if (!flag) {
                    deleteFileName = null;
                }
            }
        }
        return deleteFileName;
    }

    /**
     * 删除超时的日志文件
     *
     * @param maxFileSaveTime 最大保存时间，毫秒单位
     * @param path            文件路径
     * @param fileName        文件名称
     * @return 返回删除的文件全路径，返回null时表示没有超时日志文件
     * @author leij
     */
    private static String overTimeDeleteFile(long maxFileSaveTime, String path, String fileName) {
        String deleteFileName = null;
        String[] names = fileName.split("-");
        if (names.length == 4) {
            long creatTime = LogWriteAloneUtil.getTimeStampFromString(LogWriteConstant.FILE_DATE_TEMPLATE, names[2]);
            long currentTime = System.currentTimeMillis();
            if ((currentTime - creatTime) > maxFileSaveTime) {
                File file = new File(path, fileName);
                deleteFileName = file.getAbsolutePath();
                boolean flag = file.delete();
                if (!flag) {
                    deleteFileName = null;
                }
            }
        }
        return deleteFileName;
    }

    //

    /**
     * 获取集合中最早日志文件名
     *
     * @param list 文件名集合
     * @return 返回最早的日志文件名
     * @author leij
     */
    private static String getEarliestFileName(List<String> list) {
        String result = null;
        if (list.size() >= 2) {
            result = Collections.min(list);
        }
        return result;
    }

}

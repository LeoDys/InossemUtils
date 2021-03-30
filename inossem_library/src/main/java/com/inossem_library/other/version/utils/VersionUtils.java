/*******************************************************************************
 * @(#)MD5.java 2015年7月2日
 *
 * Copyright 2015 emrubik Group Ltd. All rights reserved.
 * EMRubik PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.inossem_library.other.version.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;

import com.inossem_library.other.version.constant.VersionConstant;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

/**
 * version 工具类
 *
 * @author LeoDys E-mail:changwen.sun@inossem.com 2020/4/8 11:13
 * @version 1.0.8
 * @since 1.0.8
 */
public class VersionUtils {

    private static final int HEX = 16;

    private static final int ADD_NUMBER = 256;

    private static final String ALGORITHM = "MD5"; // 算法

    // 十六进制下数字到字符的映射数组
    private static final String[] HEX_DIGITS = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    /**
     * 利用MD5加密文件
     *
     * @param file 待加密的文件对象
     * @return 加密后的字符串
     * @author leij
     */
    public static String encryptFile(File file) {
        String result = "";
        try {
            // 确定计算方法
            MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHM);
            FileInputStream mFileInputStream = new FileInputStream(file);
            byte[] buffer = new byte[1024 * 8];
            int length;
            while ((length = mFileInputStream.read(buffer)) != -1) {
                messageDigest.update(buffer, 0, length);
            }
            result = byteArrayToHexString(messageDigest.digest());
            mFileInputStream.close();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            result = "";
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            result = "";
        } catch (IOException e) {
            e.printStackTrace();
            result = "";
        }
        return result;
    }

    /**
     * 利用MD5进行加密
     *
     * @param data 待加密的字符串
     * @return 加密后的字符串
     * @author leij
     */
    // MD5加密
    public static String encrypt(String data) {
        String result = "";
        try {
            if (data != null && !"".equals(data)) {
                // 确定计算方法
                MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHM);
                // MD5加密
                byte[] md5Result = messageDigest.digest(data.getBytes());
                // 字节数组转成十六进制字符串
                result = byteArrayToHexString(md5Result);
                return result.toLowerCase(Locale.getDefault());
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            result = "";
        }
        return result;
    }

    /**
     * 转换字节数组为十六进制字符串
     *
     * @param b 字节数组
     * @return 十六进制字符串
     * @author leij
     */
    private static String byteArrayToHexString(byte[] b) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    /**
     * 将字节转化成十六进制形式的字符串
     *
     * @param b 字节数组
     * @return 十六进制字符串
     * @author leij
     */
    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n = ADD_NUMBER + n;
        }
        int d1 = n / HEX;
        int d2 = n % HEX;
        return HEX_DIGITS[d1] + HEX_DIGITS[d2];
    }

    //将字节转成B或KB或MB或GB，保留2位小时
    public static String getPrintSize(long size) {
        //如果字节数少于1024，则直接以B为单位，否则先除于1024，后3位因太少无意义
        if (size < 1024) {
            return String.valueOf(size) + "B";
        } else {
            size = size / 1024;
        }
        //如果原字节数除于1024之后，少于1024，则可以直接以KB作为单位
        //因为还没有到达要使用另一个单位的时候
        //接下去以此类推
        if (size < 1024) {
            return String.valueOf(size) + "KB";
        } else {
            size = size / 1024;
        }
        if (size < 1024) {
            //因为如果以MB为单位的话，要保留最后1位小数，
            //因此，把此数乘以100之后再取余
            size = size * 100;
            return String.valueOf((size / 100)) + "."
                    + String.valueOf((size % 100)) + "MB";
        } else {
            //否则如果要以GB为单位的，先除于1024再作同样的处理
            size = size * 100 / 1024;
            return String.valueOf((size / 100)) + "."
                    + String.valueOf((size % 100)) + "GB";
        }
    }

    public static boolean isApkExist(Context context, String fileName) {
        File file = new File(getDownloadApkPath(context), fileName);
        if (file.exists() && file.isFile()) {
            return true;
        } else {
            return false;
        }
    }

    public static String getDownloadApkPath(Context context) {
        return VersionUtils.getRootPath(context) + File.separator + VersionConstant.DOWNLOAD_ROOT_PATH
                + File.separator + VersionConstant.DOWNLOAD_APK;
    }

    public static String getErroMessage(Throwable exception) {
        String result;
        if (exception instanceof SocketTimeoutException) {
            result = "socket time out : ";
        } else {
            result = "download error : ";
        }
        return result;
    }

    /**
     * 得到SD卡根目录.
     */
    public static File getRootPath(Context context) {
        if (sdCardIsAvailable()) {
            return Environment.getExternalStorageDirectory(); // 取得sdcard文件路径
        } else {
            return context.getFilesDir();
        }
    }

    /**
     * SD卡是否可用.
     */
    public static boolean sdCardIsAvailable() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File sd = new File(Environment.getExternalStorageDirectory().getPath());
            return sd.canWrite();
        } else
            return false;
    }


    public static void exit(Context mContext) {
        int currentVersion = android.os.Build.VERSION.SDK_INT;
        if (currentVersion > android.os.Build.VERSION_CODES.ECLAIR_MR1) {
            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(startMain);
            System.exit(0);
        } else {// android2.1
            ActivityManager am = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
            am.restartPackage(mContext.getPackageName());
        }
    }

}

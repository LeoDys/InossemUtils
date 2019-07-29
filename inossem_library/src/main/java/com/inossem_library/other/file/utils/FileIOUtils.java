package com.inossem_library.other.file.utils;

import com.inossem_library.other.file.constant.FileIOConstant;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件相关
 *
 * @author Lin
 */
public final class FileIOUtils {

    /**
     * 将输入流写入文件
     * {@code <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>}读文件权限
     * {@code <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>}写文件权限
     *
     * @param filePath 文件的路径
     * @param is       输入流
     * @return True成功 False错误
     */
    public static boolean writeFileFromIS(final String filePath, final InputStream is) throws Throwable {
        return writeFileFromIS(getFileByPath(filePath), is, false);
    }

    /**
     * 将输入流写入文件
     * {@code <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>}读文件权限
     * {@code <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>}写文件权限
     *
     * @param filePath 文件的路径
     * @param is       输入流
     * @param append   true,则字节将写入文件的末尾而不是开头
     * @return True成功 False错误
     */
    public static boolean writeFileFromIS(final String filePath, final InputStream is, final boolean append) throws Throwable {
        return writeFileFromIS(getFileByPath(filePath), is, append);
    }

    /**
     * 将输入流写入文件
     * {@code <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>}读文件权限
     * {@code <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>}写文件权限
     *
     * @param file 文件
     * @param is   输入流
     * @return True成功 False错误
     */
    public static boolean writeFileFromIS(final File file, final InputStream is) throws Throwable {
        return writeFileFromIS(file, is, false);
    }

    /**
     * 将输入流写入文件
     * {@code <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>}读文件权限
     * {@code <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>}写文件权限
     *
     * @param file   文件
     * @param is     输入流
     * @param append true,则字节将写入文件的末尾而不是开头
     * @return True成功 False错误
     */
    public static boolean writeFileFromIS(final File file, final InputStream is, final boolean append) throws Throwable {
        if (!createOrExistsFile(file) || is == null) {
            return false;
        }
        OutputStream os = null;
        try {
            os = new BufferedOutputStream(new FileOutputStream(file, append));
            byte[] data = new byte[FileIOConstant.sBufferSize];
            for (int len; (len = is.read(data)) != -1; ) {
                /*
                 *  b     数据
                 *  off   数据中的起始偏移量
                 *  len   要写入的字节数
                 */
                os.write(data, 0, len);
            }
            return true;
        } catch (Throwable e) {
            e.printStackTrace();
            return false;
        } finally {
            // 各种关闭 finally 一定会被执行
            try {
                is.close();
            } catch (Throwable e) {
                e.printStackTrace();
            }
            try {
                if (os != null) {
                    os.close();
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 将字节数组写入文件
     * {@code <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>}读文件权限
     * {@code <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>}写文件权限
     *
     * @param filePath 文件的路径
     * @param bytes    字节数组
     * @return True成功 False错误
     */
    public static boolean writeFileFromBytesByStream(final String filePath, final byte[] bytes) throws Throwable {
        return writeFileFromBytesByStream(getFileByPath(filePath), bytes, false);
    }

    /**
     * 将字节数组写入文件
     *
     * @param filePath 文件的路径
     * @param bytes    字节数组
     * @param append   true,则字节将写入文件的末尾而不是开头
     * @return True成功 False错误
     */
    public static boolean writeFileFromBytesByStream(final String filePath, final byte[] bytes, final boolean append) throws Throwable {
        return writeFileFromBytesByStream(getFileByPath(filePath), bytes, append);
    }

    /**
     * 将字节数组写入文件
     * {@code <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>}读文件权限
     * {@code <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>}写文件权限
     *
     * @param file  文件
     * @param bytes 字节数组
     * @return True成功 False错误
     */
    public static boolean writeFileFromBytesByStream(final File file, final byte[] bytes) throws Throwable {
        return writeFileFromBytesByStream(file, bytes, false);
    }

    /**
     * 将字节数组写入文件
     * {@code <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>}读文件权限
     * {@code <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>}写文件权限
     *
     * @param file   文件
     * @param bytes  字节数组
     * @param append true,则字节将写入文件的末尾而不是开头
     * @return True成功 False错误
     */
    public static boolean writeFileFromBytesByStream(final File file, final byte[] bytes, final boolean append) throws Throwable {
        if (bytes == null || !createOrExistsFile(file)) {
            return false;
        }
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(file, append));
            bos.write(bytes);
            return true;
        } catch (Throwable e) {
            e.printStackTrace();
            return false;
        } finally {
            // 各种关闭 finally 一定会被执行
            try {
                if (bos != null) {
                    bos.close();
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 将字节数组写入文件(可强制)
     * {@code <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>}读文件权限
     * {@code <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>}写文件权限
     *
     * @param filePath 文件的路径
     * @param bytes    字节数组
     * @param isForce  是否强制写入磁盘
     * @return True成功 False错误
     */
    public static boolean writeFileFromBytesByChannel(final String filePath, final byte[] bytes, final boolean isForce) {
        return writeFileFromBytesByChannel(getFileByPath(filePath), bytes, false, isForce);
    }

    /**
     * 将字节数组写入文件(可强制)
     * {@code <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>}读文件权限
     * {@code <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>}写文件权限
     *
     * @param filePath 文件的路径
     * @param bytes    字节数组
     * @param append   true,则字节将写入文件的末尾而不是开头
     * @param isForce  是否强制写入磁盘
     * @return True成功 False错误
     */
    public static boolean writeFileFromBytesByChannel(final String filePath, final byte[] bytes, final boolean append, final boolean isForce) {
        return writeFileFromBytesByChannel(getFileByPath(filePath), bytes, append, isForce);
    }

    /**
     * 将字节数组写入文件(可强制)
     * {@code <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>}读文件权限
     * {@code <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>}写文件权限
     *
     * @param file    文件
     * @param bytes   字节数组
     * @param isForce 是否强制写入磁盘
     * @return True成功 False错误
     */
    public static boolean writeFileFromBytesByChannel(final File file, final byte[] bytes, final boolean isForce) {
        return writeFileFromBytesByChannel(file, bytes, false, isForce);
    }

    /**
     * 将字节数组写入文件(可强制)
     * {@code <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>}读文件权限
     * {@code <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>}写文件权限
     *
     * @param file    文件
     * @param bytes   字节数组
     * @param append  true,则字节将写入文件的末尾而不是开头
     * @param isForce 是否强制写入磁盘
     * @return True成功 False错误
     */
    public static boolean writeFileFromBytesByChannel(final File file, final byte[] bytes, final boolean append, final boolean isForce) {
        if (bytes == null) {
            return false;
        }
        FileChannel fc = null;
        try {
            fc = new FileOutputStream(file, append).getChannel();
            fc.position(fc.size());
            fc.write(ByteBuffer.wrap(bytes));
            /*
             * Force
             * force方法会把所有未写磁盘的数据都强制写入磁盘.
             * 这是因为在操作系统中出于性能考虑回把数据放入缓冲区,所以不能保证数据在调用write写入文件通道后就及时写到磁盘上了,除非手动调用force方法.
             * force方法需要一个布尔参数,代表是否把meta data也一并强制写入.
             */
            if (isForce) {
                fc.force(true);
            }
            return true;
        } catch (Throwable e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (fc != null) {
                    fc.close();
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 将字节数组写入文件
     * {@code <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>}读文件权限
     * {@code <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>}写文件权限
     *
     * @param filePath 文件的路径
     * @param bytes    字节数组
     * @param isForce  是否强制写入磁盘
     * @return True成功 False错误
     */
    public static boolean writeFileFromBytesByMap(final String filePath, final byte[] bytes, final boolean isForce) throws Throwable {
        return writeFileFromBytesByMap(filePath, bytes, false, isForce);
    }

    /**
     * 将字节数组写入文件
     * {@code <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>}读文件权限
     * {@code <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>}写文件权限
     *
     * @param filePath 文件的路径
     * @param bytes    字节数组
     * @param append   true,则字节将写入文件的末尾而不是开头
     * @param isForce  是否强制写入磁盘
     * @return True成功 False错误
     */
    public static boolean writeFileFromBytesByMap(final String filePath, final byte[] bytes, final boolean append, final boolean isForce) throws Throwable {
        return writeFileFromBytesByMap(getFileByPath(filePath), bytes, append, isForce);
    }

    /**
     * 将字节数组写入文件
     * {@code <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>}读文件权限
     * {@code <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>}写文件权限
     *
     * @param file    文件
     * @param bytes   字节数组
     * @param isForce 是否强制写入磁盘
     * @return True成功 False错误
     */
    public static boolean writeFileFromBytesByMap(final File file, final byte[] bytes, final boolean isForce) throws Throwable {
        return writeFileFromBytesByMap(file, bytes, false, isForce);
    }

    /**
     * 将字节数组写入文件
     * {@code <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>}读文件权限
     * {@code <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>}写文件权限
     *
     * @param file    文件
     * @param bytes   字节数组
     * @param append  true,则字节将写入文件的末尾而不是开头
     * @param isForce 是否强制写入磁盘
     * @return True成功 False错误
     */
    public static boolean writeFileFromBytesByMap(final File file, final byte[] bytes, final boolean append, final boolean isForce) throws Throwable {
        if (bytes == null || !createOrExistsFile(file)) {
            return false;
        }
        FileChannel fc = null;
        try {
            fc = new FileOutputStream(file, append).getChannel();
            MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_WRITE, fc.size(), bytes.length);
            mbb.put(bytes);
             /*
             * Force
             * force方法会把所有未写磁盘的数据都强制写入磁盘.
             * 这是因为在操作系统中出于性能考虑回把数据放入缓冲区,所以不能保证数据在调用write写入文件通道后就及时写到磁盘上了,除非手动调用force方法.
             * force方法需要一个布尔参数,代表是否把meta data也一并强制写入.
             */
            if (isForce) {
                mbb.force();
            }
            return true;
        } catch (Throwable e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (fc != null) {
                    fc.close();
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 将字符串写入文件
     * {@code <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>}读文件权限
     * {@code <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>}写文件权限
     *
     * @param filePath 文件的路径
     * @param content  上下文
     * @return True成功 False错误
     */
    public static boolean writeFileFromString(final String filePath, final String content) throws Throwable {
        return writeFileFromString(getFileByPath(filePath), content, false);
    }

    /**
     * 将字符串写入文件
     * {@code <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>}读文件权限
     * {@code <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>}写文件权限
     *
     * @param filePath 文件的路径
     * @param content  上下文
     * @param append   true,则字节将写入文件的末尾而不是开头
     * @return True成功 False错误
     */
    public static boolean writeFileFromString(final String filePath, final String content, final boolean append) throws Throwable {
        return writeFileFromString(getFileByPath(filePath), content, append);
    }

    /**
     * 将字符串写入文件
     * {@code <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>}读文件权限
     * {@code <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>}写文件权限
     *
     * @param file    文件
     * @param content The string of content.
     * @return True成功 False错误
     */
    public static boolean writeFileFromString(final File file, final String content) throws Throwable {
        return writeFileFromString(file, content, false);
    }

    /**
     * 将字符串写入文件
     * {@code <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>}读文件权限
     * {@code <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>}写文件权限
     *
     * @param file    文件
     * @param content 上下文
     * @param append  true,则字节将写入文件的末尾而不是开头
     * @return True成功 False错误
     */
    public static boolean writeFileFromString(final File file, final String content, final boolean append) throws Throwable {
        if (file == null || content == null) {
            return false;
        }
        if (!createOrExistsFile(file)) {
            return false;
        }
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(file, append));
            bw.write(content);
            return true;
        } catch (Throwable e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 读取文件到字符串链表中
     * {@code <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>}读文件权限
     * {@code <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>}写文件权限
     *
     * @param filePath 文件的路径
     * @return 文件中的行
     */
    public static List<String> readFile2List(final String filePath) {
        return readFile2List(getFileByPath(filePath), null);
    }

    /**
     * 读取文件到字符串链表中
     * {@code <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>}读文件权限
     * {@code <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>}写文件权限
     *
     * @param filePath    文件路径
     * @param charsetName 字符集
     * @return 文件中的行
     */
    public static List<String> readFile2List(final String filePath, final String charsetName) {
        return readFile2List(getFileByPath(filePath), charsetName);
    }

    /**
     * 读取文件到字符串链表中
     * {@code <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>}读文件权限
     * {@code <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>}写文件权限
     *
     * @param file 文件
     * @return 文件中的行
     */
    public static List<String> readFile2List(final File file) {
        return readFile2List(file, 0, 0x7FFFFFFF, null);
    }

    /**
     * 读取文件到字符串链表中
     * {@code <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>}读文件权限
     * {@code <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>}写文件权限
     *
     * @param file        文件
     * @param charsetName 字符集
     * @return 文件中的行
     */
    public static List<String> readFile2List(final File file, final String charsetName) {
        return readFile2List(file, 0, 0x7FFFFFFF, charsetName);
    }

    /**
     * 读取文件到字符串链表中
     * {@code <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>}读文件权限
     * {@code <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>}写文件权限
     *
     * @param filePath 文件的路径
     * @param st       这一行开始的下标
     * @param end      这一行末端的下标
     * @return 文件中的行
     */
    public static List<String> readFile2List(final String filePath, final int st, final int end) {
        return readFile2List(getFileByPath(filePath), st, end, null);
    }

    /**
     * 读取文件到字符串链表中
     * {@code <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>}读文件权限
     * {@code <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>}写文件权限
     *
     * @param filePath    文件路径
     * @param st          这一行开始的下标
     * @param end         这一行末端的下标
     * @param charsetName 字符集
     * @return 文件中的行
     */
    public static List<String> readFile2List(final String filePath, final int st, final int end, final String charsetName) {
        return readFile2List(getFileByPath(filePath), st, end, charsetName);
    }

    /**
     * 读取文件到字符串链表中
     * {@code <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>}读文件权限
     * {@code <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>}写文件权限
     *
     * @param file 文件
     * @param st   这一行开始的下标
     * @param end  这一行末端的下标
     * @return 文件中的行
     */
    public static List<String> readFile2List(final File file, final int st, final int end) {
        return readFile2List(file, st, end, null);
    }

    /**
     * 读取文件到字符串链表中
     * {@code <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>}读文件权限
     * {@code <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>}写文件权限
     *
     * @param file        文件
     * @param st          这一行开始的下标
     * @param end         这一行末端的下标
     * @param charsetName 字符集
     * @return 文件中的行
     */
    public static List<String> readFile2List(final File file, final int st, final int end, final String charsetName) {
        if (!isFileExists(file)) {
            return null;
        }
        if (st > end) {
            return null;
        }
        BufferedReader reader = null;
        try {
            String line;
            int curLine = 1;
            List<String> list = new ArrayList<>();
            if (isSpace(charsetName)) {
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            } else {
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), charsetName));
            }
            while ((line = reader.readLine()) != null) {
                if (curLine > end) {
                    break;
                }
                if (st <= curLine && curLine <= end) {
                    list.add(line);
                }
                ++curLine;
            }
            return list;
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 读取文件中的字符串
     * {@code <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>}读文件权限
     * {@code <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>}写文件权限
     *
     * @param filePath 文件的路径
     * @return 文件中的字符串
     */
    public static String readFile2String(final String filePath) throws Throwable {
        return readFile2String(getFileByPath(filePath), null);
    }

    /**
     * 读取文件中的字符串
     * {@code <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>}读文件权限
     * {@code <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>}写文件权限
     *
     * @param filePath    文件路径
     * @param charsetName 字符集
     * @return 文件中的字符串
     */
    public static String readFile2String(final String filePath, final String charsetName) throws Throwable {
        return readFile2String(getFileByPath(filePath), charsetName);
    }

    /**
     * 读取文件中的字符串
     * {@code <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>}读文件权限
     * {@code <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>}写文件权限
     *
     * @param file 文件
     * @return 文件中的字符串
     */
    public static String readFile2String(final File file) throws Throwable {
        return readFile2String(file, null);
    }

    /**
     * 读取文件中的字符串
     * {@code <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>}读文件权限
     * {@code <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>}写文件权限
     *
     * @param file        文件
     * @param charsetName 字符集
     * @return 文件中的字符串
     */
    public static String readFile2String(final File file, final String charsetName) throws Throwable {
        byte[] bytes = readFile2BytesByStream(file);
        if (bytes == null) {
            return null;
        }
        if (isSpace(charsetName)) {
            return new String(bytes);
        } else {
            return new String(bytes, charsetName);
        }
    }

    /**
     * 读取文件到字节数组中
     * {@code <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>}读文件权限
     * {@code <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>}写文件权限
     *
     * @param filePath 文件的路径
     * @return 文件中的byte[]
     */
    public static byte[] readFile2BytesByStream(final String filePath) throws Throwable {
        return readFile2BytesByStream(getFileByPath(filePath));
    }

    /**
     * 读取文件到字节数组中
     * {@code <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>}读文件权限
     * {@code <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>}写文件权限
     *
     * @param file 文件
     * @return 文件中的byte[]
     */
    public static byte[] readFile2BytesByStream(final File file) throws Throwable {
        if (!isFileExists(file)) {
            return null;
        }
        return is2Bytes(new FileInputStream(file));
    }

    /**
     * 读取文件路径到字节数组中
     * {@code <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>}读文件权限
     * {@code <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>}写文件权限
     *
     * @param filePath 文件的路径
     * @return 文件中的byte[]
     */
    public static byte[] readFile2BytesByChannel(final String filePath) {
        return readFile2BytesByChannel(getFileByPath(filePath));
    }

    /**
     * 读取文件路径到字节数组中
     * {@code <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>}读文件权限
     * {@code <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>}写文件权限
     *
     * @param file 文件
     * @return 文件中的byte[]
     */
    public static byte[] readFile2BytesByChannel(final File file) {
        if (!isFileExists(file)) {
            return null;
        }
        FileChannel fc = null;
        try {
            /*
            mode 其中参数 mode 的值可选 "r":可读,"w" :可写,"rw":可读性;
            (1)"r"    以只读方式打开.调用结果对象的任何 write 方法都将导致抛出 Throwable.
            (2)"rw"   打开以便读取和写入.
            (3)"rws"  打开以便读取和写入.相对于 "rw","rws" 还要求对"文件的内容"或"元数据"的每个更新都同步写入到基础存储设备.
            (4)"rwd"  打开以便读取和写入.相对于 "rw","rwd" 还要求对"文件的内容"的每个更新都同步写入到基础存储设备.
             */
            /*
            元数据 metadata是"关于数据的数据".
            在文件系统中,数据被包含在文件和文件夹中:
            metadata信息包括:
            "数据是一个文件,一个目录还是一个链接",
            "数据的创建时间(简称ctime)",
            "最后一次修改时间(简称mtime)",
            "数据拥有者",
            "数据拥有群组",
            "访问权限"等等.
             */
            /*
            "rw", "rws", "rwd" 的区别.
            当操作的文件是存储在本地的基础存储设备上时(如硬盘, NandFlash等),"rws" 或 "rwd", "rw" 才有区别.
            当模式是 "rw" 并且 操作的是基础存储设备上的文件:那么,关闭文件时,会将"文件内容的修改"同步到基础存储设备上.至于,"更改文件内容"时,是否会立即同步,取决于系统底层实现.
            当模式是 "rws" 并且 操作的是基础存储设备上的文件:那么,每次"更改文件内容[如write()写入数据]" 或 "修改文件元数据(如文件的mtime)"时,都会将这些改变同步到基础存储设备上.
            当模式是 "rwd" 并且 操作的是基础存储设备上的文件:那么,每次"更改文件内容[如write()写入数据]"时,都会将这些改变同步到基础存储设备上.
             */
            /*
            <a>https://www.cnblogs.com/skywang12345/p/io_26.html</a>
             */
            fc = new RandomAccessFile(file, "r").getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate((int) fc.size());
            while (true) {
                if (!((fc.read(byteBuffer)) > 0)) {
                    break;
                }
            }
            return byteBuffer.array();
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (fc != null) {
                    fc.close();
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 设置缓冲区的大小 默认大小等于8192字节
     * {@code <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>}读文件权限
     * {@code <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>}写文件权限
     *
     * @param bufferSize 缓冲区的大小
     */
    public static void setBufferSize(final int bufferSize) {
        FileIOConstant.sBufferSize = bufferSize;
    }

    /**
     * 根据文件路径获取文件
     * {@code <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>}读文件权限
     * {@code <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>}写文件权限
     *
     * @param filePath 文件路径
     * @return 文件
     */
    private static File getFileByPath(final String filePath) {
        return isSpace(filePath) ? null : new File(filePath);
    }

    /**
     * 判断文件是否存在
     * {@code <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>}读文件权限
     * {@code <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>}写文件权限
     *
     * @param file 文件
     * @return 是否存在
     */
    private static boolean createOrExistsFile(final File file) throws Throwable {
        if (file == null) {
            return false;
        }
        if (file.exists()) {
            return file.isFile();
        }
        if (!createOrExistsDir(file.getParentFile())) {
            return false;
        }
        return file.createNewFile();
    }

    /**
     * 判断目录是否存在
     * {@code <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>}读文件权限
     * {@code <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>}写文件权限
     *
     * @param file 文件
     * @return 是否存在
     */
    private static boolean createOrExistsDir(final File file) {
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }

    /**
     * 判断文件是否存在
     * {@code <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>}读文件权限
     * {@code <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>}写文件权限
     *
     * @param file 文件
     * @return 是否存在
     */
    private static boolean isFileExists(final File file) {
        return file != null && file.exists();
    }

    /**
     * 确定指定的字符集是否为空白
     *
     * @param s 字符集
     * @return 是否为空白
     */
    private static boolean isSpace(final String s) {
        if (s == null) {
            return true;
        }
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 是否流中是否为空
     */
    private static byte[] is2Bytes(final InputStream is) {
        if (is == null) {
            return null;
        }
        ByteArrayOutputStream os = null;
        try {
            os = new ByteArrayOutputStream();
            byte[] b = new byte[FileIOConstant.sBufferSize];
            int len;
            while ((len = is.read(b, 0, FileIOConstant.sBufferSize)) != -1) {
                os.write(b, 0, len);
            }
            return os.toByteArray();
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                is.close();
            } catch (Throwable e) {
                e.printStackTrace();
            }
            try {
                if (os != null) {
                    os.close();
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }
}
package com.zxy.util;

import android.os.Looper;
import android.util.Log;

import com.zxy.custom_compress.exception.InossemCompressException;
import com.zxy.tiny.Tiny;
import com.zxy.tiny.callback.FileBatchCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 强行同步压缩算法
 *
 * @author Leo E-mail:changwen.sun@inossem.com 2019/7/25 20:06
 * @version 1.0.0
 * @since JDK-1.8
 */

public class CompressSyncUtils {

    static List<String> filePaths = new ArrayList<>();
    static CountDownLatch countDownLatch = null;

    /**
     * 多张图片的压缩压缩成file
     *
     * @param files  源文件
     * @param config 配置参数 new对象传入有默认参数设置
     */
    public static List<String> filesCallBack(List<File> files, TinyConfig config) {
        if (config == null) {
            throw new InossemCompressException("TinyConfig null,please check");
        }
        final File[] fileArray = Utils.getFilesArray(files);
        if (fileArray == null || fileArray.length == 0) return null;

        final Tiny.FileCompressOptions options = new Tiny.FileCompressOptions();
        Utils.setOptions(config, options);
        filePaths = new ArrayList<>();
        // 创建一个初始值为5的倒数计数器
        countDownLatch = new CountDownLatch(1);
        Log.i("tag", "外部：" + Thread.currentThread().getId());

        new Thread(new Runnable() {
            @Override
            public void run() {

                Tiny.getInstance().source(fileArray).batchAsFile().withOptions(options).batchCompress(new FileBatchCallback() {
                    @Override
                    public void callback(boolean isSuccess, String[] outfiles, Throwable t) {
                        Log.i("tag", "内部：" + Thread.currentThread().getId());
                        // 倒数器减1
                        Looper.prepare();
                        filePaths = Arrays.asList(outfiles);
                        countDownLatch.countDown();
                        Looper.loop();
                    }
                });
            }
        }).start();

        try {
            // 阻塞当前线程，直到倒数计数器倒数到0
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return filePaths;

    }

}

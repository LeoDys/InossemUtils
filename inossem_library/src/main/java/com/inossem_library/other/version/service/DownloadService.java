package com.inossem_library.other.version.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;

import com.inossem_library.other.version.bean.UpdateAppBean;
import com.inossem_library.other.version.utils.AppUpdateUtils;
import com.inossem_library.other.version.utils.HttpManager;

import java.io.File;

import androidx.annotation.Nullable;


/**
 * 后台下载
 */
public class DownloadService extends Service {

    public static boolean isRunning = false;
    private DownloadBinder binder = new DownloadBinder();

    public static void bindService(Context context, ServiceConnection connection) {
        Intent intent = new Intent(context, DownloadService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent);
        } else {
            context.startService(intent);
        }
        context.bindService(intent, connection, Context.BIND_AUTO_CREATE);
        isRunning = true;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        isRunning = false;
        return super.onUnbind(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // 返回自定义的DownloadBinder实例
        return binder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 下载模块
     */
    private void startDownload(UpdateAppBean updateApp, final DownloadCallback callback) {
        String apkUrl = updateApp.getUrl();
        File appDir = new File(updateApp.getTargetPath());
        if (!appDir.exists()) {
            appDir.mkdirs();
        }
        updateApp.getHttpManager().download(apkUrl, updateApp.getToken(), appDir.getAbsolutePath(), updateApp.getFileName(), new FileDownloadCallBack(callback));
    }

    private void stop(String contentText) {
        close();
    }

    private void close() {
        stopSelf();
        isRunning = false;
    }

    /**
     * 进度条回调接口
     */
    public interface DownloadCallback {
        /**
         * 开始
         */
        void onStart();

        /**
         * 进度
         *
         * @param progress  进度 0.00 -1.00 ，总大小
         * @param totalSize 总大小 单位B
         */
        void onProgress(float progress, long totalSize);

        /**
         * 总大小
         *
         * @param totalSize 单位B
         */
        void setMax(long totalSize);

        /**
         * 下载完了
         *
         * @param file 下载的app
         * @return true ：下载完自动跳到安装界面，false：则不进行安装
         */
        boolean onFinish(File file);

        /**
         * 下载异常
         *
         * @param msg 异常信息
         */
        void onError(Throwable msg);

        /**
         * 当应用处于前台，准备执行安装程序时候的回调，
         *
         * @param file 当前安装包
         * @return false 默认 false ,当返回时 true 时，需要自己处理 ，前提条件是 onFinish 返回 false 。
         */
        boolean onInstallAppAndAppOnForeground(File file);
    }

    /**
     * DownloadBinder中定义了一些实用的方法
     *
     * @author user
     */
    public class DownloadBinder extends Binder {
        /**
         * 开始下载
         *
         * @param updateApp 新app信息
         * @param callback  下载回调
         */
        public void start(UpdateAppBean updateApp, DownloadCallback callback) {
            //下载
            startDownload(updateApp, callback);
        }

        public void stop(String msg) {
            DownloadService.this.stop(msg);
        }
    }

    class FileDownloadCallBack implements HttpManager.FileCallback {
        private final DownloadCallback mCallBack;
        int oldRate = 0;

        public FileDownloadCallBack(@Nullable DownloadCallback callback) {
            super();
            this.mCallBack = callback;
        }

        @Override
        public void onBefore() {
            //初始化通知栏
            if (mCallBack != null) {
                mCallBack.onStart();
            }
        }

        @Override
        public void onProgress(float progress, long total) {
            //做一下判断，防止自回调过于频繁，造成更新通知栏进度过于频繁，而出现卡顿的问题。
            int rate = Math.round(progress * 100);
            if (oldRate != rate) {
                if (mCallBack != null) {
                    mCallBack.setMax(total);
                    mCallBack.onProgress(progress, total);
                }
                //重新赋值
                oldRate = rate;
            }
        }

        @Override
        public void onError(Throwable error) {
            //App前台运行
            if (mCallBack != null) {
                mCallBack.onError(error);
            }
            try {
                close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }

        @Override
        public void onResponse(File file) {
            if (mCallBack != null) {
                if (!mCallBack.onFinish(file)) {
                    close();
                    return;
                }
            }
            try {
                if (AppUpdateUtils.isAppOnForeground(DownloadService.this) /*|| mBuilder == null*/) {
                    //App前台运行
                    if (mCallBack != null) {
                        boolean temp = mCallBack.onInstallAppAndAppOnForeground(file);
                        if (!temp) {
                            AppUpdateUtils.installApp(DownloadService.this, file);
                        }
                    } else {
                        AppUpdateUtils.installApp(DownloadService.this, file);
                    }


                } else {
                }
                //下载完自杀
                close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close();
            }
        }
    }
}

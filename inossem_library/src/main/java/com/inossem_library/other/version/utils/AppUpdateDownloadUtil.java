package com.inossem_library.other.version.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.inossem_library.other.version.bean.UpdateAppBean;
import com.inossem_library.other.version.service.DownloadService;

import androidx.annotation.Nullable;


/**
 * ----------
 * Author: Leo
 * Date: 2019/6/6
 * Email: 18304097708@163.com
 * Description:
 * ----------
 */

public class AppUpdateDownloadUtil {
    public static void startDownload(Context mActivity, final UpdateAppBean updateAppBean, @Nullable final DownloadService.DownloadCallback downloadCallback) {
        DownloadService.bindService(mActivity, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                ((DownloadService.DownloadBinder) service).start(updateAppBean, downloadCallback);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        });
    }

}

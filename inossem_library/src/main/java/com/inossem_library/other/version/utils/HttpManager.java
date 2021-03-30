package com.inossem_library.other.version.utils;

import java.io.File;
import java.io.Serializable;

import androidx.annotation.NonNull;

/**
 * app版本更新接口
 */
public interface HttpManager extends Serializable {

    /**
     * 下载
     *
     * @param url      下载地址
     * @param path     文件保存路径
     * @param fileName 文件名称
     * @param callback 回调
     */
    void download(@NonNull String url, String token, @NonNull String path, @NonNull String fileName, @NonNull FileCallback callback);

    /**
     * 下载回调
     */
    interface FileCallback {
        /**
         * 进度
         *
         * @param progress 进度0.00 - 0.50  - 1.00
         * @param total    文件总大小 单位字节
         */
        void onProgress(float progress, long total);

        /**
         * 错误回调
         *
         * @param error 错误提示
         */
        void onError(Throwable error);

        /**
         * 结果回调
         *
         * @param file 下载好的文件
         */
        void onResponse(File file);

        /**
         * 请求之前
         */
        void onBefore();
    }
}

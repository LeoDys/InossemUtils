package com.inossem_library.other.version.utils;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.Progress;

import java.io.File;
import java.util.LinkedHashMap;

import androidx.annotation.NonNull;

/**
 * 使用OkGo实现接口
 */

public class OkGoUpdateHttpUtil implements HttpManager {
    //请求头Value
    public static final String RS_VALUE = "1";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String HEADER_TOKEN = "Authorization";
    public static final String REQUESTTYPE = "requestType";
    public static final String RS = "rs";

    /**
     * 下载
     *
     * @param url      下载地址
     * @param path     文件保存路径
     * @param fileName 文件名称
     * @param callback 回调
     */
    @Override
    public void download(@NonNull String url, String token, @NonNull String path, @NonNull String fileName, @NonNull final HttpManager.FileCallback callback) {

        HttpHeaders httpHeaders = new HttpHeaders();
        LinkedHashMap<String, String> header = new LinkedHashMap<>();
        header.put(CONTENT_TYPE, "multipart/form-data");
        header.put(RS, RS_VALUE);
        header.put(HEADER_TOKEN, token);
        header.put(REQUESTTYPE, "pda");
        httpHeaders.headersMap = header;

        OkGo.<File>get(url)
                .headers(httpHeaders)
                .execute(new com.lzy.okgo.callback.FileCallback(path, fileName) {
                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<File> response) {
                        callback.onResponse(response.body());
                    }

                    @Override
                    public void onStart(com.lzy.okgo.request.base.Request<File, ? extends com.lzy.okgo.request.base.Request> request) {
                        super.onStart(request);
                        callback.onBefore();
                    }

                    @Override
                    public void onError(com.lzy.okgo.model.Response<File> response) {
                        super.onError(response);
                        callback.onError(response.getException());
                    }

                    @Override
                    public void downloadProgress(Progress progress) {
                        super.downloadProgress(progress);
                        callback.onProgress(progress.fraction, progress.totalSize);
                    }
                });

    }
}
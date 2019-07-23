package com.zxy.util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import java.util.List;

/**
 * 图片压缩使用规范类
 * Created by wen40 on 2019/7/19.
 */

public class TinyCompressUseDemoActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        // null 是多张图片的list
        TinyCompressUtils.filesCallBack(null, new TinyConfig(TinyCompressUseDemoActivity.this)
                , new TinyCompressUtils.TidyCompressFilesListener() {
                    @Override
                    public void compressCallBack(List<String> outfiles) {
                        Log.e("compressCallBack", outfiles.toString());
                    }
                });
        // null 是单张图片
        TinyCompressUtils.fileCallBack(null, new TinyConfig(TinyCompressUseDemoActivity.this)
                , new TinyCompressUtils.TidyCompressSingleFileListener() {
                    @Override
                    public void compressCallBack(String outfiles) {
                        Log.e("compressCallBack", outfiles.toString());
                    }
                });
        // null 是单张图片bitmap
        TinyCompressUtils.bitmapCallBack(null, new TinyConfig(TinyCompressUseDemoActivity.this)
                , new TinyCompressUtils.TidyCompressSingleBitmapListener() {
                    @Override
                    public void compressCallBack(Bitmap bitmap) {

                    }
                });
    }
}

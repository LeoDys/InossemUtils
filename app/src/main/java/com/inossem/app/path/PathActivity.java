package com.inossem.app.path;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inossem.BaseActivity;
import com.inossem.R;
import com.inossem.util.Utils;
import com.inossem_library.app.path.util.PathUtils;

public class PathActivity extends BaseActivity {
    TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        description = findViewById(R.id.description);
        LinearLayout buttonLayout = findViewById(R.id.buttonLayout);
        Utils.createButtons(PathActivity.this, buttonLayout, 34, new Utils.ButtonListener() {
            @Override
            public void onCreated(final Button button, int position) {
                switch (position) {
                    case 0:
                        button.setText("获取根路径" + PathUtils.getRootPath());
                        break;
                    case 1:
                        button.setText("获取数据路径" + PathUtils.getDataPath());
                        break;
                    case 2:
                        button.setText("获取下载缓存路径" + PathUtils.getDownloadCachePath());
                        break;
                    case 3:
                        button.setText("获取内存应用数据路径" + PathUtils.getInternalAppDataPath(PathActivity.this));
                        break;
                    case 4:
                        button.setText("获取内存应用代码缓存路径" + PathUtils.getInternalAppCodeCacheDir(PathActivity.this));
                        break;
                    case 5:
                        button.setText("获取内存应用缓存路径" + PathUtils.getInternalAppCachePath(PathActivity.this));
                        break;
                    case 6:
                        button.setText("获取内存应用数据库路径" + PathUtils.getInternalAppDbsPath(PathActivity.this));
                        break;
                    case 7:
                        button.setText("获取内存应用文件路径" + PathUtils.getInternalAppFilesPath(PathActivity.this));
                        break;
                    case 8:
                        button.setText("获取内存应用SP路径" + PathUtils.getInternalAppSpPath(PathActivity.this));
                        break;
                    case 9:
                        button.setText("获取内存应用未备份文件路径" + PathUtils.getInternalAppNoBackupFilesPath(PathActivity.this));
                        break;
                    case 10:
                        button.setText("获取SD路径" + PathUtils.getExternalStoragePath());
                        break;
                    case 11:
                        button.setText("获取外存音乐路径" + PathUtils.getExternalMusicPath());
                        break;
                    case 12:
                        button.setText("获取外存播客路径" + PathUtils.getExternalPodcastsPath());
                        break;
                    case 13:
                        button.setText("获取外存铃声路径" + PathUtils.getExternalRingtonesPath());
                        break;
                    case 14:
                        button.setText("获取外存闹铃路径" + PathUtils.getExternalAlarmsPath());
                        break;
                    case 15:
                        button.setText("获取外存通知路径" + PathUtils.getExternalNotificationsPath());
                        break;
                    case 16:
                        button.setText("获取外存图片路径" + PathUtils.getExternalPicturesPath());
                        break;
                    case 17:
                        button.setText("获取外存下载路径" + PathUtils.getExternalDownloadsPath());
                        break;
                    case 18:
                        button.setText("获取外存数码相机图片路径" + PathUtils.getExternalDcimPath());
                        break;
                    case 19:
                        button.setText("获取外存文档路径" + PathUtils.getExternalDocumentsPath());
                        break;
                    case 20:
                        button.setText("获取外存应用数据路径" + PathUtils.getExternalAppDataPath(PathActivity.this));
                        break;
                    case 21:
                        button.setText("获取外存应用缓存路径" + PathUtils.getExternalAppCachePath(PathActivity.this));
                        break;
                    case 22:
                        button.setText("获取外存应用文件路径" + PathUtils.getExternalAppFilesPath(PathActivity.this));
                        break;
                    case 23:
                        button.setText("获取外存应用音乐路径" + PathUtils.getExternalAppMusicPath(PathActivity.this));
                        break;
                    case 24:
                        button.setText("获取外存应用播客路径" + PathUtils.getExternalAppPodcastsPath(PathActivity.this));
                        break;
                    case 25:
                        button.setText("获取外存应用铃声路径" + PathUtils.getExternalAppRingtonesPath(PathActivity.this));
                        break;
                    case 26:
                        button.setText("获取外存应用闹铃路径" + PathUtils.getExternalAppAlarmsPath(PathActivity.this));
                        break;
                    case 27:
                        button.setText("获取外存应用通知路径" + PathUtils.getExternalAppNotificationsPath(PathActivity.this));
                        break;
                    case 28:
                        button.setText("获取外存应用图片路径" + PathUtils.getExternalAppPicturesPath(PathActivity.this));
                        break;
                    case 29:
                        button.setText("获取外存应用影片路径" + PathUtils.getExternalAppMoviesPath(PathActivity.this));
                        break;
                    case 30:
                        button.setText("获取外存应用下载路径" + PathUtils.getExternalAppDownloadPath(PathActivity.this));
                        break;
                    case 31:
                        button.setText("获取外存应用数码相机图片路径" + PathUtils.getExternalAppDcimPath(PathActivity.this));
                        break;
                    case 32:
                        button.setText("获取外存应用文档路径" + PathUtils.getExternalAppDocumentsPath(PathActivity.this));
                        break;
                    case 33:
                        button.setText("获取外存应用OBB路径" + PathUtils.getExternalAppObbPath(PathActivity.this));
                        break;
                }
            }
        });
    }
}

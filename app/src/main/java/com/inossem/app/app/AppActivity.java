package com.inossem.app.app;

import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inossem.BaseActivity;
import com.inossem.R;
import com.inossem.util.Utils;
import com.inossem_library.app.app.util.AppUtils;
import com.inossem_library.tips.logcat.util.LogUtils;
import com.inossem_library.tips.toast.util.ToastUtils;

import java.util.List;

/**
 * App相关
 *
 * @author LinH
 */
public class AppActivity extends BaseActivity {
    TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        description = findViewById(R.id.description);
        LinearLayout buttonLayout = findViewById(R.id.buttonLayout);
        Utils.createButtons(AppActivity.this, buttonLayout, 20, new Utils.ButtonListener() {
            @Override
            public void onCreated(final Button button, int position) {
                switch (position) {
                    case 0:
                        button.setText("判断App是否安装 packageName (com.inossem)");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    boolean appInstalled = AppUtils.isAppInstalled(AppActivity.this, "com.inossem");
                                    if (appInstalled) {
                                        ToastUtils.show(AppActivity.this, "已经安装");
                                    } else {
                                        ToastUtils.show(AppActivity.this, "尚未安装");
                                    }
                                } catch (PackageManager.NameNotFoundException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        break;
                    case 1:
                        button.setText("判断App是否是Debug版本 packageName (com.inossem)");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    boolean appInstalled = AppUtils.isAppDebug(AppActivity.this, "com.inossem");
                                    if (appInstalled) {
                                        ToastUtils.show(AppActivity.this, "是");
                                    } else {
                                        ToastUtils.show(AppActivity.this, "否");
                                    }
                                } catch (PackageManager.NameNotFoundException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        break;
                    case 2:
                        button.setText("是否是系统应用 packageName (com.inossem)");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    boolean appInstalled = AppUtils.isAppSystem(AppActivity.this, "com.inossem");
                                    if (appInstalled) {
                                        ToastUtils.show(AppActivity.this, "是");
                                    } else {
                                        ToastUtils.show(AppActivity.this, "否");
                                    }
                                } catch (PackageManager.NameNotFoundException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        break;
                    case 3:
                        button.setText("判断 App 是否运行 packageName (com.inossem)");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    boolean appInstalled = AppUtils.isAppRunning(AppActivity.this, "com.inossem");
                                    if (appInstalled) {
                                        ToastUtils.show(AppActivity.this, "正常运行");
                                    } else {
                                        ToastUtils.show(AppActivity.this, "没在运行");
                                    }
                                } catch (PackageManager.NameNotFoundException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        break;
                    case 4:
                        button.setText("判断 App 是否在后台运行 packageName (com.inossem)");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    boolean appInstalled = AppUtils.isAppKernelRunning(AppActivity.this, "com.inossem");
                                    if (appInstalled) {
                                        ToastUtils.show(AppActivity.this, "正常运行");
                                    } else {
                                        ToastUtils.show(AppActivity.this, "没在运行");
                                    }
                                } catch (PackageManager.NameNotFoundException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        break;
                    case 5:
                        button.setText("打开 App packageName (com.android.settings) 设置");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    AppUtils.launchApp(AppActivity.this, "com.android.settings");
                                } catch (PackageManager.NameNotFoundException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        break;
                    case 6:
                        button.setText("重启 App 杀死进程");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AppUtils.relaunchApp(AppActivity.this, true);
                            }
                        });
                        break;
                    case 7:
                        button.setText("打开 App 具体设置");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    AppUtils.launchAppDetapplicationInfolsSettings(AppActivity.this);
                                } catch (PackageManager.NameNotFoundException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        break;
                    case 8:
                        button.setText("获取 App 图标");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    Drawable appIcon = AppUtils.getAppIcon(AppActivity.this);
                                    button.setBackground(appIcon);
                                } catch (PackageManager.NameNotFoundException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        break;
                    case 9:
                        button.setText("获取 App 包名");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String appPackageName = AppUtils.getAppPackageName(AppActivity.this);
                                ToastUtils.show(AppActivity.this, appPackageName);
                            }
                        });
                        break;
                    case 10:
                        button.setText("获取 App 路径");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    String appPackageName = AppUtils.getAppPath(AppActivity.this);
                                    ToastUtils.show(AppActivity.this, appPackageName);
                                } catch (PackageManager.NameNotFoundException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        break;
                    case 11:
                        button.setText("获取 App 版本名称");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    String appVersionName = AppUtils.getAppVersionName(AppActivity.this);
                                    ToastUtils.show(AppActivity.this, appVersionName);
                                } catch (PackageManager.NameNotFoundException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        break;
                    case 12:
                        button.setText("获取 App 版本号");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    int appVersionCode = AppUtils.getAppVersionCode(AppActivity.this);
                                    ToastUtils.show(AppActivity.this, String.valueOf(appVersionCode));
                                } catch (PackageManager.NameNotFoundException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        break;
                    case 13:
                        button.setText("获取 App 签名");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    Signature[] appSignature = AppUtils.getAppSignature(AppActivity.this);
                                    ToastUtils.show(AppActivity.this, String.valueOf(appSignature));
                                } catch (PackageManager.NameNotFoundException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        break;
                    case 14:
                        button.setText("获取应用签名的的 SHA1 值");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    String appSignatureSHA1 = AppUtils.getAppSignatureSHA1(AppActivity.this);
                                    ToastUtils.show(AppActivity.this, appSignatureSHA1);
                                } catch (PackageManager.NameNotFoundException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        break;
                    case 15:
                        button.setText("获取应用签名的的 SHA256 值");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    String appSignatureSHA256 = AppUtils.getAppSignatureSHA256(AppActivity.this);
                                    ToastUtils.show(AppActivity.this, appSignatureSHA256);
                                } catch (PackageManager.NameNotFoundException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        break;
                    case 16:
                        button.setText("获取应用签名的的 MD5 值");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    String appSignatureMD5 = AppUtils.getAppSignatureMD5(AppActivity.this);
                                    ToastUtils.show(AppActivity.this, appSignatureMD5);
                                } catch (PackageManager.NameNotFoundException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        break;
                    case 17:
                        button.setText("获取 App 信息");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    AppUtils.AppInfo appInfo = AppUtils.getAppInfo(AppActivity.this);
                                    ToastUtils.show(AppActivity.this, "详情查看log信息" + appInfo.toString());
                                    LogUtils.e(appInfo.toString());
                                } catch (PackageManager.NameNotFoundException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        break;
                    case 18:
                        button.setText("获取所有已安装 App 信息");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                List<AppUtils.AppInfo> appsInfo = AppUtils.getAppsInfo(AppActivity.this);
                                ToastUtils.show(AppActivity.this, "详情查看log信息" + appsInfo.toString());
                                LogUtils.e(appsInfo.toString());
                            }
                        });
                        break;
                }
            }
        });
    }
}
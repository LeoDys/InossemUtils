package com.inossem_library.other.version;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.inossem_library.R;
import com.inossem_library.other.convert.util.ConvertUtils;
import com.inossem_library.other.version.bean.UpdateAppBean;
import com.inossem_library.other.version.constant.VersionConstant;
import com.inossem_library.other.version.service.DownloadService;
import com.inossem_library.other.version.utils.AppUpdateDownloadUtil;
import com.inossem_library.other.version.utils.AppUpdateUtils;
import com.inossem_library.other.version.utils.BaseDialogUtil;
import com.inossem_library.other.version.utils.NoDoubleClickListener;
import com.inossem_library.other.version.utils.OkGoUpdateHttpUtil;
import com.inossem_library.other.version.utils.VersionUtils;
import com.inossem_library.tips.toast.util.ToastUtils;

import java.io.File;

/**
 * x下载调用类
 * Author: Leo
 * Date: 2019/6/6
 * Email: 18304097708@163.com
 * Description:
 * ----------
 */

public class AppUpdate {

    static ExitListener appListener;

    static View.OnClickListener installListener = null;
    static View.OnClickListener downListener = null;
    static View.OnClickListener exitListener = null;

    private static void initListener(Context context, String apkName, ViewHolder viewHolder, String url, String token, String versionName, String md5, boolean isCheckMD5) {
        installListener = new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                AppUpdateUtils.installApp(context, new File(VersionUtils.getDownloadApkPath(context), apkName));
            }
        };
        downListener = new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                appUpdateOnlyDownload(context, viewHolder, url, token, versionName,
                        VersionConstant.APK_FILE_NAME + versionName + VersionConstant.APK_SUFFIX_NAME, md5,
                        isCheckMD5);
            }
        };
        exitListener = new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                if (appListener != null) {
                    appListener.exit();
                }
            }
        };
    }

    private static class ViewHolder {
        private TextView prompt, description, versionName, errorMessage, btnLeft, btnRight;

        private LinearLayout llUpdate, llBtns;

        private NumberProgressBar numberProgressBar;
    }

    /**
     * 单独下载方法
     * <p>
     * -keepattributes Annotation
     * -keepclassmembers class * {    @org.greenrobot.eventbus.Subscribe ;}
     * -keep enum org.greenrobot.eventbus.ThreadMode { *; }
     * -keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {    (java.lang.Throwable);}
     * -keep class com.allenliu.versionchecklib.**{*;}
     *
     * @param context     上下文对象
     * @param viewHolder  升级Dilaog布局文件id集合
     * @param downloadUrl 下载地址
     * @param versionName 版本名称
     * @param fileName    下载后文件命名
     * @param md5         服务器返回的apkmd5
     * @param isCheckMD5  是否检查md5
     */
    public static void appUpdateOnlyDownload(Context context, ViewHolder viewHolder,
                                             String downloadUrl, String token, String versionName,
                                             String fileName, String md5, boolean isCheckMD5) {

        if (DownloadService.isRunning)
            return;

        UpdateAppBean updateAppBean = new UpdateAppBean();

        //设置 apk 的下载地址
        updateAppBean.setUrl(downloadUrl);
        // 设置token
        updateAppBean.setToken(token);
        //设置apk 的保存路径
        updateAppBean.setTargetPath(VersionUtils.getDownloadApkPath(context));
        //实现网络接口，只实现下载就可以
        updateAppBean.setHttpManager(new OkGoUpdateHttpUtil());
        updateAppBean.setVersionName(versionName);
        updateAppBean.setFileName(fileName);

        AppUpdateDownloadUtil.startDownload(context, updateAppBean, new DownloadService.DownloadCallback() {
            @Override
            public void onStart() {
                viewHolder.prompt.setText(context.getString(R.string.update_module_downloading));
                viewHolder.numberProgressBar.setProgress(0);
                setStates(context, VersionConstant.ERROR_UPDATE_DOWNLOADING, viewHolder);
            }

            @Override
            public void onProgress(float progress, long totalSize) {
                viewHolder.numberProgressBar.setProgress(Math.round(progress * 100));
            }

            @Override
            public void setMax(long totalSize) {
            }

            @Override
            public boolean onFinish(File file) {
                String localMd5 = VersionUtils.encryptFile(file);
                if (md5.equals(localMd5)) {
                    setStates(context, VersionConstant.ERROR_UPDATE_INSTALL, viewHolder);
                    viewHolder.prompt.setText(context.getString(R.string.update_module_downloaded_install));
                    return true;
                } else {
                    setStates(context, VersionConstant.ERROR_UPDATE_ERROR, viewHolder);
                    viewHolder.errorMessage.setText(context.getString(R.string.update_module_error_md5));
                    return false;
                }
            }

            @Override
            public void onError(Throwable exception) {
                setStates(context, VersionConstant.ERROR_UPDATE_ERROR, viewHolder);
                viewHolder.errorMessage.setText(VersionUtils.getErroMessage(exception) + exception.getMessage());
            }

            @Override
            public boolean onInstallAppAndAppOnForeground(File file) {
                return false;
            }
        });
    }

    /**
     * @param context         上下文
     * @param size            apk大小
     * @param versionName     版本名
     * @param versionRemark   更新日志
     * @param url             下载地址
     * @param token           本地保存的token
     * @param md5             apk md5值
     * @param isForce         是否强制升级
     * @param isCheckMD5      是否检查MD5
     * @param exitAppListener 退出应用的回调
     * @return
     */
    public static Dialog defaultUpgradeVersionDialog(final Context context, String size, String versionName,
                                                     String versionRemark, String url, String token, String md5,
                                                     boolean isForce, boolean isCheckMD5, ExitListener exitAppListener) {
        appListener = exitAppListener;

        if (isCheckMD5) {
            if (TextUtils.isEmpty(md5)) {
                ToastUtils.show(context, context.getString(R.string.update_module_md5_not_null));
                new RuntimeException(context.getString(R.string.update_module_md5_not_null));
            }
        }
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        BaseDialogUtil baseDialog = BaseDialogUtil.createDialog(context, R.layout.default_dialog_upgrade_version_phone, (Context con, View view, Dialog dialog) -> {
            ViewHolder viewHolder = initView(view);
            viewHolder.prompt.setText(context.getString(R.string.update_module_tips));
            viewHolder.description.setText(versionRemark);
            viewHolder.versionName.setText(versionName + " ( " + VersionUtils.getPrintSize(Long.parseLong(size)) + " )");
            // apk的自定义名字
            String apkName = VersionConstant.APK_FILE_NAME + versionName + VersionConstant.APK_SUFFIX_NAME;

            initListener(context, apkName, viewHolder, url, token, versionName, md5, isCheckMD5);

            // 判断apk是不是之前就存在    存在的话判断md5是不是一致
            if (isCheckMD5) {
                if (VersionUtils.isApkExist(context, apkName) && md5.equals(VersionUtils.encryptFile(new File(VersionUtils.getDownloadApkPath(context), apkName)))) {
                    setStates(context, VersionConstant.ERROR_UPDATE_FORWORD_MD5_EXIST, viewHolder);
                } else {
                    setStates(context, VersionConstant.ERROR_UPDATE_FORWORD, viewHolder);
                }
            } else {
                setStates(context, VersionConstant.ERROR_UPDATE_FORWORD, viewHolder);
            }
        });
        Window dialogWindow = baseDialog.getWindow();
        /**
         * 如果需要间距的话，添加此行代码
         */
        dialogWindow.getDecorView().setPadding(ConvertUtils.dp2px(10), 0, ConvertUtils.dp2px(10), 0);
        if (isForce) {
            baseDialog.setCancelable(false);
        } else {
            baseDialog.setCancelable(true);
        }

        return baseDialog;
    }

    private static ViewHolder initView(View view) {
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.prompt = view.findViewById(R.id.prompt);
        viewHolder.llUpdate = view.findViewById(R.id.ll_update);
        viewHolder.btnLeft = view.findViewById(R.id.btn_left);
        viewHolder.btnRight = view.findViewById(R.id.btn_right);
        viewHolder.llBtns = view.findViewById(R.id.ll_btns);
        viewHolder.description = view.findViewById(R.id.description);
        viewHolder.description.setMovementMethod(ScrollingMovementMethod.getInstance());
        viewHolder.versionName = view.findViewById(R.id.versionName);
        viewHolder.errorMessage = view.findViewById(R.id.errorMessage);
        viewHolder.numberProgressBar = view.findViewById(R.id.numberProgressBar);
        return viewHolder;
    }

    /**
     * 更新状态切换
     *
     * @param mContext   上下文
     * @param code       状态code
     * @param viewHolder view集合
     */
    private static void setStates(Context mContext, int code, ViewHolder viewHolder) {
        viewHolder.llUpdate.setVisibility(View.GONE);
        viewHolder.errorMessage.setVisibility(View.GONE);
        viewHolder.numberProgressBar.setVisibility(View.GONE);
        viewHolder.llBtns.setVisibility(View.VISIBLE);
        switch (code) {
            case VersionConstant.ERROR_UPDATE_FORWORD_MD5_EXIST:
                viewHolder.btnLeft.setText(mContext.getString(R.string.update_module_install));
                viewHolder.btnRight.setText(mContext.getString(R.string.update_module_exit_app));
                viewHolder.btnLeft.setOnClickListener(installListener);
                viewHolder.btnRight.setOnClickListener(exitListener);
                viewHolder.llUpdate.setVisibility(View.VISIBLE);
                break;
            case VersionConstant.ERROR_UPDATE_FORWORD:
                viewHolder.btnLeft.setText(mContext.getString(R.string.update_module));
                viewHolder.btnRight.setText(mContext.getString(R.string.update_module_exit_app));
                viewHolder.btnLeft.setOnClickListener(downListener);
                viewHolder.btnRight.setOnClickListener(exitListener);
                viewHolder.llUpdate.setVisibility(View.VISIBLE);
                break;
            case VersionConstant.ERROR_UPDATE_DOWNLOADING:
                viewHolder.llBtns.setVisibility(View.GONE);
                viewHolder.btnLeft.setOnClickListener(downListener);
                viewHolder.btnRight.setOnClickListener(exitListener);
                viewHolder.numberProgressBar.setVisibility(View.VISIBLE);
                break;
            case VersionConstant.ERROR_UPDATE_DOWNLOADED:
                viewHolder.btnLeft.setText(mContext.getString(R.string.update_module_install));
                viewHolder.btnRight.setText(mContext.getString(R.string.update_module_exit_app));
                viewHolder.btnLeft.setOnClickListener(installListener);
                viewHolder.btnRight.setOnClickListener(exitListener);
                viewHolder.numberProgressBar.setVisibility(View.VISIBLE);
                break;
            case VersionConstant.ERROR_UPDATE_INSTALL:
                viewHolder.btnLeft.setText(mContext.getString(R.string.update_module_install));
                viewHolder.btnRight.setText(mContext.getString(R.string.update_module_exit_app));
                viewHolder.btnLeft.setOnClickListener(installListener);
                viewHolder.btnRight.setOnClickListener(exitListener);
                viewHolder.numberProgressBar.setVisibility(View.VISIBLE);
                break;
            case VersionConstant.ERROR_UPDATE_ERROR:
                viewHolder.btnLeft.setText(mContext.getString(R.string.update_module_reload));
                viewHolder.btnRight.setText(mContext.getString(R.string.update_module_exit_app));
                viewHolder.btnLeft.setOnClickListener(downListener);
                viewHolder.btnRight.setOnClickListener(exitListener);
                viewHolder.errorMessage.setVisibility(View.VISIBLE);
                break;
        }
    }

    public interface ExitListener {
        void exit();
    }

}

package com.inossem_library.other.push.util;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RawRes;
import androidx.core.app.NotificationCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.inossem_library.R;
import com.inossem_library.exception.constant.ExceptionEnum;
import com.inossem_library.exception.InossemException;
import com.inossem_library.other.push.constant.PushConstant;
import com.inossem_library.other.regex.util.RegexUtils;
import com.inossem_library.other.string.util.StringUtils;

import java.io.File;

import static androidx.core.app.NotificationCompat.VISIBILITY_SECRET;

/**
 * @author 郭晓龙
 * @time on 2019/7/10
 * @email xiaolong.guo@inossem.com
 * @describe 推送工具类 显示Notification、发送广播
 */
public class PushUtils {

    //上下文
    private Context mContext;
    //通知管理类
    private NotificationManager mNotificationManager;
    //PushUtils实例
    private static PushUtils instance;

    /**
     * 构造方法
     *
     * @param context
     */
    private PushUtils(Context context) {
        this.mContext = context;
    }

    /**
     * 单例模式实现
     *
     * @param context
     */
    public static PushUtils getInstance(Context context) {
        if (instance == null) {
            synchronized (PushUtils.class) {
                if (instance == null) {
                    instance = new PushUtils(context);
                }
            }
        }
        return instance;
    }

    /**
     * 发送广播显示通知
     *
     * @param type     推送类型
     * @param title    推送标题
     * @param message  推送信息
     * @param tClass   跳转Activity类
     * @param launcher 默认图标
     */
    public void push(@NonNull String type, @NonNull String title, @NonNull String message, @NonNull Class tClass, @DrawableRes Integer launcher) {
        if (StringUtils.isEmpty(type)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "type不能为空！");
        }
        if (StringUtils.isEmpty(title)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "title不能为空！");
        }
        if (StringUtils.isEmpty(message)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "message不能为空！");
        }
        if (tClass == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "tClass不能为空！");
        }
        /*
         type = push_type_indirect 需要请求接口message为类型区分 请求接口后发送广播
         type = 其他值 message直接对应具体数据 type为类型 直接发送广播
        */
        if (type.equals(PushConstant.PUSH_TYPE_INDIRECT)) {
            getData(title, message, null, null, tClass, launcher);
        } else {
            sendBC(type, title, message, null, null, tClass, launcher);
        }
    }

    /**
     * 发送广播显示通知
     *
     * @param type     推送类型
     * @param title    推送标题
     * @param message  推送信息
     * @param imgUrl   图片路径(资源文件路径R.mipmap.ic_)
     * @param tClass   跳转Activity类
     * @param launcher 默认图标
     */
    public void push(@NonNull String type, @NonNull String title, @NonNull String message, @DrawableRes Integer imgUrl, @NonNull Class tClass, @DrawableRes Integer launcher) {
        if (StringUtils.isEmpty(type)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "type不能为空！");
        }
        if (StringUtils.isEmpty(title)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "title不能为空！");
        }
        if (StringUtils.isEmpty(message)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "message不能为空！");
        }
        if (tClass == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "tClass不能为空！");
        }
        /*
        type = push_type_indirect 需要请求接口message为类型区分 请求接口后发送广播
         type = 其他值 message直接对应具体数据 type为类型 直接发送广播
        */
        if (type.equals(PushConstant.PUSH_TYPE_INDIRECT)) {
            getData(title, message, imgUrl, null, tClass, launcher);
        } else {
            sendBC(type, title, message, imgUrl, null, tClass, launcher);
        }
    }

    /**
     * @param type     推送类型
     * @param title    推送标题
     * @param message  推送信息
     * @param imgUrl   图片路径(URL地址)
     * @param tClass   跳转Activity类
     * @param launcher 默认图标
     */
    public void push(@NonNull String type, @NonNull String title, @NonNull String message, @NonNull String imgUrl, @NonNull Class tClass, @DrawableRes Integer launcher) {
        if (StringUtils.isEmpty(type)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "type不能为空！");
        }
        if (StringUtils.isEmpty(title)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "title不能为空！");
        }
        if (StringUtils.isEmpty(message)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "message不能为空！");
        }
        if (!RegexUtils.isURL(imgUrl)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "imgUrl必须是URL！");
        }
        if (tClass == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "tClass不能为空！");
        }
        /*
        * type = push_type_indirect 需要请求接口message为类型区分 请求接口后发送广播
        * type = 其他值 message直接对应具体数据 type为类型 直接发送广播
        */
        if (type.equals(PushConstant.PUSH_TYPE_INDIRECT)) {
            getData(title, message, imgUrl, null, tClass, launcher);
        } else {
            sendBC(type, title, message, imgUrl, null, tClass, launcher);
        }
    }

    /**
     * 发送广播显示通知
     *
     * @param type     推送类型
     * @param title    推送标题
     * @param message  推送信息
     * @param imgUrl   图片路径(资源文件路径R.mipmap.ic_)
     * @param soundUrl 声音文件路径
     * @param tClass   跳转Activity类
     * @param launcher 默认图标
     */
    public void push(@NonNull String type, @NonNull String title, @NonNull String message, @DrawableRes Integer imgUrl, @RawRes Integer soundUrl, @NonNull Class tClass, @DrawableRes Integer launcher) {
        if (StringUtils.isEmpty(type)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "type不能为空！");
        }
        if (StringUtils.isEmpty(title)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "title不能为空！");
        }
        if (StringUtils.isEmpty(message)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "message不能为空！");
        }
        if (tClass == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "tClass不能为空！");
        }
        /*
        type = push_type_indirect 需要请求接口message为类型区分 请求接口后发送广播
         type = 其他值 message直接对应具体数据 type为类型 直接发送广播
        */
        if (type.equals(PushConstant.PUSH_TYPE_INDIRECT)) {
            getData(title, message, imgUrl, soundUrl, tClass, launcher);
        } else {
            sendBC(type, title, message, imgUrl, soundUrl, tClass, launcher);
        }
    }

    /**
     * @param type     推送类型
     * @param title    推送标题
     * @param message  推送信息
     * @param imgUrl   图片路径(URL地址)
     * @param soundUrl 声音文件路径
     * @param tClass   跳转Activity类
     * @param launcher 默认图标
     */
    public void push(@NonNull String type, @NonNull String title, @NonNull String message, @NonNull String imgUrl, @RawRes Integer soundUrl, @NonNull Class tClass, @DrawableRes Integer launcher) {
        if (StringUtils.isEmpty(type)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "type不能为空！");
        }
        if (StringUtils.isEmpty(title)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "title不能为空！");
        }
        if (StringUtils.isEmpty(message)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "message不能为空！");
        }
        if (!RegexUtils.isURL(imgUrl)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "imgUrl必须是URL！");
        }
        if (tClass == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "tClass不能为空！");
        }
        /*
        type = push_type_indirect 需要请求接口message为类型区分 请求接口后发送广播
        type = 其他值 message直接对应具体数据 type为类型 直接发送广播
        */
        if (type.equals(PushConstant.PUSH_TYPE_INDIRECT)) {
            getData(title, message, imgUrl, soundUrl, tClass, launcher);
        } else {
            sendBC(type, title, message, imgUrl, soundUrl, tClass, launcher);
        }
    }

    /**
     * 注册广播
     *
     * @param broadcastReceiver 广播接收类
     */
    public void registerReceiver(BroadcastReceiver broadcastReceiver) {
        BroadcastUtils.getInstance(mContext).registerReceiver(PushConstant.PUSH_ACTION, broadcastReceiver);
    }

    /**
     * 取消注册广播
     */
    public void unregisterReceiver() {
        BroadcastUtils.getInstance(mContext).unregisterReceiver(PushConstant.PUSH_ACTION);
    }

    /**
     * 请求推送数据
     *
     * @param type     推送类型
     * @param imgUrl   图片路径(资源文件路径、URL地址)
     * @param soundUrl 声音文件路径
     */
    private void getData(String title, String type, Object imgUrl, Integer soundUrl, Class tClass, Integer launcher) {
        /*
        请求接口
        */
//        RetrofitUtils.getRetrofit(mContext, "郭晓龙", "郭晓龙", "推送", "推送")
//                .create(BaseDataApiService.class)
//                .getBaseData()
//                .enqueue(new InossemCallback<BaseBean<DicStockLocationAndWhTreeVo>>() {
//                    @Override
//                    public void success(Response<BaseBean<DicStockLocationAndWhTreeVo>> response) {
//                        sendBC(type, title, response.body().getReturnMsg(), imgUrl, soundUrl, tClass,launcher);
//                    }
//
//                    @Override
//                    public void failure(Response<BaseBean<DicStockLocationAndWhTreeVo>> response) {
//                        sendBC(type, title, response.body().getReturnMsg(), imgUrl, soundUrl, tClass,launcher);
//                    }
//                });
    }

    /**
     * 发送广播
     *
     * @param type     推送类型
     * @param message  推送信息
     * @param imgUrl   图片路径(资源文件路径、URL地址)
     * @param soundUrl 声音文件路径
     */
    private void sendBC(String type, String title, String message, Object imgUrl, Integer soundUrl, Class tClass, Integer launcher) {
        /*
        发送广播、显示通知
        */
        BroadcastUtils.getInstance(mContext).sendBroadcast(PushConstant.PUSH_ACTION, new String[]{PushConstant.PUSH_TYPE, PushConstant.PUSH_MESSAGE}, new Object[]{type, message});
        //设置通知PendingIntent
        setPendingIntent(type, title, message, imgUrl, soundUrl, tClass, launcher);
    }

    /**
     * 发送广播
     *
     * @param type     推送类型
     * @param message  推送信息
     * @param imgUrl   图片路径(资源文件路径、URL地址)
     * @param soundUrl 声音文件路径
     */
    private void setPendingIntent(String type, String title, String message, Object imgUrl, Integer soundUrl, Class tClass, Integer launcher) {
        /*
        显示通知
        */
        // 创建一个NotificationManager的引用
        if (null == mNotificationManager) {
            mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        }
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext);
        //适配8.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //ID
            String id = mContext.getString(R.string.app_name);
            //名称
            String name = mContext.getString(R.string.app_name);
            NotificationChannel channel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_DEFAULT);
            channel.canBypassDnd();//是否绕过请勿打扰模式
            channel.enableLights(true);//闪光灯
            channel.setLockscreenVisibility(VISIBILITY_SECRET);//锁屏显示通知
            channel.setLightColor(Color.RED);//闪关灯的灯光颜色
            channel.canShowBadge();//桌面launcher的消息角标
            channel.enableVibration(true);//是否允许震动
            channel.setBypassDnd(true);//设置可绕过  请勿打扰模式
//          channel.setVibrationPattern(new long[]{100L, 100L, 200L});//设置震动模式
            channel.shouldShowLights();//是否会有灯光
            if (soundUrl != null) {
                Uri path = Uri.parse(PushConstant.ANDROID_RESOURCE + mContext.getPackageName() + File.separator + soundUrl);
                channel.setSound(path, Notification.AUDIO_ATTRIBUTES_DEFAULT);
            } else {
                channel.setSound(null, Notification.AUDIO_ATTRIBUTES_DEFAULT);
            }
            builder.setChannelId(id);
            mNotificationManager.createNotificationChannel(channel);
        } else {
            builder.setDefaults(Notification.DEFAULT_VIBRATE | Notification.DEFAULT_LIGHTS);
            if (soundUrl != null) {
                Uri path = Uri.parse(PushConstant.ANDROID_RESOURCE + mContext.getPackageName() + File.separator + soundUrl);
                builder.setSound(path);
            }
        }

        builder.setSmallIcon(launcher)
                .setContentTitle(title)
                .setContentText(message)
                .setTicker(title)
                .setContentIntent(PendingIntent.getActivity(mContext, 0, openNotification(type, tClass), PendingIntent.FLAG_UPDATE_CURRENT));
        builder.setAutoCancel(true);

        if (imgUrl != null) {
            if (imgUrl instanceof Integer) {
                builder.setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), (Integer) imgUrl));
                mNotificationManager.notify((int) System.currentTimeMillis(), builder.build());
            }
            if (imgUrl instanceof String) {
                Glide.with(mContext).asBitmap().load(imgUrl)
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                builder.setLargeIcon(resource);
                                mNotificationManager.notify((int) System.currentTimeMillis(), builder.build());
                            }
                        });
            }
        } else {
            mNotificationManager.notify((int) System.currentTimeMillis(), builder.build());
        }
    }


    /**
     * 获取意图Intent
     *
     * @param type 推送类型
     * @return Intent
     */
    private Intent openNotification(String type, Class tClass) {
        /*
        通过类型获取意图Intent
         */
        Intent intent = new Intent(mContext, tClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    /**
     * 清除所有的通知
     */
    public void clearAllNotifications() {
        if (mNotificationManager != null)
            mNotificationManager.cancelAll();
    }
}

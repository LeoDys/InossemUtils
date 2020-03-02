package com.inossem_library.request.netty.util;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.inossem_library.R;
import com.inossem_library.request.netty.constant.NettyConstant;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.ReferenceCountUtil;

/**
 * @author 詹建宇
 * @time on 2019/7/25
 * @email jianyu.zhan@inossem.com
 * @describe Netty推送服务
 */
public class NettyService extends Service {
    private static final String CHANNEL_ID = "NettyService";
    private SocketChannel socketChannel;
    private ReceiveCallBack receiveCallBack;
    private Handler handler;

    @Override
    public void onCreate() {
        super.onCreate();
        // API 26以上要在后台启动service 但是前台要告诉用户有service正在运行
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "推送", NotificationManager.IMPORTANCE_HIGH);
            channel.enableLights(true);//设置提示灯
            channel.setLightColor(Color.RED);//设置提示灯颜色
            channel.setShowBadge(true);//显示logo
            channel.setDescription("");//设置描述
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC); //设置锁屏可见 VISIBILITY_PUBLIC=可见
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }

            Notification notification = new Notification.Builder(this)
                    .setChannelId(CHANNEL_ID)
                    //标题
                    .setContentTitle("推送")
                    //内容
                    .setContentText("运行中")
                    .setWhen(System.currentTimeMillis())
                    //小图标一定需要设置,否则会报错(如果不设置它启动服务前台化不会报错,但是你会发现这个通知不会启动),如果是普通通知,不设置必然报错
                    .setSmallIcon(R.drawable.push_ic_push)
                    .build();
            //服务前台化只能使用startForeground()方法,不能使用 notificationManager.notify(1,notification); 这个只是启动通知使用的,使用这个方法你只需要等待几秒就会发现报错了
            startForeground(1, notification);
        }
        handler = new Handler();
        NettyCache.setService(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    /**
     * 关闭Socket
     */
    private void close() {
        if (socketChannel != null) {
            socketChannel.close();
            socketChannel = null;
        }
    }

    /**
     * 重试操作
     *
     * @param mills 重试间隔时间
     */
    private void retryConnect(final long mills) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                connect(new ConnectCallBack() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onFailure() {
                        retryConnect(mills);
                    }
                });
            }
        }, mills);
    }

    /**
     * netty监听
     */
    private class ChannelHandle extends SimpleChannelInboundHandler<String> {

        //断开连接触发channelInactive
        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            super.channelInactive(ctx);
            NettyService.this.close();
            retryConnect(3000);
        }

        //利用写空闲发送心跳检测消息
        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
            if (evt instanceof IdleStateEvent) {
                IdleStateEvent e = (IdleStateEvent) evt;
                if (e.state() == IdleState.WRITER_IDLE) {
                    ctx.writeAndFlush("connect again");
                }
            }
            super.userEventTriggered(ctx, evt);
        }

        //接收信息
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, final String msg) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if (receiveCallBack != null) {
                        receiveCallBack.onReceive(msg);
                    }
                }
            });
            ReferenceCountUtil.release(msg);
        }
    }

    /**
     * 开始连接
     *
     * @param callBack 连接成功回调
     */
    public void connect(@NonNull final ConnectCallBack callBack) {
        SharedPreferences settingSp = NettyUtils.getSettingSp(this);
        final long readerIdleTime = settingSp.getLong(NettyConstant.IDLE_READ, 0);
        final long writerIdleTime = settingSp.getLong(NettyConstant.IDLE_WRITE, 0);
        final long allIdleTime = settingSp.getLong(NettyConstant.IDLE_ALL, 0);
        String host = settingSp.getString(NettyConstant.HOST, "");
        int port = settingSp.getInt(NettyConstant.PORT, 0);

        try {
            final NioEventLoopGroup group = new NioEventLoopGroup();
            new Bootstrap()
                    .channel(NioSocketChannel.class)
                    .group(group)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    //禁用nagle算法 Nagle算法就是为了尽可能发送大块数据，避免网络中充斥着许多小数据块。
                    .option(ChannelOption.TCP_NODELAY, true)//屏蔽Nagle算法试图
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast(new IdleStateHandler(readerIdleTime, writerIdleTime, allIdleTime, TimeUnit.MINUTES));//5分钟未发送数据，回调userEventTriggered
                            pipeline.addLast(new ObjectEncoder());
                            pipeline.addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(null)));
                            pipeline.addLast(new ChannelHandle());
                        }
                    })
                    .connect(new InetSocketAddress(host, port))
                    .addListener(new ChannelFutureListener() {
                        @Override
                        public void operationComplete(ChannelFuture future) {
                            if (future.isSuccess()) {
                                socketChannel = (SocketChannel) future.channel();
                                callBack.onSuccess();
                            } else {
                                close();
                                // 这里一定要关闭，不然一直重试会引发OOM
                                future.channel().close();
                                group.shutdownGracefully();
                                callBack.onFailure();

                            }
                        }
                    });
        } catch (Exception ignored) {

        }
    }

    /**
     * 设置接收信息回调
     *
     * @param receiveCallBack
     */
    public void setReceiveCallback(ReceiveCallBack receiveCallBack) {
        this.receiveCallBack = receiveCallBack;
    }

    /**
     * 发送消息
     *
     * @param message      消息实体
     * @param sendCallBack 是否发送成功的回调
     */
    public void send(String message, final SendCallBack sendCallBack) {
        socketChannel.writeAndFlush(message)
                .addListener(new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture future) {
                        if (sendCallBack == null) {
                            return;
                        }
                        if (future.isSuccess()) {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    sendCallBack.onSuccess();
                                }
                            });
                        } else {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    sendCallBack.onFailure();
                                }
                            });
                        }
                    }
                });
    }

    /**
     * 断开连接
     */
    public void disConnect() {
        if (socketChannel != null && socketChannel.isActive()) {
            socketChannel.flush();
            socketChannel.close();
        }
    }

}

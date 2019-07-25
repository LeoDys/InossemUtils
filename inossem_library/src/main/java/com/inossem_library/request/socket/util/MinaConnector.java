package com.inossem_library.request.socket.util;

import com.inossem_library.request.socket.constant.SocketConstants;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderAdapter;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.apache.mina.transport.socket.nio.NioDatagramConnector;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.net.InetSocketAddress;

/**
 * @author 郭晓龙
 * @time on 2019/7/16
 * @email xiaolong.guo@inossem.com
 * @describe Socket控制器
 */
public class MinaConnector implements INetConnector {

    private IoSession session;
    public static boolean isCon = false;
    private NioSocketConnector connectortcp;
    private NioDatagramConnector connectorudp;
    private MinaCallBack mCallBack;
    //MinaConnector实例
    private static MinaConnector instance;

    /**
     * 构造方法
     *
     * @param callBack 返回数据回调
     */
    public MinaConnector(MinaCallBack callBack) {
        mCallBack = callBack;
    }

    /**
     * 单例模式实现
     *
     * @param callBack 返回数据回调
     */
    public static MinaConnector getInstance(MinaCallBack callBack) {
        if (instance == null) {
            synchronized (MinaConnector.class) {
                if (instance == null) {
                    instance = new MinaConnector(callBack);
                }
            }
        }
        return instance;
    }

    /**
     * 连接服务
     *
     * @param host IP地址
     * @param port 端口号
     * @return 返回连接状态true、false
     */
    public boolean connectServer(String host, int port) {
        isCon = startTcpConnect(host, port);
        return isCon;
    }

    /**
     * 断开连接
     */
    public void disconnect() {
        if (connectortcp != null) {
            connectortcp.dispose();
        }
        if (session != null) {
            session.close(true);
        }
        isCon = false;
    }

    /**
     * 获取连接状态
     *
     * @return 返回连接状态true、false
     */
    public boolean isConnect() {
        return isCon;
    }

    /**
     * 发送数据
     *
     * @param msg 发送数据
     */
    public void write(String msg) {
        if (session != null) {
            session.write(msg);
        }
    }

    /**
     * 启动UDP连接
     *
     * @param host IP地址
     * @param port 端口号
     * @return 返回连接状态true、false
     */
    private boolean startUdpConnect(String host, int port) {
        isCon = false;
        connectorudp = new NioDatagramConnector();
        connectorudp.setHandler(new MinaConnectorHandler(mCallBack));
        connectorudp.setConnectTimeoutMillis(SocketConstants.TIMEOUTMILLIS);
        connectorudp.getFilterChain().addLast(SocketConstants.CODEC, new ProtocolCodecFilter(new ByteArrayCodecFactory()));
        // 连结到服务器:
        ConnectFuture cf = connectorudp.connect(new InetSocketAddress(host, port));
        cf.awaitUninterruptibly();
        session = cf.getSession();
        isCon = session.isConnected();
        return isCon;
    }

    /**
     * 启动TCP连接
     *
     * @param host IP地址
     * @param port 端口号
     * @return 返回连接状态true、false
     */
    private boolean startTcpConnect(String host, int port) {
        isCon = false;
        connectortcp = new NioSocketConnector();
        // 设定服务器端的消息处理器
        connectortcp.setHandler(new MinaConnectorHandler(mCallBack));
        connectortcp.setConnectTimeoutMillis(SocketConstants.TIMEOUTMILLIS);
        connectortcp.getFilterChain().addLast(SocketConstants.CODEC, new ProtocolCodecFilter(new ByteArrayCodecFactory()));
        // 连结到服务器
        ConnectFuture cf = connectortcp.connect(new InetSocketAddress(host, port));
        cf.awaitUninterruptibly();
        session = cf.getSession();
        isCon = session.isConnected();
        return isCon;
    }

    /**
     * 编码适配器
     */
    public class ByteArrayEncoder extends ProtocolEncoderAdapter {

        @Override
        public void encode(IoSession session, Object message,
                           ProtocolEncoderOutput out) {
            String cmd = (String) message;
            out.write(IoBuffer.wrap(cmd.getBytes()));
            out.flush();
        }
    }

    /**
     * 解码适配器
     */
    public class ByteArrayDecoder extends ProtocolDecoderAdapter {
        @Override
        public void decode(IoSession session, IoBuffer in, ProtocolDecoderOutput out)
                throws Exception {
            int limit = in.limit();
            byte[] bytes = new byte[limit];
            in.get(bytes);
            //目前后台发送的数据前面都会有2个字符的无用数据，换成16进制后包括空格就是6位 不包括空格是4位
//            out.write(ConvertUtils.hexStr2Str(ConvertUtils.bytes2HexString(bytes)));
            out.write(new String(bytes));
        }
    }

    /**
     * Mina协议编码工厂
     */
    public class ByteArrayCodecFactory implements ProtocolCodecFactory {
        private ByteArrayDecoder decoder;
        private ByteArrayEncoder encoder;

        public ByteArrayCodecFactory() {
            encoder = new ByteArrayEncoder();
            decoder = new ByteArrayDecoder();
        }

        @Override
        public ProtocolDecoder getDecoder(IoSession session) throws Exception {
            return decoder;
        }

        @Override
        public ProtocolEncoder getEncoder(IoSession session) throws Exception {
            return encoder;
        }
    }

}

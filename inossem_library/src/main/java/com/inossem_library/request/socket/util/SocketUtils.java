package com.inossem_library.request.socket.util;

import com.inossem_library.exception.ExceptionEnum;
import com.inossem_library.exception.InossemException;
import com.inossem_library.other.string.util.StringUtils;

/**
 * @author 郭晓龙
 * @time on 2019/7/16
 * @email xiaolong.guo@inossem.com
 * @describe Socket网络代理
 */
public class SocketUtils {

    //INetConnector实例
    public static INetConnector netConnector = null;

    /**
     * 连接网关
     *
     * @return 返回连接状态true、false
     */
    public static boolean getConnect(String host, int port, MinaCallBack callBack) {
        if (StringUtils.isEmpty(host)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "host不能为空！");
        }
        if (callBack == null) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "callBack不能为空！");
        }
        if (netConnector != null && netConnector.isConnect()) {
            return true;
        }
        netConnector = MinaConnector.getInstance(callBack);
        return netConnector.connectServer(host, port);
    }

    /**
     * 发送数据
     *
     * @param message 发送数据
     */
    public static void send(String message) {
        if (StringUtils.isEmpty(message)) {
            throw new InossemException(ExceptionEnum.NULL_PARAMS, "message不能为空！");
        }
        netConnector.write(message);
    }

    /**
     * 停止网络服务
     */
    public static void stopNetConnect() {
        if (netConnector != null) {
            netConnector.disconnect();
            netConnector = null;
        }
    }
}

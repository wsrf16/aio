package com.aio.portable.swiss.suite.net.tcp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class TcpSugar {
    private static final Log log = LogFactory.getLog(TcpSugar.class);

    /**
     * 测试telnet 机器端口的连通性
     * @param host
     * @param port
     * @param timeout
     * @return
     */
    public static boolean telnet(String host, int port, int timeout){
        Socket socket = new Socket();
        boolean isConnected = false;
        try {
            socket.connect(new InetSocketAddress(host, port), timeout);
            isConnected = socket.isConnected();
        } catch (IOException e) {
            isConnected = false;
            log.warn(e);
        }finally{
            try {
                socket.close();
            } catch (IOException e) {
                isConnected = false;
                log.warn(e);
            }
        }
        return isConnected;
    }
}

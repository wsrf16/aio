package com.aio.portable.swiss.suite.net.tcp.verticle;

import com.aio.portable.swiss.suite.log.facade.LogHub;
import com.aio.portable.swiss.suite.log.factory.LogHubFactory;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetSocket;

public class TCPReverseProxyVerticle extends AbstractVerticle {
    private LogHub log = LogHubFactory.staticBuild();

    private int proxyPort;
    private String actualHost;
    private int actualPort;
    private NetServer netServer;
    private NetClient netClient;
    private ProxyConnection proxyConnection;
//    private static final Vertx VERTX = Vertx.vertx();


    public TCPReverseProxyVerticle(int proxyPort, String actualHost, int actualPort) {
        this.proxyPort = proxyPort;
        this.actualHost = actualHost;
        this.actualPort = actualPort;
    }

    @Override
    public void start() {
        netServer = this.getVertx().createNetServer(); // 创建代理服务器
        netClient = this.getVertx().createNetClient(); // 创建连接 MySQL 的客户端

        // 监听来自客户端的连接请求
        netServer.connectHandler(clientSocket -> {
            // 收到客户端请求后，代理服务器向真实服务器发起连接
            netClient.connect(actualPort, actualHost, result -> {
                if (result.succeeded()) {
                    NetSocket serverSocket = result.result();
                    // 连接成功，开始双向转发数据
                    proxyConnection = new ProxyConnection(clientSocket, serverSocket);
                    proxyConnection.start();
                } else {
                    log.error("连接目标端失败", result.cause());
                    clientSocket.close();
                }
            });
        }).listen(proxyPort, result -> {
            if (result.succeeded()) {
                log.info("代理端已启动，监听端口: " + proxyPort);
            } else {
                log.error("代理端启动失败", result.cause());
            }
        });
    }

    public void stop() {
        if (this.proxyConnection != null) {
            this.proxyConnection.close();
        }
    }

    public void close() {
        this.stop();
        if (this.netServer != null) {
            netServer.close();
//            netServer = null;
        }
        if (this.netClient != null) {
            netClient.close();
//            netClient = null;
        }
    }


    static class ProxyConnection {
        private final NetSocket clientSocket;
        private final NetSocket serverSocket;

        public ProxyConnection(NetSocket clientSocket, NetSocket serverSocket) {
            this.clientSocket = clientSocket;
            this.serverSocket = serverSocket;
        }

        public NetSocket getClientSocket() {
            return clientSocket;
        }

        public NetSocket getServerSocket() {
            return serverSocket;
        }

        public void start() {
            clientSocket.handler(buffer -> serverSocket.write(buffer));
            serverSocket.handler(buffer -> clientSocket.write(buffer));

            clientSocket.closeHandler(v -> serverSocket.close());
            serverSocket.closeHandler(v -> clientSocket.close());
            clientSocket.exceptionHandler(e -> close());
            serverSocket.exceptionHandler(e -> close());
        }

        private void close() {
            if (clientSocket !=null) {
                clientSocket.close();
            }
            if (serverSocket !=null) {
                serverSocket.close();
            }
        }
    }

    public static TCPReverseProxyVerticle build(int proxyPort, String actualHost, int actualPort) {
        return new TCPReverseProxyVerticle(proxyPort, actualHost, actualPort);
    }

    public static Future<String> deploy(Vertx vertx, TCPReverseProxyVerticle verticle) {
        return vertx.deployVerticle(verticle);
    }

    public static Future<String> deploy(Vertx vertx, int proxyPort, String actualHost, int actualPort) {
        TCPReverseProxyVerticle verticle = new TCPReverseProxyVerticle(proxyPort, actualHost, actualPort);
        return vertx.deployVerticle(verticle);
    }

    public static Future<String> deploy(TCPReverseProxyVerticle verticle) {
        return Vertx.vertx().deployVerticle(verticle);
    }

    public static Future<String> deploy(int proxyPort, String actualHost, int actualPort) {
        TCPReverseProxyVerticle verticle = new TCPReverseProxyVerticle(proxyPort, actualHost, actualPort);
        return Vertx.vertx().deployVerticle(verticle);
    }

    public static void undeploy(TCPReverseProxyVerticle verticle, Handler<AsyncResult<Void>> handler) {
        verticle.close();

        String deploymentID = verticle.deploymentID();
        if (deploymentID != null) {
            verticle.getVertx().undeploy(deploymentID, handler);
        }
    }

    public static void undeploy(TCPReverseProxyVerticle verticle) {
        undeploy(verticle, null);
    }
}



package com.aio.portable.swiss.suite.net.tcp.verticle;

import com.aio.portable.swiss.suite.log.facade.LogHub;
import com.aio.portable.swiss.suite.log.factory.LogHubFactory;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetSocket;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class TCPReverseProxyVerticle extends AbstractVerticle {
    private LogHub log = LogHubFactory.staticBuild();

    private int proxyPort;
    private String actualHost;
    private int actualPort;
    private NetServer netProxyServer;
    private NetClient netClient;
//    private ProxyConnection proxyConnection;
//    private static final Vertx VERTX = Vertx.vertx();
    private final Set<ProxyConnection> activeConnectionPool = Collections.newSetFromMap(new ConcurrentHashMap<>());

    public TCPReverseProxyVerticle(int proxyPort, String actualHost, int actualPort) {
        this.proxyPort = proxyPort;
        this.actualHost = actualHost;
        this.actualPort = actualPort;
    }

    @Override
    public void start(Promise<Void> startPromise) {
        netProxyServer = this.getVertx().createNetServer();
        netClient = this.getVertx().createNetClient();

        // listen receive event
        netProxyServer.connectHandler(clientSocket -> {
            // send to actualServer
            netClient.connect(actualPort, actualHost, ar -> {
                if (ar.succeeded()) {
                    NetSocket actualServerSocket = ar.result();
                    // Connection established, starting bidirectional data forwarding.
                    ProxyConnection proxyConnection = new ProxyConnection(clientSocket, actualServerSocket);

                    activeConnectionPool.add(proxyConnection);
                    proxyConnection.start();
                } else {
                    log.error("Failed to connect to the target.", ar.cause());
                    clientSocket.close();
                }
            });
        }).listen(proxyPort, result -> {
            if (result.succeeded()) {
                log.info("Proxy started, listening on port: " + proxyPort);
                startPromise.complete();
            } else {
                log.error("Failed to start proxy.", result.cause());
                startPromise.fail(result.cause());
            }
        });
    }

    public void stop(Promise<Void> stopPromise) {
        for (ProxyConnection conn : activeConnectionPool) {
            conn.close();
        }
        activeConnectionPool.clear();

//        if (this.proxyConnection != null) {
//            this.proxyConnection.close();
//        }

        Future<Void> netProxyServerClose = netProxyServer != null ? netProxyServer.close() : Future.succeededFuture();
        Future<Void> netClientClose = netClient != null ? netClient.close() : Future.succeededFuture();

        netProxyServerClose.compose(v -> netClientClose)
                .onComplete(ar -> {
                    if (ar.succeeded()) {
                        log.info("TCP Reverse Proxy stopped cleanly.");
                        stopPromise.complete();
                    } else {
                        log.error("Error during proxy shutdown", ar.cause());
                        stopPromise.fail(ar.cause());
                    }
                });


    }

//    public void close() {
//        this.stop();
//        if (this.netProxyServer != null) {
//            netProxyServer.close();
//        }
//        if (this.netClient != null) {
//            netClient.close();
//        }
//    }

    public void undeploy(Handler<AsyncResult<Void>> handler) {
        String deploymentID = this.deploymentID();
        if (deploymentID != null) {
            this.getVertx().undeploy(deploymentID, handler);
        }
    }

    public void undeploy() {
        String deploymentID = this.deploymentID();
        if (deploymentID != null) {
            this.getVertx().undeploy(deploymentID);
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
        String deploymentID = verticle.deploymentID();
        if (deploymentID != null) {
            verticle.getVertx().undeploy(deploymentID, handler);
        }
    }

    public static void undeploy(TCPReverseProxyVerticle verticle) {
        String deploymentID = verticle.deploymentID();
        if (deploymentID != null) {
            verticle.getVertx().undeploy(deploymentID);
        }
    }
}



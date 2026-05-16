package com.aio.portable.park.endpoint.ws;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class MySqlTcpProxy {

    // 代理监听的本地端口（例如 33060）
    @Value("${proxy.local.port:33060}")
    private int localPort;

    // 后端真实 MySQL 的地址
//    @Value("${proxy.remote.host:127.0.0.1}")
    @Value("${proxy.remote.host:10.125.144.176}")

    private String remoteHost;

    // 后端真实 MySQL 的端口（默认 3306）
//    @Value("${proxy.remote.port:3306}")
    @Value("${proxy.remote.port:32451}")
    private int remotePort;

    private final EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    private final EventLoopGroup workerGroup = new NioEventLoopGroup();

    @PostConstruct
    public void start() throws InterruptedException {
        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        // 当有客户端连接代理时，添加自定义的转发处理器
                        ch.pipeline().addLast(new ProxyHandler(remoteHost, remotePort));
                    }
                })
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true);

        ChannelFuture f = b.bind(localPort).sync();
        System.out.println("TCP 代理已启动，监听本地端口: " + localPort + "，转发至: " + remoteHost + ":" + remotePort);
    }

    @PreDestroy
    public void destroy() {
        workerGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
    }

    /**
     * 核心转发处理器：负责将客户端的数据转发给 MySQL，并将 MySQL 的响应转发回客户端
     */
    @ChannelHandler.Sharable
    static class ProxyHandler extends ChannelInboundHandlerAdapter {
        private final String remoteHost;
        private final int remotePort;
        private Channel outboundChannel; // 与真实 MySQL 的连接

        public ProxyHandler(String remoteHost, int remotePort) {
            this.remoteHost = remoteHost;
            this.remotePort = remotePort;
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) {
            final Channel inboundChannel = ctx.channel();

            // 建立与真实 MySQL 的连接
            Bootstrap b = new Bootstrap();
            b.group(inboundChannel.eventLoop())
                    .channel(ctx.channel().getClass())
                    .handler(new ChannelInboundHandlerAdapter() {
                        @Override
                        public void channelRead(ChannelHandlerContext outboundCtx, Object msg) {
                            // 收到 MySQL 的响应，转发给客户端
                            inboundChannel.writeAndFlush(msg).addListener((ChannelFutureListener) future -> {
                                if (!future.isSuccess()) {
                                    future.channel().close();
                                }
                            });
                        }

                        @Override
                        public void exceptionCaught(ChannelHandlerContext outboundCtx, Throwable cause) {
                            cause.printStackTrace();
                            outboundCtx.close();
                        }
                    })
                    .option(ChannelOption.AUTO_READ, false);

            ChannelFuture f = b.connect(remoteHost, remotePort);
            outboundChannel = f.channel();
            f.addListener((ChannelFutureListener) future -> {
                if (future.isSuccess()) {
                    // 连接 MySQL 成功，开始读取客户端发来的数据
                    inboundChannel.read();
                } else {
                    inboundChannel.close();
                }
            });
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) {
            // 收到客户端（如 Navicat、JDBC）的请求数据，转发给 MySQL
            if (outboundChannel.isActive()) {
                outboundChannel.writeAndFlush(msg).addListener((ChannelFutureListener) future -> {
                    if (future.isSuccess()) {
                        ctx.channel().read(); // 继续读取下一批数据
                    } else {
                        future.channel().close();
                    }
                });
            }
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) {
            // 客户端断开连接，关闭与 MySQL 的连接
            if (outboundChannel != null) {
                outboundChannel.close();
            }
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            cause.printStackTrace();
            ctx.close();
        }
    }
}

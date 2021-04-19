package com.aio.portable.swiss.suite.net.protocol.rpc.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.SocketAddress;
import java.util.Arrays;
import java.util.Optional;

public class NettySugar {

    /**
     * newServerBootstrap
     *
     * @param channelClass
     * @param childHandlerArrays
     * @return
     */
    public final static ServerBootstrap newServerBootstrap(Class<? extends ServerChannel> channelClass, ChannelHandler... childHandlerArrays) {
        EventLoopGroup acceptGroup = new NioEventLoopGroup();
        EventLoopGroup transferGroup = new NioEventLoopGroup();

        final ServerBootstrap server = new ServerBootstrap()
                .group(acceptGroup, transferGroup)
                .channel(channelClass)
                // 服务端处理客户端连接请求是按顺序处理的，所以同一时间只能处理一个客户端连接，多个客户端来的时候，服务端将不能处理的客户端连接请求放在队列中等待处理，backlog参数指定了队列的大小。
                .option(ChannelOption.SO_BACKLOG, 128);

        Optional.of(childHandlerArrays).ifPresent(handlers -> {
            Arrays.stream(handlers).forEach(handler -> server.childHandler(handler));
        });
        return server;
    }


    /**
     * bindNewServerBootstrap
     *
     * @param localAddress
     * @param childHandlerArrays
     * @return
     */
    public final static void bindNewServerBootstrap(SocketAddress localAddress, ChannelHandler... childHandlerArrays) throws InterruptedException {
        ServerBootstrap server = newServerBootstrap(NioServerSocketChannel.class, childHandlerArrays);
        ChannelFuture channelFuture = null;
        try {
            channelFuture = server.bind(localAddress).sync();
        } finally {
            if (channelFuture != null)
                channelFuture.channel().closeFuture().sync();
        }
    }


    /**
     * newClientBootstrap
     *
     * @param channelClass
     * @param childHandlerArrays
     * @return
     */
    public final static Bootstrap newClientBootstrap(Class<? extends Channel> channelClass, ChannelHandler... childHandlerArrays) {
        EventLoopGroup group = new NioEventLoopGroup();

        Bootstrap client = new Bootstrap()
                .group(group)
                .channel(channelClass)
                .option(ChannelOption.SO_BACKLOG, 128);

        Optional.of(childHandlerArrays).ifPresent(handlers -> {
            Arrays.stream(handlers).forEach(handler -> client.handler(handler));
        });
        return client;
    }
}

package com.aio.portable.swiss.suite.net.protocol.tcp.netty.sample;

import com.aio.portable.swiss.suite.net.protocol.tcp.netty.NettySugar;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;

import java.net.SocketAddress;
import java.util.concurrent.TimeUnit;

class NettyServer {
    public void launch(SocketAddress localAddress) throws InterruptedException {
        NettySugar.bindNewServerBootstrap(localAddress, new ChannelInboundHandlerAdapter() {
            @Override
            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//                super.channelRead(ctx, msg);
                String input = ((ByteBuf) msg).toString(CharsetUtil.UTF_8);
                System.out.println(input);
            }
        });
    }

    public void launch2(SocketAddress localAddress) throws InterruptedException {
        ServerBootstrap server = NettySugar.newServerBootstrap(NioServerSocketChannel.class,
                new DiscardServerHandlerAdapter(), new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel channel) throws Exception {
                        channel.pipeline()
                                // 满足指定时间的空闲后触发
                                .addLast(new IdleStateHandler(5, 0, 0, TimeUnit.SECONDS))
                                .addLast(new DiscardServerHandlerAdapter())
                        ;
                    }
                });

        // 阻塞直到bind成功
        ChannelFuture channelFuture = server.bind(localAddress).sync();
        // 阻塞直到关闭成功
        channelFuture.channel().closeFuture().sync();
    }

    public void launch1(SocketAddress localAddress) throws InterruptedException {
        EventLoopGroup acceptGroup = new NioEventLoopGroup();
        EventLoopGroup transferGroup = new NioEventLoopGroup();

        ServerBootstrap server = new ServerBootstrap()
                .group(acceptGroup, transferGroup)
                .channel(NioServerSocketChannel.class)
                // 二选一
                .childHandler(new DiscardServerHandlerAdapter())
                // 二选一
                .childHandler(
                        new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel channel) throws Exception {
                                channel.pipeline()
                                        // 满足指定时间的空闲后触发
                                        .addLast(new IdleStateHandler(5, 0, 0, TimeUnit.SECONDS))
                                        .addLast(new DiscardServerHandlerAdapter())
                                ;
                            }
                        }
                )
                // 服务端处理客户端连接请求是按顺序处理的，所以同一时间只能处理一个客户端连接，多个客户端来的时候，服务端将不能处理的客户端连接请求放在队列中等待处理，backlog参数指定了队列的大小。
                .option(ChannelOption.SO_BACKLOG, 128);
        // 阻塞直到bind成功
        ChannelFuture channelFuture = server.bind(localAddress).sync();
        // 阻塞直到关闭成功
        channelFuture.channel().closeFuture().sync();

//        b.bind(localAddress).channel().close().sync();
    }
}

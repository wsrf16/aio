package com.aio.portable.swiss.structure.net.protocol.tcp.netty.sample;

import com.aio.portable.swiss.structure.net.protocol.tcp.netty.NettySugar;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import org.springframework.util.StringUtils;

import java.net.SocketAddress;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

class NettyClient {
    public void launch(SocketAddress localAddress) throws InterruptedException {
        // new IdleStateHandler(0, 4, 0, TimeUnit.SECONDS)
        Bootstrap client = NettySugar.newClientBootstrap(NioSocketChannel.class,
                new SendClientHandlerAdapter(), new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline()
                                .addLast(new IdleStateHandler(0, 4, 0, TimeUnit.SECONDS))
                                .addLast(new StringDecoder())
                                .addLast(new StringEncoder());
                    }
                }
        );

//        ChannelFuture channelFuture = client.bind(localAddress).sync();
//        Channel channel = channelFuture.channel();

        ChannelFuture channelFuture = client.connect(localAddress).sync();
        channelFuture.addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {

            }
        });

        Channel channel = channelFuture.channel();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.next();
            if (StringUtils.hasLength(input))
                break;

            channel.writeAndFlush(input);
//            channel.writeAndFlush("This is something.");
        }
        channel.closeFuture().sync();
    }
}

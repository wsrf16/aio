package com.aio.portable.swiss.suite.net.tcp.netty;

import com.aio.portable.swiss.sugar.meta.ClassLoaderSugar;
import com.aio.portable.swiss.sugar.meta.ClassSugar;
import com.aio.portable.swiss.sugar.type.StreamSugar;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.AttributeKey;
import io.netty.util.DefaultAttributeMap;

import java.lang.reflect.Array;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class NettySugar {

    /**
     * newServerBootstrap
     *
     * @param channelClass
     * @param childHandlerArrays
     * @return
     */
    public static ServerBootstrap newServerBootstrap(Class<? extends ServerChannel> channelClass, ChannelHandler... childHandlerArrays) {
        EventLoopGroup acceptGroup = new NioEventLoopGroup();
        EventLoopGroup transferGroup = new NioEventLoopGroup();

        ServerBootstrap server = new ServerBootstrap()
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
    public static void bindNewServerBootstrap(SocketAddress localAddress, ChannelHandler... childHandlerArrays) throws InterruptedException {
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
    public static Bootstrap newClientBootstrap(Class<? extends Channel> channelClass, ChannelHandler... childHandlerArrays) {
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

    private static Class<?> defaultAttributeClazz;

    private synchronized static Class<?> getDefaultAttributeClazz() {
        return defaultAttributeClazz == null ? ClassLoaderSugar.forName("io.netty.util.DefaultAttributeMap$DefaultAttribute") : defaultAttributeClazz;
    }

    public static <T> HashMap<String, Object> extractedKeyValue(Channel channel) {
        Object attributes = ClassSugar.Fields.getDeclaredFieldValue(channel, DefaultAttributeMap.class, "attributes");
        int length = Array.getLength(attributes);
        List<Object> list = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            Object attribute = Array.get(attributes, i);
            list.add(attribute);
        }

        HashMap<String, Object> channelDict = StreamSugar.toMap(list.stream(),
                c -> {
                    AttributeKey<T> attributeKey = ClassSugar.Fields.getDeclaredFieldValue(c, getDefaultAttributeClazz(), "key");
                    return attributeKey.name();
                },
                c -> ClassSugar.Fields.getDeclaredFieldValue(c, AtomicReference.class, "value"));

        return channelDict;
    }
}

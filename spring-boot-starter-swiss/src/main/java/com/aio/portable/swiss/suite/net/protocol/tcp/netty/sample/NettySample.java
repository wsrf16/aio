package com.aio.portable.swiss.suite.net.protocol.tcp.netty.sample;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

class NettySample {
    public void todo(String[] args) {
        NettyServer nettyServer = new NettyServer();
        int port = 8765;
        if (args.length > 0)
            port = Integer.parseInt(args[0]);
        SocketAddress localAddress = new InetSocketAddress(port);
        new Thread(() -> {
            try {
                nettyServer.launch(localAddress);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();


        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        NettyClient nettyClient = new NettyClient();
        try {
            nettyClient.launch(localAddress);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

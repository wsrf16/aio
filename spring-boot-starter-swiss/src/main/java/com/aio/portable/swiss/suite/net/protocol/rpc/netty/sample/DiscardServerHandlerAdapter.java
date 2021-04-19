package com.aio.portable.swiss.suite.net.protocol.rpc.netty.sample;

import com.aio.portable.swiss.global.Constant;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

@ChannelHandler.Sharable
class DiscardServerHandlerAdapter extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        System.out.println("channelActive: " + "连接成功！");        //2
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        super.channelRead(ctx, msg);

        String input = ((ByteBuf) msg).toString(CharsetUtil.UTF_8);
        System.out.println("client send: " + input);

        String output = "-> server has received: " + input + Constant.LINE_SEPARATOR;
        ByteBuf buf = Unpooled.buffer().writeBytes((Constant.LINE_SEPARATOR + output).getBytes());
        ctx.channel().writeAndFlush(buf);
        ReferenceCountUtil.retain(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
//        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)//4
//                .addListener(ChannelFutureListener.CLOSE);


        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)//4
                .addListener(ConnectionListener.CLOSE);


    }

    class ConnectionListener implements ChannelFutureListener {
        //            private Client client;
//            public ConnectionListener(Client client) {
//                this.client = client;
//            }
        @Override
        public void operationComplete(ChannelFuture channelFuture) throws Exception {
//                if (!channelFuture.isSuccess()) {
//                    System.out.println("Reconnect");
//                    final EventLoop loop = channelFuture.channel().eventLoop();
//                    loop.schedule(new Runnable() {
//                        @Override
//                        public void run() {
//                            client.createBootstrap(new Bootstrap(), loop);
//                        }
//                    }, 1L, TimeUnit.SECONDS);
//                }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,
                                Throwable cause) {
        cause.printStackTrace();                //5
        ctx.close();                            //6
        System.out.println("客户端异常退出！");
    }


    /**
     * 空闲次数
     */
    private int idle_count = 1;
    /**
     * 发送次数
     */
    private int count = 0;

    /**
     * userEventTriggered（超时触发）
     *
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);

        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (IdleState.READER_IDLE.equals(event.state())) { // 如果读通道处于空闲状态，说明没有接收到心跳命令
                if (idle_count >= 2) {
                    System.out.println("超过两次无客户端请求，关闭该channel");
                    ctx.channel().close();
                } else {
                    System.out.println("已等待5秒还没收到客户端发来的消息");
                    idle_count++;
                }
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
}

package com.aio.portable.park;


import com.aio.portable.park.common.AppLogHubFactory;
import com.aio.portable.swiss.spring.SpringContextHolder;
import com.aio.portable.swiss.suite.log.facade.LogHub;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringEncoder;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.io.*;


@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
        DruidDataSourceAutoConfigure.class,
//        MybatisAutoConfiguration.class,
        KafkaAutoConfiguration.class,
        RabbitAutoConfiguration.class,
//        SpringContextHolder.class
})
// VMoptions: -javaagent:./jagent/target/jagent-1.1.4-SNAPSHOT.jar=Hello
//@EnableNetworkProxy
public class ParkApplication {
    static LogHub log;

    public static void main(String[] args) {
//        AnnotationConfigEmbeddedWebApplicationContext

        ConfigurableApplicationContext context = SpringApplication.run(ParkApplication.class, args);

//        ConfigurableApplicationContext context = SpringContextHolder.run(ParkApplication.class, args, c -> c.setWebApplicationType(WebApplicationType.NONE));


//        try {
//            EventLoopGroup workerGroup = new NioEventLoopGroup();
//            try {
//                Bootstrap b = new Bootstrap(); // (1)
//                b.group(workerGroup); // (2)
//                b.channel(NioSocketChannel.class); // (3)
//                b.option(ChannelOption.SO_KEEPALIVE, true); // (4)
//                b.handler(new ChannelInitializer<SocketChannel>() {
//                    @Override
//                    public void initChannel(SocketChannel ch) throws Exception {
//                        ch.pipeline().addLast(new LengthFieldPrepender(2,0,false));
//                        ch.pipeline().addLast(new StringEncoder());
//                        ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
//                            // 在于server建立连接后，即发送请求报文
//                            public void channelActive(ChannelHandlerContext ctx) {
//                                ctx.writeAndFlush("i am request!");
//                                ctx.writeAndFlush("i am a anther request!");
//                            }
//                        });
//                    }
//                });
//                // Start the client.
//                ChannelFuture f = b.connect("127.0.0.1", 9000).sync(); // (5)
//                // Wait until the connection is closed.
//                f.channel().closeFuture().sync();
//            } finally {
//                workerGroup.shutdownGracefully();
//            }
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//            System.out.print("aaa");
//            System.out.print("\r");
//            System.out.print("\b");
//            System.out.print("bbb");
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        System.exit(0);
//        ConfigurableApplicationContext context = SpringContextHolder.run(ParkApplication.class, args, c -> c = c);

        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
        log = AppLogHubFactory.staticBuild();
        log.i("it is up to u. ");
        String[] beanNames = beanFactory.getBeanDefinitionNames();

        Environment environment = context.getEnvironment();
        String[] activeProfiles = environment.getActiveProfiles();
        String[] defaultProfiles = environment.getDefaultProfiles();

    }





}


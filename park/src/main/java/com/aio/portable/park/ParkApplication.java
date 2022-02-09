package com.aio.portable.park;


import com.aio.portable.park.common.AppLogHubFactory;
import com.aio.portable.park.common.UserInfoEntity;
import com.aio.portable.park.test.batchrequest.BatchRequestTest;
import com.aio.portable.swiss.hamlet.bean.ResponseWrapper;
import com.aio.portable.swiss.sugar.UnsafeSugar;
import com.aio.portable.swiss.suite.log.facade.LogHub;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.redisson.spring.starter.RedissonAutoConfiguration;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
        DruidDataSourceAutoConfigure.class,
//        MybatisAutoConfiguration.class,
        KafkaAutoConfiguration.class,
        RabbitAutoConfiguration.class,
        RedissonAutoConfiguration.class,
//        RedisAutoConfiguration.class,
//        SpringContextHolder.class
})
// VMoptions: -javaagent:./jagent/target/jagent-1.1.4-SNAPSHOT.jar=Hello
//@EnableNetworkProxy
public class ParkApplication {
    static LogHub log = AppLogHubFactory.staticBuild();


    static String base = "string";
    public static void main(String[] args) {
        log.i("static","loghub");

//
//
//
////        int i=1;
////        while (i == 1) {
////            Enhancer enhancer = new Enhancer();
////            enhancer.setSuperclass(Callback.class);
////            enhancer.setUseCache(false);
////            enhancer.setCallback(new MethodInterceptor() {
////                public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
////                    return methodProxy.invokeSuper(o, objects);
////                }
////            });
////            Callback callback = (Callback) enhancer.create();
////        }
//
//
//
//        ArrayList<Object> list = new ArrayList<>();
//        int ii = 1;
//        while (ii>0) {
////            list.add(Math.random() * 1000000000);
//            list.add(String.valueOf(ii++).intern());
//
//        }
//
//        int iii = 1;
//        if (iii==1)
//            System.exit(0);
//
//        UserInfoEntity userInfo = new UserInfoEntity();
//        userInfo.setId(11111);
//        userInfo.setName("abc");
//        ResponseWrapper<BatchRequestTest.BatchResponse> batch = new BatchRequestTest().batch(userInfo);
//
//
//        CompletableFuture<String> future  = CompletableFuture.supplyAsync(() -> "Hello");
//
//        future.complete("World");
//
//        try {
//            System.out.println(future.get());
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }



//OOM.oomMetaspace1();
//OOM.oomHeapSpace();
//OOM.stackoverflow();


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



package com.aio.portable.park;


import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;


@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
        DruidDataSourceAutoConfigure.class,
        MybatisAutoConfiguration.class,
        KafkaAutoConfiguration.class,
        RabbitAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class
//        RedissonAutoConfiguration.class,
//        RedisAutoConfiguration.class,
//        SpringContextHolder.class
})
// VMoptions: -javaagent:./jagent/target/jagent-1.1.4-SNAPSHOT.jar=Hello
public class ParkApplication {
//    static LogHub log = AppLogHubFactory.staticBuild();


    static String base = "string";
    public static void main(String[] args) {
//        log.i("static", "loghub");




////        int i = 1;
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
//
//        UserInfoEntity userInfo = new UserInfoEntity();
//        userInfo.setId(11111);
//        userInfo.setName("abc");
//        ResponseWrapper<BatchRequestTest.BatchResponse> batch = new BatchRequestTest().batch(userInfo);
//
//
//        CompletableFuture<String> future  = CompletableFuture.supplyAsync(() -> "Hello");
//        future.complete("World");
//        try {
//            System.out.println(future.get());
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }






//        AnnotationConfigEmbeddedWebApplicationContext

        ConfigurableApplicationContext context = SpringApplication.run(ParkApplication.class, args);
        //ch.qos.logback.core.Appender

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
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        System.exit(0);
//        ConfigurableApplicationContext context = SpringContextHolder.run(ParkApplication.class, args, c -> c = c);

        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
        String[] beanNames = beanFactory.getBeanDefinitionNames();

        Environment environment = context.getEnvironment();
        String[] activeProfiles = environment.getActiveProfiles();
        String[] defaultProfiles = environment.getDefaultProfiles();

    }





}



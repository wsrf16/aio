package com.aio.portable.park;


import com.aio.portable.park.common.AppLogHubFactory;
import com.aio.portable.park.test.observer.MyObservable;
import com.aio.portable.park.test.observer.ReaderObserver;
import com.aio.portable.swiss.sugar.type.CollectionSugar;
import com.aio.portable.swiss.suite.log.facade.LogHub;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.apache.shardingsphere.sql.parser.api.CacheOption;
import org.apache.shardingsphere.sql.parser.api.SQLParserEngine;
import org.apache.shardingsphere.sql.parser.api.SQLVisitorEngine;
import org.apache.shardingsphere.sql.parser.core.ParseContext;
import org.apache.shardingsphere.sql.parser.spi.DatabaseTypedSQLParserFacade;
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

import java.util.List;
import java.util.Properties;
import java.util.ServiceLoader;


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
//        log.i("static","loghub");

//        org.apache.shardingsphere.sql.parser.mysql.parser.MySQLParserFacade ll;
//        List<DatabaseTypedSQLParserFacade> databaseTypedSQLParserFacades = CollectionSugar.toList(ServiceLoader.load(DatabaseTypedSQLParserFacade.class).iterator());
//        String sql = "select order_id from t_order where status = 'OK'";
//        CacheOption cacheOption = new CacheOption(128, 1024L, 4);
//        SQLParserEngine parserEngine = new SQLParserEngine("MySQL",
//                cacheOption, false);
//        ParseContext parseContext = parserEngine.parse(sql, false);
//        SQLVisitorEngine visitorEngine = new SQLVisitorEngine("MySQL",
//                "FORMAT", new Properties());
//        String result = visitorEngine.visit(parseContext);
//        System.out.println(result);
//        System.out.println(result);

//        ReaderObserver readerObserver = new ReaderObserver();
//        MyObservable observable = new MyObservable();
//        observable.addObserver(readerObserver);
//        observable.publish("1111111111");


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
//        log = AppLogHubFactory.staticBuild();
//        log.i("it is up to u. ");
        String[] beanNames = beanFactory.getBeanDefinitionNames();

        Environment environment = context.getEnvironment();
        String[] activeProfiles = environment.getActiveProfiles();
        String[] defaultProfiles = environment.getDefaultProfiles();

    }





}



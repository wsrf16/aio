package com.aio.portable.park;


import com.aio.portable.park.bean.Student;
import com.aio.portable.park.bean.UserInfoEntity;
import com.aio.portable.park.dao.master.model.Book;
import com.aio.portable.swiss.global.ColorEnum;
import com.aio.portable.swiss.hamlet.interceptor.log.annotation.NetworkProxy;
import com.aio.portable.swiss.spring.SpringContextHolder;
import com.aio.portable.swiss.sugar.resource.ClassSugar;
import com.aio.portable.swiss.sugar.type.CollectionSugar;
import com.aio.portable.swiss.sugar.type.DateTimeSugar;
import com.aio.portable.swiss.sugar.type.NumberSugar;
import com.aio.portable.swiss.sugar.type.StringSugar;
import com.aio.portable.swiss.suite.bean.DeepCloneSugar;
import com.aio.portable.swiss.suite.bean.serializer.json.ByteSugar;
import com.aio.portable.swiss.suite.bean.serializer.json.JacksonSugar;
import com.aio.portable.swiss.suite.net.tcp.proxy.NetworkProxySugar;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.ClassUtils;

import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
        DruidDataSourceAutoConfigure.class,
        MybatisAutoConfiguration.class,
        MybatisPlusAutoConfiguration.class,
        KafkaAutoConfiguration.class,
        RabbitAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class,
//        LoggingApplicationListener.class
//        RedissonAutoConfiguration.class,
//        RedisAutoConfiguration.class,
//        SpringContextHolder.class
})
// VMoptions: -javaagent:./jagent/target/jagent-1.1.4-SNAPSHOT.jar=Hello
// VMoptions: -Xlog:gc*:file=/log.txt -Xloggc:/log2.txt -XX:+PrintGCDetails -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/gc.txt
@NetworkProxy
public class ParkApplication {
//    static LogHub log = AppLogHubFactory.staticBuild();

    public static void main(String[] args) throws URISyntaxException, InterruptedException {
//        boolean primitiveOrWrapper = ClassUtils.isPrimitiveOrWrapper(String.class);
        NetworkProxySugar.SystemProxies.setUseSystemProxies(true);
        // flattened Nested
        System.out.println(ClassSugar.getPath(String.class));
        System.out.println(ClassSugar.getPath(Student.class));
        Book book = new Book();
        book.setAuthor("aaa");
        byte[] bytes = ByteSugar.objToByte(book);
        Book book1 = ByteSugar.byteToObj(bytes, Book.class);

//        log.i("static", "loghub");
        System.out.println("𝄞" + "𝄞".length());
        ConfigurableApplicationContext context = SpringApplication.run(ParkApplication.class, args);
    }




}



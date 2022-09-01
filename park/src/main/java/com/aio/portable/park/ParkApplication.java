package com.aio.portable.park;


//import com.aio.portable.park.common.GeoHash;
import com.aio.portable.park.common.AppLogHubFactory;
import com.aio.portable.park.common.UserInfoEntity;
import com.aio.portable.swiss.spring.factories.listener.SwissApplicationListener;
import com.aio.portable.swiss.sugar.resource.ResourceSugar;
import com.aio.portable.swiss.suite.algorithm.crypto.rsa.RSAKeyPair;
import com.aio.portable.swiss.suite.algorithm.crypto.rsa.RSASugar;
import com.aio.portable.swiss.suite.algorithm.encode.JDKBase64Convert;
import com.aio.portable.swiss.suite.algorithm.geo.GeoHash;
import com.aio.portable.swiss.suite.algorithm.geo.GeoPoint;
import com.aio.portable.swiss.spring.SpringContextHolder;
import com.aio.portable.swiss.suite.bean.BeanSugar;
import com.aio.portable.swiss.suite.log.facade.LogHub;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.ResolvableType;
import org.springframework.core.env.Environment;

import javax.crypto.Cipher;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
        DruidDataSourceAutoConfigure.class,
        MybatisAutoConfiguration.class,
        MybatisPlusAutoConfiguration.class,
        KafkaAutoConfiguration.class,
        RabbitAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class
//        RedissonAutoConfiguration.class,
//        RedisAutoConfiguration.class,
//        SpringContextHolder.class
})
// VMoptions: -javaagent:./jagent/target/jagent-1.1.4-SNAPSHOT.jar=Hello
// VMoptions: -Xlog:gc*:file=/log.txt -Xloggc:/log2.txt -XX:+PrintGCDetails -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/gc.txt
public class ParkApplication {
    static LogHub log = AppLogHubFactory.staticBuild();

    public static void main(String[] args) {
        ResolvableType resolvableType2 = ResolvableType.forClass(Map.class, HashMap.class);
//        "𝄞".length()
//        log.i("static", "loghub");

//        ConfigurableApplicationContext context = SpringApplication.run(ParkApplication.class, args);

        ConfigurableApplicationContext context = SpringContextHolder.run(ParkApplication.class, c -> c = c, args);
    }





}



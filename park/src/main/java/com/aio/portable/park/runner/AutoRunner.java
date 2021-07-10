package com.aio.portable.park.runner;

import com.aio.portable.park.common.AppLogHubFactory;
import com.aio.portable.park.config.root.ApplicationConfig;
import com.aio.portable.park.postprocessor.UserInfoEntity;
import com.aio.portable.park.test.BeanOrder;
import com.aio.portable.park.test.MyDatabaseTest;
import com.aio.portable.swiss.middleware.zookeeper.ZooKeeperSugar;
import com.aio.portable.swiss.sugar.PathSugar;
import com.aio.portable.swiss.sugar.ShellSugar;
import com.aio.portable.swiss.sugar.StringSugar;
import com.aio.portable.swiss.sugar.ThrowableSugar;
import com.aio.portable.swiss.suite.bean.BeanSugar;
import com.aio.portable.swiss.suite.bean.serializer.json.JacksonSugar;
import com.aio.portable.swiss.suite.log.annotation.LogMarker;
import com.aio.portable.swiss.suite.log.facade.LogHub;
import com.aio.portable.swiss.suite.log.impl.es.ESLogNote;
import com.aio.portable.swiss.suite.log.support.StandardLogNote;
import com.aio.portable.swiss.suite.spring.SpringContextHolder;
import com.aio.portable.swiss.suite.storage.persistence.NodePersistence;
import com.aio.portable.swiss.suite.storage.persistence.redis.RedisPO;
import com.aio.portable.swiss.suite.storage.persistence.zookeeper.ZooKeeperPO;
import com.aio.portable.swiss.suite.systeminfo.OSInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class AutoRunner implements ApplicationRunner {
    @Autowired
    MyDatabaseTest myDatabaseTest;

    LogHub log = AppLogHubFactory.staticBuild();

    @Autowired
    ApplicationConfig rootConfig;

//    @Autowired
//    RestTemplate restTemplate;

    @Value("${swagger.api-info.title:}")
    String swaggerApiInfoTitle;

    @Autowired
    public StringRedisTemplate stringRedisTemplate;

    @Override
    @LogMarker
    public void run(ApplicationArguments applicationArguments) {
//        com.auth0.jwt.algorithms.Algorithm
//        log.getLogList().get(1)
//        final int order = propertySourcesPlaceholderConfigurerrr.getOrder();
//        UserInfoEntity userInfoEntity;

//        try {
//            throw new NullPointerException("aaaaa");
//        } catch (NullPointerException e) {
//            e.printStackTrace();
//            new BizException(1, e);
//        }


//        List<RowDataEntity> rowDataEntityList = new ArrayList<>();
//        RowDataEntity rowDataEntity = new RowDataEntity();
//
//        List<ColumnEntity> columnEntityList = new ArrayList<>();
//        {
//            ColumnEntity columnEntity = new ColumnEntity();
//            columnEntity.setName("name");
//            columnEntity.setValue("jimmy");
//            columnEntityList.add(columnEntity);
//        }
//        {
//            ColumnEntity columnEntity = new ColumnEntity();
//            columnEntity.setName("id");
//            columnEntity.setValue("22");
//            columnEntityList.add(columnEntity);
//        }
//        rowDataEntity.setBeforeColumnEntityList(columnEntityList);
//
//        UserInfoEntity userInfoEntity1 = rowDataEntity.parseBeforeRowModel(UserInfoEntity.class);


//        List<Integer> list = new ArrayList<>();
//        list.add(0);
//        list.add(1);
//
//        final Integer[] integers1 = CollectionSugar.toArray(list, Integer.class);
//        final Integer[] integers2 = CollectionSugar.toArrayNullable(list);


//        UriComponentsBuilder.fromHttpUrl()

//        final String ccc = UrlSugar.concat("/http://aaa////////", "/bbb", "ccc", "/ddd", "/eee?", "/fff=111", "&ggg=222&", "&hhh=333");






        myDatabaseTest.blah();

    }


    //    @EventListener({ApplicationReadyEvent.class})
    void applicationReadyEvent() {
        if (OSInfo.isWindows()) {
            String port = SpringContextHolder.getEnvironment().getProperty("server.port", StringSugar.EMPTY);
            String contextPath = SpringContextHolder.getEnvironment().getProperty("server.servlet.contextPath", StringSugar.EMPTY);
            String concat = PathSugar.concatBy("/", port, contextPath);
//        String url = MessageFormat.format("http://localhost:{0}/v2/api-docs", concat);
            String url = MessageFormat.format("http://localhost:{0}/doc.html", concat);
            ShellSugar.Windows.loadURL(url);
        }
    }





}




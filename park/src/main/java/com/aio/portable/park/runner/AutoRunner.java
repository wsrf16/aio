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
import com.aio.portable.swiss.suite.storage.persistence.file.FilePO;
import com.aio.portable.swiss.suite.storage.persistence.redis.RedisPO;
import com.aio.portable.swiss.suite.storage.persistence.zookeeper.ZooKeeperPO;
import com.aio.portable.swiss.suite.systeminfo.OSInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
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
        foobar();
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

        try {
            ThrowableSugar.catchThenReturn(() -> {
                        int a = 1;
                        int b = 0;
                        int c = a / b;
                        return c;
                    }, (e) -> null
            );
            int a = 1;
            int b = 0;
            int c = a / b;
        } catch (Exception e) {
//            e.printStackTrace();
            new Thread(()->{log.setAsync(false).e("000000000", e);}).start();
        }


//        new UrlPathHelper().getLookupPathForRequest(null)
        new BeanOrder(new UserInfoEntity());
        final StandardLogNote esLogNote = new ESLogNote();
        esLogNote.setName("aaa");


        final Field field = BeanSugar.Fields.getDeclaredFieldIncludeParents(StandardLogNote.class).get(1);
        final JsonProperty annotation = field.getAnnotation(JsonProperty.class);

        try {
            InvocationHandler h = Proxy.getInvocationHandler(annotation);
            // 获取 AnnotationInvocationHandler 的 memberValues 字段
            Field hField = h.getClass().getDeclaredField("memberValues");
            // 因为这个字段事 private final 修饰，所以要打开权限
            hField.setAccessible(true);
            // 获取 memberValues
            Map memberValues = (Map) hField.get(h);
            // 修改 value 属性值
            memberValues.put("value", "nnnname");
            System.out.println(JacksonSugar.obj2ShortJson(esLogNote));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        myDatabaseTest.blah();


        try {
            Thread.sleep(0);
//            Class.forName(ResourceTest.class.toString());
//            ResourceTest resourceTest = new ResourceTest();
        } catch (Exception e) {
            e.printStackTrace();
        }
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








    public void foobar() {
//        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
//        redisStandaloneConfiguration.setHostName("http://mecs.com");

//        JedisConnectionFactory redisConnectionFactory = new JedisConnectionFactory(redisStandaloneConfiguration);
//        redisConnectionFactory.setHostName("http://mecs.com");

//        new JedisConnectionFactory()
//        JedisClientConfiguration.builder().usePooling().poolConfig()
//        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate(redisConnectionFactory);
//        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();


        Map<String, Object> map1 = new HashMap<>();
        map1.put("key1", 1111);

        Map<String, Object> map2 = new HashMap<>();
        map2.put("key1", 1111);
        map2.put("key2", 2222);

        Map<String, Object> map3 = new HashMap<>();
        map3.put("key1", 1111);
        map3.put("key2", 2222);
        map3.put("key3", 3333);

        Map<String, Object> map4 = new HashMap<>();
        map4.put("key1", 1111);
        map4.put("key2", 2222);
        map4.put("key3", 3333);
        map4.put("key4", 4444);

        ZooKeeper build = ZooKeeperSugar.build("10.124.202.121:32181", 60000, null);
//        ZooKeeperSugar.clearIfExists(build, "/database1/group-group-task");
        NodePersistence po;
        po = ZooKeeperPO.singletonInstance(build);
        ((ZooKeeperPO) po).setDatabase("database1");
//        po(map1, map2, map3, map4, po);

        po = RedisPO.singletonInstance(stringRedisTemplate, "database1");
        po(map1, map2, map3, map4, po);

        po = FilePO.singletonInstance("database1");
        po(map1, map2, map3, map4, po);
    }

    private void po(Map<String, Object> map1, Map<String, Object> map2, Map<String, Object> map3, Map<String, Object> map4, NodePersistence po) {
        po.set("table1", map1);
        po.set("table2", map2, "table1");
        po.set("table21", map2, "table1");
        po.set("table3", map3, "table1", "table2");
        po.set("table31", map3, "table1", "table2");
        po.set("table4", map4, "table1", "table2", "table3");
        po.set("table41", map4, "table1", "table2", "table3");

        Map<String, Map> all = po.getAll("table1", Map.class);
        List<String> table1s = po.getChildren("table1");
        List<String> table2s = po.getChildren("table2", "table1");
        Map<String, Map> table2 = po.getAll("table2", Map.class, "table1");
        List<String> tables = po.tables();

        po.remove("table41", "table1", "table2", "table3");
        po.remove("table31", "table1", "table2");

        po.clear("table1");
        po.clearDatabase();
    }
}




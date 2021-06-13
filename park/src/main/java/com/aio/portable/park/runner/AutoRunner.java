package com.aio.portable.park.runner;

import com.aio.portable.park.postprocessor.UserInfoEntity;
import com.aio.portable.park.common.AppLogHubFactory;
import com.aio.portable.park.config.root.ApplicationConfig;
import com.aio.portable.park.test.BeanOrder;
import com.aio.portable.park.test.MyDatabaseTest;
import com.aio.portable.park.test.ResourceTest;
import com.aio.portable.swiss.global.Constant;
import com.aio.portable.swiss.sugar.*;
import com.aio.portable.swiss.suite.bean.BeanSugar;
import com.aio.portable.swiss.suite.bean.serializer.json.JacksonSugar;
import com.aio.portable.swiss.suite.io.PathSugar;
import com.aio.portable.swiss.suite.log.facade.LogHub;
import com.aio.portable.swiss.suite.log.annotation.LogMarker;
import com.aio.portable.swiss.suite.log.impl.es.ESLogNote;
import com.aio.portable.swiss.suite.log.support.StandardLogNote;
import com.aio.portable.swiss.suite.systeminfo.OSInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.text.MessageFormat;
import java.util.ArrayList;
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

    @Override
    @LogMarker
    public void run(ApplicationArguments applicationArguments) {
//        com.auth0.jwt.algorithms.Algorithm
//        log.getLogList().get(1)
//        final int order = propertySourcesPlaceholderConfigurerrr.getOrder();
        List<Integer> list = new ArrayList<>();
        list.add(0);
        list.add(1);

        final Integer[] integers1 = CollectionSugar.toArray(list, Integer.class);
        final Integer[] integers2 = CollectionSugar.toArrayNullable(list);

        log.error("6666666666666666666");


        try {
            ThrowableSugar.catchThenReturn(() -> {
                        int a = 1;
                        int b = 0;
                        int c = a / b;
                        return c;
                    }, (e) -> null
            );
        } catch (Exception e) {
            e.printStackTrace();
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
}




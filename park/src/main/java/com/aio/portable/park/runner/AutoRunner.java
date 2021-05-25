package com.aio.portable.park.runner;

import com.aio.portable.park.beanprocessor.UserInfoEntity;
import com.aio.portable.park.common.AppLogHubFactory;
import com.aio.portable.park.config.ApplicationConfig;
import com.aio.portable.park.test.BeanOrder;
import com.aio.portable.park.test.MyDatabaseTest;
import com.aio.portable.park.test.ResourceTest;
import com.aio.portable.swiss.suite.bean.BeanSugar;
import com.aio.portable.swiss.suite.bean.serializer.json.JacksonSugar;
import com.aio.portable.swiss.suite.log.facade.LogHub;
import com.aio.portable.swiss.suite.log.annotation.LogMarker;
import com.aio.portable.swiss.suite.log.impl.es.ESLogNote;
import com.aio.portable.swiss.suite.log.support.StandardLogNote;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.UrlPathHelper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Map;

@Configuration
public class AutoRunner implements ApplicationRunner {
    @Autowired
    MyDatabaseTest myDatabaseTest;

    LogHub log = AppLogHubFactory.staticBuild();

    @Autowired
    ApplicationConfig rootConfig;

//    @Autowired
//    LogTest logTest;

//    static abstract class Abb {
//        static Abb abb;
//        abstract void ff();
//        final static void f() {
//            abb.ff();
//        }
//    }
//    class Ab extends Abb {
//        @Override
//        void ff() {
//            System.out.println("ok");
//        }
//    }

    @Override
    @LogMarker
    public void run(ApplicationArguments applicationArguments) {
//        com.auth0.jwt.algorithms.Algorithm
//        log.getLogList().get(1)
        cert(11111111);

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
//            StringSugar.rightPad(22,22)

            Thread.sleep(0);
//            Class.forName(ResourceTest.class.toString());
            ResourceTest resourceTest = new ResourceTest();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }






    public void cert(int sth) {
        System.out.println(sth);
//            DataCertCreate dataCertCreate = new DataCertCreate();
//            String[] info = { "huahua_user", "hnu", "university", "china", "hunan", "changsha", "111111", "11111111", "1" };
//            // 生成公钥
//            boolean createPublicKey = dataCertCreate.createPublicKey(info);
//            System.out.println("PUBLIC KEY CREATE OK, result==" + createPublicKey);
//
//            boolean createPublicKeyBYDecode = dataCertCreate.createPublicKeyBYDecode(info);
//            System.out.println("PUBLIC KEY BY BASE64Encoder CREATE OK, result==" + createPublicKeyBYDecode);
//
//            boolean createPrivateKey = dataCertCreate.createPrivateKey(info);
//            System.out.println("PRIVATE KEY CREATE OK, result==" + createPrivateKey);
//
//            Boolean pfx = dataCertCreate.toPFX(info);
//            System.out.println("transToPFX OK, result==" + pfx);
//
//
//
//
//            CertSugar.createKeyStore(new File("d:\\a"), "", new X500Name(""), "");
//            CertSugar.CertInfo certInfo = new CertSugar.CertInfo();
//            CertSugar.createSubjectCert(certInfo, "","", new File("d:\\a"), "", "cn", "");
    }
}




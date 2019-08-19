package com.york.portable.swiss.sandbox;

import com.york.portable.swiss.assist.log.hub.LogHub;
//import com.york.portable.swiss.assist.log.hub.factory.baselogger.classic.ConsoleBaseLogger;
import com.york.portable.swiss.bean.SingletonProvider;
import com.york.portable.swiss.bean.serializer.SerializerEnum;
import com.york.portable.swiss.bean.serializer.json.GsonUtil;
import com.york.portable.swiss.bean.serializer.json.JacksonUtil;
import com.york.portable.swiss.assist.cache.CacheRoom;
import com.york.portable.swiss.ciphering.TotalEncrypt;
//import com.york.portable.swiss.db.SqlSpeller;
//import com.york.portable.hamlet.model.ResponseWrapper;
import com.york.portable.swiss.net.http.HttpSwift;
import com.york.portable.swiss.sandbox.a中文.AA;
import com.york.portable.swiss.sandbox.a中文.BB;
import com.york.portable.swiss.sugar.*;
import com.york.portable.swiss.assist.log.classic.impl.slf4j.Slf4jLogger;
import com.york.portable.swiss.resource.ClassLoaderUtils;
import com.york.portable.swiss.resource.ClassUtils;
import com.york.portable.swiss.resource.StreamClassLoader;
import com.york.portable.swiss.resource.PackageUtils;
import com.york.portable.swiss.resource.ResourceUtils;
import com.york.portable.swiss.assist.document.method.PropertiesMapping;
import com.york.portable.swiss.assist.log.classic.impl.console.ConsoleLogger;
import com.york.portable.swiss.assist.log.classic.impl.file.FileLogger;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;

import java.awt.print.Book;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLClassLoader;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Sample {
    public static void sample() throws Exception {
        int sampleNum = 5;
//        switch (sampleNum) {
//            case 0:
//                ResourceUtilSample.sample();
//                break;
//            case 1:
//                PackageUtilBlah.sample();
//                break;
//            case 2:
//                PagePocketSample.sample();
//                break;
//            case 3:
//                LoggerSample.sample();
//                break;
//            case 4:
//                HttpClientUtilSample.sample();
//                break;
//            case 5:
//                JsonUtilSample.sample();
//                break;
//            case 6:
////                SqlSpellerSample.sample();
//                break;
//            case 7:
//                ClassLoaderUtilSample.sample();
//                break;
//            case 8:
//                ClassUtilBlah.sample();
//                break;
//            case 9:
//                break;
//            case 10:
//                break;
//            case 11:
//                break;
//            case 12:
//                break;
//            case 13:
//                break;
//
//        }
    }




//    public static class SqlSpellerSample {
//        public static void sample() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
//            Wood wood = new Wood();
//            wood.setA(1111);
////            wood.setB("republicOfChina");
//
//            String sql1 = SqlSpeller.spellInsertText(wood);
//            String sql2 = SqlSpeller.spellSelectText(wood);
//            String sql3 = SqlSpeller.spellUpdateText(wood);
//            String sql4 = SqlSpeller.spellConditionText(wood);
//        }
//    }

    public static class CacheRoomSample {
        public static void sample() {
            Map<String, String> map = new HashMap<String, String>();
            map.put("a", "1");
            map.put("b", "2");
            map.put("c", "3");
            map.put("d", "4");

            CacheRoom.saveByJson("A1", map);
        }
    }




    public class PropertiesMappingSample {
        public void sample() throws IOException {
            PropertiesMapping pps = new PropertiesMapping("1.properties");
            BigDecimal v1 = pps.getDecimal("AAA");
            Date v2 = pps.getDateTime("BBB");
            String v3 = pps.getString("CCCD", "888");
        }
    }





    @Flag(isMaster = true, age = 22)
    public class AnnotationBlah {
        public void test() {
            Class<?> clazz = AnnotationBlah.class;
            Flag flag = clazz.getAnnotation(Flag.class);
        }
    }


    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD, ElementType.METHOD, ElementType.TYPE})
    @interface Flag {
        boolean isMaster() default false;

        int age() default 18;
    }




    public static class ResourceUtilSample {
    }


    public static class BasicTypeBlah {
        public static void blah() {
            BasicType.InnerClosure innerClosure = new BasicType().serialNumber();
            String s1 = innerClosure.serialNumber();
            String s2 = innerClosure.serialNumber();
        }
    }

    public static class PathUtilsBlah {
        public static void blah() {
            String[] directories = new String[]{"/a/\\1\\", "/b/\\2", "c\\3\\", "d",
                    "//e\\\\", "\\/f", "g/\\", "h//"};
            String concat = PathUtils.concat(directories);
        }
    }

    public static class TotalEncryptBlah {
        public static void blah() throws UnsupportedEncodingException {
            String a1 = TotalEncrypt.md5("aaa");
            String a2 = TotalEncrypt.SpringFrameWork.encodeBase64("aaa");
        }
    }

    public static class PackageUtilsBlah {
        public static void blah() throws IOException, ClassNotFoundException {
            List<String> list = PackageUtils.getClassName(PackageUtilsBlah.class.getPackage().getName());
            for (String name : list) {
//            Class<?> clazz = Class.forName(name);
                Class<?> clazz = Thread.currentThread().getContextClassLoader().loadClass(name);
                if (clazz.isAnnotationPresent(com.york.portable.swiss.sandbox.a中文.Flag.class)) {
                    com.york.portable.swiss.sandbox.a中文.Flag flag = clazz.getAnnotation(com.york.portable.swiss.sandbox.a中文.Flag.class);
                    int age = flag.age();
                    System.out.println(name);
                }
            }
        }
    }


    public static class ClassUtilBlah {
        public static void sample() throws IOException {
            String ss = ClassUtils.getPath(AA.class);
            boolean b1 = ClassUtils.exist("com.york.portable.swiss.sandbox.Wood");
            boolean b2 = ClassUtils.exist("com.york.portable.swiss.sandbox.Wood");


            if (isExistJackson())
                System.out.println(JacksonUtil.obj2Json(new Wood() {
                    {
                        setA(888);
                    }
                }));

            System.out.println();

            if (isExistGson())
                System.out.println(GsonUtil.obj2Json(new Wood() {
                    {
                        setA(888);
                    }
                }));
        }

        private static boolean isExistJackson() throws IOException {
            return ClassUtils.exist(("com.fasterxml.jackson.databind.JsonSerializer"));
        }

        private static boolean isExistGson() throws IOException {
            return ClassUtils.exist(("com.google.gson.Gson"));
        }
    }

    public static class PackageUtilBlah {

    }

    public static class SingletonProviderBlah {
        public static void blah() throws InstantiationException, IllegalAccessException {
            AA aa = SingletonProvider.instance(AA.class);
            aa.aa = 77;
            BB bb = SingletonProvider.instance(BB.class);
            bb.aa = 44;
            AA aa1 = SingletonProvider.instance(AA.class);
            BB bb2 = SingletonProvider.instance(BB.class);
        }
    }



}

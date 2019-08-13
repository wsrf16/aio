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
        switch (sampleNum) {
            case 0:
                ResourceUtilSample.sample();
                break;
            case 1:
                PackageUtilBlah.sample();
                break;
            case 2:
                PagePocketSample.sample();
                break;
            case 3:
                LoggerSample.sample();
                break;
            case 4:
                HttpClientUtilSample.sample();
                break;
            case 5:
                JsonUtilSample.sample();
                break;
            case 6:
//                SqlSpellerSample.sample();
                break;
            case 7:
                ClassLoaderUtilSample.sample();
                break;
            case 8:
                ClassUtilBlah.sample();
                break;
            case 9:
                break;
            case 10:
                break;
            case 11:
                break;
            case 12:
                break;
            case 13:
                break;

        }
    }


    public static class UnixTimeSample {
        public static void sample() {
            long longg = DateTimeUtils.UnixTime.nowUnix();
            long longgg = DateTimeUtils.UnixTime.convertDateTime2Unix(LocalDateTime.now());
            LocalDateTime time = DateTimeUtils.UnixTime.convertUnix2DateTime(longg);
            long longggg = DateTimeUtils.UnixTime.convertUTC2Unix(time);
        }
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


    public static class LoggerSample { //extends ConsoleBaseLogger {
        //        private org.apache.logging.log4j.Logger logger = org.apache.logging.log4j.LogManager.getLogger(this.getClass());
        public static void sample() {
//            {
//                new LoggerSample().logger.i("abc-abc-abc");
//            }
            {
                ConsoleLogger consoleLogger = ConsoleLogger.build("custom");
//                consoleLogger.getSerializer().setSerializer(SerializerEnum.JACKXML);
                consoleLogger.d("this is console.");
//                ResponseWrapper s = null;
//                try {
//                    String sss = s.toString();
//                    int ss = s.getStatusCode();
//                } catch (Exception e) {
//                    consoleLogger.e("测试异常", e);
//                }
            }
            int _a = 1;
            if (_a == 1) {
//                return;
            }
            {
                LogHub loggerSet = LogHub.build(ConsoleLogger.build(LoggerSample.class), FileLogger.build(LoggerSample.class));
                loggerSet = LogHub.build(Slf4jLogger.build(LoggerSample.class));
                loggerSet.d("this is loggerhub.");
            }
//            {
//                Log4j2Logger log4j2Logger = Log4j2Logger.build(Sample.class);
//                log4j2Logger.i("this is log4j2logger.");
//            }
            {
//                org.slf4j.Logger loggerBack = org.slf4j.LoggerFactory.getLogger(Sample.class);
                Slf4jLogger slf4jLogger = Slf4jLogger.build(Sample.class);
                slf4jLogger.i("this is logback.");
            }
            {
                try {
                    int a = 0;
                    int b = 1 / a;
                } catch (Exception e) {
                    ConsoleLogger logger = ConsoleLogger.build(Sample.class);
                    logger.e(e);
                }
            }
        }

        public static void hubSample() {
            ConsoleLogger logger1 = ConsoleLogger.build("logDir");
            logger1.d("aaaa");

            FileLogger logger2 = FileLogger.build("logDir");
            //logger2.openSingleIPPrefix();
            logger2.d("aaaa");

            LogHub hub = LogHub.build(logger1, logger2);
            Map<String, String> map = new HashMap<String, String>();

            try {
                int a = 1;
                int b = 0;
                int c = a / b;
            } catch (Exception e) {
                map.put("a", "1");
                map.put("b", "2");
                map.put("c", "3");
                map.put("d", "4");
                hub.e("summary", map, e);
            }
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


    public static class PagePocketSample {
        public static void sample() {
            List<Integer> ints = Stream.iterate(1, item -> item + 1).limit(101).collect(Collectors.toList());
            PagePocket<Integer> pocket = PagePocket.paging(ints, 3, 31);
            Integer currentPage = pocket.getPageIndex();
            List<Integer> currentPageItems = pocket.getCurrentPageItems();
            Integer currentSize = pocket.getPageSize();
            Integer currentSizeCapcity = pocket.getPageCapcity();
//        List<Integer> totalItems = pocket.totalItems;
            Integer totalPages = pocket.getTotalPages();
            Integer totalCount = pocket.getTotalCount();


            PagePocket<Integer> pock111 = CacheRoom.popByJson("A1", PagePocket.class);

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


    public static class ClassLoaderUtilSample {
        public static void sample() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
            String name;
            boolean b;
            name = "java.lang.System";
            b = ClassLoaderUtils.hasLoadedInCurrentThread(name);
            printResult(name, b);

            name = "java.sql.Date";
            b = ClassLoaderUtils.hasLoadedInCurrentThread(name);
            printResult(name, b);
            java.sql.Date date = new java.sql.Date(0);
            b = ClassLoaderUtils.hasLoadedInCurrentThread(name);
            printResult(name, b);

            name = "com.york.portable.swiss.sandbox.Wood";
            b = ClassLoaderUtils.hasLoadedInCurrentThread(name);
            printResult(name, b);
            Wood wood = new Wood();
            b = ClassLoaderUtils.hasLoadedInCurrentThread(name);
            printResult(name, b);

        }
    }

    private static void printResult(String name, boolean b) {
        if (b) {
            System.out.println(MessageFormat.format("{0}已经加载!", name));
        } else {
            System.out.println(MessageFormat.format("{0}尚未加载!", name));
        }
    }

    public static class ResourceUtilSample {
        public static void sample() throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException {
            ResourceUtils.getResourcesInClassFile("com/york/portable/swiss/sandbox/a中文/AA.class");
            ResourceUtils.getResourcesByClassName("com.york.portable.swiss.sandbox.Wood");
            ResourceUtils.getResourcesByClass(Book.class);


            String jarPath = new File("console-1.0-SNAPSHOT.jar").getAbsolutePath();
            String resourceInJar = "/sandbox/console/Book.class";
            URL url = ResourceUtils.getResourceInJar(jarPath, resourceInJar);
            List<URL> urlList = ResourceUtils.getResourcesInJar(jarPath);

            {
                String className = ResourceUtils.path2FullName(resourceInJar);
                Class clazz = StreamClassLoader.buildByFile("console-1.0-SNAPSHOT.jar").loadClassByBinary(className);
                className = "com.york.portable.swiss.sandbox.Wood";
                Class clazz1 = StreamClassLoader.buildByFile("target/classes/com/york/portable/swiss/sandbox/Wood.class").loadClassByBinary(className);
                Class clazz2 = StreamClassLoader.buildByResource("com/york/portable/swiss/sandbox/Wood.class").loadClassByBinary(className);
                Object obj = clazz.newInstance();
                Object obj1 = clazz.newInstance();
            }
            {
                URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{new URL("file:/" + jarPath)});
                Class clazz = urlClassLoader.loadClass("sandbox.console.Book");
                Object obj = clazz.newInstance();
                Object obj1 = clazz.newInstance();
            }
        }
    }

    public static class JsonUtilSample {
        public static void sample() throws IOException {
            JsonModel a = new JsonModel() {{
                setNo(88);
            }};
            List<JsonModel> list = new ArrayList<>();
            list.add(a);
            Map<Integer, JsonModel> map = new HashMap<>();
            map.put(1, a);

            a = JacksonUtil.json2T(JacksonUtil.obj2Json(a), JsonModel.class);
            {
                JsonModel _model;
                _model= JacksonUtil.json2T(JacksonUtil.obj2Json(""), JsonModel.class);
                _model = JacksonUtil.json2T(JacksonUtil.obj2Json(null), JsonModel.class);
            }
            list = JacksonUtil.json2Complex(JacksonUtil.obj2Json(list));
            map = JacksonUtil.json2Complex(JacksonUtil.obj2Json(map));
//        String aListJson = JsonUtil.obj2Json(aList);
//        AA b = JsonUtil.json2Obj(aJson);
//        List<AA> bList = JsonUtil.json2Obj(aListJson);
        }

        static class JsonModel {
            public int getNo() {
                return no;
            }

            public void setNo(int no) {
                this.no = no;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            private int no;

            private String name;
        }
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
        public static void sample() throws IOException {
            String packageName = PackageUtilBlah.class.getPackage().getName();
            List<String> list = PackageUtils.getClassName(packageName);
        }
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

    public static class RegexUtilsBlah {
        public static void blah() {
            String ret1 = RegexUtils.fakePhone("12345678901");
            String ret2 = RegexUtils.replaceAll("12345678901", "4567", "xxxx");
            boolean ret3 = RegexUtils.matches("12345678901", "456");
            System.out.println(ret1);
            System.out.println(ret2);
            System.out.println(ret3);
        }
    }


    public static class BeanUtilsSample {
        public class People {
            private int age;
            private String name;
            private Date birthday;


            public int getAge() {
                return age;
            }

            public void setAge(int age) {
                this.age = age;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public Date getBirthday() {
                return birthday;
            }

            public void setBirthday(Date birthday) {
                this.birthday = birthday;
            }
        }


        public static void sample() throws IllegalAccessException, InstantiationException, ClassNotFoundException, InvocationTargetException {

            String typeName = People.class.getTypeName();
            Class clazz = Class.forName(typeName);
            Object people = clazz.newInstance();
            ConvertUtils.register(new DateLocaleConverter(), java.util.Date.class);
            // 鎴?
            ConvertUtils.register(
                    new Converter() {
                        public Object convert(Class type, Object value) {
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            try {
                                return simpleDateFormat.parse(value.toString());
                            } catch (ParseException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            return null;
                        }
                    }
                    , java.util.Date.class);
            BeanUtils.setProperty(people, "age", 111);
            BeanUtils.setProperty(people, "name", "york");
            BeanUtils.setProperty(people, "birthday", "2018-08-08");

            System.out.println(people);
        }
    }


    public static class HttpClientUtilSample {
        public static void sample() throws IOException {
            class People {
                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                private String name;
            }

            People people = new People();
            people.setName("John");
            String url = "http://www.baidu.com";
            HttpHost httpProxy = new HttpHost("127.0.0.1", 8888, "http");
            RequestConfig config = RequestConfig.custom().setProxy(httpProxy).build();
            HttpSwift.getSerializer().setSerializer(SerializerEnum.SERIALIZE_JACKXML);
            StringEntity entity = HttpSwift.buildJsonObjectEntity(people, "utf-8");
            Header[] headers = newHeaders("sign");

            HttpPost httpPost = HttpSwift.buildPost(url, config, entity, headers);
            CloseableHttpClient client;
            {
                client = HttpClientBuilder.create().build();
                client = HttpClients.createDefault();
            }
            CloseableHttpResponse response = client.execute(httpPost);
            String result = HttpSwift.getResult(response);
            System.out.println(result);
        }

        private static Header[] newHeaders(String sign) {
            List<org.apache.http.message.BasicHeader> headers = new ArrayList<>();
            BasicHeader header2 = new BasicHeader("sign", sign);
            BasicHeader header1 = new BasicHeader("Accept-Language", "zh-cn");
            headers.add(header1);
            headers.add(header2);
            return headers.toArray(new BasicHeader[0]);
        }
    }
}

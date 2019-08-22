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











}

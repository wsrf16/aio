package com.aio.portable.park.runner;

import com.aio.portable.park.config.AppLogHubFactory;
import com.aio.portable.park.config.RootConfig;
import com.aio.portable.park.test.LogTest;
import com.aio.portable.park.test.MyDatabaseTest;
import com.aio.portable.swiss.suite.log.LogHub;
import com.aio.portable.swiss.suite.net.protocol.http.RestTemplater;
import com.aio.portable.swiss.suite.net.protocol.http.resttemplate.SkipSSLSimpleClientHttpRequestFactory;
import com.aio.portable.swiss.suite.resource.PackageSugar;
import com.aio.portable.swiss.suite.resource.ResourceSugar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class AutoRunner implements ApplicationRunner {

//    @Autowired
//    MyDatabaseTest mybatisTest;
    @Autowired
    MyDatabaseTest myDatabaseTest;

    LogHub log = AppLogHubFactory.logHub();//.setSamplerRate(1f);

    @Autowired
    RootConfig rootConfig;

    @Autowired
    LogTest logTest;

    @Autowired
    RestTemplateBuilder restTemplateBuilder;

    @Override
    public void run(ApplicationArguments applicationArguments) {
//            ResponseEntity<String> stringResponseEntity = RestTemplater.getJsonUTF8(RestTemplater.Build.buildSkipSSLRestTemplate(restTemplateBuilder), "https://10.124.154.8/a/login", RestTemplater.Http.jsonHttpHead(), String.class);
//            ResponseEntity<String> stringResponseEntity1 = RestTemplater.getJsonUTF8(RestTemplater.Build.buildSkipSSLRestTemplate(restTemplateBuilder), "https://10.124.154.8/a/login", RestTemplater.Http.jsonHttpHead(), String.class, null);

//        logTest.logStyle();
//        myDatabaseTest.blah();



    }

    private void aa() {
        //        ResourceSugar.ByClassLoader.getResources("classpath:config/mapper/*.xml")
        System.exit(0);
        String root = "D:/NutDisk/Program/Resource/Library/Java/_solution/Project/all-in-one/";
        root = "./";
        String file = root + "park/target/park.jar";


        try {
            List<URL> collect = ResourceSugar.getResourcesInJar(file).stream().collect(Collectors.toList());
            String url = collect.get(128).toString();

            // "jar:file:/D:/NutDisk/Program/Resource/Library/Java/_solution/Project/all-in-one/park/target/ppppark.jar!/BOOT-INF/lib/park-db-1.1.4-SNAPSHOT.jar!/com/aio/portable/parkdb/dao/master/model/Book.class";

//            ResourceWorld.convert2QualifiedClassName(url)

            URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{new URL("file:/" + file)});
            URLClassLoader urlClassLoader1 = urlClassLoader;
            Class<?> aClass = urlClassLoader.loadClass("com.aio.portable.park.config.BeanConfig");

        } catch (Exception e) {
            e.printStackTrace();
        }

        List<String> path1 = PackageSugar.getQualifiedClassNameByPath("file:/" + file);
        List<String> path2 = PackageSugar.getQualifiedClassNameByPath(root + "park/target");





//        try {
//            PackageWorld.getQualifiedClassNameByJar("file:/" + file + "!/com/aio/portable/parkdb/dao/master/model");
//
//            List<String> qualifiedClassName = PackageWorld.getQualifiedClassName("com.aio.portable.swiss.ciphering");
//            List<String> qualifiedClassName2 = PackageWorld.getQualifiedClassName("com.aio.portable.swiss.sandbox");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }










//    public static RestTemplate buildRestTemplate() {
//
//        RestTemplate restTemplate = new RestTemplate();
//        if (ignoreSSL) {//ignoreSSL为true时，绕过证书
//            HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
//            factory.setConnectionRequestTimeout(300000);
//            factory.setConnectTimeout(300000);
//            factory.setReadTimeout(300000);
//            // https
//            CloseableHttpClient httpClient = getHttpsClient();
//            factory.setHttpClient(httpClient);
//            restTemplate = new RestTemplate(factory);
//        }
//        reInitMessageConverter(restTemplate);
//        return restTemplate;
//    }
//
//    public static CloseableHttpClient getHttpsClient() {
//
//        CloseableHttpClient httpClient;
//        if (ignoreSSL) {//ignoreSSL为true时，绕过证书
//            SSLContext sslContext = null;
//            try {
//                sslContext = SSLContexts.custom().loadTrustMaterial(null, new TrustStrategy() {
//                    @Override
//                    public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
//                        return true;
//                    }
//                }).build();
//            } catch (NoSuchAlgorithmException e) {
//                e.getStackTrace();
//            } catch (KeyManagementException e) {
//                e.getStackTrace();
//            } catch (KeyStoreException e) {
//                e.getStackTrace();
//            }
//            httpClient = HttpClients.custom().setSSLContext(sslContext).
//                    setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
//        } else {
//            httpClient = HttpClients.createDefault();
//        }
//        return httpClient;
//    }


//    @Bean
//    public RestTemplate restTemplate() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
//        TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;
//
//        //忽略证书
//        SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
//                .loadTrustMaterial(null, acceptingTrustStrategy)
//                .build();
//
//        SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);
//
//        CloseableHttpClient httpClient = HttpClients.custom()
//                .setSSLSocketFactory(csf)
//                .build();
//
//        HttpComponentsClientHttpRequestFactory requestFactory =
//                new HttpComponentsClientHttpRequestFactory();
//
//        requestFactory.setHttpClient(httpClient);
//        RestTemplate restTemplate = new RestTemplate(requestFactory);
//        //处理中文乱码
//        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
//        return restTemplate;
//    }










//    @Autowired
//    RestTemplate restTemplate;
//
//    @Bean
//    public RestTemplate restTemplate(ClientHttpRequestFactory factory){
//        return new RestTemplate(factory);
//    }
//
//    @Bean
//    public ClientHttpRequestFactory skipSSLSimpleClientHttpRequestFactory() {
//        SkipSSLSimpleClientHttpRequestFactory factory = new SkipSSLSimpleClientHttpRequestFactory();
//        factory.setReadTimeout(5000);
//        factory.setConnectTimeout(15000);
//        return factory;
//    }
}

package com.aio.portable.swiss.spring.factories.autoconfigure;

import com.aio.portable.swiss.suite.net.tcp.proxy.classic.HttpProxyBean;
import com.aio.portable.swiss.suite.net.tcp.proxy.classic.HttpsProxyBean;
import com.aio.portable.swiss.suite.net.tcp.proxy.classic.SocksProxyBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

//@Configuration
public class NetworkProxyAutoConfiguration {//implements InitializingBean {
//    Log log = LogFactory.getLog(NetworkProxyAutoConfiguration.class);

    @ConditionalOnProperty(
            prefix = "proxy.http",
            name = "host"
    )
    @ConfigurationProperties("proxy.http")
    @Bean
    public HttpProxyBean httpProxyObject() {
        return new HttpProxyBean();
    }

//    @Bean
//    public Proxy proxyHttp(ProxyObject.Http object) {
//        if (object.getAutoOn()) {
//            if (object.getHost() != null)
//                NetProxySugar.Http.setProxyHost(object.getHost());
//            if (object.getPort() != null)
//                NetProxySugar.Http.setProxyPort(object.getPort());
//        }
//
//        Proxy.Type type = Type.HTTP;
//        SocketAddress sa = new InetSocketAddress(object.getHost(), object.getPort());
//        Proxy proxy = new Proxy(type, sa);
//        return proxy;
//    }


    @ConditionalOnProperty(
            prefix = "proxy.https",
            name = "host"
    )
    @ConfigurationProperties("proxy.https")
    @Bean
    public HttpsProxyBean proxyHttps() {
        return new HttpsProxyBean();
    }

//    @Bean
//    public Proxy proxyHttps(ProxyObject.Https object) {
//        if (object.getAutoOn()) {
//            if (object.getHost() != null)
//                NetProxySugar.Https.setProxyHost(object.getHost());
//            if (object.getPort() != null)
//                NetProxySugar.Https.setProxyPort(object.getPort());
//        }
//
//        Proxy.Type type = Type.HTTP;
//        SocketAddress sa = new InetSocketAddress(object.getHost(), object.getPort());
//        Proxy proxy = new Proxy(type, sa);
//        return proxy;
//    }


//    @ConditionalOnProperty(
//            prefix = "proxy.ftp",
//            name = "proxyHost"
//    )
//    @ConfigurationProperties("proxy.ftp")
//    @Bean
//    public ProxyObject.Ftp proxyFtp() {
//        return new ProxyObject.Ftp();
//    }
//
//    @Bean
//    public Proxy proxyFtp(ProxyObject.Ftp object) {
//        if (object.getAutoOn()) {
//            if (object.getHost() != null)
//                NetProxySugar.Ftp.setProxyHost(object.getHost());
//            if (object.getPort() != null)
//                NetProxySugar.Ftp.setProxyPort(object.getPort());
//        }
//
//        Proxy.Type type = Type.;
//        SocketAddress sa = new InetSocketAddress(object.getHost(), object.getPort());
//        Proxy proxy = new Proxy(type, sa);
//        return proxy;
//    }


    @ConditionalOnProperty(
            prefix = "proxy.socks",
            name = "host"
    )
    @ConfigurationProperties("proxy.socks")
    @Bean
    public SocksProxyBean socksHttp() {
        return new SocksProxyBean();
    }


//    @Bean
//    public Proxy proxySocks(ProxyObject.Socks object) {
//        if (object.getAutoOn()) {
//            if (object.getHost() != null)
//                NetProxySugar.Socks.setProxyHost(object.getHost());
//            if (object.getPort() != null)
//                NetProxySugar.Socks.setProxyPort(object.getPort());
//        }
//
//        Proxy.Type type = Type.SOCKS;
//        SocketAddress sa = new InetSocketAddress(object.getHost(), object.getPort());
//        Proxy proxy = new Proxy(type, sa);
//        return proxy;
//    }







}

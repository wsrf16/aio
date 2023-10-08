package com.aio.portable.swiss.spring.factories.autoconfigure;

import com.aio.portable.swiss.suite.net.tcp.proxy.NetworkProxyBean;
import com.aio.portable.swiss.suite.net.tcp.proxy.classic.HttpProxyBean;
import com.aio.portable.swiss.suite.net.tcp.proxy.classic.HttpsProxyBean;
import com.aio.portable.swiss.suite.net.tcp.proxy.classic.SocksProxyBean;
import com.aio.portable.swiss.suite.net.tcp.proxy.classic.SystemProxiesBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

public class NetworkProxyAutoConfiguration {//implements InitializingBean {
//    Log log = LogFactory.getLog(NetworkProxyAutoConfiguration.class);

    @ConditionalOnProperty(
            prefix = "proxy.http",
            name = "host"
    )
    @ConfigurationProperties("proxy.http")
    @Bean
    public HttpProxyBean httpProxyBean() {
        return new HttpProxyBean();
    }

    @ConditionalOnProperty(
            prefix = "proxy.https",
            name = "host"
    )
    @ConfigurationProperties("proxy.https")
    @Bean
    public HttpsProxyBean httpsProxyBean() {
        return new HttpsProxyBean();
    }

    @ConditionalOnProperty(
            prefix = "proxy.socks",
            name = "host"
    )
    @ConfigurationProperties("proxy.socks")
    @Bean
    public SocksProxyBean socksProxyBean() {
        return new SocksProxyBean();
    }

//    @ConditionalOnProperty(
//            prefix = "proxy.system",
//            name = "useSystemProxies"
//    )
//    @ConfigurationProperties("proxy.system")
//    @Bean
//    public SystemProxiesBean systemProxiesBean() {
//        return new SystemProxiesBean();
//    }

    @Bean
    public NetworkProxyBean networkProxyBean() {
        return new NetworkProxyBean();
    }

//    @Autowired(required = false)
//    public NetworkProxyBean networkProxyBean;




}

package com.aio.portable.swiss.suite.net.tcp.proxy;

import com.aio.portable.swiss.suite.net.tcp.TcpSugar;
import com.aio.portable.swiss.suite.net.tcp.proxy.classic.HttpProxyBean;
import com.aio.portable.swiss.suite.net.tcp.proxy.classic.HttpsProxyBean;
import com.aio.portable.swiss.suite.net.tcp.proxy.classic.SocksProxyBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.MessageFormat;

public class NetworkProxyBean implements InitializingBean {
    private final static Log log = LogFactory.getLog(NetworkProxyBean.class);

    @Autowired(required = false)
    HttpProxyBean httpProxyBean;

    @Autowired(required = false)
    HttpsProxyBean httpsProxyBean;

    @Autowired(required = false)
    SocksProxyBean socksProxyBean;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (httpProxyBean != null || httpsProxyBean != null || socksProxyBean != null) {
            if (httpProxyBean != null && httpProxyBean.getAutomatically()) {
                httpProxyBean.on();
            }

            if (httpsProxyBean != null && httpsProxyBean.getAutomatically()) {
                httpsProxyBean.on();
            }

            if (socksProxyBean != null && socksProxyBean.getAutomatically()) {
                socksProxyBean.on();
            }
        }
    }

    public final static boolean test(String host, Integer port) {
        boolean telnet = TcpSugar.telnet(host, port, 5000);
        if (telnet)
            log.info(MessageFormat.format("Test connection: {0}:{1} - Connection succeeded.", host, String.valueOf(port)));
        else
            log.warn(MessageFormat.format("Test connection: {0}:{1} - Connection failed.", host, String.valueOf(port)));
        return telnet;
    }

//    public final static String info(ProxyBean proxyObject) {
//        return MessageFormat.format("set proxy: host-{0} port-{1}", proxyObject.getHost(), String.valueOf(proxyObject.getPort()));
//    }
}

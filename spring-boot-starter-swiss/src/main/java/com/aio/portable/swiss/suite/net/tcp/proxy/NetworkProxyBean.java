package com.aio.portable.swiss.suite.net.tcp.proxy;

import com.aio.portable.swiss.suite.log.solution.local.LocalLog;
import com.aio.portable.swiss.suite.net.tcp.TcpSugar;
import com.aio.portable.swiss.suite.net.tcp.proxy.classic.HttpProxyBean;
import com.aio.portable.swiss.suite.net.tcp.proxy.classic.HttpsProxyBean;
import com.aio.portable.swiss.suite.net.tcp.proxy.classic.SocksProxyBean;
import com.aio.portable.swiss.suite.net.tcp.proxy.classic.SystemProxiesBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.Proxy;
import java.net.ProxySelector;
import java.net.URI;
import java.text.MessageFormat;
import java.util.List;

public class NetworkProxyBean implements InitializingBean {
//    private static final Log log = LogFactory.getLog(NetworkProxyBean.class);
    private static final LocalLog log = LocalLog.getLog(NetworkProxyBean.class);

    @Autowired(required = false)
    HttpProxyBean httpProxyBean;

    @Autowired(required = false)
    HttpsProxyBean httpsProxyBean;

    @Autowired(required = false)
    SocksProxyBean socksProxyBean;

    @Autowired(required = false)
    SystemProxiesBean systemProxiesBean;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (httpProxyBean != null || httpsProxyBean != null || socksProxyBean != null || systemProxiesBean != null) {
            if (httpProxyBean != null && httpProxyBean.getAutomatically()) {
                httpProxyBean.on();
            }

            if (httpsProxyBean != null && httpsProxyBean.getAutomatically()) {
                httpsProxyBean.on();
            }

            if (socksProxyBean != null && socksProxyBean.getAutomatically()) {
                socksProxyBean.on();
            }

            if (systemProxiesBean != null && systemProxiesBean.getAutomatically()) {
                {
                    System.setProperty("java.net.useSystemProxies", "true");
                    List<Proxy> proxyList = null;
                    proxyList = ProxySelector.getDefault().select(new URI("http://10.124.165.203:8060/Overview"));
                    for (int j = 0; j < proxyList.size(); j++) {
                        Proxy proxy = proxyList.get(j);
                        if (proxy.type() != Proxy.Type.DIRECT) {
                            System.out.println(proxy);
                        }
                    }
                }
                systemProxiesBean.on();
                {
                    System.setProperty("java.net.useSystemProxies", "true");
                    List<Proxy> proxyList = null;
                    proxyList = ProxySelector.getDefault().select(new URI("http://10.124.165.203:8060/Overview"));
                    for (int j = 0; j < proxyList.size(); j++) {
                        Proxy proxy = proxyList.get(j);
                        if (proxy.type() != Proxy.Type.DIRECT) {
                            System.out.println(proxy);
                        }
                    }
                }
            }
        }
    }

    public static final boolean test(String host, int port) {
        return test(host, port, 5000);
    }

    public static final boolean test(String host, int port, int timeout) {
        boolean telnet = TcpSugar.telnet(host, port, timeout);
        if (telnet)
            log.info(MessageFormat.format("Test connection: {0}:{1} - Connection succeeded.", host, String.valueOf(port)));
        else
            log.warn(MessageFormat.format("Test connection: {0}:{1} - Connection failed.", host, String.valueOf(port)));
        return telnet;
    }

//    public static final String info(ProxyBean proxyObject) {
//        return MessageFormat.format("set proxy: host-{0} port-{1}", proxyObject.getHost(), String.valueOf(proxyObject.getPort()));
//    }
}

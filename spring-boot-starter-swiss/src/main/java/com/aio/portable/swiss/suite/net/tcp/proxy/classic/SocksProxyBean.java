package com.aio.portable.swiss.suite.net.tcp.proxy.classic;

import com.aio.portable.swiss.suite.net.tcp.proxy.NetworkProxyBean;
import com.aio.portable.swiss.suite.net.tcp.proxy.NetworkProxySugar;
import com.aio.portable.swiss.suite.net.tcp.proxy.ProxyBean;

import java.util.List;

public class SocksProxyBean implements ProxyBean {
    private String host;

    private Integer port;

    private List<String> nonProxyHosts;

    private boolean automatically = true;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public List<String> getNonProxyHosts() {
        return nonProxyHosts;
    }

    public void setNonProxyHosts(List<String> nonProxyHosts) {
        this.nonProxyHosts = nonProxyHosts;
    }

    public boolean getAutomatically() {
        return automatically;
    }

    public void setAutomatically(boolean automatically) {
        this.automatically = automatically;
    }

    public void on() {
        if (NetworkProxyBean.test(host, port)) {
            if (this.host != null)
                NetworkProxySugar.Socks.setProxyHost(this.host);
            if (this.port != null)
                NetworkProxySugar.Socks.setProxyPort(this.port);
            if (this.nonProxyHosts != null)
                NetworkProxySugar.Socks.setNonProxyHosts(this.nonProxyHosts);
        }
    }

    public void force() {
        if (this.host != null)
            NetworkProxySugar.Socks.setProxyHost(this.host);
        if (this.port != null)
            NetworkProxySugar.Socks.setProxyPort(this.port);
        if (this.nonProxyHosts != null)
            NetworkProxySugar.Socks.setNonProxyHosts(this.nonProxyHosts);
    }

    public void off() {
        NetworkProxySugar.Socks.clear();
    }
}

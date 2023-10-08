package com.aio.portable.swiss.suite.net.tcp.proxy.classic;

import com.aio.portable.swiss.suite.net.tcp.proxy.NetworkProxyBean;
import com.aio.portable.swiss.suite.net.tcp.proxy.NetworkProxySugar;
import com.aio.portable.swiss.suite.net.tcp.proxy.ProxyBean;

import java.util.List;

public class SocksProxyBean implements ProxyBean {
    private String host;

    // 1080
    private Integer port;

    private String nonProxyHosts;

    private String userName;

    private String password;

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

    public String getNonProxyHosts() {
        return nonProxyHosts;
    }

    public void setNonProxyHosts(String nonProxyHosts) {
        this.nonProxyHosts = nonProxyHosts;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getAutomatically() {
        return automatically;
    }

    public void setAutomatically(boolean automatically) {
        this.automatically = automatically;
    }

    public boolean on() {
        return this.on(false);
    }

    public boolean on(boolean force) {
        if (!force && !NetworkProxyBean.test(host, port))
            return false;
        if (this.host != null)
            NetworkProxySugar.Socks.setProxyHost(this.host);
        if (this.port != null)
            NetworkProxySugar.Socks.setProxyPort(this.port);
        if (this.nonProxyHosts != null)
            NetworkProxySugar.Socks.setNonProxyHosts(this.nonProxyHosts);
        if (this.userName != null)
            NetworkProxySugar.Socks.setProxyHost(this.userName);
        if (this.password != null)
            NetworkProxySugar.Socks.setProxyHost(this.password);
        return true;
    }

    public void off() {
        NetworkProxySugar.Socks.unset();
    }
}

package com.aio.portable.swiss.suite.net.tcp.proxy.classic;

import com.aio.portable.swiss.suite.net.tcp.proxy.NetworkProxyBean;
import com.aio.portable.swiss.suite.net.tcp.proxy.NetworkProxySugar;
import com.aio.portable.swiss.suite.net.tcp.proxy.ProxyBean;

import java.util.List;

public class HttpProxyBean implements ProxyBean {
    private String host;

    // 80
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
            NetworkProxySugar.Http.setProxyHost(this.host);
        if (this.port != null)
            NetworkProxySugar.Http.setProxyPort(this.port);
        if (this.nonProxyHosts != null)
            NetworkProxySugar.Http.setNonProxyHosts(this.nonProxyHosts);
        if (this.userName != null)
            NetworkProxySugar.Http.setProxyHost(this.userName);
        if (this.password != null)
            NetworkProxySugar.Http.setProxyHost(this.password);
        return true;
    }

    public void off() {
        NetworkProxySugar.Http.unset();
    }
}

package com.aio.portable.swiss.suite.net.tcp.proxy.classic;

import com.aio.portable.swiss.suite.net.tcp.proxy.NetworkProxyBean;
import com.aio.portable.swiss.suite.net.tcp.proxy.NetworkProxySugar;
import com.aio.portable.swiss.suite.net.tcp.proxy.ProxyBean;

import java.util.List;

public class FtpProxyBean implements ProxyBean {
    private String host;

    // 80
    private Integer port;

    private String nonProxyHosts;

    private String userName;

    private String password;

    private boolean automatic = true;

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
        return automatic;
    }

    public void setAutomatically(boolean automatically) {
        this.automatic = automatically;
    }

    public boolean on() {
        return this.on(false);
    }

    public boolean on(boolean force) {
        if (!force && !NetworkProxyBean.test(host, port))
            return false;
        if (this.host != null)
            NetworkProxySugar.Ftp.setProxyHost(this.host);
        if (this.port != null)
            NetworkProxySugar.Ftp.setProxyPort(this.port);
        if (this.nonProxyHosts != null)
            NetworkProxySugar.Ftp.setNonProxyHosts(this.nonProxyHosts);
        if (this.userName != null)
            NetworkProxySugar.Ftp.setProxyHost(this.userName);
        if (this.password != null)
            NetworkProxySugar.Ftp.setProxyHost(this.password);
        return true;
    }

    public void off() {
        NetworkProxySugar.Ftp.unset();
    }
}

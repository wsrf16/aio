package com.aio.portable.swiss.suite.net.tcp.proxy.classic;

import com.aio.portable.swiss.suite.net.tcp.proxy.NetworkProxyBean;
import com.aio.portable.swiss.suite.net.tcp.proxy.NetworkProxySugar;
import com.aio.portable.swiss.suite.net.tcp.proxy.ProxyBean;

import java.util.List;

public class FtpProxyBean implements ProxyBean {
    private String host;

    // 80
    private Integer port;

    private List<String> nonProxyHosts;

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

    public List<String> getNonProxyHosts() {
        return nonProxyHosts;
    }

    public void setNonProxyHosts(List<String> nonProxyHosts) {
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

    public void on() {
        if (NetworkProxyBean.test(host, port)) {
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
        }
    }

    public void force() {
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
    }

    public void off() {
        NetworkProxySugar.Ftp.unset();
    }
}

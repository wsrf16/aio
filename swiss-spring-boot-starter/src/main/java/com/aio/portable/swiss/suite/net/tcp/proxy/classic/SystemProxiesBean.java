package com.aio.portable.swiss.suite.net.tcp.proxy.classic;

import com.aio.portable.swiss.suite.net.tcp.proxy.NetworkProxyBean;
import com.aio.portable.swiss.suite.net.tcp.proxy.NetworkProxySugar;

public class SystemProxiesBean {
    private Boolean useSystemProxies;

    // 1080
//    private Integer port;
//
//    private String nonProxyHosts;
//
//    private String userName;
//
//    private String password;

    private boolean automatically = true;

    public Boolean getUseSystemProxies() {
        return useSystemProxies;
    }

    public void setUseSystemProxies(Boolean useSystemProxies) {
        this.useSystemProxies = useSystemProxies;
    }

//    public Integer getPort() {
//        return port;
//    }
//
//    public void setPort(Integer port) {
//        this.port = port;
//    }
//
//    public String getNonProxyHosts() {
//        return nonProxyHosts;
//    }
//
//    public void setNonProxyHosts(String nonProxyHosts) {
//        this.nonProxyHosts = nonProxyHosts;
//    }
//
//    public String getUserName() {
//        return userName;
//    }
//
//    public void setUserName(String userName) {
//        this.userName = userName;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }

    public boolean getAutomatically() {
        return automatically;
    }

    public void setAutomatically(boolean automatically) {
        this.automatically = automatically;
    }

    public boolean on() {
        if (this.useSystemProxies != null)
            NetworkProxySugar.SystemProxies.setUseSystemProxies(this.useSystemProxies);
        return true;
    }

    public void off() {
        NetworkProxySugar.SystemProxies.unset();
    }
}

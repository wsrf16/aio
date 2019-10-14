package com.aio.portable.swiss.autoconfigure.properties.resttemplate;

public class Agent {
    private boolean enable = false;
    private String host = "127.0.0.1";
    private int port = 8888;

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}

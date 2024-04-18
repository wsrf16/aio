package com.aio.portable.swiss.suite.system;

import com.aio.portable.swiss.spring.SpringContextHolder;
import com.aio.portable.swiss.sugar.type.StringSugar;
import com.aio.portable.swiss.sugar.location.UrlSugar;

public abstract class SpringInfo {
    public static final String localhost() {
        return HostInfo.getHostName();
    }

    public static final int port() {
        int port = SpringContextHolder.getStandardServletEnvironment().getProperty("server.port", Integer.class, 8080);
        return port;
    }

    public static final String contextPath() {
        String contextPath = SpringContextHolder.getStandardServletEnvironment().getProperty("server.servlet.contextPath", StringSugar.EMPTY);
        return contextPath;
    }

    public static final String url() {
        String url = UrlSugar.concat(localhost() + ":" + port(), contextPath());
        return url;
    }


}


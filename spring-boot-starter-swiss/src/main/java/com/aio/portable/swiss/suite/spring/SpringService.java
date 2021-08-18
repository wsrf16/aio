package com.aio.portable.swiss.suite.spring;

import com.aio.portable.swiss.sugar.PathSugar;
import com.aio.portable.swiss.sugar.StringSugar;
import com.aio.portable.swiss.sugar.UrlSugar;
import com.aio.portable.swiss.suite.systeminfo.HostInfo;

public abstract class SpringService {
    public final static String localhost() {
        return HostInfo.getHostName();
    }

    public final static int port() {
        int port = SpringContextHolder.getEnvironment().getProperty("server.port", Integer.class, 8080);
        return port;
    }

    public final static String contextPath() {
        String contextPath = SpringContextHolder.getEnvironment().getProperty("server.servlet.contextPath", StringSugar.EMPTY);
        return contextPath;
    }

    public final static String url() {
        String url = UrlSugar.concat(localhost() + ":" + port(), contextPath());
        return url;
    }


}


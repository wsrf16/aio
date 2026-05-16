package com.aio.portable.swiss.suite.system;

import com.aio.portable.swiss.global.Constant;
import com.aio.portable.swiss.spring.SpringContextHolder;
import com.aio.portable.swiss.sugar.location.URLSugar;

public abstract class SpringInfo {
    public static String localhost() {
        return HostInfo.getHostName();
    }

    public static int port() {
        int port = SpringContextHolder.getStandardServletEnvironment().getProperty("server.port", Integer.class, 8080);
        return port;
    }

    public static String contextPath() {
        String contextPath = SpringContextHolder.getStandardServletEnvironment().getProperty("server.servlet.contextPath", Constant.EMPTY);
        return contextPath;
    }

    public static String url() {
        String url = URLSugar.concat(localhost() + ":" + port(), contextPath());
        return url;
    }


}


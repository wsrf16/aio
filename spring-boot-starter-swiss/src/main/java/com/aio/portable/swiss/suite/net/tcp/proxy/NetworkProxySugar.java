package com.aio.portable.swiss.suite.net.tcp.proxy;

import com.aio.portable.swiss.sugar.CollectionSugar;

import java.util.List;

public abstract class NetworkProxySugar {
    public static class Http {
        public static void setProxyHost(String host) {
            System.setProperty("http.proxyHost", host);
        }

        public static void setProxyPort(int port) {
            System.setProperty("http.proxyPort", String.valueOf(port));
        }

        public static void setNonProxyHosts(List<String> nonProxyHosts) {
            System.setProperty("http.nonProxyHosts", CollectionSugar.join(nonProxyHosts, "|"));
        }

        public static void clear() {
            System.clearProperty("http.proxyHost");
            System.clearProperty("http.proxyPort");
            System.clearProperty("http.nonProxyHosts");
        }
    }

    public static class Https {
        public static void setProxyHost(String host) {
            System.setProperty("https.proxyHost", host);
        }

        public static void setProxyPort(int port) {
            System.setProperty("https.proxyPort", String.valueOf(port));
        }

        public static void setNonProxyHosts(List<String> nonProxyHosts) {
            System.setProperty("https.nonProxyHosts", CollectionSugar.join(nonProxyHosts, "|"));
        }

        public static void clear() {
            System.clearProperty("https.proxyHost");
            System.clearProperty("https.proxyPort");
            System.clearProperty("https.nonProxyHosts");
        }
    }

    public static class Ftp {
        public static void setProxyHost(String host) {
            System.setProperty("ftp.proxyHost", host);
        }

        public static void setProxyPort(int port) {
            System.setProperty("ftp.proxyPort", String.valueOf(port));
        }

        public static void setNonProxyHosts(List<String> nonProxyHosts) {
            System.setProperty("ftp.nonProxyHosts", CollectionSugar.join(nonProxyHosts, "|"));
        }

        public static void clear() {
            System.clearProperty("ftp.proxyHost");
            System.clearProperty("ftp.proxyPort");
            System.clearProperty("ftp.nonProxyHosts");
        }
    }

    public static class Socks {
        public static void setProxyHost(String host) {
            System.setProperty("socks.proxyHost", host);
        }

        public static void setProxyPort(int port) {
            System.setProperty("socks.proxyPort", String.valueOf(port));
        }

        public static void setNonProxyHosts(List<String> nonProxyHosts) {
            System.setProperty("socks.nonProxyHosts", CollectionSugar.join(nonProxyHosts, "|"));
        }

        public static void clear() {
            System.clearProperty("socks.proxyHost");
            System.clearProperty("socks.proxyPort");
            System.clearProperty("socks.nonProxyHosts");
        }
    }
}

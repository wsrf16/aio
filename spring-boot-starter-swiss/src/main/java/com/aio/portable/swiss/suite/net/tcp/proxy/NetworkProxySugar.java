package com.aio.portable.swiss.suite.net.tcp.proxy;

import com.aio.portable.swiss.sugar.type.CollectionSugar;

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

        public static void setProxyUserName(String userName) {
            System.setProperty("http.proxyUserName", userName);
        }

        public static void setProxyPassword(String password) {
            System.setProperty("http.proxyPortPassword", password);
        }

        public static void unset() {
            System.clearProperty("http.proxyHost");
            System.clearProperty("http.proxyPort");
            System.clearProperty("http.nonProxyHosts");
            System.clearProperty("http.proxyUserName");
            System.clearProperty("http.proxyPortPassword");
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

        public static void setProxyUserName(String userName) {
            System.setProperty("https.proxyUserName", userName);
        }

        public static void setProxyPassword(String password) {
            System.setProperty("https.proxyPortPassword", password);
        }

        public static void unset() {
            System.clearProperty("https.proxyHost");
            System.clearProperty("https.proxyPort");
            System.clearProperty("https.nonProxyHosts");
            System.clearProperty("https.proxyUserName");
            System.clearProperty("https.proxyPortPassword");
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

        public static void setProxyUserName(String userName) {
            System.setProperty("ftp.proxyUserName", userName);
        }

        public static void setProxyPassword(String password) {
            System.setProperty("ftp.proxyPortPassword", password);
        }

        public static void unset() {
            System.clearProperty("ftp.proxyHost");
            System.clearProperty("ftp.proxyPort");
            System.clearProperty("ftp.nonProxyHosts");
            System.clearProperty("ftp.proxyUserName");
            System.clearProperty("ftp.proxyPortPassword");
        }
    }

    public static class Socks {
        public static void setProxyHost(String host) {
            System.setProperty("socksProxyHost", host);
        }

        public static void setProxyPort(int port) {
            System.setProperty("socksProxyPort", String.valueOf(port));
        }

        public static void setNonProxyHosts(List<String> nonProxyHosts) {
            System.setProperty("socksNonProxyHosts", CollectionSugar.join(nonProxyHosts, "|"));
        }

        public static void setProxyUserName(String userName) {
            System.setProperty("java.net.socks.username", userName);
        }

        public static void setProxyPassword(String password) {
            System.setProperty("java.net.socks.password", password);
        }

        public static void unset() {
            System.clearProperty("socksProxyHost");
            System.clearProperty("socksProxyPort");
            System.clearProperty("socksNonProxyHosts");
            System.clearProperty("java.net.socks.username");
            System.clearProperty("java.net.socks.password");
        }
    }
}

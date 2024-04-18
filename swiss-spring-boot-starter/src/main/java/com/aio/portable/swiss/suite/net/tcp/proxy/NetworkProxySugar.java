package com.aio.portable.swiss.suite.net.tcp.proxy;

import com.aio.portable.swiss.sugar.type.CollectionSugar;

import java.util.List;

public abstract class NetworkProxySugar {
    public static class Http {
        public static void setProxyHost(String host) {
            System.setProperty("http.proxyHost", host);
        }

        public static String getProxyHost() {
            return System.getProperty("http.proxyHost");
        }

        public static void setProxyPort(int port) {
            System.setProperty("http.proxyPort", String.valueOf(port));
        }

        public static String getProxyPort() {
            return System.getProperty("http.proxyPort");
        }

        // "localhost|127.0.0.1"
        public static void setNonProxyHosts(String nonProxyHosts) {
            System.setProperty("http.nonProxyHosts", nonProxyHosts);
        }

        public static String getNonProxyHosts() {
            return System.getProperty("http.nonProxyHosts");
        }

        public static void setProxyUserName(String userName) {
            System.setProperty("http.proxyUserName", userName);
        }

        public static String getProxyUserName() {
            return System.getProperty("http.proxyUserName");
        }

        public static void setProxyPassword(String password) {
            System.setProperty("http.proxyPortPassword", password);
        }

        public static String getProxyPassword() {
            return System.getProperty("http.proxyPortPassword");
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

        public static String getProxyHost() {
            return System.getProperty("https.proxyHost");
        }

        public static void setProxyPort(int port) {
            System.setProperty("https.proxyPort", String.valueOf(port));
        }

        public static String getProxyPort() {
            return System.getProperty("https.proxyPort");
        }

        public static void setNonProxyHosts(List<String> nonProxyHosts) {
            System.setProperty("https.nonProxyHosts", CollectionSugar.join(nonProxyHosts, "|"));
        }

        public static String getNonProxyHosts() {
            return System.getProperty("http.nonProxyHosts");
        }

        public static void setProxyUserName(String userName) {
            System.setProperty("https.proxyUserName", userName);
        }

        public static String getProxyUserName() {
            return System.getProperty("https.proxyUserName");
        }

        public static void setProxyPassword(String password) {
            System.setProperty("https.proxyPortPassword", password);
        }

        public static String getProxyPassword() {
            return System.getProperty("https.proxyPortPassword");
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

        public static String getProxyHost() {
            return System.getProperty("ftp.proxyHost");
        }

        public static void setProxyPort(int port) {
            System.setProperty("ftp.proxyPort", String.valueOf(port));
        }

        public static String getProxyPort() {
            return System.getProperty("ftp.proxyPort");
        }

        public static void setNonProxyHosts(String nonProxyHosts) {
            System.setProperty("ftp.nonProxyHosts", nonProxyHosts);
        }

        public static String getNonProxyHosts() {
            return System.getProperty("ftp.nonProxyHosts");
        }

        public static void setProxyUserName(String userName) {
            System.setProperty("ftp.proxyUserName", userName);
        }

        public static String getProxyUserName() {
            return System.getProperty("ftp.proxyUserName");
        }

        public static void setProxyPassword(String password) {
            System.setProperty("ftp.proxyPortPassword", password);
        }

        public static String getProxyPassword() {
            return System.getProperty("ftp.proxyPortPassword");
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

        public static String getProxyHost() {
            return System.getProperty("socksProxyHost");
        }

        public static void setProxyPort(int port) {
            System.setProperty("socksProxyPort", String.valueOf(port));
        }

        public static String getProxyPort() {
            return System.getProperty("socksProxyPort");
        }

        public static void setNonProxyHosts(String nonProxyHosts) {
            System.setProperty("socksNonProxyHosts", nonProxyHosts);
        }

        public static String getNonProxyHosts() {
            return System.getProperty("socksNonProxyHosts");
        }

        public static void setProxyUserName(String userName) {
            System.setProperty("java.net.socks.username", userName);
        }

        public static String getProxyUserName() {
            return System.getProperty("java.net.socks.username");
        }

        public static void setProxyPassword(String password) {
            System.setProperty("java.net.socks.password", password);
        }

        public static String getProxyPassword() {
            return System.getProperty("java.net.socks.password");
        }

        public static void unset() {
            System.clearProperty("socksProxyHost");
            System.clearProperty("socksProxyPort");
            System.clearProperty("socksNonProxyHosts");
            System.clearProperty("java.net.socks.username");
            System.clearProperty("java.net.socks.password");
        }
    }

    public static class SystemProxies {
        public static void setUseSystemProxies(boolean useSystemProxies) {
            System.setProperty("java.net.useSystemProxies", Boolean.toString(useSystemProxies));
        }

        public static boolean getSystemProxies() {
            return Boolean.getBoolean("java.net.useSystemProxies");
        }

        public static void unset() {
            System.clearProperty("java.net.useSystemProxies");
        }
    }
}

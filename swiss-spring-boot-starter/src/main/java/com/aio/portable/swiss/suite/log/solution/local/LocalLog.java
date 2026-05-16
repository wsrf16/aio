package com.aio.portable.swiss.suite.log.solution.local;

import com.aio.portable.swiss.suite.log.solution.console.ConsoleLog;
import com.aio.portable.swiss.suite.log.solution.console.ConsoleLogProperties;

public class LocalLog extends ConsoleLog {
//    private static ConsoleLogProperties properties;
//
//    public synchronized static ConsoleLogProperties properties() {
//        if (properties == null) {
//            properties = new ConsoleLogProperties();
//            properties.setEnabled(true);
//        }
//        return properties;
//    }

//    private static final ConsoleLogProperties properties = new ConsoleLogProperties();
//
//    static {
////        properties = new ConsoleLogProperties();
//        properties.setEnabled(false);
//    }
//
//    public static ConsoleLogProperties getSystemProperties() {
//        return properties;
//    }

//    public static void setSystemProperties(ConsoleLogProperties properties) {
//        SystemLog.properties = properties;
//    }

    public static boolean DEFAULT_ENABLED = false;

    private static ConsoleLogProperties getDisabledConsoleLogProperties() {
        ConsoleLogProperties props = new ConsoleLogProperties();
        props.setEnabled(DEFAULT_ENABLED);
        return props;
    }


//    public SystemLog(String name) {
//        super(name);
//    }

    public LocalLog(String name) {
        super(name, getDisabledConsoleLogProperties());
    }

    public LocalLog(Class<?> clazz) {
        super(clazz, getDisabledConsoleLogProperties());
    }

    public LocalLog() {
        super(getDisabledConsoleLogProperties());
    }

    public static LocalLog getLog(String name) {
        return new LocalLog(name);
    }

    public static LocalLog getLog(Class<?> clazz) {
        return new LocalLog(clazz);
    }

    public static LocalLog getLog() {
        return new LocalLog();
    }
}

package com.aio.portable.swiss.suite.system;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

public abstract class OSInfo {
    private static String OS = System.getProperty("os.name").toLowerCase();

    public final static boolean isLinux() {
        return OS.indexOf("linux") >= 0;
    }

    public final static boolean isMacOS() {
        return OS.indexOf("mac") >= 0 && OS.indexOf("os") > 0 && OS.indexOf("x") < 0;
    }

    public final static boolean isMacOSX() {
        return OS.indexOf("mac") >= 0 && OS.indexOf("os") > 0 && OS.indexOf("x") > 0;
    }

    public final static boolean isWindows() {
        return OS.indexOf("windows") >= 0;
    }

    public final static boolean isOS2() {
        return OS.indexOf("os/2") >= 0;
    }

    public final static boolean isSolaris() {
        return OS.indexOf("solaris") >= 0;
    }

    public final static boolean isSunOS() {
        return OS.indexOf("sunos") >= 0;
    }

    public final static boolean isMPEiX() {
        return OS.indexOf("mpe/ix") >= 0;
    }

    public final static boolean isHPUX() {
        return OS.indexOf("hp-ux") >= 0;
    }

    public final static boolean isAix() {
        return OS.indexOf("aix") >= 0;
    }

    public final static boolean isOS390() {
        return OS.indexOf("os/390") >= 0;
    }

    public final static boolean isFreeBSD() {
        return OS.indexOf("freebsd") >= 0;
    }

    public final static boolean isIrix() {
        return OS.indexOf("irix") >= 0;
    }

    public final static boolean isDigitalUnix() {
        return OS.indexOf("digital") >= 0 && OS.indexOf("unix") > 0;
    }

    public final static boolean isNetWare() {
        return OS.indexOf("netware") >= 0;
    }

    public final static boolean isOSF1() {
        return OS.indexOf("osf1") >= 0;
    }

    public final static boolean isOpenVMS() {
        return OS.indexOf("openvms") >= 0;
    }



}
package com.aio.portable.swiss.suite.system;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

public abstract class OSInfo {
    private static String OS = System.getProperty("os.name").toLowerCase();

    public static final boolean isLinux() {
        return OS.indexOf("linux") >= 0;
    }

    public static final boolean isMacOS() {
        return OS.indexOf("mac") >= 0 && OS.indexOf("os") > 0 && OS.indexOf("x") < 0;
    }

    public static final boolean isMacOSX() {
        return OS.indexOf("mac") >= 0 && OS.indexOf("os") > 0 && OS.indexOf("x") > 0;
    }

    public static final boolean isWindows() {
        return OS.indexOf("windows") >= 0;
    }

    public static final boolean isOS2() {
        return OS.indexOf("os/2") >= 0;
    }

    public static final boolean isSolaris() {
        return OS.indexOf("solaris") >= 0;
    }

    public static final boolean isSunOS() {
        return OS.indexOf("sunos") >= 0;
    }

    public static final boolean isMPEiX() {
        return OS.indexOf("mpe/ix") >= 0;
    }

    public static final boolean isHPUX() {
        return OS.indexOf("hp-ux") >= 0;
    }

    public static final boolean isAix() {
        return OS.indexOf("aix") >= 0;
    }

    public static final boolean isOS390() {
        return OS.indexOf("os/390") >= 0;
    }

    public static final boolean isFreeBSD() {
        return OS.indexOf("freebsd") >= 0;
    }

    public static final boolean isIrix() {
        return OS.indexOf("irix") >= 0;
    }

    public static final boolean isDigitalUnix() {
        return OS.indexOf("digital") >= 0 && OS.indexOf("unix") > 0;
    }

    public static final boolean isNetWare() {
        return OS.indexOf("netware") >= 0;
    }

    public static final boolean isOSF1() {
        return OS.indexOf("osf1") >= 0;
    }

    public static final boolean isOpenVMS() {
        return OS.indexOf("openvms") >= 0;
    }




    public static final int getAvailableProcessors() {
        return Runtime.getRuntime().availableProcessors();
    }

    public static final int getProcessID() {
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        return Integer.valueOf(runtimeMXBean.getName().split("@")[0])
                .intValue();
    }

}
package com.aio.portable.swiss.suite.system;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

public class RuntimeInfo {

    public final static int getAvailableProcessors() {
        return Runtime.getRuntime().availableProcessors();
    }

    public final static int getProcessID() {
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        return Integer.valueOf(runtimeMXBean.getName().split("@")[0])
                .intValue();
    }


}

package com.aio.portable.swiss.structure.log.base.factory.classic;

import com.aio.portable.swiss.structure.log.base.classic.impl.file.FileLog;
import com.aio.portable.swiss.structure.log.base.LogHub;
import com.aio.portable.swiss.structure.log.base.factory.LogHubFactory;

public class FileHubFactory implements LogHubFactory {
    protected static FileHubFactory instance = new FileHubFactory();

    public synchronized static FileHubFactory newInstance() {
        return instance == null ? new FileHubFactory() : instance;
    }

    protected FileHubFactory() {
    }

    public LogHub build(String className) {
        LogHub logger = LogHub.build(FileLog.build(className));
        return logger;
    }
}
package com.aio.portable.swiss.structure.log.hub.factory.classic;

import com.aio.portable.swiss.structure.log.classic.impl.file.FileLogger;
import com.aio.portable.swiss.structure.log.hub.LogHub;
import com.aio.portable.swiss.structure.log.hub.factory.LogHubFactory;

public class FileHubFactory implements LogHubFactory {
    protected static FileHubFactory instance = new FileHubFactory();

    public synchronized static FileHubFactory newInstance() {
        return instance == null ? new FileHubFactory() : instance;
    }

    protected FileHubFactory() {
    }

    public LogHub build(String className) {
        LogHub logger = LogHub.build(FileLogger.build(className));
        return logger;
    }
}
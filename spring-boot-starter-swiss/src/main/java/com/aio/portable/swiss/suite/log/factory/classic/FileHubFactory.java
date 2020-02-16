package com.aio.portable.swiss.suite.log.factory.classic;

import com.aio.portable.swiss.suite.log.classic.impl.file.FileLog;
import com.aio.portable.swiss.suite.log.LogHub;
import com.aio.portable.swiss.suite.log.factory.LogHubFactory;

public class FileHubFactory implements LogHubFactory {
    protected static FileHubFactory instance = new FileHubFactory();

    public synchronized static FileHubFactory singletonInstance() {
        return instance == null ? new FileHubFactory() : instance;
    }

    protected FileHubFactory() {
    }

    public LogHub build(String className) {
        LogHub logger = LogHub.build(FileLog.build(className));
        return logger;
    }
}
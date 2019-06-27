package com.york.portable.swiss.assist.log.hub.factory.classic;

import com.york.portable.swiss.assist.log.classic.impl.file.FileLogger;
import com.york.portable.swiss.assist.log.hub.LoggerHubImp;
import com.york.portable.swiss.assist.log.hub.factory.LoggerHubFactory;

public class FileHubFactory implements LoggerHubFactory {
    protected static FileHubFactory instance = new FileHubFactory();

    public synchronized static FileHubFactory newInstance() {
        return instance == null ? new FileHubFactory() : instance;
    }

    protected FileHubFactory() {
    }

    public LoggerHubImp build(String className) {
        LoggerHubImp logger = LoggerHubImp.build(FileLogger.build(className));
        return logger;
    }
}
package com.aio.portable.swiss.suite.log.factory.classic;

import com.aio.portable.swiss.suite.log.impl.file.FileLog;
import com.aio.portable.swiss.suite.log.LogHub;
import com.aio.portable.swiss.suite.log.factory.LogHubFactory;

public class FileHubFactory extends LogHubFactory {
    protected static FileHubFactory instance = new FileHubFactory();

    public synchronized static FileHubFactory singletonInstance() {
        return instance;
    }

    protected FileHubFactory() {
    }

    @Override
    public LogHub build(String className) {
        LogHub logger = LogHub.build(FileLog.build(className));
        logger.setEnable(this.isEnable());
        logger.setBaseLevel(this.getLevel());
        return logger;
    }
}
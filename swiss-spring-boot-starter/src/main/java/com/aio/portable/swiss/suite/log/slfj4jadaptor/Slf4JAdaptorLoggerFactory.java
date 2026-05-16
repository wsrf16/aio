package com.aio.portable.swiss.suite.log.slfj4jadaptor;

import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;

import java.util.concurrent.ConcurrentHashMap;

public class Slf4JAdaptorLoggerFactory implements ILoggerFactory {
    private final ConcurrentHashMap<String, Logger> loggerMap = new ConcurrentHashMap<>();

    public Logger getLogger(String name) {
        return loggerMap.computeIfAbsent(name, Slf4JAdaptorLogger::new);
    }
}

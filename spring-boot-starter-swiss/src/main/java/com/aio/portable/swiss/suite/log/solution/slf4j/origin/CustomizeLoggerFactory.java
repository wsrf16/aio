package com.aio.portable.swiss.suite.log.solution.slf4j.origin;

import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;

public class CustomizeLoggerFactory implements ILoggerFactory {
    public Logger getLogger(String name) {
        return new CustomizeLogHub(name);
    }
}

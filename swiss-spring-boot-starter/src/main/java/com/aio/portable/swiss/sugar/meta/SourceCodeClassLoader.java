package com.aio.portable.swiss.sugar.meta;

import com.aio.portable.swiss.suite.algorithm.encode.JDKBase64Convert;
import groovy.lang.GroovyClassLoader;

import java.util.HashMap;
import java.util.Map;

public class SourceCodeClassLoader {
    private GroovyClassLoader classLoader = new GroovyClassLoader();

    private Map<String, Class<?>> hasLoadedClassCache = new HashMap<>();

    public Class parseClass(String text) {
        String id = JDKBase64Convert.encodeToString(text);
        Class clazz;
        synchronized (this) {
            if (hasLoadedClassCache.containsKey(id)) {
                clazz = hasLoadedClassCache.get(id);
            } else {
                clazz = classLoader.parseClass(text);
                hasLoadedClassCache.putIfAbsent(id, clazz);
            }
        }
        return clazz;
    }



    public GroovyClassLoader getRootClassLoader() {
        return classLoader;
    }

    public Class[] getLoadedClasses() {
        return classLoader.getLoadedClasses();
    }

    public void clearCache() {
        synchronized (this) {
            classLoader.clearCache();
            hasLoadedClassCache.clear();
        }
    }
}

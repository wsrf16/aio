package com.aio.portable.swiss.sugar.resource.classloader;

import com.aio.portable.swiss.suite.bean.BeanSugar;
import com.aio.portable.swiss.suite.io.listen.WatchServiceThread;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class WatchClassLoader extends WatchServiceThread {
    private static final Log log = LogFactory.getLog(WatchClassLoader.class);

    private ByteCodeClassLoader folderClassLoader;

    private Map<String, Class<?>> loadedClass = new HashMap<>();

    public WatchClassLoader(String directory) {
        super(directory);

        folderClassLoader = new ByteCodeClassLoader(directory);
        WatchServiceThread watchServiceThread = new WatchServiceThread(directory);
        watchServiceThread.setModifyHandler(f -> {
            folderClassLoader = new ByteCodeClassLoader(directory);

            loadedClass.keySet().forEach(name -> {
                Class<?> clazz = loadClass(name);
                log.debug(f.getPath() + " reload：" + Arrays.asList(BeanSugar.Fields.getDeclaredFieldIncludeParents(clazz)));
            });
        });
        watchServiceThread.listen();
    }

    public final Class<?> loadClass(String name) {
        Class<?> clazz = folderClassLoader.loadClass(name);
        loadedClass.put(name, clazz);
        return clazz;
    }
}

package com.aio.portable.swiss.swiss;

import com.aio.portable.swiss.suite.resource.classloader.WatchClassLoader;
import io.swagger.util.ReflectionUtils;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;

public class WatchClassLoaderTest {
    @Test
    public static void todo() {
        try {
            String name = "com.chinaunicom.software.origin.api.config.AAAA";
            WatchClassLoader classLoader = new WatchClassLoader("D:/AAAA/");
            Class<?> clazz = classLoader.loadClass(name);
            Object obj = clazz.newInstance();
            System.out.println("reload：" + Arrays.asList(ReflectionUtils.getDeclaredFields(clazz)));



            URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{new URL("file:/AAAA/")});
            Class<?> clazz0 = urlClassLoader.loadClass(name);
            Object object0 = clazz0.newInstance();
            System.out.println(clazz0.getClassLoader() + "::" + clazz0.getName());

            WatchClassLoader watchClassLoader = new WatchClassLoader("/AAAA");
            Class<?> clazz1 = watchClassLoader.loadClass(name);
            Object object1 = clazz1.newInstance();
            System.out.println(clazz1.getClassLoader() + "::" + clazz1.getName());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}

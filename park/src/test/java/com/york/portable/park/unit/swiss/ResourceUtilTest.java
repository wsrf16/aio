package com.york.portable.park.unit.swiss;

import com.york.portable.swiss.resource.PackageUtils;
import com.york.portable.swiss.resource.ResourceUtils;
import com.york.portable.swiss.resource.StreamClassLoader;
import com.york.portable.swiss.sugar.StackTraceInfo;
import org.junit.Test;
import org.springframework.boot.test.context.TestComponent;

import java.awt.print.Book;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;

@TestComponent
public class ResourceUtilTest {

    @Test
    public void main() throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        ResourceUtils.getResourcesInClassFile("com/york/portable/swiss/sandbox/a中文/AA.class");
        ResourceUtils.getResourcesByClassName("com.york.portable.swiss.sandbox.Wood");
        ResourceUtils.getResourcesByClass(Book.class);


        String jarPath = new File("console-1.0-SNAPSHOT.jar").getAbsolutePath();
        String resourceInJar = "/sandbox/console/Book.class";
        URL url = ResourceUtils.getResourceInJar(jarPath, resourceInJar);
        List<URL> urlList = ResourceUtils.getResourcesInJar(jarPath);

        {
            String className = ResourceUtils.path2FullName(resourceInJar);
            Class clazz = StreamClassLoader.buildByFile("console-1.0-SNAPSHOT.jar").loadClassByBinary(className);
            className = "com.york.portable.swiss.sandbox.Wood";
            Class clazz1 = StreamClassLoader.buildByFile("target/classes/com/york/portable/swiss/sandbox/Wood.class").loadClassByBinary(className);
            Class clazz2 = StreamClassLoader.buildByResource("com/york/portable/swiss/sandbox/Wood.class").loadClassByBinary(className);
            Object obj = clazz.newInstance();
            Object obj1 = clazz.newInstance();
        }
        {
            URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{new URL("file:/" + jarPath)});
            Class clazz = urlClassLoader.loadClass("sandbox.console.Book");
            Object obj = clazz.newInstance();
            Object obj1 = clazz.newInstance();
        }
    }



    @Test
    public void getClassName() throws IOException {
        String packageName = StackTraceInfo.Current.getClassName();
        List<String> list = PackageUtils.getClassName(packageName);
    }

}

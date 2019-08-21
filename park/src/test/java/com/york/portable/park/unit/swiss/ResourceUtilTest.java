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
    public void getClassName() throws IOException {
        String packageName = StackTraceInfo.Current.getClassName();
        List<String> list = PackageUtils.getClassName(packageName);
    }

}

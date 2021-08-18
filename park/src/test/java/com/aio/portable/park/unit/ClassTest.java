package com.aio.portable.park.unit;

import com.aio.portable.swiss.sandbox.Wood;
import com.aio.portable.swiss.sugar.StackTraceSugar;
import com.aio.portable.swiss.suite.bean.serializer.json.GsonSugar;
import com.aio.portable.swiss.suite.bean.serializer.json.JacksonSugar;
import com.aio.portable.swiss.suite.resource.ClassLoaderSugar;
import com.aio.portable.swiss.suite.resource.ClassSugar;
import com.aio.portable.swiss.suite.resource.PackageSugar;
import com.aio.portable.swiss.suite.resource.ResourceSugar;
import org.junit.Test;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.util.List;

@TestComponent
public class ClassTest {
    @Test
    private static void foobar() throws IOException {
        boolean b1 = ClassLoaderSugar.isPresent("Wood");
        boolean b2 = ClassLoaderSugar.isPresent("Wood");

        String s2 = ClassUtils.convertClassNameToResourcePath("com.company.biz.Book");
        String s = ClassSugar.convertClassNameToResourceLocation("com.company.biz.Book");
        String s1 = ResourceSugar.convertResourceLocationToClassName("/com/company/biz/Book.class");


        if (existJackson())
            System.out.println(JacksonSugar.obj2Json(new Wood() {
                {
                    setA(888);
                }
            }));

        System.out.println();

        if (existGson())
            System.out.println(GsonSugar.obj2Json(new Wood() {
                {
                    setA(888);
                }
            }));
    }

    @Test
    public static boolean existJackson() {
        return ClassLoaderSugar.isPresent(("com.fasterxml.jackson.databind.JsonSerializer"));
    }

    @Test
    public static boolean existGson() {
        return ClassLoaderSugar.isPresent(("com.google.gson.Gson"));
    }











    @Test
    public void getClassName() throws IOException {
        String packageName = StackTraceSugar.Current.getClassName();
        List<String> list = PackageSugar.getClassNameList(packageName);
    }
}

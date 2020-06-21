package com.aio.portable.swiss.swiss;

import com.aio.portable.swiss.sandbox.Wood;
import com.aio.portable.swiss.sandbox.a中文.AA;
import com.aio.portable.swiss.sugar.StackTraceSugar;
import com.aio.portable.swiss.suite.bean.serializer.json.GsonSugar;
import com.aio.portable.swiss.suite.bean.serializer.json.JacksonSugar;
import com.aio.portable.swiss.suite.resource.ClassSugar;
import com.aio.portable.swiss.suite.resource.PackageSugar;
import org.junit.Test;
import org.springframework.boot.test.context.TestComponent;

import java.io.IOException;
import java.util.List;

@TestComponent
public class ClassTest {
    @Test
    private static void todo() throws IOException {
        String ss = ClassSugar.getPath(AA.class);
        boolean b1 = ClassSugar.exist("Wood");
        boolean b2 = ClassSugar.exist("Wood");


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
    public static boolean existJackson() throws IOException {
        return ClassSugar.exist(("com.fasterxml.jackson.databind.JsonSerializer"));
    }

    @Test
    public static boolean existGson() throws IOException {
        return ClassSugar.exist(("com.google.gson.Gson"));
    }











    @Test
    public void getClassName() throws IOException {
        String packageName = StackTraceSugar.Current.getClassName();
        List<String> list = PackageSugar.getQualifiedClassName(packageName);
    }
}

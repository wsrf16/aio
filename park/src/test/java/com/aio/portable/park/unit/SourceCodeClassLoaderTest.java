package com.aio.portable.park.unit;

import com.aio.portable.swiss.sugar.meta.SourceCodeClassLoader;
import org.junit.Test;
import org.springframework.boot.test.context.TestComponent;

@TestComponent
public class SourceCodeClassLoaderTest {
    @Test
    public void foobar() {
        String sourcecode = "package com.aio.portable.park;\n" +
                "\n" +
                "import java.util.List;\n" +
                "\n" +
                "public class Task0 implements MetricJob {\n" +
                "    @Override\n" +
                "    public Object execute(Object input) {\n" +
                "        if (input instanceof List) {\n" +
                "            List list = (List) input;\n" +
                "            return \"共有\" + list.size() + \"条\";\n" +
                "        }\n" +
                "        return input;\n" +
                "    }\n" +
                "}\n";
        SourceCodeClassLoader sourceCodeClassLoader = new SourceCodeClassLoader();
        Class clazz = sourceCodeClassLoader.parseClass(sourcecode);
    }
}

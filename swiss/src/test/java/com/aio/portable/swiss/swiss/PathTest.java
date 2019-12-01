package com.aio.portable.swiss.swiss;

import com.aio.portable.swiss.sugar.PathSugar;
import org.junit.Test;
import org.springframework.boot.test.context.TestComponent;

@TestComponent
public class PathTest {
    @Test
    public static void todo() {
        String[] directories = new String[]{"/a/\\1\\", "/b/\\2", "c\\3\\", "d",
                "//e\\\\", "\\/f", "g/\\", "h//"};
        String concat = PathSugar.concat(directories);
    }
}

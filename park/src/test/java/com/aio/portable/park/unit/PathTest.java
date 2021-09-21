package com.aio.portable.park.unit;

import com.aio.portable.swiss.sugar.location.PathSugar;
import org.junit.Test;
import org.springframework.boot.test.context.TestComponent;

@TestComponent
public class PathTest {
    @Test
    public void foobar() {
        String[] directories = new String[]{"/a/\\1\\", "/b/\\2", "c\\3\\", "d",
                "//e\\\\", "\\/f", "g/\\", "h//"};
        String concat = PathSugar.concatByOS(directories);
    }
}

package com.aio.portable.swiss.swiss;

import com.aio.portable.swiss.sugar.resource.PackageSugar;
import com.aio.portable.swiss.sugar.StackTraceSugar;
import org.junit.Test;
import org.springframework.boot.test.context.TestComponent;

import java.io.IOException;
import java.util.List;

@TestComponent
public class ResourceUtilTest {

    @Test
    public void getClassName() throws IOException {
        String packageName = StackTraceSugar.Current.getClassName();
        List<String> list = PackageSugar.getQualifiedClassName(packageName);
    }

}

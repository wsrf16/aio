package com.aio.portable.park.unit.swiss;

import com.aio.portable.swiss.resource.PackageUtils;
import com.aio.portable.swiss.sugar.StackTraceInfo;
import org.junit.Test;
import org.springframework.boot.test.context.TestComponent;

import java.io.IOException;
import java.util.List;

@TestComponent
public class ResourceUtilTest {

    @Test
    public void getClassName() throws IOException {
        String packageName = StackTraceInfo.Current.getClassName();
        List<String> list = PackageUtils.getClassName(packageName);
    }

}

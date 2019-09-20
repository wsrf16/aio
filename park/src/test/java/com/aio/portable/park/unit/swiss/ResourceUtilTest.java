package com.aio.portable.park.unit.swiss;

import com.aio.portable.swiss.resource.PackageWorld;
import com.aio.portable.swiss.sugar.StackTraceInfos;
import org.junit.Test;
import org.springframework.boot.test.context.TestComponent;

import java.io.IOException;
import java.util.List;

@TestComponent
public class ResourceUtilTest {

    @Test
    public void getClassName() throws IOException {
        String packageName = StackTraceInfos.Current.getClassName();
        List<String> list = PackageWorld.getClassName(packageName);
    }

}

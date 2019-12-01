package com.aio.portable.swiss.swiss;

import com.aio.portable.swiss.structure.document.method.PropertiesMapping;
import org.springframework.boot.test.context.TestComponent;

import java.io.IOException;
import java.math.BigDecimal;

@TestComponent
public class PropertiesMapingTest {
    public static void todo() throws IOException {
        PropertiesMapping pps = new PropertiesMapping("1.properties");
        BigDecimal v1 = pps.getDecimal("AAA");
//            Date v2 = pps.getDateTime("BBB");
        String v3 = pps.getString("CCCD", "888");
    }
}

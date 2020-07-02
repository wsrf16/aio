package com.aio.portable.swiss.swiss;

import com.aio.portable.swiss.sugar.RegexSugar;
import org.junit.Test;
import org.springframework.boot.test.context.TestComponent;

import java.util.List;

@TestComponent
public class RegexTest {
    @Test
    public void regex() {
        String ret1 = RegexSugar.blurPhone("12345678901");
        String ret2 = RegexSugar.replaceAll("4567", "12345678901", "xxxx");
        boolean ret3 = RegexSugar.match("456", "12345678901");
        System.out.println(ret1);
        System.out.println(ret2);
        System.out.println(ret3);



        String input = "${name}-babalala-${age}-${address}++${name}-babalala";
        String regex = "\\$\\{(.+?)\\}-(ba.+?)";
        // .group(0): ${name}-baba
        // .group(1): name
        // .group(2): baba
        List<List<String>> matches = RegexSugar.findMore(regex, input);

        String replacement[] = {"1", "2", "3", "4", "5"};
        RegexSugar.replace(regex, input, "1", "2", "3", "4", "5");
    }
}

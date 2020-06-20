package com.aio.portable.swiss.swiss;

import com.aio.portable.swiss.sugar.PatternSugar;
import org.junit.Test;
import org.springframework.boot.test.context.TestComponent;

import java.util.List;

@TestComponent
public class RegexTest {
    @Test
    public void regex() {
        String ret1 = PatternSugar.blurPhone("12345678901");
        String ret2 = PatternSugar.replaceAll("4567", "12345678901", "xxxx");
        boolean ret3 = PatternSugar.match("456", "12345678901");
        System.out.println(ret1);
        System.out.println(ret2);
        System.out.println(ret3);



        String input = "${name}-babalala-${age}-${address}++${name}-babalala";
        String regex = "\\$\\{(.+?)\\}-(ba.+?)";
        // .group(0): ${name}-baba
        // .group(1): name
        // .group(2): baba
        List<List<String>> matches = PatternSugar.findMore(regex, input);

        String replacement[] = {"1", "2", "3", "4", "5"};
        PatternSugar.replace(regex, input, "1", "2", "3", "4", "5");
    }
}

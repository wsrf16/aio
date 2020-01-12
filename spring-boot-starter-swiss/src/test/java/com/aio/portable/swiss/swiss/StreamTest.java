package com.aio.portable.swiss.swiss;

import com.aio.portable.swiss.sugar.StreamSugar;
import org.junit.Test;
import org.springframework.boot.test.context.TestComponent;

import java.util.List;
import java.util.stream.Collectors;

@TestComponent
public class StreamTest {
    @Test
    public static void todo() {
        List<Integer> list = StreamSugar.increase(1, n -> n + 1, 100).collect(Collectors.toList());
        StreamSugar.split(list, 8);
    }
}

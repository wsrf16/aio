package com.aio.portable.park.unit;

import com.aio.portable.swiss.sugar.type.StreamSugar;
import org.junit.Test;
import org.springframework.boot.test.context.TestComponent;

import java.util.List;
import java.util.stream.Collectors;

@TestComponent
public class StreamTest {
    @Test
    public void foobar() {
        List<Integer> list = StreamSugar.increase(1, n -> n + 1, 100).collect(Collectors.toList());
        StreamSugar.split(list, 8);
    }
}

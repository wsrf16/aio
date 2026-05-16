package com.aio.portable.park.unit;

import org.junit.Test;
import org.springframework.boot.test.context.TestComponent;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@TestComponent
public class FlexTest {
    @Test
    public void foobar() {
        ArrayList<String> list1 = new ArrayList<>(Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9"));
        ArrayList<String> list2 = new ArrayList<>(Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i"));
        Flux<String> flux1 = Flux.just("1", "2", "3", "4", "5", "6", "7", "8", "9");
        Flux<String> flux2 = Flux.just("a", "b");
        Flux<String> stringFlux = Flux.mergeSequential(flux1, flux2);


        Flux<String> zipped = Flux.zip(flux1, flux2, (s1, s2) -> {
            s1 = s1 == null ? "" : s1;
            s2 = s2 == null ? "" : s2;
            return s1 + s2;
        });
        List<String> block = zipped.collectList().block();
        System.out.println(block);
    }
}

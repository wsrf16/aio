package com.aio.portable.park.unit;

import com.aio.portable.swiss.sugar.ThrowableSugar;
import com.aio.portable.swiss.sugar.meta.PackageSugar;
import org.junit.Test;
import org.springframework.boot.test.context.TestComponent;

import java.io.IOException;
import java.util.List;

@TestComponent
public class ThrowableTest {
    @Test
    public void foobar() throws IOException, ClassNotFoundException {
        Integer run3 = ThrowableSugar.toRuntimeExceptionThrower(() -> 1 / 0).get();
        ThrowableSugar.toRuntimeExceptionThrower(() -> System.out.println()).run();

        ThrowableSugar.Result<Integer> run1 = ThrowableSugar.guard(() -> 1 / 1).get();
        ThrowableSugar.Result<Integer> run2 = ThrowableSugar.guard(() -> 1 / 0).get();

    }
}

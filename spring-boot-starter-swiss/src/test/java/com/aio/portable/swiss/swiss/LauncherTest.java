package com.aio.portable.swiss.swiss;

import com.aio.portable.swiss.schedule.timer.AbstractTask;
import com.aio.portable.swiss.schedule.timer.Launcher;
import org.junit.Test;
import org.springframework.boot.test.context.TestComponent;

@TestComponent
public class LauncherTest {
    @Test
    public static void todo() {
        Launcher launcher = new Launcher(new AbstractTask() {
            @Override
            public void run() {
                System.out.println("hello");
            }
        }, "0 0 1 1 * ?", null, false);

        launcher.start();
    }
}

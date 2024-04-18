package com.aio.portable.park.unit;

import com.aio.portable.swiss.sugar.concurrent.ThreadSugar;
import com.aio.portable.swiss.suite.algorithm.identity.TOTPSugar;
import com.aio.portable.swiss.suite.algorithm.identity.ULID;
import org.junit.Test;
import org.springframework.boot.test.context.TestComponent;

@TestComponent
public class TOTPTest {
    @Test
    public void foobar() {
        int i = 0;
        while (i++ < 30) {
//            String abcdefgh234567MNS = Base32Convert.decodeToString("ABCDEFGH234567MN");
            String s = TOTPSugar.generateByPeriod("abcdefgh234567MNS", 30, 6);
            System.out.println(s);
            ThreadSugar.sleep(1000);
        }
    }

}

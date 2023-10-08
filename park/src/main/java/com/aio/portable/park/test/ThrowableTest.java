package com.aio.portable.park.test;

import com.aio.portable.park.common.AppLogHubFactory;
import com.aio.portable.swiss.hamlet.exception.BizException;
import com.aio.portable.swiss.suite.log.facade.LogHub;
import org.springframework.stereotype.Component;

@Component
public class ThrowableTest {
    LogHub log = AppLogHubFactory.staticBuild().setAsync(false);

    public void ff(){
        try {
            throw new BizException(1, "11111");
        } catch (BizException e) {
            try {
                throw new BizException(2, "22222", e);
            } catch (BizException ex) {
                try {
                    throw new BizException(3, "33333", e);
                } catch (BizException exc) {
                    log.e(e);
                    System.out.println();
                    System.out.println();
                }
            }

        }
    }

    public void todoThrow() {
        try {
            throw3();
        } catch (Exception e) {
            log.e(e);
            System.out.println();
            System.out.println();
        }
    }

    private void throw3() {
        try {
            throw2();
        } catch (Exception e) {
            throw new BizException(3, e.getMessage(), e);
        }
    }

    private void throw2() {
        try {
            throw1();
        } catch (Exception e) {
            throw new BizException(2, e.getMessage(), e);

        }
    }

    private void throw1() {
        throw new BizException(1, "11111");
    }

}

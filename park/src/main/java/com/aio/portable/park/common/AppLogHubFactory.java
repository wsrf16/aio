package com.aio.portable.park.common;

import com.aio.portable.swiss.suite.log.facade.LogHub;
import com.aio.portable.swiss.suite.log.factory.LogHubFactory;
import org.springframework.stereotype.Component;

@Component
public class AppLogHubFactory extends LogHubFactory {
    @Override
    public LogHub build(String className) {
        return super.build(className).setAsync(false);
    }

}

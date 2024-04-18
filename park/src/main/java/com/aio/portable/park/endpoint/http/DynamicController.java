package com.aio.portable.park.endpoint.http;

import com.aio.portable.swiss.hamlet.bean.ResponseWrapper;
import com.aio.portable.swiss.hamlet.bean.ResponseWrappers;
import com.aio.portable.swiss.spring.SpringController;
import com.aio.portable.swiss.sugar.resource.ClassSugar;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.handler.AbstractHandlerMethodMapping;

import java.lang.reflect.Method;


@Component
public class DynamicController {

    AbstractHandlerMethodMapping abstractHandlerMethodMapping;

    public DynamicController(AbstractHandlerMethodMapping abstractHandlerMethodMapping) {
        this.abstractHandlerMethodMapping = abstractHandlerMethodMapping;
    }

    public void registerTest() {
        registerTest(abstractHandlerMethodMapping);
    }

    public void registerTest(AbstractHandlerMethodMapping abstractHandlerMethodMapping) {
        Method method = ClassSugar.getDeclaredMethod(this.getClass(), "test");
        SpringController.registerMapping(abstractHandlerMethodMapping, this,
                method,
                "/test");
    }


    @ResponseBody
    public ResponseWrapper<String> test() throws Exception {
        return ResponseWrappers.succeed("testtttttt");
    }

}

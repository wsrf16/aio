package com.aio.portable.park.endpoint.http;

import com.aio.portable.swiss.spring.SpringController;
import com.aio.portable.swiss.sugar.resource.ClassSugar;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.handler.AbstractHandlerMethodMapping;


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
        SpringController.registerMapping(abstractHandlerMethodMapping, this,
                ClassSugar.getDeclaredMethod(this.getClass(), "test"),
                "/malicious");
    }


    @ResponseBody
    public void test() throws Exception {
        System.out.println("testtttttt");
    }

}

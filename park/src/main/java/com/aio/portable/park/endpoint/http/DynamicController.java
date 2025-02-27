package com.aio.portable.park.endpoint.http;

import com.aio.portable.swiss.hamlet.bean.ResponseWrapper;
import com.aio.portable.swiss.hamlet.bean.ResponseWrappers;
import com.aio.portable.swiss.spring.SpringController;
import com.aio.portable.swiss.sugar.meta.ClassSugar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.handler.AbstractHandlerMethodMapping;

import java.lang.reflect.Method;


@Component
public class DynamicController {

    @Autowired
    AbstractHandlerMethodMapping abstractHandlerMethodMapping;

    public DynamicController(AbstractHandlerMethodMapping abstractHandlerMethodMapping) {
        this.abstractHandlerMethodMapping = abstractHandlerMethodMapping;
        registerTest();
    }

    public void registerTest() {
        Method method = ClassSugar.Methods.getDeclaredMethod(this.getClass(), "test");
        SpringController.registerMapping(abstractHandlerMethodMapping, this,
                method,
                RequestMethod.GET,
                "/test");
    }

    @ResponseBody
    public ResponseWrapper<String> test() throws Exception {
        return ResponseWrappers.succeed("testtttttt");
    }

}

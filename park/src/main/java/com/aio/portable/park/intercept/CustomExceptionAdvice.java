package com.aio.portable.park.intercept;

import com.aio.portable.swiss.hamlet.interceptor.classic.HamletExceptionAdvice;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionAdvice extends HamletExceptionAdvice {

//    public CustomExceptionAdvice(LogHubFactory logHubFactory) {
//        super(logHubFactory);
//    }

}

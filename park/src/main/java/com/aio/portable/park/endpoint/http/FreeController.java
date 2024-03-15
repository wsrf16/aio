package com.aio.portable.park.endpoint.http;

import com.aio.portable.swiss.hamlet.bean.ResponseWrapper;
import com.aio.portable.swiss.hamlet.bean.ResponseWrappers;
import com.aio.portable.swiss.spring.SpringController;
import com.aio.portable.swiss.suite.net.tcp.http.RestTemplater;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URISyntaxException;

@Controller
@ApiIgnore
@RequestMapping(FreeController.FREE_CONTEXT)
public class FreeController {
    public static final String FREE_CONTEXT = "/free";
    public static final String PROXY_CONTEXT = "/proxy";

    @Autowired
    private RestTemplate skipSSLRestTemplate;

    @ApiOperation(value = "中转", notes = "中转")
    @RequestMapping(PROXY_CONTEXT + "/**")
    public ResponseEntity<Object> handleRequest(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        String shimContextPath = contextPath + FREE_CONTEXT + PROXY_CONTEXT;
        ResponseEntity<Object> responseEntity = SpringController.forward(skipSSLRestTemplate, request, shimContextPath);
        return responseEntity;
    }




}

package com.aio.portable.park.controller;

import com.aio.portable.swiss.hamlet.exception.BizException;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.io.IOException;

@Controller
@ApiIgnore
@RequestMapping("/")
public class RootController {

    @ApiOperation(value = "首页")
    @GetMapping("/")
    public String index() throws IOException {
//        int i = 1;
//        if (i == 1)
//            throw new BizException(111, "aaaaaaaaaaa");
        return "doc.html";
    }

}

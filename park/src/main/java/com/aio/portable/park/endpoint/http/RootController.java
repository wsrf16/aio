package com.aio.portable.park.endpoint.http;

import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

import java.io.IOException;

@Controller
@ApiIgnore
@RequestMapping("/")
public class RootController {

    @ApiOperation(value = "首页")
    @GetMapping("/")
    public String index() throws IOException {
        return "doc.html";
    }

}

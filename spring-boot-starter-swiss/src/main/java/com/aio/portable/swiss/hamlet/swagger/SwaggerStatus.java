package com.aio.portable.swiss.hamlet.swagger;

import com.aio.portable.swiss.hamlet.bean.BizStatus;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.Response;
import springfox.documentation.service.ResponseMessage;

import java.util.ArrayList;
import java.util.List;

public abstract class SwaggerStatus {
    public static List<ResponseMessage> toResponseMessageList(List<BizStatus> bizStatusList) {
        List<ResponseMessage> codes = new ArrayList<>();
        ResponseMessageBuilder builder = new ResponseMessageBuilder();
        for (BizStatus status : bizStatusList) {
            codes.add(builder
                    .code(status.getCode())
                    .message(status.getMessage())
                    .build());
        }
        return codes;
    }

    public static List<Response> toResponseList(List<BizStatus> bizStatusList) {
        List<Response> codes = new ArrayList<>();
        ResponseBuilder builder = new ResponseBuilder();
        for (BizStatus status : bizStatusList) {
            codes.add(builder
                    .code(String.valueOf(status.getCode()))
                    .description(status.getMessage())
                    .build());
        }
        return codes;
    }
}

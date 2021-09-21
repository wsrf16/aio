package com.aio.portable.swiss.hamlet.swagger;

import com.aio.portable.swiss.hamlet.bean.BizStatus;
import com.aio.portable.swiss.spring.factories.autoconfigure.properties.Swagger2Properties;
import com.google.common.base.Predicate;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.ApiSelector;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

public class Swagger2Builder {
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

    public static Swagger2Properties buildProperties(String packageName, List<ResponseMessage> responseMessageList) {
        Swagger2Properties swagger2Properties = new Swagger2Properties();
        swagger2Properties.setPackageName(packageName);
        swagger2Properties.setResponseMessageList(responseMessageList);
        return swagger2Properties;
    }

    public static List<Parameter> createParameters() {
        Parameter tokenParameter = new ParameterBuilder()
                // 参数类型支持header, cookie, body, query etc
                .parameterType("header")
                // 参数名
                .name(HttpHeaders.AUTHORIZATION)
                // 默认值
                .defaultValue("")
                .description("Authorization Header")
                // 指定参数值的类型
                .modelRef(new ModelRef("string"))
                // 非必需，这里是全局配置，然而在登陆的时候是不用验证的
                .required(false)
                .build();

        List<Parameter> parameters = new ArrayList<>();
        parameters.add(tokenParameter);
        return parameters;
    }

//    public static Docket createRestApi2(Swagger2Properties swagger2Properties) {
//        return createRestApi2(swagger2Properties, each -> true);
//
//    }

    public static Docket createRestApi2(Swagger2Properties swagger2Properties) {
        ApiInfo apiInfo = swagger2Properties.getApiInfo().toSwaggerApiInfo();
        boolean enable = swagger2Properties.getEnabled();
        List<ResponseMessage> responseMessageList = swagger2Properties.getResponseMessageList();
        String packageName = swagger2Properties.getPackageName();

        final List<Parameter> parameters = createParameters();

        ApiSelectorBuilder builder = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .globalResponseMessage(RequestMethod.GET, responseMessageList)
                .globalResponseMessage(RequestMethod.POST, responseMessageList)
                .globalResponseMessage(RequestMethod.PUT, responseMessageList)
                .globalResponseMessage(RequestMethod.DELETE, responseMessageList)
                .globalResponseMessage(RequestMethod.HEAD, responseMessageList)
                .globalResponseMessage(RequestMethod.OPTIONS, responseMessageList)
                .globalResponseMessage(RequestMethod.PATCH, responseMessageList)
                .globalResponseMessage(RequestMethod.TRACE, responseMessageList)
                .globalOperationParameters(parameters)
                .enable(enable)
                .host(swagger2Properties.getHost())
                .select()
                .paths(ApiSelector.DEFAULT.getPathSelector());
//        PathSelectors.any()
//        ApiSelector.DEFAULT.getPathSelector()
        if (StringUtils.hasText(packageName))
            builder.apis(RequestHandlerSelectors.basePackage(packageName));
        else
            builder.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class));

        return builder.build();
    }
}

package com.aio.portable.swiss.hamlet.swagger;

import com.aio.portable.swiss.hamlet.bean.BizStatus;
import com.aio.portable.swiss.spring.factories.autoconfigure.properties.Swagger3Properties;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.util.StringUtils;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.service.ParameterType;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.service.Response;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.ApiSelector;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Swagger3Builder {
    public static List<Response> buildResponseList(List<BizStatus> bizStatusList) {
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

    public static Swagger3Properties buildProperties(String packageName, List<Response> responseList) {
        Swagger3Properties swagger3Properties = new Swagger3Properties();
        swagger3Properties.setPackageName(packageName);
        swagger3Properties.setResponseList(responseList);
        return swagger3Properties;
    }

    public static List<RequestParameter> createRequestParameters() {
        RequestParameter tokenParameter = new RequestParameterBuilder()
                // 参数名
                .name(HttpHeaders.AUTHORIZATION)
                // 默认值
//                .defaultValue("")
                .in(ParameterType.HEADER)
                .description("Authorization Header")
                // 指定参数值的类型
//                .modelRef(new ModelRef("string"))
                // 非必需，这里是全局配置，然而在登陆的时候是不用验证的
                .required(false)
                .build();

        List<RequestParameter> requestParameters = new ArrayList<>();
        requestParameters.add(tokenParameter);
        return requestParameters;
    }

    public static Docket createDocket(Swagger3Properties swagger3Properties) {
        springfox.documentation.service.ApiInfo apiInfo = swagger3Properties.getApiInfo().toSwaggerApiInfo();
        boolean enable = swagger3Properties.getEnabled();
        List<Response> responseList = swagger3Properties.getResponseList();
        String packageName = swagger3Properties.getPackageName();
        String host = swagger3Properties.getHost();
        List<RequestParameter> requestParameters = swagger3Properties.getRequestParameters() != null ?
                swagger3Properties.getRequestParameters() :
                createRequestParameters();

        Docket docket = new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo)
                .globalResponses(HttpMethod.GET, responseList)
                .globalResponses(HttpMethod.POST, responseList)
                .globalResponses(HttpMethod.PUT, responseList)
                .globalResponses(HttpMethod.DELETE, responseList)
                .globalResponses(HttpMethod.HEAD, responseList)
                .globalResponses(HttpMethod.OPTIONS, responseList)
                .globalResponses(HttpMethod.PATCH, responseList)
                .globalResponses(HttpMethod.TRACE, responseList)
                .enable(enable)
                .host(host)
                .globalRequestParameters(requestParameters);

        Predicate<RequestHandler> selector = StringUtils.hasText(packageName) ? RequestHandlerSelectors.basePackage(packageName) : RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class);

        ApiSelectorBuilder builder = docket
                .select()
                .paths(ApiSelector.DEFAULT.getPathSelector())
                .apis(selector);

        return builder.build();
    }
}

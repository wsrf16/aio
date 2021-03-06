package com.aio.portable.swiss.factories.autoconfigure;

import com.aio.portable.swiss.factories.autoconfigure.properties.Swagger2Properties;
import io.swagger.annotations.ApiOperation;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

//@Configuration
@ConditionalOnClass(Docket.class)
@EnableConfigurationProperties(Swagger2Properties.class)
public class Swagger2AutoConfiguration {

//    @Bean
//    @ConditionalOnBean(Swagger2Properties.class)
//    public ApiInfo buildApiInfo(Swagger2Properties swagger2Properties) {
//        return swagger2Properties.getApiInfo();
//    }

    private List<Parameter> createParameters() {
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

    private List<RequestParameter> createRequestParameters() {
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

//    @Bean
//    @ConditionalOnBean(Swagger2Properties.class)
    public Docket createRestApi(Swagger2Properties swagger2Properties) {
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
                .paths(PathSelectors.any());
        if (StringUtils.hasText(packageName))
            builder.apis(RequestHandlerSelectors.basePackage(packageName));
        else
            builder.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class));

        return builder.build();
    }







    @Bean
    @ConditionalOnBean(Swagger2Properties.class)
    public Docket createRestApi3(Swagger2Properties swagger2Properties) {
        ApiInfo apiInfo = swagger2Properties.getApiInfo().toSwaggerApiInfo();
        boolean enable = swagger2Properties.getEnabled();
        List<Response> responseList = swagger2Properties.getResponseList();
        String packageName = swagger2Properties.getPackageName();
        String host = swagger2Properties.getHost();
        List<RequestParameter> requestParameters = swagger2Properties.getRequestParameters() != null ?
                        swagger2Properties.getRequestParameters() :
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
                .paths(PathSelectors.any())
                .apis(selector);

        return builder.build();
    }



}

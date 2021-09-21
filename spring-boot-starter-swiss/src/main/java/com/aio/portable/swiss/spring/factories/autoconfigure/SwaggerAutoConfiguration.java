package com.aio.portable.swiss.spring.factories.autoconfigure;

import com.aio.portable.swiss.hamlet.swagger.Swagger2Builder;
import com.aio.portable.swiss.hamlet.swagger.Swagger3Builder;
import com.aio.portable.swiss.spring.factories.autoconfigure.properties.Swagger2Properties;
import com.aio.portable.swiss.spring.factories.autoconfigure.properties.Swagger3Properties;
import io.swagger.annotations.ApiOperation;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
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
//@ConditionalOnClass({Docket.class, Swagger2Properties.class})
//@EnableConfigurationProperties(Swagger3Properties.class)
public class SwaggerAutoConfiguration {

//    @Bean
//    @ConditionalOnBean(Swagger2Properties.class)
//    public ApiInfo buildApiInfo(Swagger2Properties swagger2Properties) {
//        return swagger2Properties.getApiInfo();
//    }

//    private List<Parameter> createParameters() {
//        Parameter tokenParameter = new ParameterBuilder()
//                // 参数类型支持header, cookie, body, query etc
//                .parameterType("header")
//                // 参数名
//                .name(HttpHeaders.AUTHORIZATION)
//                // 默认值
//                .defaultValue("")
//                .description("Authorization Header")
//                // 指定参数值的类型
//                .modelRef(new ModelRef("string"))
//                // 非必需，这里是全局配置，然而在登陆的时候是不用验证的
//                .required(false)
//                .build();
//
//        List<Parameter> parameters = new ArrayList<>();
//        parameters.add(tokenParameter);
//        return parameters;
//    }

//    private List<RequestParameter> createRequestParameters() {
//        RequestParameter tokenParameter = new RequestParameterBuilder()
//                // 参数名
//                .name(HttpHeaders.AUTHORIZATION)
//                // 默认值
////                .defaultValue("")
//                .in(ParameterType.HEADER)
//                .description("Authorization Header")
//                // 指定参数值的类型
////                .modelRef(new ModelRef("string"))
//                // 非必需，这里是全局配置，然而在登陆的时候是不用验证的
//                .required(false)
//                .build();
//
//        List<RequestParameter> requestParameters = new ArrayList<>();
//        requestParameters.add(tokenParameter);
//        return requestParameters;
//    }

    @Bean
    @ConditionalOnClass(name = "springfox.documentation.service.ResponseMessage")
    @ConditionalOnBean({Swagger2Properties.class})
    @ConditionalOnMissingBean({Docket.class})
    public Docket createRestApi2(Swagger2Properties swagger2Properties) {
        return Swagger2Builder.createRestApi2(swagger2Properties);
    }







    @Bean
    @ConditionalOnClass(name = "springfox.documentation.service.Response")
    @ConditionalOnBean({Swagger3Properties.class})
    @ConditionalOnMissingBean({Docket.class})
    public Docket createRestApi3(Swagger3Properties swagger3Properties) {
        return Swagger3Builder.createDocket(swagger3Properties);
    }



}

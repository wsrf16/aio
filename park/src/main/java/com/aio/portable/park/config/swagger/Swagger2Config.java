//package com.aio.portable.park.config;
//
//
//import com.aio.portable.park.common.BizStatusEnum;
//import com.aio.portable.park.controller.RootController;
//import com.aio.portable.swiss.hamlet.swagger.Swagger2Builder;
//import com.aio.portable.swiss.spring.factories.autoconfigure.properties.Swagger2Properties;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.util.StringUtils;
//import org.springframework.web.bind.annotation.RequestMethod;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.Parameter;
//import springfox.documentation.service.ResponseMessage;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spi.service.contexts.ApiSelector;
//import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
//import java.util.List;
//
//
//@EnableSwagger2
//@Configuration
//@ConditionalOnClass(ApiInfo.class)
//public class Swagger2Config {
//    @Bean
//    @ConditionalOnClass(ApiInfo.class)
//    @ConditionalOnProperty(prefix = "swagger.api-info", name = "title")
//    @ConfigurationProperties(prefix = "swagger")
//    public Swagger2Properties swagger2Properties() {
//        return Swagger2Builder.buildProperties(RootController.class.getPackage().getName(), Swagger2Builder.toResponseMessageList(BizStatusEnum.values()));
//    }
//
//    @Bean
//    @ConditionalOnMissingBean({Docket.class})
//    public Docket createRestApi2(Swagger2Properties swagger2Properties) {
//        ApiInfo apiInfo = swagger2Properties.getApiInfo().toSwaggerApiInfo();
//        boolean enable = swagger2Properties.getEnabled();
//        List<ResponseMessage> responseMessageList = swagger2Properties.getResponseMessageList();
//        String packageName = swagger2Properties.getPackageName();
//        final List<Parameter> parameters = Swagger2Builder.createParameters();
//        ApiSelectorBuilder builder = new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(apiInfo)
//                .globalResponseMessage(RequestMethod.GET, responseMessageList)
//                .globalResponseMessage(RequestMethod.POST, responseMessageList)
//                .globalResponseMessage(RequestMethod.PUT, responseMessageList)
//                .globalResponseMessage(RequestMethod.DELETE, responseMessageList)
//                .globalResponseMessage(RequestMethod.HEAD, responseMessageList)
//                .globalResponseMessage(RequestMethod.OPTIONS, responseMessageList)
//                .globalResponseMessage(RequestMethod.PATCH, responseMessageList)
//                .globalResponseMessage(RequestMethod.TRACE, responseMessageList)
//                .globalOperationParameters(parameters)
//                .enable(enable)
//                .host(swagger2Properties.getHost())
//                .select()
//                .paths(ApiSelector.DEFAULT.getPathSelector());
//        if (StringUtils.hasText(packageName))
//            builder.apis(RequestHandlerSelectors.basePackage(packageName));
//        else
//            builder.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class));
//
//        return builder.build();
//    }
//
//}
//

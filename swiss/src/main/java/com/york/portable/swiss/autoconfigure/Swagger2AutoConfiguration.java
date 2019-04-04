package com.york.portable.swiss.autoconfigure;

import com.york.portable.swiss.extra.Swagger2Properties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.List;

@Configuration
@ConditionalOnClass(Docket.class)
public class Swagger2AutoConfiguration {

//    @Bean
//    @ConditionalOnBean(Swagger2Properties.class)
//    public ApiInfo buildApiInfo(Swagger2Properties swagger2Properties) {
//        return swagger2Properties.getApiInfo();
//    }

    @Bean
    @ConditionalOnBean(Swagger2Properties.class)
    @ConditionalOnClass(Docket.class)
    public Docket createRestApi(Swagger2Properties swagger2Properties) {
        ApiInfo apiInfo = swagger2Properties.getApiInfo().toSwaggerApiInfo();
        boolean enable = swagger2Properties.getEnable();
        List<ResponseMessage> responseMessageList = swagger2Properties.getResponseMessageList();
        String packageName = swagger2Properties.getPackageName();

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .globalResponseMessage(RequestMethod.GET, responseMessageList)
                .enable(enable)
                .select()
                .apis(RequestHandlerSelectors.basePackage(packageName))
                .paths(PathSelectors.any())
                .build();
    }

}

package com.aio.portable.park.config;

import com.aio.portable.park.controller.DemoController;
import com.aio.portable.swiss.factories.autoconfigure.properties.Swagger2Properties;
import com.aio.portable.swiss.hamlet.swagger.SwaggerStatus;
import com.aio.portable.park.common.BizStatusEnum;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;

@Import(BeanValidatorPluginsConfiguration.class)
//@EnableSwagger2
@EnableOpenApi
@Configuration
@ConditionalOnClass(ApiInfo.class)
public class Swagger2Config {
    @Bean
    public Swagger2Properties swagger2Properties() {
        return Swagger2Properties.buildByResponse(DemoController.class.getPackage().getName(),
                SwaggerStatus.toResponseList(BizStatusEnum.values()));
    }

}

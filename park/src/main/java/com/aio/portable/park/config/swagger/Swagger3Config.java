package com.aio.portable.park.config.swagger;

import com.aio.portable.park.common.BizStatusEnum;
import com.aio.portable.park.endpoint.http.RootController;
import com.aio.portable.swiss.hamlet.swagger.Swagger3Builder;
import com.aio.portable.swiss.spring.factories.autoconfigure.properties.Swagger3Properties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;

@Import(BeanValidatorPluginsConfiguration.class)
@Configuration
@ConditionalOnClass(ApiInfo.class)
//@EnableOpenApi
//@EnableSwagger2
public class Swagger3Config {
    @Bean
    @ConditionalOnClass({ApiInfo.class, EnableOpenApi.class})
    @ConditionalOnProperty(prefix = "swagger.api-info", name = "title")
    @ConfigurationProperties(prefix = "swagger")
    public Swagger3Properties swagger3Properties() {
        return Swagger3Builder.buildProperties(RootController.class.getPackage().getName(),
                Swagger3Builder.buildResponseList(BizStatusEnum.values()));
    }

}

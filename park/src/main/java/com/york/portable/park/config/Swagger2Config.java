package com.york.portable.park.config;

import com.york.portable.park.controller.DemoController;
import com.york.portable.swiss.extra.Swagger2Properties;
import com.york.portable.swiss.hamlet.model.BizStatusEnum;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

//@Import(BeanValidatorPluginsConfiguration.class)
@EnableSwagger2
@Configuration
@ConditionalOnClass(ApiInfo.class)
public class Swagger2Config {
    private final List<ResponseMessage> responseMessageList() {
        List<ResponseMessage> codes = new ArrayList<>();
        ResponseMessageBuilder builder = new ResponseMessageBuilder();
        for (BizStatusEnum status : BizStatusEnum.values()) {
            codes.add(builder.code(status.getCode())
                    .message(status.getDescription())
                    .build());
        }
        return codes;
    }

    @Bean
    @ConditionalOnClass(ApiInfo.class)
    @ConditionalOnProperty(prefix = "swagger", name = "enable", havingValue = "true")
    @ConfigurationProperties(prefix = "swagger")
    public Swagger2Properties swagger2Properties() {
        return Swagger2Properties.build(DemoController.class.getPackage().getName(), responseMessageList());
    }

}

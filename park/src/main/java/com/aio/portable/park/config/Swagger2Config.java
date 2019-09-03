package com.aio.portable.park.config;

import com.aio.portable.park.controller.DemoController;
import com.aio.portable.swiss.autoconfigure.properties.Swagger2Properties;
import com.aio.portable.swiss.hamlet.model.BizStatusEnum;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Import(BeanValidatorPluginsConfiguration.class)
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
    public Swagger2Properties swagger2Properties() {
        return Swagger2Properties.build(DemoController.class.getPackage().getName(), responseMessageList());
    }

}
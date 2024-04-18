package com.aio.portable.swiss.hamlet.bean;

import com.aio.portable.swiss.sugar.type.DateTimeSugar;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.core.Ordered;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

public abstract class HamletBeanConfig {
    public ObjectMapper objectMapper(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateTimeSugar.Format.FORMAT_NORMAL_LONG);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));

        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder()
                .indentOutput(true)
                .dateFormat(simpleDateFormat)
                .modulesToInstall(new ParameterNamesModule());
        return builder.build();
    }

    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter(ObjectMapper objectMapper){
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        mappingJackson2HttpMessageConverter.setObjectMapper(objectMapper);
//        List<MediaType> list = new ArrayList<MediaType>() {{
//            add(MediaType.APPLICATION_JSON_UTF8);
//        }};
//        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(list);
        return mappingJackson2HttpMessageConverter;
    }

    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.initialize();
        return threadPoolTaskScheduler;
    }


}

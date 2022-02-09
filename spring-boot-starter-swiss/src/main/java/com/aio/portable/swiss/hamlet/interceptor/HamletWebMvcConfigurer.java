package com.aio.portable.swiss.hamlet.interceptor;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

public abstract class HamletWebMvcConfigurer implements WebMvcConfigurer {
//    @Autowired
//    private ObjectMapper objectMapper;

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        AntPathMatcher matcher = new AntPathMatcher();
        matcher.setCaseSensitive(false);
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    }

//    @Override
//    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
//        converters.clear();
//        converters.add(new MappingJackson2HttpMessageConverter(objectMapper));
//        converters.add(mappingJackson2HttpMessageConverter);
//        converters.add(new MappingJackson2XmlHttpMessageConverter(factory.xml().build()));


//        converters.add(new ByteArrayHttpMessageConverter());
//        converters.add(new StringHttpyMessageConverter(Charset.forName("UTF-8")));
//        converters.add(new ResourceHttpMessageConverter());
//        converters.add(new SourceHttpMessageConverter());
//        converters.add(new AllEncompassingFormHttpMessageConverter());
//        converters.add(new MappingJackson2HttpMessageConverter());
//        converters.add(new StringHttpMessageConverter());
//    }

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new GlobalHandlerInterceptor()).addPathPatterns("/**");
//    }

//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        Constant.RESOURCE_HANDLE_MAP.entrySet().forEach(c -> registry.addResourceHandler(c.getKey()).addResourceLocations(c.getValue()));
//    }

//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        new HashMap<String, String>() {{
//            put("/log/**", ResourceUtils.FILE_URL_PREFIX + new File("").getAbsolutePath() + "log/");
//        }}
//        .entrySet().forEach(c ->
//                registry.addResourceHandler(c.getKey())
//                        .addResourceLocations(c.getValue())
//        );
//    }

    /***
     * 不加提示以下信息
     * /null/swagger-resources
     * /null/swagger-resources/configuration/ui
     * /null/swagger-resources/configuration/security
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("swagger-ui.html")
//                .addResourceLocations("classpath:/META-INF/resources/");
//
//        registry.addResourceHandler("/webjars/**")
//                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

//    static final String ORIGINS[] = new String[] { "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS" };

    @Override
    @LoadBalanced
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedHeaders("*")
//                .exposedHeaders("*")
                .maxAge(3600);
    }



}
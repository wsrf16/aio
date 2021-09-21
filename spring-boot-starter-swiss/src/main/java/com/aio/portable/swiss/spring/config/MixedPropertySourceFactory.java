package com.aio.portable.swiss.spring.config;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.DefaultPropertySourceFactory;
import org.springframework.core.io.support.EncodedResource;

import java.io.IOException;
import java.util.Properties;

/**
 * MixedPropertySourceFactory
 * eg.
 * @PropertySource(value = {"classpath:clazz1.yml", "classpath:clazz2.properties"}, factory = ResourceFactory.class)
 * @ConfigurationProperties(prefix = "spring.ip-list")
 * @Data
 * public class IPListConfig {}
 */
public class MixedPropertySourceFactory extends DefaultPropertySourceFactory {

    @Override
    public PropertySource<?> createPropertySource(String name, EncodedResource resource) throws IOException {
        String sourceName = name != null ? name : resource.getResource().getFilename();
        if (!resource.getResource().exists()) {
            return new PropertiesPropertySource(sourceName, new Properties());
        } else if (sourceName.endsWith(".yml") || sourceName.endsWith(".yaml")) {
            Properties propertiesFromYaml = loadYaml(resource);
            return new PropertiesPropertySource(sourceName, propertiesFromYaml);
        } else {
            return super.createPropertySource(name, resource);
        }
    }

    private Properties loadYaml(EncodedResource resource) throws IOException {
        YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
        factory.setResources(resource.getResource());
        factory.afterPropertiesSet();
        return factory.getObject();
    }
}




//@PropertySource(value = {"classpath:ip-list.yml"},factory = YamlConfigFactory.class)
//@Component
//@ConfigurationProperties(prefix = "ip-list")
//@Data
//class IPListConfig
//{
//    private String appName;
//    private String ip;
//
//}
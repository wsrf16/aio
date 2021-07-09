package com.aio.portable.swiss.sugar;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.util.Properties;

public abstract class PropertySugar {
    public final static Properties getProperties(Resource... resources) {
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        yaml.setResources(resources);
        return yaml.getObject();
    }

//    public final static Properties getProperties(ClassPathResource resource) {
//        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
//        yaml.setResources(resource);
//        return yaml.getObject();
//    }
//
//    public final static Properties getProperties(FileSystemResource resource) {
//        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
//        yaml.setResources(resource);
//        return yaml.getObject();
//    }
}

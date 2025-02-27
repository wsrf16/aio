package com.aio.portable.swiss.sugar.meta;

import com.aio.portable.swiss.spring.SpringContextHolder;
import com.aio.portable.swiss.sugar.type.CollectionSugar;
import com.aio.portable.swiss.sugar.type.StringSugar;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationProperty;
import org.springframework.boot.context.properties.source.ConfigurationPropertyName;
import org.springframework.boot.origin.Origin;
import org.springframework.boot.origin.PropertySourceOrigin;
import org.springframework.core.annotation.AnnotationConfigurationException;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;

import java.util.List;
import java.util.Properties;

public abstract class PropertySugar {
    public static final Properties getProperties(Resource... resources) {
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        yaml.setResources(resources);
        return yaml.getObject();
    }

//    public static final Properties getProperties(ClassPathResource resource) {
//        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
//        yaml.setResources(resource);
//        return yaml.getObject();
//    }
//
//    public static final Properties getProperties(FileSystemResource resource) {
//        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
//        yaml.setResources(resource);
//        return yaml.getObject();
//    }

    /**
     * getPropertyBean
     *
     * @param environment
     * @param name
     * @param clazz
     * @param <T>
     * @return
     */
    public static final <T> T getPropertyBean(Environment environment, String name, Class<T> clazz) {
        BindResult<T> bind = Binder.get(environment).bind(name, clazz);
        T t = bind != null && bind.isBound() ? bind.get() : null;
        return t;
    }

    /**
     * getPropertyBean
     *
     * @param name
     * @param clazz
     * @param <T>
     * @return
     */
    public static final <T> T getPropertyBean(String name, Class<T> clazz) {
        Environment environment = SpringContextHolder.getStandardServletEnvironment();
        return getPropertyBean(environment, name, clazz);
    }


    /**
     * getPropertyBean
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static final <T> T getPropertyBean(Class<T> clazz) {
        boolean present = clazz.isAnnotationPresent(ConfigurationProperties.class);
        if (!present)
            throw new AnnotationConfigurationException("Miss '@ConfigurationProperties' on " + clazz.getName() + ".");

        ConfigurationProperties[] annotationsByType = clazz.getAnnotationsByType(ConfigurationProperties.class);
        String name = StringSugar.getFirstHasText(null, annotationsByType[0].prefix(), annotationsByType[0].value());
        if (name == null)
            throw new AnnotationConfigurationException("'@ConfigurationProperties.class' prefix or value is null.");

        T propertyBean = getPropertyBean(name, clazz);
        return propertyBean;
    }

    /**
     * getPropertySourceList
     * @return
     */
    public static final List<PropertySource<?>> getPropertySourceList() {
        return CollectionSugar.toList(SpringContextHolder.getStandardServletEnvironment().getPropertySources().iterator());
    }

    /**
     * resolvePlaceholders
     * @param placeholder
     * @return
     */
    public static final String resolvePlaceholders(String placeholder) {
        String value = SpringContextHolder.getStandardServletEnvironment().resolvePlaceholders(placeholder);
        return value;
    }

    /**
     * buildPropertySourceOrigin
     * @param propertySource
     * @param name
     * @return
     */
    public static final ConfigurationProperty buildPropertySourceOrigin(PropertySource<?> propertySource, String name) {
        ConfigurationPropertyName propertyName = ConfigurationPropertyName.of(name);
        Object value = propertySource.getProperty(name);
        Origin origin = PropertySourceOrigin.get(propertySource, name);
        return new ConfigurationProperty(propertyName, value, origin);
    }

    /**
     * buildPropertySourceOrigin
     * @param name
     * @param value
     * @param origin
     * @return
     */
    public static final ConfigurationProperty buildPropertySourceOrigin(String name, Object value, Origin origin) {
        ConfigurationPropertyName propertyName = ConfigurationPropertyName.of(name);
        return new ConfigurationProperty(propertyName, value, origin);
    }


}

package com.aio.portable.swiss.suite.storage.db;

import com.aio.portable.swiss.sugar.type.CollectionSugar;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.source.InvalidConfigurationPropertyValueException;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.config.SortedResourcesFactoryBean;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class DataSourceSugar {
    // org.springframework.boot.autoconfigure.jdbc.DataSourceInitializer
    // org.springframework.jdbc.datasource.init.DataSourceInitializer
    public static ResourceDatabasePopulator newDatabasePopulator(DataSourceProperties properties) {
        List<Resource> resourcesSchema = getScripts("spring.datasource.schema", properties.getSchema(), "schema", properties.getPlatform());
        List<Resource> resourcesData = getScripts("spring.datasource.data", properties.getData(), "data", properties.getPlatform());
        List<Resource> resources = CollectionSugar.union(resourcesSchema, resourcesData);

        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.setContinueOnError(properties.isContinueOnError());
        populator.setSeparator(properties.getSeparator());
        if (properties.getSqlScriptEncoding() != null) {
            populator.setSqlScriptEncoding(properties.getSqlScriptEncoding().name());
        }
        for (Resource resource : resources) {
            populator.addScript(resource);
        }
//        if (StringUtils.hasText(username) && StringUtils.hasText(password)) {
//            dataSource = DataSourceBuilder.create(properties.getClassLoader())
//                    .driverClassName(properties.determineDriverClassName()).url(properties.determineUrl())
//                    .username(username).password(password).build();
//        }
//            DatabasePopulatorUtils.execute(populator, dataSource);
        return populator;
    }

    private static final List<Resource> getScripts(String propertyName, List<String> resources, String fallback, String platform) {
        if (resources != null) {
            return getResources(propertyName, resources, true);
        }
        List<String> fallbackResources = new ArrayList<>();
        fallbackResources.add("classpath*:" + fallback + "-" + platform + ".sql");
        fallbackResources.add("classpath*:" + fallback + ".sql");
        return getResources(propertyName, fallbackResources, false);
    }

    private static final List<Resource> getResources(String propertyName, List<String> locations, boolean validate) {
        List<Resource> resources = new ArrayList<>();
        for (String location : locations) {
            for (Resource resource : doGetResources(location)) {
                if (resource.exists()) {
                    resources.add(resource);
                }
                else if (validate) {
                    throw new InvalidConfigurationPropertyValueException(propertyName, resource,
                            "The specified resource does not exist.");
                }
            }
        }
        return resources;
    }

    private static final Resource[] doGetResources(String location) {
        try {
            DefaultResourceLoader defaultResourceLoader = new DefaultResourceLoader();
            DefaultResourceLoader resourceLoader = defaultResourceLoader;
            SortedResourcesFactoryBean factory = new SortedResourcesFactoryBean(resourceLoader,
                    Collections.singletonList(location));
            factory.afterPropertiesSet();
            return factory.getObject();
        }
        catch (Exception ex) {
            throw new IllegalStateException("Unable to load resources from " + location, ex);
        }
    }

}

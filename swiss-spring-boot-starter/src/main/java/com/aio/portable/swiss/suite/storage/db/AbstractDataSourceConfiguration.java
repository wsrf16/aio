package com.aio.portable.swiss.suite.storage.db;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

public abstract class AbstractDataSourceConfiguration {
    protected DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    protected static final <T extends DataSource> T createDataSource(DataSourceProperties properties, Class<T> clazz) {
        return properties
                .initializeDataSourceBuilder()
                .type(clazz)
                .build();
    }

    protected static final DataSource createDataSource(DataSourceProperties properties) {
        return properties
                .initializeDataSourceBuilder()
                .build();
    }

//    protected DataSource dataSource(DataSourceProperties properties) {
////        return DruidDataSourceBuilder.create().build();
//        // jdbc-url
////        return DataSourceBuilder.create().build();
//        // url
//        return properties
//                .initializeDataSourceBuilder()
//                .type(HikariDataSource.class)
//                .build();
//    }

    protected DataSourceTransactionManager dataPlatformTransactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    protected DataSourceInitializer dataSourceInitializer(
            DataSource dataSource,
            DataSourceProperties dataSourceProperties) {
        ResourceDatabasePopulator populator = DataSourceSugar.newDatabasePopulator(dataSourceProperties);

        DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
        dataSourceInitializer.setDataSource(dataSource);
        dataSourceInitializer.setDatabasePopulator(populator);
        return dataSourceInitializer;
    }
}

package com.aio.portable.swiss.data.freedatasource.config;

import com.aio.portable.swiss.data.multidatasource.TtemplateDataSourceConfiguration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

//@Configuration
@EnableJpaRepositories(basePackages = {JpaTemplateDataSourceConfiguration.BASE_PACKAGES},
        entityManagerFactoryRef = JpaTemplateDataSourceConfiguration.LOCAL_CONTAINER_ENTITY_MANAGER_FACTORY_BEAN,
        transactionManagerRef = JpaTemplateDataSourceConfiguration.PLATFORM_TRANSACTION_MANAGER_BEAN)
public class JpaTemplateDataSourceConfiguration extends TtemplateDataSourceConfiguration {
    public static final String BASE_PACKAGES = "com.aio.portable.park.parkdb.dao.master.mapper";
    private static final String SPECIAL_NAME = "master";

    protected static final String DATA_SOURCE_PREFIX = "spring.datasource." + SPECIAL_NAME;
    protected static final String DATA_SOURCE_BEAN = SPECIAL_NAME + "DataSource";
    protected static final String PLATFORM_TRANSACTION_MANAGER_BEAN = SPECIAL_NAME + "PlatformTransactionManager";
    protected static final String LOCAL_CONTAINER_ENTITY_MANAGER_FACTORY_BEAN = SPECIAL_NAME + "LocalContainerEntityManagerFactoryBean";

    //    protected static final String JDBC_PREFIX = DATA_SOURCE_PREFIX + ".jdbc";
    protected static final String DATA_SOURCE_PROPERTIES_BEAN = SPECIAL_NAME + "DataSourceProperties";

    @Bean(DATA_SOURCE_PROPERTIES_BEAN)
    @Primary
    @ConfigurationProperties(prefix = DATA_SOURCE_PREFIX)
    public DataSourceProperties dataSourceProperties() {
        return super.dataSourceProperties();
    }

    @ConditionalOnProperty(prefix = DATA_SOURCE_PREFIX, value = "url")
    @ConfigurationProperties(prefix = DATA_SOURCE_PREFIX)
    @Bean(DATA_SOURCE_BEAN)
    @Primary
    public DataSource dataSource(@Qualifier(DATA_SOURCE_PROPERTIES_BEAN)DataSourceProperties properties) throws ClassNotFoundException {
        return super.dataSource(properties);
    }


    @ConditionalOnBean(name = DATA_SOURCE_BEAN)
    @Bean(PLATFORM_TRANSACTION_MANAGER_BEAN)
    public PlatformTransactionManager platformTransactionManager(@Qualifier(DATA_SOURCE_BEAN)DataSource dataSource) {
        return super.platformTransactionManager(dataSource);
    }

    @ConditionalOnBean(name = DATA_SOURCE_BEAN)
    @Bean(LOCAL_CONTAINER_ENTITY_MANAGER_FACTORY_BEAN)
    public LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean(@Qualifier(DATA_SOURCE_BEAN)DataSource dataSource) {
        return super.localContainerEntityManagerFactoryBean(dataSource);
    }
}

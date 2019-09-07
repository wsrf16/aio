package com.aio.portable.swiss.data.multidatasource;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

//@Configuration
//@EnableJpaRepositories(basePackages = {TemplateDataSourceConfiguration.REPOSITORY_BASE_PACKAGES}, entityManagerFactoryRef = TemplateDataSourceConfiguration.LOCAL_CONTAINER_ENTITY_MANAGER_FACTORY_BEAN, transactionManagerRef = TemplateDataSourceConfiguration.PLATFORM_TRANSACTION_MANAGER_BEAN)
@ConditionalOnClass({DataSource.class, EmbeddedDatabaseType.class})
public abstract class JpaBaseDataSourceConfiguration {
    public final static String REPOSITORY_BASE_PACKAGES = null; //"com.aio.portable.parkdb.dao.third.mapper";
    public final static String ENTITY_BASE_PACKAGES = null; //"com.aio.portable.parkdb.dao.third.model";
    private final static String SPECIAL_NAME = StringUtils.EMPTY; //"third";
    private final static String PERSISTENCE_UNIT = StringUtils.EMPTY; //"persistenceUnit";

    protected final static String DATA_SOURCE_PREFIX = "spring.datasource." + SPECIAL_NAME;
    protected final static String DATA_SOURCE_BEAN = SPECIAL_NAME + "DataSource";
    protected final static String PLATFORM_TRANSACTION_MANAGER_BEAN = SPECIAL_NAME + "PlatformTransactionManager";
    protected final static String LOCAL_CONTAINER_ENTITY_MANAGER_FACTORY_BEAN = SPECIAL_NAME + "LocalContainerEntityManagerFactoryBean";

    //    protected final static String JDBC_PREFIX = DATA_SOURCE_PREFIX + ".jdbc";
    protected final static String DATA_SOURCE_PROPERTIES_BEAN = SPECIAL_NAME + "DataSourceProperties";
    protected final static String ENTITY_MANAGER_BEAN = SPECIAL_NAME + "EntityManager";


//    @ConditionalOnProperty(prefix = DATA_SOURCE_PREFIX, value = "url")
//    @Bean(DATA_SOURCE_PROPERTIES_BEAN)
//    @Primary
//    @ConfigurationProperties(prefix = DATA_SOURCE_PREFIX)
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

//    @ConditionalOnBean(name = DATA_SOURCE_PROPERTIES_BEAN)
//    @Bean(DATA_SOURCE_BEAN)
//    @Primary
    public DataSource dataSource(@Qualifier(DATA_SOURCE_PROPERTIES_BEAN)DataSourceProperties properties) throws ClassNotFoundException {
        Class<? extends DataSource> clazz = HikariDataSource.class;
        return properties.initializeDataSourceBuilder().type(clazz).build();
//        return DataSourceBuilder.create()
//                .driverClassName(properties.getDriverClassName())
//                .url(properties.getUrl())
    }

//    @ConditionalOnBean(name = DATA_SOURCE_BEAN)
//    @Bean(LOCAL_CONTAINER_ENTITY_MANAGER_FACTORY_BEAN)
    public LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean(@Qualifier(DATA_SOURCE_BEAN)DataSource dataSource, EntityManagerFactoryBuilder builder, JpaProperties jpaProperties, String entityBasePackages, String persistenceUnit) {
        LocalContainerEntityManagerFactoryBean factory = builder
                .dataSource(dataSource)
                .properties(jpaProperties.getProperties())
                .packages(entityBasePackages)
                .persistenceUnit(persistenceUnit)
                .build();
        return factory;
    }

//    @ConditionalOnBean(name = LOCAL_CONTAINER_ENTITY_MANAGER_FACTORY_BEAN)
//    @Bean(name = ENTITY_MANAGER_BEAN)
    public EntityManager entityManager(@Qualifier(LOCAL_CONTAINER_ENTITY_MANAGER_FACTORY_BEAN) LocalContainerEntityManagerFactoryBean factory) {
        return factory.getObject().createEntityManager();
    }

//    @ConditionalOnBean(name = DATA_SOURCE_BEAN)
//    @Bean(PLATFORM_TRANSACTION_MANAGER_BEAN)
    public DataSourceTransactionManager dataPlatformTransactionManager(@Qualifier(DATA_SOURCE_BEAN)DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

//    @ConditionalOnBean(name = LOCAL_CONTAINER_ENTITY_MANAGER_FACTORY_BEAN)
//    @Bean(PLATFORM_TRANSACTION_MANAGER_BEAN)
    public JpaTransactionManager jpaPlatformTransactionManager(@Qualifier(LOCAL_CONTAINER_ENTITY_MANAGER_FACTORY_BEAN) LocalContainerEntityManagerFactoryBean factory) {
        return new JpaTransactionManager(factory.getObject());
    }
}

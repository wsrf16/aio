package com.aio.portable.park.config.db;

import com.aio.portable.swiss.structure.database.jpa.multidatasource.JpaBaseDataSourceConfiguration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(basePackages = {ThirdDataSourceConfiguration.REPOSITORY_BASE_PACKAGES}, entityManagerFactoryRef = ThirdDataSourceConfiguration.LOCAL_CONTAINER_ENTITY_MANAGER_FACTORY_BEAN, transactionManagerRef = ThirdDataSourceConfiguration.PLATFORM_TRANSACTION_MANAGER_BEAN)
@ConditionalOnClass({DataSource.class, EmbeddedDatabaseType.class})
//@Import(HibernateJpaAutoConfiguration.class)
public class ThirdDataSourceConfiguration extends JpaBaseDataSourceConfiguration {
    public final static String REPOSITORY_BASE_PACKAGES = "com.aio.portable.parkdb.dao.third.mapper";
    public final static String ENTITY_BASE_PACKAGES = "com.aio.portable.parkdb.dao.third.model";
    private final static String SPECIAL_NAME = "third";
    private final static String PERSISTENCE_UNIT = "persistenceUnit";

    protected final static String DATA_SOURCE_PREFIX = "spring.datasource." + SPECIAL_NAME;
    protected final static String JPA_PREFIX = DATA_SOURCE_PREFIX + ".jpa";

    protected final static String DATA_SOURCE_BEAN = SPECIAL_NAME + "DataSource";
    protected final static String PLATFORM_TRANSACTION_MANAGER_BEAN = SPECIAL_NAME + "PlatformTransactionManager";
    protected final static String LOCAL_CONTAINER_ENTITY_MANAGER_FACTORY_BEAN = SPECIAL_NAME + "LocalContainerEntityManagerFactoryBean";

    protected final static String DATA_SOURCE_PROPERTIES_BEAN = SPECIAL_NAME + "DataSourceProperties";
    protected final static String JPA_PROPERTIES_BEAN = SPECIAL_NAME + "JpaProperties";
    protected final static String ENTITY_MANAGER_BEAN = SPECIAL_NAME + "EntityManager";


    @ConditionalOnProperty(prefix = DATA_SOURCE_PREFIX, value = "url")
    @Bean(DATA_SOURCE_PROPERTIES_BEAN)
    @ConfigurationProperties(prefix = DATA_SOURCE_PREFIX)
    @Primary
    public DataSourceProperties dataSourceProperties() {
        return super.dataSourceProperties();
    }

    @ConditionalOnBean(name = DATA_SOURCE_PROPERTIES_BEAN)
    @Bean(JPA_PROPERTIES_BEAN)
    @ConfigurationProperties(prefix = JPA_PREFIX)
    @Primary
    public JpaProperties jpaProperties() {
        return super.jpaProperties();
    }

    @ConditionalOnBean(name = DATA_SOURCE_PROPERTIES_BEAN)
    @Bean(DATA_SOURCE_BEAN)
//    @Primary
    public DataSource dataSource(@Qualifier(DATA_SOURCE_PROPERTIES_BEAN)DataSourceProperties properties) throws ClassNotFoundException {
        return super.dataSource(properties);
    }

    @ConditionalOnBean(name = DATA_SOURCE_BEAN)
    @Bean(LOCAL_CONTAINER_ENTITY_MANAGER_FACTORY_BEAN)
    public LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean(@Qualifier(DATA_SOURCE_BEAN)DataSource dataSource, EntityManagerFactoryBuilder builder, @Qualifier(JPA_PROPERTIES_BEAN)JpaProperties jpaProperties) {
        return super.localContainerEntityManagerFactoryBean(dataSource, builder, jpaProperties, ENTITY_BASE_PACKAGES, PERSISTENCE_UNIT);
    }

    @ConditionalOnBean(name = LOCAL_CONTAINER_ENTITY_MANAGER_FACTORY_BEAN)
    @Bean(name = ENTITY_MANAGER_BEAN)
    public EntityManager entityManager(@Qualifier(LOCAL_CONTAINER_ENTITY_MANAGER_FACTORY_BEAN) LocalContainerEntityManagerFactoryBean factory) {
        return super.entityManager(factory);
    }

//    @ConditionalOnBean(name = DATA_SOURCE_BEAN)
//    @Bean(PLATFORM_TRANSACTION_MANAGER_BEAN)
//    public PlatformTransactionManager platformTransactionManager(@Qualifier(DATA_SOURCE_BEAN)DataSource dataSource) {
//        return super.dataPlatformTransactionManager(dataSource);
//    }

    @ConditionalOnBean(name = LOCAL_CONTAINER_ENTITY_MANAGER_FACTORY_BEAN)
    @Bean(PLATFORM_TRANSACTION_MANAGER_BEAN)
    public PlatformTransactionManager platformTransactionManager(@Qualifier(LOCAL_CONTAINER_ENTITY_MANAGER_FACTORY_BEAN) LocalContainerEntityManagerFactoryBean factory) {
        return super.jpaPlatformTransactionManager(factory);
    }
}

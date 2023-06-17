//package com.aio.portable.swiss.suite.storage.db.jpa.multidatasource;
//
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
//import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import javax.persistence.EntityManager;
//import javax.sql.DataSource;
//
////@Configuration
////@EnableJpaRepositories(basePackages = {JpaTemplateDataSourceConfiguration.REPOSITORY_BASE_PACKAGES}, entityManagerFactoryRef = JpaTemplateDataSourceConfiguration.LOCAL_CONTAINER_ENTITY_MANAGER_FACTORY_BEAN, transactionManagerRef = JpaTemplateDataSourceConfiguration.PLATFORM_TRANSACTION_MANAGER_BEAN)
//@ConditionalOnClass({DataSource.class, EmbeddedDatabaseType.class})
//class JpaTemplateDataSourceConfiguration extends JpaBaseDataSourceConfiguration {
//    public static final String REPOSITORY_BASE_PACKAGES = "com.aio.portable.park.dao.third.mapper";
//    public static final String ENTITY_BASE_PACKAGES = "com.aio.portable.park.dao.third.model";
//    private static final String SPECIAL_NAME = "third";
//    private static final String PERSISTENCE_UNIT = "persistenceUnit";
//
//    protected static final String DATA_SOURCE_PREFIX = "spring.datasource." + SPECIAL_NAME;
//    protected static final String JPA_PREFIX = DATA_SOURCE_PREFIX + ".jpa";
//
//    protected static final String DATA_SOURCE_BEAN = SPECIAL_NAME + "DataSource";
//    protected static final String PLATFORM_TRANSACTION_MANAGER_BEAN = SPECIAL_NAME + "PlatformTransactionManager";
//    protected static final String LOCAL_CONTAINER_ENTITY_MANAGER_FACTORY_BEAN = SPECIAL_NAME + "LocalContainerEntityManagerFactoryBean";
//
//    protected static final String DATA_SOURCE_PROPERTIES_BEAN = SPECIAL_NAME + "DataSourceProperties";
//    protected static final String JPA_PROPERTIES_BEAN = SPECIAL_NAME + "JpaProperties";
//    protected static final String ENTITY_MANAGER_BEAN = SPECIAL_NAME + "EntityManager";
//
//
//    @ConditionalOnProperty(prefix = DATA_SOURCE_PREFIX, value = "url")
//    @Bean(DATA_SOURCE_PROPERTIES_BEAN)
//    @ConfigurationProperties(prefix = DATA_SOURCE_PREFIX)
////    @Primary
//    public DataSourceProperties dataSourceProperties() {
//        return super.dataSourceProperties();
//    }
//
//    @ConditionalOnBean(name = DATA_SOURCE_PROPERTIES_BEAN)
//    @Bean(JPA_PROPERTIES_BEAN)
//    @ConfigurationProperties(prefix = JPA_PREFIX)
////    @Primary
//    public JpaProperties jpaProperties() {
//        return super.jpaProperties();
//    }
//
//    @ConditionalOnBean(name = DATA_SOURCE_PROPERTIES_BEAN)
//    @Bean(DATA_SOURCE_BEAN)
////    @Primary
//    public DataSource dataSource(@Qualifier(DATA_SOURCE_PROPERTIES_BEAN)DataSourceProperties properties) throws ClassNotFoundException {
//        return super.dataSource(properties);
//    }
//
//
//    @ConditionalOnBean(name = DATA_SOURCE_BEAN)
//    @Bean(LOCAL_CONTAINER_ENTITY_MANAGER_FACTORY_BEAN)
//    public LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean(@Qualifier(DATA_SOURCE_BEAN)DataSource dataSource, EntityManagerFactoryBuilder builder, JpaProperties jpaProperties) {
//        return super.localContainerEntityManagerFactoryBean(dataSource, builder, jpaProperties, ENTITY_BASE_PACKAGES, PERSISTENCE_UNIT);
//    }
//
//    @ConditionalOnBean(name = LOCAL_CONTAINER_ENTITY_MANAGER_FACTORY_BEAN)
//    @Bean(name = ENTITY_MANAGER_BEAN)
//    public EntityManager entityManager(@Qualifier(LOCAL_CONTAINER_ENTITY_MANAGER_FACTORY_BEAN) LocalContainerEntityManagerFactoryBean factory) {
//        return super.entityManager(factory);
//    }
//
////    @ConditionalOnBean(name = DATA_SOURCE_BEAN)
////    @Bean(PLATFORM_TRANSACTION_MANAGER_BEAN)
////    public PlatformTransactionManager platformTransactionManager(@Qualifier(DATA_SOURCE_BEAN)DataSource dataSource) {
////        return super.dataPlatformTransactionManager(dataSource);
////    }
//
//    @ConditionalOnBean(name = LOCAL_CONTAINER_ENTITY_MANAGER_FACTORY_BEAN)
//    @Bean(PLATFORM_TRANSACTION_MANAGER_BEAN)
//    public PlatformTransactionManager platformTransactionManager(@Qualifier(LOCAL_CONTAINER_ENTITY_MANAGER_FACTORY_BEAN) LocalContainerEntityManagerFactoryBean factory) {
//        return super.jpaPlatformTransactionManager(factory);
//    }
//}

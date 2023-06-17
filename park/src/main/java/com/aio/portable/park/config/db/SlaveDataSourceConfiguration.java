package com.aio.portable.park.config.db;

import com.aio.portable.swiss.suite.storage.db.jpa.multidatasource.JpaBaseDataSourceConfiguration;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.orm.jpa.EntityManagerFactoryBuilderCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitManager;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {SlaveDataSourceConfiguration.REPOSITORY_BASE_PACKAGES}, entityManagerFactoryRef = SlaveDataSourceConfiguration.LOCAL_CONTAINER_ENTITY_MANAGER_FACTORY_BEAN, transactionManagerRef = SlaveDataSourceConfiguration.PLATFORM_TRANSACTION_MANAGER_BEAN)
@ConditionalOnClass({DataSource.class, EmbeddedDatabaseType.class})
@ConditionalOnProperty(prefix = SlaveDataSourceConfiguration.DATA_SOURCE_PREFIX, name = "url")
//@Import(HibernateJpaAutoConfiguration.class)
public class SlaveDataSourceConfiguration extends JpaBaseDataSourceConfiguration {
    public static final String REPOSITORY_BASE_PACKAGES = "com.aio.portable.park.dao.slave.repository";
    public static final String ENTITY_BASE_PACKAGES = "com.aio.portable.park.dao.slave.model";
    private static final String SPECIAL_NAME = "slave";
    private static final String PERSISTENCE_UNIT = "persistenceUnit";

    protected static final String DATA_SOURCE_PREFIX = "spring.datasource." + SPECIAL_NAME;
    protected static final String JPA_PREFIX = DATA_SOURCE_PREFIX + ".jpa";

    protected static final String DATA_SOURCE_BEAN = SPECIAL_NAME + "DataSource";
    protected static final String JPA_VENDOR_ADAPTER_BEAN = SPECIAL_NAME + "JpaVendorAdapter";
    protected static final String ENTITY_MANAGER_FACTORY_BUILDER_BEAN = SPECIAL_NAME + "EntityManagerFactoryBuilder";

    protected static final String PLATFORM_TRANSACTION_MANAGER_BEAN = SPECIAL_NAME + "PlatformTransactionManager";
    protected static final String LOCAL_CONTAINER_ENTITY_MANAGER_FACTORY_BEAN = SPECIAL_NAME + "LocalContainerEntityManagerFactoryBean";

    protected static final String DATA_SOURCE_PROPERTIES_BEAN = SPECIAL_NAME + "DataSourceProperties";
    protected static final String JPA_PROPERTIES_BEAN = SPECIAL_NAME + "JpaProperties";
    protected static final String ENTITY_MANAGER_BEAN = SPECIAL_NAME + "EntityManager";
    private static final String DATA_SOURCE_INITIALIZER_BEAN = SPECIAL_NAME + "DataSourceInitializer";


    @ConditionalOnProperty(prefix = DATA_SOURCE_PREFIX, value = "url")
    @Bean(DATA_SOURCE_PROPERTIES_BEAN)
    @ConfigurationProperties(prefix = DATA_SOURCE_PREFIX)
    public DataSourceProperties dataSourceProperties() {
        return super.dataSourceProperties();
    }

    @Bean(JPA_PROPERTIES_BEAN)
    @ConfigurationProperties(prefix = JPA_PREFIX)
    public JpaProperties jpaProperties() {
        return super.jpaProperties();
    }

    @Bean(DATA_SOURCE_BEAN)
    @ConfigurationProperties(prefix = DATA_SOURCE_PREFIX + ".hikari")
    public DataSource dataSource(@Qualifier(DATA_SOURCE_PROPERTIES_BEAN)DataSourceProperties properties) {
        return super.dataSource(properties);
    }

    @Bean(JPA_VENDOR_ADAPTER_BEAN)
    public JpaVendorAdapter jpaVendorAdapter(
            @Qualifier(JPA_PROPERTIES_BEAN)JpaProperties jpaProperties,
            @Qualifier(DATA_SOURCE_BEAN)DataSource dataSource) {
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setShowSql(jpaProperties.isShowSql());
        adapter.setDatabase(jpaProperties.determineDatabase(dataSource));
        adapter.setDatabasePlatform(jpaProperties.getDatabasePlatform());
        adapter.setGenerateDdl(jpaProperties.isGenerateDdl());
        return adapter;
    }

    @Bean(ENTITY_MANAGER_FACTORY_BUILDER_BEAN)
    public EntityManagerFactoryBuilder entityManagerFactoryBuilder(
            JpaVendorAdapter jpaVendorAdapter,
            ObjectProvider<PersistenceUnitManager> persistenceUnitManager,
            ObjectProvider<EntityManagerFactoryBuilderCustomizer> customizers,
            @Qualifier(JPA_PROPERTIES_BEAN)JpaProperties jpaProperties) {
        EntityManagerFactoryBuilder builder = new EntityManagerFactoryBuilder(jpaVendorAdapter,
                jpaProperties.getProperties(), persistenceUnitManager.getIfAvailable());
        customizers.orderedStream().forEach((customizer) -> customizer.customize(builder));
        return builder;
    }

    @Bean(LOCAL_CONTAINER_ENTITY_MANAGER_FACTORY_BEAN)
    public LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean(@Qualifier(DATA_SOURCE_BEAN)DataSource dataSource, EntityManagerFactoryBuilder builder, @Qualifier(JPA_PROPERTIES_BEAN)JpaProperties jpaProperties) {
        return super.localContainerEntityManagerFactoryBean(dataSource, builder, jpaProperties, ENTITY_BASE_PACKAGES, PERSISTENCE_UNIT);
    }

    @Bean(name = ENTITY_MANAGER_BEAN)
    public EntityManager entityManager(@Qualifier(LOCAL_CONTAINER_ENTITY_MANAGER_FACTORY_BEAN) LocalContainerEntityManagerFactoryBean factory) {
        return super.entityManager(factory);
    }

    @Bean(PLATFORM_TRANSACTION_MANAGER_BEAN)
    public JpaTransactionManager jpaTransactionManager(@Qualifier(LOCAL_CONTAINER_ENTITY_MANAGER_FACTORY_BEAN) LocalContainerEntityManagerFactoryBean factory) {
        return super.jpaPlatformTransactionManager(factory);
    }

    @Bean(DATA_SOURCE_INITIALIZER_BEAN)
    public DataSourceInitializer dataSourceInitializer(
            @Qualifier(DATA_SOURCE_BEAN)DataSource dataSource,
            @Qualifier(DATA_SOURCE_PROPERTIES_BEAN) DataSourceProperties dataSourceProperties) {
        return super.dataSourceInitializer(dataSource, dataSourceProperties);
    }
}

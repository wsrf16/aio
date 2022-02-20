package com.aio.portable.swiss.suite.storage.db.jpa.multidatasource;

import com.aio.portable.swiss.global.Constant;
import com.aio.portable.swiss.suite.storage.db.AbstractDataSourceConfiguration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

//@Configuration
//@EnableJpaRepositories(basePackages = {TemplateDataSourceConfiguration.REPOSITORY_BASE_PACKAGES}, entityManagerFactoryRef = TemplateDataSourceConfiguration.LOCAL_CONTAINER_ENTITY_MANAGER_FACTORY_BEAN, transactionManagerRef = TemplateDataSourceConfiguration.PLATFORM_TRANSACTION_MANAGER_BEAN)
//@EntityScan(basePackages = {TemplateDataSourceConfiguration.ENTITY_BASE_PACKAGES})
@ConditionalOnClass({DataSource.class, EmbeddedDatabaseType.class})
public abstract class JpaBaseDataSourceConfiguration extends AbstractDataSourceConfiguration {
    // custom
    public static final String REPOSITORY_BASE_PACKAGES = null; //"com.aio.portable.parkdb.dao.third.mapper";
    public static final String ENTITY_BASE_PACKAGES = null; //"com.aio.portable.parkdb.dao.third.model";
    private static final String SPECIAL_NAME = Constant.EMPTY; //"third";

    // constant
    public static final String PERSISTENCE_UNIT = "persistenceUnit";

    // properties
    protected static final String DATA_SOURCE_PREFIX = "spring.datasource." + SPECIAL_NAME;
    protected static final String JPA_PREFIX = DATA_SOURCE_PREFIX + ".jpa";

    // bean
    protected static final String DATA_SOURCE_BEAN = SPECIAL_NAME + "DataSource";
    protected static final String PLATFORM_TRANSACTION_MANAGER_BEAN = SPECIAL_NAME + "PlatformTransactionManager";
    protected static final String LOCAL_CONTAINER_ENTITY_MANAGER_FACTORY_BEAN = SPECIAL_NAME + "LocalContainerEntityManagerFactoryBean";
    protected static final String DATA_SOURCE_PROPERTIES_BEAN = SPECIAL_NAME + "DataSourceProperties";
    protected static final String JPA_PROPERTIES_BEAN = SPECIAL_NAME + "JpaProperties";
    protected static final String ENTITY_MANAGER_BEAN = SPECIAL_NAME + "EntityManager";



    public JpaProperties jpaProperties() {
        return new JpaProperties();
    }


    public LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean(@Qualifier(DATA_SOURCE_BEAN)DataSource dataSource, EntityManagerFactoryBuilder builder, JpaProperties jpaProperties, String entityBasePackages, String persistenceUnit) {
//        org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration
//        map.put("hibernate.naming.physical-strategy","org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy");
        LocalContainerEntityManagerFactoryBean factory = builder
                .dataSource(dataSource)
                .properties(jpaProperties.getProperties())
                .packages(StringUtils.tokenizeToStringArray(entityBasePackages, ","))
                .persistenceUnit(persistenceUnit)
                .build();
        return factory;
    }

    public EntityManager entityManager(@Qualifier(LOCAL_CONTAINER_ENTITY_MANAGER_FACTORY_BEAN) LocalContainerEntityManagerFactoryBean factory) {
        return factory.getObject().createEntityManager();
    }



    public JpaTransactionManager jpaPlatformTransactionManager(@Qualifier(LOCAL_CONTAINER_ENTITY_MANAGER_FACTORY_BEAN) LocalContainerEntityManagerFactoryBean factory) {
        return new JpaTransactionManager(factory.getObject());
    }
}

package com.aio.portable.park.config.db;

import com.aio.portable.swiss.suite.storage.db.DataSourceSugar;
import com.aio.portable.swiss.suite.storage.db.mybatis.multidatasource.MybatisBaseDataSourceConfiguration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = {MasterDataSourceConfiguration.BASE_PACKAGES}, sqlSessionTemplateRef = MasterDataSourceConfiguration.SQL_SESSION_TEMPLATE_BEAN)
public class MasterDataSourceConfiguration extends MybatisBaseDataSourceConfiguration {
    public static final String BASE_PACKAGES = "com.aio.portable.parkdb.dao.master.mapper";
    private static final String SPECIAL_NAME = "master";

    private static final String DATA_SOURCE_PREFIX = "spring.datasource." + SPECIAL_NAME;
    public static final String SQL_SESSION_TEMPLATE_BEAN = SPECIAL_NAME + "SQLSessionTemplate";
    private static final String DATA_SOURCE_BEAN = SPECIAL_NAME + "DataSource";
    private static final String SQL_SESSION_FACTORY_BEAN = SPECIAL_NAME + "SQLSessionFactory";
    private static final String PLATFORM_TRANSACTION_MANAGER_BEAN = SPECIAL_NAME + "PlatformTransactionManager";

    protected static final String DATA_SOURCE_PROPERTIES_BEAN = SPECIAL_NAME + "DataSourceProperties";
    private static final String MYBATIS_PREFIX = DATA_SOURCE_PREFIX + ".mybatis";
    private static final String MYBATIS_PROPERTIES_BEAN = SPECIAL_NAME + "MybatisProperties";
    protected static final String DATA_SOURCE_INITIALIZER_BEAN = SPECIAL_NAME + "DataSourceInitializer";

    @ConditionalOnProperty(prefix = DATA_SOURCE_PREFIX, value = "url")
    @ConfigurationProperties(prefix = DATA_SOURCE_PREFIX)
    @Bean(DATA_SOURCE_PROPERTIES_BEAN)
    public DataSourceProperties dataSourceProperties() {
        return super.dataSourceProperties();
    }

    @ConfigurationProperties(prefix = MYBATIS_PREFIX)
    @Bean(MYBATIS_PROPERTIES_BEAN)
    public MybatisProperties mybatisProperties() {
        return super.mybatisProperties();
    }

    @Bean(DATA_SOURCE_BEAN)
    public DataSource dataSource(
            @Qualifier(DATA_SOURCE_PROPERTIES_BEAN)DataSourceProperties dataSourceProperties) {
        return super.dataSource(dataSourceProperties);
    }

    @Bean(SQL_SESSION_FACTORY_BEAN)
    public SqlSessionFactory sqlSessionFactory(
            @Qualifier(DATA_SOURCE_BEAN)DataSource dataSource,
            @Qualifier(MYBATIS_PROPERTIES_BEAN)MybatisProperties mybatisProperties) throws Exception {
        return super.sqlSessionFactory(dataSource, mybatisProperties);
    }

    @Bean(SQL_SESSION_TEMPLATE_BEAN)
    public SqlSessionTemplate sqlSessionTemplate(
            @Qualifier(SQL_SESSION_FACTORY_BEAN)SqlSessionFactory sqlSessionFactory,
            @Qualifier(MYBATIS_PROPERTIES_BEAN)MybatisProperties properties) throws Exception {
        return super.sqlSessionTemplate(sqlSessionFactory, properties);
    }

    @Bean(PLATFORM_TRANSACTION_MANAGER_BEAN)
    public PlatformTransactionManager platformTransactionManager(
            @Qualifier(DATA_SOURCE_BEAN)DataSource dataSource) {
        return super.dataPlatformTransactionManager(dataSource);
    }

    @Bean(DATA_SOURCE_INITIALIZER_BEAN)
    public DataSourceInitializer dataSourceInitializer(
            @Qualifier(DATA_SOURCE_BEAN)DataSource dataSource,
            @Qualifier(DATA_SOURCE_PROPERTIES_BEAN) DataSourceProperties dataSourceProperties) {
        return super.dataSourceInitializer(dataSource, dataSourceProperties);
    }



}

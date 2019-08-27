package com.york.portable.park.config.mybatis;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.york.portable.swiss.data.freedatasource.config.BaseDataSourceConfiguration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

//@Configuration
@MapperScan(basePackages = {SlaveDataSourceConfiguration.BASE_PACKAGES}, sqlSessionTemplateRef = SlaveDataSourceConfiguration.SQL_SESSION_TEMPLATE_BEAN)
public class SlaveDataSourceConfiguration extends BaseDataSourceConfiguration {
    public static final String BASE_PACKAGES = "com.york.portable.park.parkdb.dao.slave.mapper";
    private static final String SPECIAL_NAME = "slave";

    private static final String DATA_SOURCE_PREFIX = "spring.datasource." + SPECIAL_NAME;
    public static final String SQL_SESSION_TEMPLATE_BEAN = SPECIAL_NAME + "SQLSessionTemplate";
    private static final String DATA_SOURCE_BEAN = SPECIAL_NAME + "DataSource";
    private static final String SQL_SESSION_FACTORY_BEAN = SPECIAL_NAME + "SQLSessionFactory";
    private static final String PLATFORM_TRANSACTION_MANAGER_BEAN = SPECIAL_NAME + "PlatformTransactionManager";

    private static final String MYBATIS_PREFIX = DATA_SOURCE_PREFIX + ".mybatis";
    private static final String MYBATIS_PROPERTIES_BEAN = SPECIAL_NAME + "MybatisProperties";

    @Bean(MYBATIS_PROPERTIES_BEAN)
    @ConfigurationProperties(prefix = MYBATIS_PREFIX)
    public MybatisProperties mybatisProperties() {
//        this.properties = new MybatisProperties();
        return new MybatisProperties();
    }

    @Bean(DATA_SOURCE_BEAN)
    @ConfigurationProperties(prefix = DATA_SOURCE_PREFIX)
    @ConditionalOnProperty(prefix = DATA_SOURCE_PREFIX, value = "url")
    @ConditionalOnClass(DruidDataSourceBuilder.class)
    public DataSource dataSource() {
        return super.dataSource();
    }

    @ConditionalOnBean(name = DATA_SOURCE_BEAN)
    @Bean(SQL_SESSION_FACTORY_BEAN)
    public SqlSessionFactory sqlSessionFactory(@Qualifier(DATA_SOURCE_BEAN)DataSource dataSource, @Qualifier(MYBATIS_PROPERTIES_BEAN) MybatisProperties properties) throws Exception {
        return super.sqlSessionFactory(dataSource, properties);
    }

    @ConditionalOnBean(name = SQL_SESSION_FACTORY_BEAN)
    @Bean(SQL_SESSION_TEMPLATE_BEAN)
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier(SQL_SESSION_FACTORY_BEAN) SqlSessionFactory sqlSessionFactory, @Qualifier(MYBATIS_PROPERTIES_BEAN) MybatisProperties properties) throws Exception {
        return super.sqlSessionTemplate(sqlSessionFactory, properties);
    }

    @ConditionalOnBean(name = DATA_SOURCE_BEAN)
    @Bean(PLATFORM_TRANSACTION_MANAGER_BEAN)
    public PlatformTransactionManager platformTransactionManager(@Qualifier(DATA_SOURCE_BEAN)DataSource dataSource) {
        return super.platformTransactionManager(dataSource);
    }
}
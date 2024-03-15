package com.aio.portable.park.config.db;

import com.aio.portable.swiss.suite.storage.db.mybatis.multidatasource.MybatisBaseDataSourceConfiguration;
import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.toolkit.GlobalConfigUtils;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;

// DataSourceAutoConfiguration
// org.springframework.boot.autoconfigure.jdbc.DataSourceConfiguration
@Configuration
@MapperScan(basePackages = {MasterDataSourceConfiguration.BASE_PACKAGES}, sqlSessionTemplateRef = MasterDataSourceConfiguration.SQL_SESSION_TEMPLATE_BEAN)
@ConditionalOnProperty(prefix = MasterDataSourceConfiguration.DATA_SOURCE_PREFIX, name = "url")
public class MasterDataSourceConfiguration extends MybatisBaseDataSourceConfiguration {
    protected static final String BASE_PACKAGES = "com.aio.portable.park.dao.master.mapper";
    protected static final String SPECIAL_NAME = "master";

    protected static final String DATA_SOURCE_PREFIX = "spring.datasource." + SPECIAL_NAME;
    protected static final String MYBATIS_PREFIX = DATA_SOURCE_PREFIX + ".mybatis";

    protected static final String DATA_SOURCE_PROPERTIES_BEAN = SPECIAL_NAME + "DataSourceProperties";
    protected static final String DATA_SOURCE_BEAN = SPECIAL_NAME + "DataSource";
    protected static final String SQL_SESSION_TEMPLATE_BEAN = SPECIAL_NAME + "SQLSessionTemplate";
    protected static final String SQL_SESSION_FACTORY_BEAN = SPECIAL_NAME + "SQLSessionFactory";
    protected static final String PLATFORM_TRANSACTION_MANAGER_BEAN = SPECIAL_NAME + "PlatformTransactionManager";
    protected static final String MYBATIS_PROPERTIES_BEAN = SPECIAL_NAME + "MybatisProperties";
    protected static final String MYBATIS_PLUS_PROPERTIES_BEAN = SPECIAL_NAME + "MybatisPlusProperties";
    protected static final String DATA_SOURCE_INITIALIZER_BEAN = SPECIAL_NAME + "DataSourceInitializer";

    @ConditionalOnProperty(prefix = DATA_SOURCE_PREFIX, value = "url")
    @Bean(DATA_SOURCE_PROPERTIES_BEAN)
    @ConfigurationProperties(prefix = DATA_SOURCE_PREFIX)
    public DataSourceProperties dataSourceProperties() {
        return super.dataSourceProperties();
    }

    @ConfigurationProperties(prefix = MYBATIS_PREFIX)
    @Bean(MYBATIS_PROPERTIES_BEAN)
    @ConditionalOnMissingClass("com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties")
    public MybatisProperties mybatisProperties() {
        return super.mybatisProperties();
    }

    @ConfigurationProperties(prefix = MYBATIS_PREFIX)
    @Bean(MYBATIS_PLUS_PROPERTIES_BEAN)
    @ConditionalOnClass(name = "com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties")
    public MybatisPlusProperties mybatisPlusProperties() {
        return super.mybatisPlusProperties();
    }

    @Configuration
    @ConditionalOnMissingBean(value = {DataSource.class}, name = DATA_SOURCE_BEAN)
    @ConditionalOnProperty(
            prefix = DATA_SOURCE_PREFIX,
            name = {"type"}
    )
    static class Generic {
        Generic() {
        }

        @Bean
        public DataSource dataSource(@Qualifier(DATA_SOURCE_PROPERTIES_BEAN)DataSourceProperties properties) {
            return createDataSource(properties);
        }
    }

    @Configuration
    @ConditionalOnClass({HikariDataSource.class})
    @ConditionalOnMissingBean(value = {HikariDataSource.class}, name = DATA_SOURCE_BEAN)
    @ConditionalOnProperty(
            name = {DATA_SOURCE_PREFIX + ".type"},
            havingValue = "com.zaxxer.hikari.HikariDataSource",
            matchIfMissing = true
    )
    static class Hikari {
        Hikari() {
        }

        @Bean(DATA_SOURCE_BEAN)
        public DataSource dataSource(@Qualifier(DATA_SOURCE_PROPERTIES_BEAN)DataSourceProperties properties) {
            HikariDataSource dataSource = createDataSource(properties, HikariDataSource.class);
            if (StringUtils.hasText(properties.getName())) {
                dataSource.setPoolName(properties.getName());
            }

            return dataSource;
        }
    }

    @Configuration
    @ConditionalOnClass({DruidDataSource.class})
    @ConditionalOnMissingBean(value = {DruidDataSource.class}, name = DATA_SOURCE_BEAN)
    @ConditionalOnProperty(
            prefix = DATA_SOURCE_PREFIX,
            name = {"type"},
            havingValue = "com.alibaba.druid.pool.DruidDataSource"
    )
    static class Druid {
        Druid() {
        }

        @Bean(DATA_SOURCE_BEAN)
        public DataSource dataSource(@Qualifier(DATA_SOURCE_PROPERTIES_BEAN)DataSourceProperties properties) {
            DruidDataSource dataSource = createDataSource(properties, DruidDataSource.class);
            if (StringUtils.hasText(properties.getName())) {
                dataSource.setName(properties.getName());
            }

            return dataSource;
        }
    }

//    @Bean(DATA_SOURCE_BEAN)
//    public DataSource dataSource(
//            @Qualifier(DATA_SOURCE_PROPERTIES_BEAN)DataSourceProperties dataSourceProperties) {
//        return createDataSource(dataSourceProperties);
//    }

    @Bean(SQL_SESSION_FACTORY_BEAN)
    @ConditionalOnMissingBean(name = MYBATIS_PLUS_PROPERTIES_BEAN)
    public SqlSessionFactory sqlSessionFactory(
            @Qualifier(DATA_SOURCE_BEAN)DataSource dataSource,
            @Qualifier(MYBATIS_PROPERTIES_BEAN) MybatisProperties mybatisProperties) {
        return super.sqlSessionFactory(dataSource, mybatisProperties, null);
    }

    @Bean(SQL_SESSION_FACTORY_BEAN)
    @ConditionalOnBean(name = MYBATIS_PLUS_PROPERTIES_BEAN)
    public SqlSessionFactory sqlSessionFactoryPlus(
            @Qualifier(DATA_SOURCE_BEAN)DataSource dataSource,
            @Qualifier(MYBATIS_PLUS_PROPERTIES_BEAN) MybatisPlusProperties mybatisPlusProperties) {
        return super.sqlSessionFactory(dataSource, mybatisPlusProperties, this::mybatisSqlSessionFactoryBeanIntercept);
    }

    private void mybatisSqlSessionFactoryBeanIntercept(MybatisSqlSessionFactoryBean factoryBean) {
        factoryBean.setPlugins(mybatisPlusInterceptor());
        GlobalConfig globalConfig = GlobalConfigUtils.defaults();
//        globalConfig.setMetaObjectHandler(new MybastisColumnsHandler());
//        globalConfig.setBanner(false);
        factoryBean.setGlobalConfig(globalConfig);
    }

    private MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = super.mysqlInterceptor();
        return interceptor;
    }

    @Bean(SQL_SESSION_TEMPLATE_BEAN)
    @ConditionalOnMissingBean(name = MYBATIS_PLUS_PROPERTIES_BEAN)
    public SqlSessionTemplate sqlSessionTemplate(
            @Qualifier(SQL_SESSION_FACTORY_BEAN)SqlSessionFactory sqlSessionFactory,
            @Qualifier(MYBATIS_PROPERTIES_BEAN)MybatisProperties properties) {
        return super.sqlSessionTemplate(sqlSessionFactory, properties);
    }

    @Bean(SQL_SESSION_TEMPLATE_BEAN)
    @ConditionalOnBean(name = MYBATIS_PLUS_PROPERTIES_BEAN)
    public SqlSessionTemplate sqlSessionTemplatePlus(
            @Qualifier(SQL_SESSION_FACTORY_BEAN)SqlSessionFactory sqlSessionFactory,
            @Qualifier(MYBATIS_PLUS_PROPERTIES_BEAN) MybatisPlusProperties mybatisPlusProperties) {
        return super.sqlSessionTemplate(sqlSessionFactory, mybatisPlusProperties);
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

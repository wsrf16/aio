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
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = {"com.york.portable.park.parkdb.dao.master.mapper"}, sqlSessionTemplateRef = "masterSQLSessionTemplate")
public class MasterDataSourceConfiguration extends BaseDataSourceConfiguration {

    public MasterDataSourceConfiguration(@Qualifier("masterMybatisProperties") MybatisProperties properties) {
        super(properties);
    }

    @Bean("masterDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.master")
    @ConditionalOnProperty(prefix = "spring.datasource.master", value = "url")
    @ConditionalOnClass(DruidDataSourceBuilder.class)
    public DataSource dataSource() {
        return super.dataSource();
    }

    @ConditionalOnBean(name = "masterDataSource")
    @Bean("masterSQLSessionFactory")
    @Primary
    @ConditionalOnMissingBean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        return super.sqlSessionFactory();
    }

    @ConditionalOnBean(name = "masterSQLSessionFactory")
    @Bean("masterSQLSessionTemplate")
    @Primary
    @ConditionalOnMissingBean
    public SqlSessionTemplate sqlSessionTemplate() throws Exception {
        return super.sqlSessionTemplate();
    }

    @ConditionalOnBean(name = "masterDataSource")
    @Bean("masterPlatformTransactionManager")
    @Primary
    public PlatformTransactionManager platformTransactionManager() {
        return super.platformTransactionManager();
    }
}

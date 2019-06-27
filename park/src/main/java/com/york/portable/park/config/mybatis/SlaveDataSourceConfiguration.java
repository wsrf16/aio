package com.york.portable.park.config.mybatis;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.york.portable.swiss.data.freedatasource.config.BaseDataSourceConfiguration;
import org.apache.ibatis.session.ExecutorType;
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
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = {"com.york.portable.park.parkdb.dao.master.mapper"}, sqlSessionTemplateRef = "slaveSQLSessionTemplate")
public class SlaveDataSourceConfiguration extends BaseDataSourceConfiguration {

    public SlaveDataSourceConfiguration(@Qualifier("slaveMybatisProperties") MybatisProperties properties) {
        super(properties);
    }

    @Bean("slaveDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.slave")
    @ConditionalOnProperty(prefix = "spring.datasource.slave", value = "url")
    @ConditionalOnClass(DruidDataSourceBuilder.class)
    public DataSource dataSource() {
        return super.dataSource();
    }

    @ConditionalOnBean(name = "slaveDataSource")
    @Bean("slaveSQLSessionFactory")
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        return super.sqlSessionFactory();
    }

    @ConditionalOnBean(name = "slaveSQLSessionFactory")
    @Bean("slaveSQLSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate() throws Exception {
        return super.sqlSessionTemplate();
    }

    @ConditionalOnBean(name = "slaveDataSource")
    @Bean("slavePlatformTransactionManager")
    public PlatformTransactionManager platformTransactionManager() {
        return super.platformTransactionManager();
    }
}

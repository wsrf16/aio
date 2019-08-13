package com.york.portable.park.config.mybatis.choice;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.york.portable.park.config.mybatis.choice.DataSourceKey;
import com.york.portable.swiss.data.freedatasource.config.FreeDataSourceConfiguration;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

//@Configuration
//@MapperScan(basePackages = {"com.york.portable.park.parkdb.dao.master.mapper"})
//@ConditionalOnClass({ SqlSessionFactory.class, SqlSessionFactoryBean.class })
//public class MybatisDataSource extends FreeDataSourceConfiguration {
//
//    @Autowired
//    MybatisProperties mybatisProperties;
//
//    @Bean
//    @ConfigurationProperties(prefix = "spring.datasource.master")
//    @ConditionalOnProperty(prefix = "spring.datasource.master", value = "url")
//    @ConditionalOnClass(DruidDataSourceBuilder.class)
//    public DataSource defaultTargetDataSource() {
//        return DruidDataSourceBuilder.create().build();
//    }
//
////    @Override
//    public String mapperScanBasePackages() {
//        return "com.york.portable.park.parkdb.dao.master.mapper";
//    }
//
////    @Bean
////    @ConfigurationProperties(prefix = "spring.datasource.slave")
////    public DataSource slaveTargetDataSource() {
////        return DruidDataSourceBuilder.create().build();
////    }
//
//    protected Map<Object, Object> targetDataSources() {
//        Map<Object, Object> targetDataSources = new HashMap<>();
//        targetDataSources.put(DataSourceKey.DEFAULT, defaultTargetDataSource());
////        targetDataSources.put(DataSourceKey.SLAVE, slaveTargetDataSource());
//
//        return targetDataSources;
//    }
//
//
//}

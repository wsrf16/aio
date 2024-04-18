package com.aio.portable.swiss.suite.storage.db.mybatis.multidatasource;

import com.aio.portable.swiss.suite.storage.db.AbstractDataSourceConfiguration;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.function.Consumer;

@ConditionalOnClass({DataSource.class, EmbeddedDatabaseType.class})
public abstract class MybatisBaseDataSourceConfiguration extends AbstractDataSourceConfiguration {

    public MybatisProperties mybatisProperties() {
        return new MybatisProperties();
    }

    public MybatisPlusProperties mybatisPlusProperties() {
        return new MybatisPlusProperties();
    }

//    @PostConstruct
//    public void checkConfigFileExists() {
//        if (properties.isCheckConfigLocation() && StringUtils.hasText(properties.getConfigLocation())) {
//            Resource resource = new ClassPathResource(properties.getConfigLocation());
//            Assert.state(resource.exists(), "Cannot find config location: " + resource
//                    + " (please add config file or check your Mybatis configuration)");
//        }
//    }

    public MybatisPlusInterceptor mysqlInterceptor() {
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
        paginationInnerInterceptor.setOptimizeJoin(false);
        paginationInnerInterceptor.setDbType(DbType.MYSQL);
        paginationInnerInterceptor.setOverflow(true);
        OptimisticLockerInnerInterceptor optimisticLockerInnerInterceptor = new OptimisticLockerInnerInterceptor();

        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(paginationInnerInterceptor);
        interceptor.addInnerInterceptor(optimisticLockerInnerInterceptor);
        return interceptor;
    }

    public SqlSessionFactory sqlSessionFactory(DataSource dataSource, MybatisProperties properties, Consumer<SqlSessionFactoryBean> sqlSessionFactoryBeanInterceptor) {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setVfs(SpringBootVFS.class);
        String configLocation = properties.getConfigLocation();
        if (StringUtils.hasText(configLocation)) {
            factoryBean.setConfigLocation(new ClassPathResource(configLocation));
        }
        Configuration configuration = properties.getConfiguration();
        if (configuration == null && !StringUtils.hasText(configLocation)) {
            configuration = new Configuration();
        }
//        if (configuration != null && !CollectionUtils.isEmpty(this.configurationCustomizers)) {
//            for (ConfigurationCustomizer customizer : this.configurationCustomizers) {
//                customizer.customize(configuration);
//            }
//        }
        factoryBean.setConfiguration(configuration);
        if (properties.getConfigurationProperties() != null) {
            factoryBean.setConfigurationProperties(properties.getConfigurationProperties());
        }
//        if (!ObjectUtils.isEmpty(this.interceptors)) {
//            factory.setPlugins(this.interceptors);
//        }
//        if (this.databaseIdProvider != null) {
//            factory.setDatabaseIdProvider(this.databaseIdProvider);
//        }
        if (StringUtils.hasLength(properties.getTypeAliasesPackage())) {
            factoryBean.setTypeAliasesPackage(properties.getTypeAliasesPackage());
        }
        if (StringUtils.hasLength(properties.getTypeHandlersPackage())) {
            factoryBean.setTypeHandlersPackage(properties.getTypeHandlersPackage());
        }
        if (!ObjectUtils.isEmpty(properties.resolveMapperLocations())) {
            factoryBean.setMapperLocations(properties.resolveMapperLocations());
        }

        if (sqlSessionFactoryBeanInterceptor != null)
            sqlSessionFactoryBeanInterceptor.accept(factoryBean);

        try {
            return factoryBean.getObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public SqlSessionFactory sqlSessionFactory(DataSource dataSource, MybatisPlusProperties properties, Consumer<MybatisSqlSessionFactoryBean> sqlSessionFactoryBeanInterceptor) {
        MybatisSqlSessionFactoryBean factoryBean = new MybatisSqlSessionFactoryBean();
//        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setVfs(SpringBootVFS.class);
        String configLocation = properties.getConfigLocation();
        if (StringUtils.hasText(configLocation)) {
            factoryBean.setConfigLocation(new ClassPathResource(configLocation));
        }
        MybatisConfiguration configuration = properties.getConfiguration();
        if (configuration == null && !StringUtils.hasText(configLocation)) {
            configuration = new MybatisConfiguration();
        }
//        if (configuration != null && !CollectionUtils.isEmpty(this.configurationCustomizers)) {
//            for (ConfigurationCustomizer customizer : this.configurationCustomizers) {
//                customizer.customize(configuration);
//            }
//        }
        factoryBean.setConfiguration(configuration);
        if (properties.getConfigurationProperties() != null) {
            factoryBean.setConfigurationProperties(properties.getConfigurationProperties());
        }
//        if (!ObjectUtils.isEmpty(this.interceptors)) {
//            factory.setPlugins(this.interceptors);
//        }
//        if (this.databaseIdProvider != null) {
//            factory.setDatabaseIdProvider(this.databaseIdProvider);
//        }
        if (StringUtils.hasLength(properties.getTypeAliasesPackage())) {
            factoryBean.setTypeAliasesPackage(properties.getTypeAliasesPackage());
        }
        if (StringUtils.hasLength(properties.getTypeHandlersPackage())) {
            factoryBean.setTypeHandlersPackage(properties.getTypeHandlersPackage());
        }
        if (!ObjectUtils.isEmpty(properties.resolveMapperLocations())) {
            factoryBean.setMapperLocations(properties.resolveMapperLocations());
        }

        if (sqlSessionFactoryBeanInterceptor != null)
            sqlSessionFactoryBeanInterceptor.accept(factoryBean);

        try {
            return factoryBean.getObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory, MybatisProperties properties) {
        ExecutorType executorType = properties.getExecutorType();
        return executorType == null ?
                new SqlSessionTemplate(sqlSessionFactory) : new SqlSessionTemplate(sqlSessionFactory, executorType);
    }

    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory, MybatisPlusProperties properties) {
        ExecutorType executorType = properties.getExecutorType();
        return executorType == null ?
                new SqlSessionTemplate(sqlSessionFactory) : new SqlSessionTemplate(sqlSessionFactory, executorType);
    }


}

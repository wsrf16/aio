package com.aio.portable.swiss.suite.storage.db.mybatis.multidatasource;

import com.aio.portable.swiss.suite.storage.db.AbstractDataSourceConfiguration;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import com.baomidou.mybatisplus.autoconfigure.SpringBootVFS;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;

@ConditionalOnClass({DataSource.class, EmbeddedDatabaseType.class})
public abstract class MybatisPlusBaseDataSourceConfiguration extends AbstractDataSourceConfiguration {

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

        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(paginationInnerInterceptor);
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        return interceptor;
    }

    public SqlSessionFactory sqlSessionFactory(
            DataSource dataSource,
            MybatisConfiguration mybatisConfiguration,
            GlobalConfig globalConfig,
            MybatisPlusInterceptor mybatisPlusInterceptor,
            MybatisPlusProperties properties) {
        MybatisSqlSessionFactoryBean factoryBean = new MybatisSqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setVfs(SpringBootVFS.class);
//        MybatisPlusProperties.CoreConfiguration coreConfiguration = properties.getConfiguration();
//        MybatisConfiguration configuration = mybatisConfiguration;
//        if (coreConfiguration != null || !StringUtils.hasText(properties.getConfigLocation())) {
//            configuration = new MybatisConfiguration();
//        }
//        if (configuration != null && coreConfiguration != null) {
//            coreConfiguration.applyTo(configuration);
//        }
//        if (configuration != null && !CollectionUtils.isEmpty(this.configurationCustomizers)) {
//            for (ConfigurationCustomizer customizer : this.configurationCustomizers) {
//                customizer.customize(configuration);
//            }
//        }
        if (mybatisConfiguration != null) {
            factoryBean.setConfiguration(mybatisConfiguration);
        }
        if (globalConfig != null) {
            factoryBean.setGlobalConfig(globalConfig);
        }
        if (properties.getConfigurationProperties() != null) {
            factoryBean.setConfigurationProperties(properties.getConfigurationProperties());
        }
        if (StringUtils.hasLength(properties.getConfigLocation())) {
            factoryBean.setConfigLocation(new ClassPathResource(properties.getConfigLocation()));
        }
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
        if (!ObjectUtils.isEmpty(mybatisPlusInterceptor)) {
            factoryBean.setPlugins(mybatisPlusInterceptor);
        }

        try {
            return factoryBean.getObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory, MybatisPlusProperties properties) {
        ExecutorType executorType = properties.getExecutorType();
        return executorType == null ?
                new SqlSessionTemplate(sqlSessionFactory) : new SqlSessionTemplate(sqlSessionFactory, executorType);
    }


}

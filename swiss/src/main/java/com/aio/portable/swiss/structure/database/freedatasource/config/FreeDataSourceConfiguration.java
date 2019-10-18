package com.aio.portable.swiss.structure.database.freedatasource.config;

//import com.aio.portable.swiss.data.batis.MybatisExtraProperties;
import com.aio.portable.swiss.structure.database.freedatasource.RoutingDataSource;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.Map;


public abstract class FreeDataSourceConfiguration {
    public final static String DEFAULT = "default";

    @Autowired
    protected MybatisProperties properties;

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    @ConditionalOnProperty(prefix = "spring.datasource", value = "url")
    public DataSource defaultRootDataSource() {
//        return DruidDataSourceBuilder.create().build();
        return DataSourceBuilder.create().build();
    }

    @Bean
    public abstract DataSource defaultTargetDataSource();
    protected abstract Map<Object, Object> targetDataSources();

//    @Autowired
//    public FreeDataSourceConfiguration(MybatisExtraProperties properties) {
//        this.properties = properties;
//    }

    @Bean
    @DependsOn("defaultTargetDataSource")
    public DataSource dataSource() {
        DataSource defaultTargetDataSource = defaultTargetDataSource() != null ? defaultTargetDataSource() : defaultRootDataSource();
        Map<Object, Object> targetDataSources = targetDataSources();

        RoutingDataSource routingDataSource = new RoutingDataSource();
        routingDataSource.setDefaultTargetDataSource(defaultTargetDataSource);
        routingDataSource.setTargetDataSources(targetDataSources);

        return routingDataSource;
    }


//    @Bean
//    public SqlSessionFactory sqlSessionFactory(DataSource dataSource, MybatisExtraProperties mybatisProperties) throws Exception {
//        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
//        sqlSessionFactoryBean.setDataSource(dataSource);
//
//        if (mybatisProperties != null) {
////            Resource[] mapperLocations = mybatisProperties.getMapperLocations();
////            if (mapperLocations != null || mapperLocations.length > 0) {
////                sqlSessionFactoryBean.setMapperLocations(mapperLocations);
////            }
//            String mapperLocations = mybatisProperties.getMapperLocations();
//            if (StringUtils.isNotBlank(mapperLocations)) {
//                sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapperLocations));
//            }
//            String typeAliasesPackage = mybatisProperties.getTypeAliasesPackage();
//            if (StringUtils.isNotBlank(typeAliasesPackage))
//                sqlSessionFactoryBean.setTypeAliasesPackage(typeAliasesPackage);
//            String configLocation = mybatisProperties.getConfigLocation();
//            if (StringUtils.isNotBlank(configLocation))
//                sqlSessionFactoryBean.setConfigLocation(new ClassPathResource(configLocation));
//            String typeHandlersPackage = mybatisProperties.getTypeHandlersPackage();
//            if (StringUtils.isNotBlank(typeHandlersPackage))
//                sqlSessionFactoryBean.setTypeHandlersPackage(mybatisProperties.getTypeHandlersPackage());
//        }
//
//        return sqlSessionFactoryBean.getObject();
//    }

    @PostConstruct
    public void checkConfigFileExists() {
        if (properties.isCheckConfigLocation() && StringUtils.hasText(properties.getConfigLocation())) {
            Resource resource = new ClassPathResource(properties.getConfigLocation());
            Assert.state(resource.exists(), "Cannot find config location: " + resource
                    + " (please add config file or check your Mybatis configuration)");
        }
    }

    @Bean
    @ConditionalOnMissingBean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(dataSource);
        factory.setVfs(SpringBootVFS.class);
        if (StringUtils.hasText(properties.getConfigLocation())) {
            factory.setConfigLocation(new ClassPathResource(properties.getConfigLocation()));
        }
        Configuration configuration = properties.getConfiguration();
        if (configuration == null && !StringUtils.hasText(properties.getConfigLocation())) {
            configuration = new Configuration();
        }
//        if (configuration != null && !CollectionUtils.isEmpty(this.configurationCustomizers)) {
//            for (ConfigurationCustomizer customizer : this.configurationCustomizers) {
//                customizer.customize(configuration);
//            }
//        }
        factory.setConfiguration(configuration);
        if (properties.getConfigurationProperties() != null) {
            factory.setConfigurationProperties(properties.getConfigurationProperties());
        }
//        if (!ObjectUtils.isEmpty(this.interceptors)) {
//            factory.setPlugins(this.interceptors);
//        }
//        if (this.databaseIdProvider != null) {
//            factory.setDatabaseIdProvider(this.databaseIdProvider);
//        }
        if (StringUtils.hasLength(properties.getTypeAliasesPackage())) {
            factory.setTypeAliasesPackage(properties.getTypeAliasesPackage());
        }
        if (StringUtils.hasLength(properties.getTypeHandlersPackage())) {
            factory.setTypeHandlersPackage(properties.getTypeHandlersPackage());
        }
        if (!ObjectUtils.isEmpty(properties.resolveMapperLocations())) {
            factory.setMapperLocations(properties.resolveMapperLocations());
        }

        return factory.getObject();
    }

    @Bean
    @ConditionalOnMissingBean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory, MybatisProperties properties) {
        ExecutorType executorType = properties.getExecutorType();
        return executorType != null ?
                new SqlSessionTemplate(sqlSessionFactory, executorType) : new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean
    public PlatformTransactionManager platformTransactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }
}

package com.aio.portable.swiss.suite.storage.db.mybatis.multidatasource;

import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;

//import com.aio.portable.swiss.data.batis.MybatisExtraProperties;

@ConditionalOnClass({DataSource.class, EmbeddedDatabaseType.class})
public abstract class MybatisBaseDataSourceConfiguration {
    public MybatisProperties mybatisProperties() {
        return new MybatisProperties();
    }

//    @Bean
//    @ConfigurationProperties(prefix = "spring.datasource")
//    @ConditionalOnProperty(prefix = "spring.datasource", value = "url")
    @ConditionalOnClass(DataSourceBuilder.class)
    public DataSource dataSource() {
//        return DruidDataSourceBuilder.create().build();
        return DataSourceBuilder.create().build();
    }


//    @PostConstruct
//    public void checkConfigFileExists() {
//        if (properties.isCheckConfigLocation() && StringUtils.hasText(properties.getConfigLocation())) {
//            Resource resource = new ClassPathResource(properties.getConfigLocation());
//            Assert.state(resource.exists(), "Cannot find config location: " + resource
//                    + " (please add config file or check your Mybatis configuration)");
//        }
//    }

    public SqlSessionFactory sqlSessionFactory(DataSource dataSource, MybatisProperties properties) throws Exception {
//        DataSource dataSource = dataSource();
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setVfs(SpringBootVFS.class);
        if (StringUtils.hasText(properties.getConfigLocation())) {
            factoryBean.setConfigLocation(new ClassPathResource(properties.getConfigLocation()));
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

        return factoryBean.getObject();
    }

    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory, MybatisProperties properties) throws Exception {
//        SqlSessionFactory sqlSessionFactory = sqlSessionFactory(properties);
        ExecutorType executorType = properties.getExecutorType();
        return executorType != null ?
                new SqlSessionTemplate(sqlSessionFactory, executorType) : new SqlSessionTemplate(sqlSessionFactory);
    }

    public PlatformTransactionManager platformTransactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}

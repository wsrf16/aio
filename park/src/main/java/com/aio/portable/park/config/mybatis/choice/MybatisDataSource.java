package com.aio.portable.park.config.mybatis.choice;

//@Configuration
//@MapperScan(basePackages = {"com.aio.portable.parkdb.dao.master.mapper"})
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
//        return "com.aio.portable.parkdb.dao.master.mapper";
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

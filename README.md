# aio

------------------------------------- free datasource -------------------------------------
mybatis:
  mapper-locations: classpath:mapper/*Mapper.xml
  type-aliases-package: 
  config-location: 


spring:
  datasource:
    main:
      type: com.alibaba.druid.pool.DruidDataSource
      driver-class-name: com.mysql.cj.jdbc.Driver
      url: jdbc:mysql://192.168.71.254:3306/tdatabase?characterEncoding=utf8&useSSL=true&serverTimezone=Asia/Shanghai&autoReconnect=true&allowMultiQueries=true&useCursorFetch=true
      username: root
      password: 123456
    slave:
      type: com.alibaba.druid.pool.DruidDataSource
      driver-class-name: com.mysql.cj.jdbc.Driver
      url: jdbc:mysql://192.168.71.254:3306/tdatabase?characterEncoding=utf8&useSSL=true&serverTimezone=Asia/Shanghai&autoReconnect=true&allowMultiQueries=true&useCursorFetch=true
      username: root
      password: 123456

@Configuration
@MapperScan(basePackages = {"com.york.portable.park.dao.master.mapper"})
public class CustomDataSourceConfiguration extends FreeDataSourceConfiguration {
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.default")
    public DataSource defaultTargetDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.slave")
    public DataSource slaveTargetDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    protected Map<Object, Object> targetDataSources() {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DataSourceKey.DEFAULT, defaultTargetDataSource());
        targetDataSources.put(DataSourceKey.SLAVE, slaveTargetDataSource());
        return targetDataSources;
    }
}

@Aspect
@Configuration
public class DataSourceAspect extends FreeDataSourceAspect {
}


------------------------------------- log -------------------------------------
spring:
  log:
    rabbitmq:
      enable: true
      host: 10.4.8.7
      port: 5672
      username: yixincapital
      password: yixincapital
      virtual-host: yx_mq
      es-index: @profiles.active@_@project.build.logName@_log
      publisher-confirms: true
      publisher-returns: true
      binding-list:
      - type: direct
        routing-key: application-log-queue
        queue: application-log-queue
        exchange: application-log-exchange

------------------------------------- log -------------------------------------
------------------------------------- log -------------------------------------
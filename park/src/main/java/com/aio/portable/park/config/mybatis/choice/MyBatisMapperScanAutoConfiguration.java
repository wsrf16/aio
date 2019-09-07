package com.aio.portable.park.config.mybatis.choice;

/**
 * MyBatis扫描接口，使用的tk.mybatis.spring.mapper.MapperScannerConfigurer <br/>
 * 如果你不使用通用Mapper，可以改为org.xxx...
 *
 */
//@Configuration
//@AutoConfigureAfter(FreeDataSourceConfiguration.class)
//public class MyBatisMapperScanAutoConfiguration {
//
//    @Bean
//    public static MapperScannerConfigurer mapperScannerConfigurer() {
//        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
//        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
//        mapperScannerConfigurer.setBasePackage("com.aio.portable.parkdb.dao.master.mapper");
//        /**
//        Properties properties = new Properties();
//        // 这里要特别注意，不要把MyMapper放到 basePackage 中，也就是不能同其他Mapper一样被扫描到。
//        properties.setProperty("mappers", MyMapper.class.getName());
//        // insert和update中，是否判断字符串类型!=''，少数方法会用到
//        properties.setProperty("notEmpty", "false");
//        // 取回主键的方式
//        properties.setProperty("IDENTITY", "MYSQL");
//        //实体和表转换时的约定规则，默认驼峰转下划线。注解@Table和@Column优先级高于此配置
//        //http://git.oschina.net/free/Mapper/blob/master/wiki/mapper3/2.Integration.md
//        properties.put("style", "camelhump");
//        mapperScannerConfigurer.setProperties(properties);
//        */
//        return mapperScannerConfigurer;
//    }
//
//}
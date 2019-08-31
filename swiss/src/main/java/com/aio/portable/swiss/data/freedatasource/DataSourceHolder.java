package com.aio.portable.swiss.data.freedatasource;

public class DataSourceHolder {
    public static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    public static void putDataSource(String key) {
        contextHolder.set(key);
    }

    public static String getDataSource() {
        return contextHolder.get();
    }

    public static void clearDataSourceType() {
        contextHolder.remove();
    }

//    public static boolean containsDataSource(String dataSourceId){
//        return dataSourceIds.contains(dataSourceId);
//    }
}

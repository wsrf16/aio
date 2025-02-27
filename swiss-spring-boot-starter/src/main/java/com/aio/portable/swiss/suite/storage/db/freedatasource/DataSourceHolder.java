package com.aio.portable.swiss.suite.storage.db.freedatasource;

public class DataSourceHolder {
    public static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<>();

    public static void putDataSource(String key) {
        CONTEXT_HOLDER.set(key);
    }

    public static String getDataSource() {
        return CONTEXT_HOLDER.get();
    }

    public static void clearDataSourceType() {
        CONTEXT_HOLDER.remove();
    }

//    public static boolean containsDataSource(String dataSourceId){
//        return dataSourceIds.contains(dataSourceId);
//    }
}

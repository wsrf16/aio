package com.aio.portable.swiss.suite.storage.db.mybatis.toolkit;

import com.aio.portable.swiss.suite.storage.db.mybatis.EnhanceBaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface InformationSchemaMapper<S> extends EnhanceBaseMapper<S> {
//    @Select("SELECT COLUMN_NAME, COLUMN_COMMENT FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = #{dbName} AND TABLE_NAME = #{tableName}")

    @Select("SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = #{databaseName} AND TABLE_NAME = #{tableName}")
    List<S> getSchema(@Param("databaseName") String databaseName, @Param("tableName") String tableName);

    @Select("SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = #{tableName}")
    List<S> getCurrentSchema(@Param("tableName") String tableName);

//    @Select("SELECT COLUMN_NAME, COLUMN_COMMENT FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = #{databaseName} AND TABLE_NAME = #{tableName}")
//    List<Map<String, Object>> getFieldComments(@Param("databaseName") String databaseName, @Param("tableName") String tableName);
}

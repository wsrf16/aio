package com.aio.portable.swiss.suite.storage.db.mybatis.toolkit;

import com.aio.portable.swiss.suite.storage.db.mybatis.EnhanceBaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface DDLMapper<S> extends EnhanceBaseMapper<S> {
    @Select("SHOW CREATE table #{tableName}")
    List<S> getSchema(@Param("tableName") String tableName);
}

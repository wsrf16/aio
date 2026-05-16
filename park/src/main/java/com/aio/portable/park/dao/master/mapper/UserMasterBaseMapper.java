package com.aio.portable.park.dao.master.mapper;

import com.aio.portable.park.dao.master.model.User;
import com.aio.portable.swiss.suite.storage.db.mybatis.EnhanceBaseMapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;


//@TargetDataSource("master")
//@Service
public interface UserMasterBaseMapper extends EnhanceBaseMapper<User> {
    @SelectProvider(type = SqlProvider.class, method = "buildSelectSql")
    List<User> universal(Map<String, Object> conditions);
}

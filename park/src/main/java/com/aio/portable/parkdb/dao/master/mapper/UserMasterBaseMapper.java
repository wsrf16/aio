package com.aio.portable.parkdb.dao.master.mapper;

import com.aio.portable.parkdb.dao.master.model.Book;
import com.aio.portable.parkdb.dao.master.model.User;
import com.aio.portable.swiss.suite.storage.db.freedatasource.TargetDataSource;
import com.aio.portable.swiss.suite.storage.db.mybatis.EnhanceBaseMapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Service;

import java.util.List;


//@TargetDataSource("master")
//@Service
public interface UserMasterBaseMapper extends EnhanceBaseMapper<User> {
}

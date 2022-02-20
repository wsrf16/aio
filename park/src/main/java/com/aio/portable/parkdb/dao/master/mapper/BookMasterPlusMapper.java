package com.aio.portable.parkdb.dao.master.mapper;

import com.aio.portable.parkdb.dao.master.model.Book;
import com.aio.portable.swiss.suite.storage.db.freedatasource.TargetDataSource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;


@TargetDataSource("master")
public interface BookMasterPlusMapper extends BaseMapper<Book> {

}

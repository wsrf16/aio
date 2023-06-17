package com.aio.portable.park.dao.master.mapper;

import com.aio.portable.park.dao.master.model.Book;
import com.aio.portable.swiss.suite.storage.db.freedatasource.TargetDataSource;

import java.util.List;


@TargetDataSource("master")
public interface BookMasterMapper {
    List<Book> getAll();

    Book get(int id);

    int insert(Book book);
}

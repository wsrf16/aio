package com.aio.portable.parkdb.dao.slave.mapper;

import com.aio.portable.parkdb.dao.slave.model.Book;
import com.aio.portable.swiss.data.freedatasource.TargetDataSource;

import java.util.List;


@TargetDataSource("slave")
public interface BookSlaveMapper {
    List<Book> getAll();

    Book get(int id);

    int insert(Book book);
}

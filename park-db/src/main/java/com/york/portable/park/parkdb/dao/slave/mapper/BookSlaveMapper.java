package com.york.portable.park.parkdb.dao.slave.mapper;

import com.york.portable.park.parkdb.dao.slave.model.Book;
import com.york.portable.swiss.data.freedatasource.TargetDataSource;

import java.util.List;


@TargetDataSource("slave")
public interface BookSlaveMapper {
    List<Book> getAll();

    Book get(int id);

    int insert(Book book);
}

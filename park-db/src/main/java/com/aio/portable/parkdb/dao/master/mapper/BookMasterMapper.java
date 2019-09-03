package com.aio.portable.parkdb.dao.master.mapper;

import com.aio.portable.parkdb.dao.master.model.Book;
import com.aio.portable.swiss.data.freedatasource.TargetDataSource;

import java.util.List;


@TargetDataSource("master")
public interface BookMasterMapper {
    List<Book> getAll();

    Book get(int id);

    int insert(Book book);
}
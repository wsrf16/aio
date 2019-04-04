package com.york.portable.park.parkdb.dao.master.mapper;

import com.york.portable.park.parkdb.dao.master.model.Book;
import com.york.portable.swiss.data.freedatasource.TargetDataSource;

import java.util.List;


@TargetDataSource("slave")
public interface BookMapper {
    List<Book> getAll();

    Book get(int id);

//    int insert(Book book);
}

package com.aio.portable.parkdb.dao.third.mapper;

import com.aio.portable.parkdb.dao.third.model.Book;
import com.aio.portable.swiss.data.freedatasource.TargetDataSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import java.util.List;


public interface BookThirdRepository extends JpaRepository<Book, Long> {

}

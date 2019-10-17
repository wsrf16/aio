package com.aio.portable.parkdb.dao.third.mapper;

import com.aio.portable.parkdb.dao.third.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BookThirdRepository extends JpaRepository<Book, Long> {

}

package com.aio.portable.parkdb.dao.slave.repository;

import com.aio.portable.parkdb.dao.slave.model.Book;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;


public interface BookSlaveRepository extends JpaRepositoryImplementation<Book, Long> {

}

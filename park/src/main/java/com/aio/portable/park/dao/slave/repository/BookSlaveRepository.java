package com.aio.portable.park.dao.slave.repository;

import com.aio.portable.park.dao.slave.model.Book;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;


public interface BookSlaveRepository extends JpaRepositoryImplementation<Book, Long> {

}

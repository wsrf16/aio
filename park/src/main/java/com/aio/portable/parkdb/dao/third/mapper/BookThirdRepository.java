package com.aio.portable.parkdb.dao.third.mapper;

import com.aio.portable.parkdb.dao.third.model.Book;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;


public interface BookThirdRepository extends JpaRepositoryImplementation<Book, Long> {

}

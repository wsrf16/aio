package com.aio.portable.parkdb.dao.third.mapper;

import com.aio.portable.parkdb.dao.third.mapper.base.BaseJpaRepository;
import com.aio.portable.parkdb.dao.third.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BookThirdRepository extends BaseJpaRepository<Book, Long> {

}

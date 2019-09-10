package com.aio.portable.park.other.test;

import com.aio.portable.parkdb.dao.master.mapper.BookMasterMapper;
import com.aio.portable.parkdb.dao.slave.mapper.BookSlaveMapper;
import com.aio.portable.parkdb.dao.third.mapper.BookThirdRepository;
import com.aio.portable.parkdb.dao.third.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Optional;

@Configuration
public class MybatisTest {
    @Autowired(required = false)
    BookMasterMapper bookMasterMapper;
    @Autowired(required = false)
    BookSlaveMapper bookSlaveMapper;
    @Autowired(required = false)
    BookThirdRepository bookThirdRepository;

    public void blah() {
        {
            if (bookMasterMapper != null) {
                com.aio.portable.parkdb.dao.master.model.Book book1 = bookMasterMapper.get(10);
                com.aio.portable.parkdb.dao.master.model.Book book2 = bookMasterMapper.get(10);
            }
            if (bookSlaveMapper != null) {
                com.aio.portable.parkdb.dao.slave.model.Book book1 = bookSlaveMapper.get(10);
                com.aio.portable.parkdb.dao.slave.model.Book book2 = bookSlaveMapper.get(10);
            }
            if (bookThirdRepository != null) {
                List<com.aio.portable.parkdb.dao.third.model.Book> all = bookThirdRepository.findAll();
                Optional<com.aio.portable.parkdb.dao.third.model.Book> book1 = bookThirdRepository.findById(10L);
                com.aio.portable.parkdb.dao.third.model.Book book = new com.aio.portable.parkdb.dao.third.model.Book();
                book.setDescription("abc");
                book.setAuthor("ooooo");
//                book.setId(100L);
                bookThirdRepository.save(book);
                Optional<com.aio.portable.parkdb.dao.third.model.Book> book2 = bookThirdRepository.findById(10L);
            }
        }
        {
            com.aio.portable.parkdb.dao.master.model.Book book = new com.aio.portable.parkdb.dao.master.model.Book();
            book.setAuthor("author");
            book.setName("name");
            book.setDescription("description");
            if (bookMasterMapper != null) {
                bookMasterMapper.insert(book);
            }
        }
    }
}

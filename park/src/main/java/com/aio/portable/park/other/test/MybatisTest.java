package com.aio.portable.park.other.test;

import com.aio.portable.parkdb.dao.master.mapper.BookMasterMapper;
import com.aio.portable.parkdb.dao.master.model.Book;
import com.aio.portable.parkdb.dao.slave.mapper.BookSlaveMapper;
import com.aio.portable.parkdb.dao.third.mapper.BookThirdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

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
                Book book1 = bookMasterMapper.get(10);
                Book book2 = bookMasterMapper.get(10);
            }
            if (bookSlaveMapper != null) {
                com.aio.portable.parkdb.dao.slave.model.Book book1 = bookSlaveMapper.get(10);
                com.aio.portable.parkdb.dao.slave.model.Book book2 = bookSlaveMapper.get(10);
            }
            if (bookThirdRepository != null) {
                Optional<com.aio.portable.parkdb.dao.third.model.Book> book1 = bookThirdRepository.findById(10L);
                Optional<com.aio.portable.parkdb.dao.third.model.Book> book2 = bookThirdRepository.findById(10L);
            }
        }
        {
            Book book = new Book();
            book.setAuthor("author");
            book.setName("name");
            book.setDescription("description");
            bookMasterMapper.insert(book);
        }
    }
}

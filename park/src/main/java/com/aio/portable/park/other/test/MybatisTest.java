package com.aio.portable.park.other.test;

import com.aio.portable.parkdb.dao.master.mapper.BookMasterMapper;
import com.aio.portable.parkdb.dao.master.model.Book;
import org.springframework.beans.factory.annotation.Autowired;

//@Configuration
public class MybatisTest {
    @Autowired
    BookMasterMapper bookMasterMapper;
    @Autowired
    BookMasterMapper bookSlaveMapper;

    public void blah() {
        {
            Book book1 = bookMasterMapper.get(16);
            Book book2 = bookSlaveMapper.get(16);
            Book book = bookSlaveMapper.get(16);

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

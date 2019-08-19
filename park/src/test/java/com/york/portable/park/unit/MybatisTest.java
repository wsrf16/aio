package com.york.portable.park.unit;

import com.york.portable.park.parkdb.dao.master.mapper.BookMasterMapper;
import com.york.portable.park.parkdb.dao.master.model.Book;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//@Service
public class MybatisTest {
    @Autowired
    BookMasterMapper bookMasterMapper;
    @Autowired
    BookMasterMapper bookSlaveMapper;

    @Test
    public void main() {
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

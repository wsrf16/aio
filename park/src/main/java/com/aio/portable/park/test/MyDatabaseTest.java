package com.aio.portable.park.test;

import com.aio.portable.parkdb.dao.master.mapper.BookMasterMapper;
import com.aio.portable.parkdb.dao.master.mapper.BookMasterPlusMapper;
import com.aio.portable.parkdb.dao.slave.mapper.BookSlaveMapper;
import com.aio.portable.parkdb.dao.third.repository.BookThirdRepository;
import com.aio.portable.parkdb.dao.third.model.Book;
import com.aio.portable.parkdb.dao.third.model.BookVO;
import com.aio.portable.swiss.spring.SpringContextHolder;
import com.aio.portable.swiss.suite.storage.db.jpa.JPASugar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class MyDatabaseTest {
    @Autowired(required = false)
    BookMasterMapper bookMasterMapper;
    @Autowired(required = false)
    BookMasterPlusMapper bookMasterPlusMapper;
    @Autowired(required = false)
    BookSlaveMapper bookSlaveMapper;
    @Autowired(required = false)
    BookThirdRepository bookThirdRepository;

    public void blah() {
        master();
        slave();
        third();
    }

    private BookVO condition() {
        BookVO vo = new BookVO();
        vo.setIdGreaterThanEqual(5L);
        vo.setNameLike("Shakespeare");
        vo.setIdLessThanEqual(9L);
        vo.setId(1L);
        ArrayList<String> ins = new ArrayList<>();
        ins.add("Shakespeare");
        vo.setAuthorIn(ins);
        return vo;
    }


    private void master() {
        if (bookMasterMapper != null) {
            List<com.aio.portable.parkdb.dao.master.model.Book> all = bookMasterMapper.getAll();
            com.aio.portable.parkdb.dao.master.model.Book book1 = bookMasterMapper.get(3);

            com.aio.portable.parkdb.dao.master.model.Book book = new com.aio.portable.parkdb.dao.master.model.Book();
            book.setAuthor("author");
            book.setName("name");
            book.setDescription("description");
            if (bookMasterMapper != null) {
//                bookMasterMapper.insert(book);
            }
        }
        if (bookMasterPlusMapper != null) {
            com.aio.portable.parkdb.dao.master.model.Book book = bookMasterPlusMapper.selectById(1);
        }
    }

    private void slave() {
        if (bookSlaveMapper != null) {
            com.aio.portable.parkdb.dao.slave.model.Book book1 = bookSlaveMapper.get(2);
            com.aio.portable.parkdb.dao.slave.model.Book book2 = bookSlaveMapper.get(10);
        }
    }

    private void third() {
        BookVO vo = condition();
        if (bookThirdRepository != null) {
            Specification<Book> specification = JPASugar.<Book>buildSpecification(vo);
            Sort sort = JPASugar.<Book>buildSort(BookVO.class);
            List<Book> all = bookThirdRepository.findAll(specification, sort);
            List<Book> all1 = bookThirdRepository.findAll();

            Book book = new Book();
            book.setDescription("abc");
            book.setAuthor("ooooo");
//            bookThirdRepository.save(book);
        }
    }
}

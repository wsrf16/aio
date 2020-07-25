package com.aio.portable.park.test;

import com.aio.portable.parkdb.dao.master.mapper.BookMasterMapper;
import com.aio.portable.parkdb.dao.slave.mapper.BookSlaveMapper;
import com.aio.portable.parkdb.dao.third.mapper.BookThirdRepository;
import com.aio.portable.parkdb.dao.third.model.Book;
import com.aio.portable.parkdb.dao.third.model.BookVO;
import com.aio.portable.swiss.suite.storage.rds.jpa.JPASugar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class MyDatabaseTest {
    @Autowired(required = false)
    BookMasterMapper bookMasterMapper;
    @Autowired(required = false)
    BookSlaveMapper bookSlaveMapper;
    @Autowired(required = false)
    BookThirdRepository bookThirdRepository;

    public void blah() {
        master();
        slave();
        third();
    }

    private void third() {
        BookVO vo = condition();
        if (bookThirdRepository != null) {
            Specification<Book> specification = JPASugar.<Book>buildSpecification(vo);
            Sort sort = JPASugar.<Book>buildSort(BookVO.class);
            List<Book> all = bookThirdRepository.findAll(specification, sort);


            Book book = new Book();
            book.setDescription("abc");
            book.setAuthor("ooooo");
            bookThirdRepository.save(book);
        }
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

    private void slave() {
        if (bookSlaveMapper != null) {
            com.aio.portable.parkdb.dao.slave.model.Book book1 = bookSlaveMapper.get(10);
            com.aio.portable.parkdb.dao.slave.model.Book book2 = bookSlaveMapper.get(10);
        }
    }

    private void master() {
        if (bookMasterMapper != null) {
            com.aio.portable.parkdb.dao.master.model.Book book1 = bookMasterMapper.get(10);

            com.aio.portable.parkdb.dao.master.model.Book book = new com.aio.portable.parkdb.dao.master.model.Book();
            book.setAuthor("author");
            book.setName("name");
            book.setDescription("description");
//                if (bookMasterMapper != null) {
//                    bookMasterMapper.insert(book);
//                }
        }
    }
}

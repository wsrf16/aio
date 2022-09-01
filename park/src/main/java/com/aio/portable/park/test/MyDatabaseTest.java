package com.aio.portable.park.test;

import com.aio.portable.parkdb.dao.master.mapper.BookMasterMapper;
import com.aio.portable.parkdb.dao.master.mapper.UserMasterBaseMapper;
import com.aio.portable.parkdb.dao.master.model.User;
import com.aio.portable.parkdb.dao.slave.model.Book;
import com.aio.portable.parkdb.dao.slave.model.BookVO;
import com.aio.portable.parkdb.dao.slave.repository.BookSlaveRepository;
import com.aio.portable.park.service.master.BookMasterBaseService;
import com.aio.portable.park.service.master.UserMasterBaseService;
import com.aio.portable.swiss.suite.storage.db.jpa.JPASugar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Configuration
public class MyDatabaseTest {
    @Autowired(required = false)
    public BookMasterMapper bookMasterMapper;
    @Autowired(required = false)
    public BookMasterBaseService bookMasterBaseService;
    @Autowired(required = false)
    public UserMasterBaseService userMasterBaseService;
    @Autowired(required = false)
    public BookSlaveRepository bookSlaveRepository;

    public void blah() {
        master();
        slave();
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

        if (userMasterBaseService != null) {
            List<User> all = userMasterBaseService.selectList();
            Iterator aa = all.iterator();
            userMasterBaseService.updateByAge(c -> c.set(User::getName, "21nnnnnnnnnnnnnnn"), 21);
            User user = userMasterBaseService.selectById(1);
            User user1 = new User();
            user1.setAge(111);
            userMasterBaseService.update(user1, c -> c.eq(User::getAge, 21));
            List<User> users = userMasterBaseService.selectListByName("Jone");
        }
    }

    private void slave() {
        BookVO vo = condition();
        if (bookSlaveRepository != null) {
            Specification<Book> specification = JPASugar.<Book>buildSpecification(vo);
            Sort sort = JPASugar.<Book>buildSort(BookVO.class);
            List<Book> all = bookSlaveRepository.findAll(specification, sort);
            List<Book> all1 = bookSlaveRepository.findAll();

            Book book = new Book();
            book.setDescription("abc");
            book.setAuthor("ooooo");
//            bookThirdRepository.save(book);
        }
    }
}

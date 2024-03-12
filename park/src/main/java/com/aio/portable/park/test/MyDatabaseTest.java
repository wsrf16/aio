package com.aio.portable.park.test;

import com.aio.portable.park.dao.master.mapper.BookMasterMapper;
import com.aio.portable.park.dao.master.model.Book;
import com.aio.portable.park.dao.master.model.User;
import com.aio.portable.park.dao.master.model.UserConditionDTO;
import com.aio.portable.park.dao.slave.model.BookVO;
import com.aio.portable.park.dao.slave.repository.BookSlaveRepository;
import com.aio.portable.park.service.master.BookMasterBaseService;
import com.aio.portable.park.service.master.UserMasterBaseService;
import com.aio.portable.park.service.master.UserMasterServiceImpl;
import com.aio.portable.swiss.sugar.resource.function.LambdaConsumer;
import com.aio.portable.swiss.sugar.resource.function.LambdaFunction;
import com.aio.portable.swiss.suite.bean.BeanSugar;
import com.aio.portable.swiss.suite.storage.db.jpa.JPASugar;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.lang.invoke.SerializedLambda;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

@Configuration
public class MyDatabaseTest {
    @Autowired(required = false)
    BookMasterMapper bookMasterMapper;
    @Autowired(required = false)
    BookMasterBaseService bookMasterBaseService;
    @Autowired(required = false)
    UserMasterBaseService userMasterBaseService;
    @Autowired(required = false)
    UserMasterServiceImpl userMasterServiceImpl;
    @Autowired(required = false)
    BookSlaveRepository bookSlaveRepository;

    public void blah() {
        master();
        slave();
    }

    private BookVO condition() {
        BookVO vo = new BookVO();
        vo.setIdGreaterThanEqual(5);
        vo.setNameLike("Shakespeare");
        vo.setIdLessThanEqual(9);
        vo.setId(1);
        ArrayList<String> ins = new ArrayList<>();
        ins.add("Shakespeare");
        vo.setAuthorIn(ins);
        return vo;
    }


    private void master() {
        if (bookMasterMapper != null) {
            List<Book> all = bookMasterMapper.getAll();
            Book book1 = bookMasterMapper.get(3);

            Book book = new Book();
            book.setName("name");
            book.setDescription("description");
            if (bookMasterMapper != null) {
                bookMasterMapper.insert(book);
            }
        }

        if (userMasterBaseService != null) {
            Page<User> userPage = userMasterBaseService.selectPage(2, 2, new User());

            List<User> all = userMasterBaseService.selectList();
            Iterator aa = all.iterator();
            userMasterBaseService.updateByAge(c -> c.set(User::getName, "abcdefg"), 21);
            User user = userMasterBaseService.selectById(1);
            User user1 = new User();
            user1.setAge(111);
            userMasterBaseService.update(user1, c -> c.eq(User::getAge, 21));
            List<User> users1 = userMasterBaseService.selectListByName("Jone");
            UserConditionDTO userConditionDTO = new UserConditionDTO();
            userConditionDTO.setNameLike("o");
            userConditionDTO.setEmailLike("gmail");
            userConditionDTO.setIdIn(Arrays.asList(2, 3, 4));
            List<User> users2 = userMasterBaseService.selectList(userConditionDTO);
            System.out.println();
        }
    }

    private void slave() {
        BookVO vo = condition();
        if (bookSlaveRepository != null) {
            Specification<com.aio.portable.park.dao.slave.model.Book> specification = JPASugar.<com.aio.portable.park.dao.slave.model.Book>buildSpecification(vo);
            Sort sort = JPASugar.<com.aio.portable.park.dao.slave.model.Book>buildSort(BookVO.class);
            List<com.aio.portable.park.dao.slave.model.Book> all1 = bookSlaveRepository.findAll();

            List<com.aio.portable.park.dao.slave.model.Book> all = bookSlaveRepository.findAll(specification, sort);
            com.aio.portable.park.dao.slave.model.Book book = new com.aio.portable.park.dao.slave.model.Book();
            book.setDescription("abc");
            book.setAuthor("ooooo");
//            bookSlaveRepository.save(book);
        }
    }
}

package com.aio.portable.park.service.master;

import com.aio.portable.park.dao.master.mapper.BookMasterBaseMapper;
import com.aio.portable.park.dao.master.model.Book;
import com.aio.portable.park.dao.master.model.BookCondition;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookMasterBaseService {
    @Autowired
    BookMasterBaseMapper bookMasterBaseMapper;

    public Book selectById(Integer id) {
        return bookMasterBaseMapper.selectById(id);
    }

    public BookCondition selectByIdToBookDTO(Integer id) {
        return bookMasterBaseMapper.selectById(id, BookCondition.class);
    }

    public List<Book> selectList(Book book) {
        return bookMasterBaseMapper.selectList(book);
    }

    public List<Book> selectListByName(String name) {
        return bookMasterBaseMapper.selectList(c -> c.eq(Book::getName, name));
    }

    public List<Book> selectListByAuthor(String author) {
        return bookMasterBaseMapper.selectList(c -> c.eq(Book::getAuthor, author));
    }

    public List<BookCondition> selectListByAuthorToBookDTO(String author) {
        return bookMasterBaseMapper.selectList(c -> c.eq(Book::getAuthor, author), BookCondition.class);
    }

    public List<BookCondition> selectListToBookDTO(Book book) {
        return bookMasterBaseMapper.selectList(book, BookCondition.class);
    }

    public List<BookCondition> selectBatchIdsToBookDTO(List<String> idList) {
        return bookMasterBaseMapper.selectBatchIds(idList, BookCondition.class);
    }

    public List<Book> selectListByAuthorForDescription(String author) {
        return bookMasterBaseMapper.selectList(c -> c.eq(Book::getAuthor, author), Book::getDescription);
    }

    public Page<Book> selectPage(int page, int size, Book book) {
        return bookMasterBaseMapper.selectPage(new Page<>(page, size), book, c -> c.orderByAsc(Book::getName));
    }

    public Page<Book> selectPageByAuthor(int page, int size, String author) {
        return bookMasterBaseMapper.selectPage(new Page<>(page, size), c -> c.eq(Book::getAuthor, author));
    }

    public int insertBatch(List<Book> bookList) {
        return bookMasterBaseMapper.insertBatch(bookList);
    }

    public int updateById(Book book) {
        return bookMasterBaseMapper.updateById(book);
    }

    public int update(Book book, Book condition) {
        return bookMasterBaseMapper.update(book, condition);
    }

    public int deleteBatchIds(List<String> idList) {
        return bookMasterBaseMapper.deleteBatchIds(idList);
    }

}

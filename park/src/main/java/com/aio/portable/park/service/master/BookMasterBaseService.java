package com.aio.portable.park.service.master;

import com.aio.portable.parkdb.dao.master.mapper.BookMasterBaseMapper;
import com.aio.portable.parkdb.dao.master.mapper.UserMasterBaseMapper;
import com.aio.portable.parkdb.dao.master.mapper.UserMasterBaseMapper;
import com.aio.portable.parkdb.dao.master.model.Book;
import com.aio.portable.parkdb.dao.master.model.BookDTO;
import com.aio.portable.parkdb.dao.master.model.User;
import com.aio.portable.parkdb.dao.master.model.UserDTO;
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

    public BookDTO selectByIdToBookDTO(Integer id) {
        return bookMasterBaseMapper.selectById(id, BookDTO.class);
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

    public List<BookDTO> selectListByAuthorToBookDTO(String author) {
        return bookMasterBaseMapper.selectList(c -> c.eq(Book::getAuthor, author), BookDTO.class);
    }

    public List<BookDTO> selectListToBookDTO(Book book) {
        return bookMasterBaseMapper.selectList(book, BookDTO.class);
    }

    public List<BookDTO> selectBatchIdsToBookDTO(List<String> idList) {
        return bookMasterBaseMapper.selectBatchIds(idList, BookDTO.class);
    }

    public List<Book> selectListByAuthorOnlyDescription(String author) {
        return bookMasterBaseMapper.selectList(c -> c.eq(Book::getAuthor, author), Book::getDescription);
    }

    public Page<Book> selectPage(int page, int size, Book book) {
        return bookMasterBaseMapper.selectPage(new Page<>(page, size), book);
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

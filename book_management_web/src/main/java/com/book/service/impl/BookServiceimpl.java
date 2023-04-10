package com.book.service.impl;

import com.book.dal.BookMapper;
import com.book.dal.StudentMapper;
import com.book.entity.Book;
import com.book.entity.Borrow;
import com.book.entity.Student;
import com.book.service.BookService;
import com.book.servlet.pages.IndexServlet;
import com.book.utils.MybatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.util.*;
import java.util.stream.Collectors;

public class BookServiceimpl implements BookService {
    @Override
    public List<Borrow> getBorrowList() {
        try(SqlSession sqlSession = MybatisUtil.getSession ()){
            BookMapper bookMapper = sqlSession.getMapper (BookMapper.class);
            List<Borrow> list = bookMapper.getBorrowList ();
            return list;
        }
    }

    @Override
    public void returnBook(String  id) {
        try(SqlSession sqlSession = MybatisUtil.getSession ()){
            BookMapper bookMapper = sqlSession.getMapper (BookMapper.class);
            bookMapper.deleteBorrow (Integer.parseInt (id));
        }
    }

    @Override
    public List<Book> getActiveBookList() {
        HashSet<Integer> set = new HashSet<> ();
        this.getBorrowList ().forEach (borrow -> set.add(borrow.getBook_id ()));
        try(SqlSession sqlSession = MybatisUtil.getSession ()){
            BookMapper bookMapper = sqlSession.getMapper (BookMapper.class);
            return bookMapper.getBookList ()
                    .stream ()
                    .filter(book -> !set.contains (book.getBid ()))
                    .collect(Collectors.toList());
        }
    }

    @Override
    public List<Student> getStudentList() {
        try(SqlSession sqlSession = MybatisUtil.getSession ()){
            StudentMapper mapper = sqlSession.getMapper (StudentMapper.class);
            return mapper.getStudentList ();
        }
    }

    @Override
    public void addBorrow(int sid, int bid, Date time) {
        try(SqlSession sqlSession = MybatisUtil.getSession ()){
            BookMapper bookMapper = sqlSession.getMapper (BookMapper.class);
            bookMapper.addBorrow (sid,bid,time);
        }
    }

    @Override
    public Map<Book,Boolean> getBookList() {
        HashSet<Integer> set = new HashSet<> ();
        this.getBorrowList ().forEach (borrow -> set.add(borrow.getBook_id ()));
        try(SqlSession sqlSession = MybatisUtil.getSession ()){
            BookMapper bookMapper = sqlSession.getMapper (BookMapper.class);
            Map<Book,Boolean> map = new LinkedHashMap<> ();
            bookMapper.getBookList ().forEach (book -> {
                map.put(book,set.contains (book.getBid ()));
            });

            return map;
        }
    }

    @Override
    public void deleteBook(int bid) {
        try(SqlSession sqlSession = MybatisUtil.getSession ()){
            BookMapper bookMapper = sqlSession.getMapper (BookMapper.class);
            bookMapper.deleteBook (bid);
        }
    }

    @Override
    public void addBook(String title, String description, double price) {
        try(SqlSession sqlSession = MybatisUtil.getSession ()){
            BookMapper bookMapper = sqlSession.getMapper (BookMapper.class);
            bookMapper.addBook (title,description,price);
        }
    }
}

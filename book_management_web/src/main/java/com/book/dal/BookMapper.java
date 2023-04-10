package com.book.dal;

import com.book.entity.Book;
import com.book.entity.Borrow;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

public interface BookMapper {

    @Results({
            @Result(column  = "bid" , property = "book_id"),
            @Result(column  = "sid" , property = "student_id"),
            @Result(column  = "title" , property = "book_name"),
            @Result(column  = "name" , property = "student_name"),
            @Result(column  = "time" , property = "time"),
            @Result(column  = "id" , property = "id"),
    })
    @Select ("select * from borrow , student , book where borrow.bid = book.bid and student.sid = borrow.sid")
    List<Borrow> getBorrowList();

    @Delete ("delete from borrow where id = #{id}")
    void deleteBorrow(int id);

    @Select ("select * from book ")
    List<Book> getBookList();

    @Insert ("insert into borrow(sid, bid,time) value(#{sid} ,#{bid},#{time})")
    void addBorrow(@Param("sid") int sid, @Param("bid") int bid ,@Param ("time") Date time);

    @Delete ("delete from book where bid=#{bid}")
    void deleteBook(int bid);

    @Insert ("insert into book(title,description,price) value(#{title},#{description},#{price})")
    void addBook(@Param("title") String title,@Param("description") String description,@Param("price") double price);
}

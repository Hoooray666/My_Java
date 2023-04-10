package com.book.dal;

import com.book.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {
    @Select ("select * from admin where username=#{username} and password = #{password}")
    User getUser(@Param ("username") String username, @Param ("password") String password);

    @Select ("select * from admin where username = #{username}")
    User ExistOrNot(String username);

    @Insert("insert into admin(username,password) values(#{username},#{password})")
    int Register(@Param("username") String username, @Param("password") String password);
}

package com.book.service.impl;

import com.book.dal.UserMapper;
import com.book.entity.User;
import com.book.service.UserService;
import com.book.utils.MybatisUtil;
import jakarta.servlet.http.HttpSession;
import org.apache.ibatis.session.SqlSession;

public class UserServiceImpl implements UserService {
    @Override
    public boolean auth(String username, String password, HttpSession session) {
        try(SqlSession sqlSession = MybatisUtil.getSession ()){
            UserMapper mapper = sqlSession.getMapper (UserMapper.class);
            User user = mapper.getUser (username, password);
            if(user == null){
                return false;
            }
            session.setAttribute ("user",user);
            return true;
        }
    }

    public boolean auth1(String username,String password,HttpSession session){
        try(SqlSession sqlSession = MybatisUtil.getSession ()){
            UserMapper mapper = sqlSession.getMapper (UserMapper.class);
            User user = mapper.ExistOrNot (username);
            if(user != null){
                session.setAttribute ("register-failure",new Object ());
                return false;
            }
            mapper.Register (username,password);
            return true;
        }
    }
}

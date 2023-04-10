package com.book.utils;

import com.mysql.cj.Session;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;

public class MybatisUtil {
    private static SqlSessionFactory factory;
    static{
        try {
            factory = new SqlSessionFactoryBuilder ().build (Resources.getResourceAsStream ("mybatis-config.xml"));
        } catch (IOException e) {
            e.printStackTrace ();
        }
    }

    public static SqlSession getSession(){
        return factory.openSession (true);
    }
}

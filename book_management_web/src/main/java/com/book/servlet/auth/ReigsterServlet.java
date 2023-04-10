package com.book.servlet.auth;

import com.book.dal.UserMapper;
import com.book.service.UserService;
import com.book.service.impl.UserServiceImpl;
import com.book.utils.ThymeleafUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.context.Context;

import java.io.IOException;

@WebServlet("/register")
public class ReigsterServlet extends HttpServlet {

    UserService service;
    @Override
    public void init() throws ServletException {
        service =new UserServiceImpl () ;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Context context=new Context () ;
        resp.setContentType ("text/html;charset=UTF-8");
        if(req.getSession ().getAttribute ("register-failure") != null){
            context.setVariable ("failure",true);
            req.getSession ().removeAttribute ("register-failure");
        }
        ThymeleafUtil.process ("register.html",context,resp.getWriter ());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter ("username");
        String password = req.getParameter ("password");
        if(service.auth1(username,password,req.getSession ())){
            resp.sendRedirect ("login");
        }else{
            req.getSession ().setAttribute ("register-failure",new Object ());
            this.doGet (req,resp);
        }
    }
}

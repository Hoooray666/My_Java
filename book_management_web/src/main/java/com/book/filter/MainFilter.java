package com.book.filter;

import com.book.entity.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/*")
public class MainFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
        String url = req.getRequestURL ().toString ();
        HttpSession session = req.getSession ();
        User user = (User) session.getAttribute ("user");
        if( !url.contains ("/static/") && !url.endsWith ("login") && !url.endsWith ("register")){
            if(user == null){
                resp.sendRedirect ("login");
                return;
            }
        }
        chain.doFilter(req,resp);
    }
}

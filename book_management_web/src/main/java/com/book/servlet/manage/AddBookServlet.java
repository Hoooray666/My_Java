package com.book.servlet.manage;

import com.book.service.BookService;
import com.book.service.impl.BookServiceimpl;
import com.book.utils.ThymeleafUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.context.Context;

import java.io.IOException;

@WebServlet("/add-book")
public class AddBookServlet extends HttpServlet {
    BookService service  ;

    @Override
    public void init() throws ServletException {
        service = new BookServiceimpl ();
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Context context = new Context ();
        ThymeleafUtil.process ("add-book.html",context,resp.getWriter ());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = req.getParameter ("title");
        String description = req.getParameter ("description");
        double price = Double.parseDouble (req.getParameter ("price"));
        service.addBook (title,description,price);
        resp.sendRedirect ("books");
    }
}

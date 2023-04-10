package com.book.servlet.pages;

import com.book.entity.Book;
import com.book.entity.User;
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
import java.util.ArrayList;

@WebServlet("/books")
public class BookServlet extends HttpServlet {

    BookService service  ;

    @Override
    public void init() throws ServletException {
        service = new BookServiceimpl ();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Context context = new Context ();
        context.setVariable ("book_list",service.getBookList ().keySet ());
        context.setVariable ("book_list_status",new ArrayList<> (service.getBookList ().values ()));
        ThymeleafUtil.process ("books.html",context,resp.getWriter ());
    }
}

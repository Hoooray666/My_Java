package com.book.service;

import jakarta.servlet.http.HttpSession;

public interface UserService {
    boolean auth(String username, String password, HttpSession session) ;
    boolean auth1(String username, String password, HttpSession session);
}

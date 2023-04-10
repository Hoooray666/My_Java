package com.book.entity;

import lombok.Data;

import java.util.Objects;

@Data
public class User {
    int id;
    String username;
    String nickname;
    String password;

    @Override
    public String toString() {
        return "username : "+username +" password : "+password;
    }
}

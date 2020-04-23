package com.example.springstarter.repository;

import com.example.springstarter.entity.User;

public interface LoginRepository {

    User findUser(String username);
    boolean isValidUser(String username,  String password);
}

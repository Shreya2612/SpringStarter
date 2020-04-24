package com.example.springstarter.repository;

import com.example.springstarter.entity.User;
import com.example.springstarter.model.UserModel;

public interface UserRepository {

    User findUserByUserName(String username);
 //   User createUser(String username, String password);

    User createUser(UserModel model);
}

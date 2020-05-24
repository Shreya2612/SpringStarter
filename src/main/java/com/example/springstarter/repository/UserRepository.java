package com.example.springstarter.repository;

import com.example.springstarter.entity.User;
import com.example.springstarter.model.request.UserModel;

public interface UserRepository {

    //User findUserByUserName(String username);
 //   User createUser(String username, String password);

    /*public User findUserByUserName(String username) {
        if(CREATE_MAP.containsKey(username)) {
            return new User(username,CREATE_MAP.get(username));   //if i want to send username only?
        }
        return new User();
}*/
     User findUserByUserName(String username);
     User findUserById(Long id);
     User createUser(UserModel model);
     User deleteUserById(Long id);
     User updateUser(Long id, UserModel model);
}


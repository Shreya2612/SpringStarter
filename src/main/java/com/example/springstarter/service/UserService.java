package com.example.springstarter.service;

import com.example.springstarter.model.ApiResponse;
import com.example.springstarter.model.UserModel;
import com.example.springstarter.util.Constants;

import java.util.Collections;

public interface UserService {

    ApiResponse getUser(String firstName, Long id);
    ApiResponse getUser(Long id);
    ApiResponse addUser(UserModel model);
    ApiResponse deleteUser(Long id);
    ApiResponse updateUser(Long id, UserModel model);


    ApiResponse getUserList();
}


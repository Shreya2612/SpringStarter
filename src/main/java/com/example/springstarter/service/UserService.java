package com.example.springstarter.service;

import com.example.springstarter.model.ApiResponse;
import com.example.springstarter.model.UserModel;

public interface UserService {

    ApiResponse getUser(String username);
    ApiResponse getUser(Long id);
    ApiResponse addUser(UserModel model);

    ApiResponse deleteUser(Long id);

    ApiResponse updateUser(Long id, UserModel model);
}

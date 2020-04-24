package com.example.springstarter.service;

import com.example.springstarter.model.ApiResponse;
import com.example.springstarter.model.UserModel;

public interface UserService {

    ApiResponse getUser(UserModel model);
    ApiResponse addUser(UserModel model);
}

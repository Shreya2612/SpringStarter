package com.example.springstarter.service;

import com.example.springstarter.model.response.ApiResponse;
import com.example.springstarter.model.request.LoginModel;
import com.example.springstarter.model.request.UpdatePasswordModel;


public interface AuthService {
    ApiResponse login(LoginModel model);
    ApiResponse updpwd(String username, UpdatePasswordModel model);

}

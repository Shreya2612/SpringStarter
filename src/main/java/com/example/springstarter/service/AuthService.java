package com.example.springstarter.service;

import com.example.springstarter.model.ApiResponse;
import com.example.springstarter.model.LoginModel;
import com.example.springstarter.model.UpdatePasswordModel;


public interface AuthService {
    ApiResponse login(LoginModel model);
    ApiResponse updpwd(String username, UpdatePasswordModel model);

}

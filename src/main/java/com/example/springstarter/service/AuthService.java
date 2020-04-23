package com.example.springstarter.service;

import com.example.springstarter.model.ApiResponse;
import com.example.springstarter.model.LoginModel;
import org.springframework.stereotype.Service;


public interface AuthService {
    ApiResponse login(LoginModel model);

}

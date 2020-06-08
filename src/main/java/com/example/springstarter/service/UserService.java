package com.example.springstarter.service;

import com.example.springstarter.model.response.ApiResponse;
import com.example.springstarter.model.request.UserModel;

public interface UserService {

    ApiResponse getUser(String firstName, Long id);
    ApiResponse getUser(Long id);
    ApiResponse addUser(UserModel model);
    ApiResponse deleteUser(Long id);
    ApiResponse updateUser(Long id, UserModel model);
    ApiResponse getUserList();


    ApiResponse addContactList(Long id , UserModel model);
    ApiResponse getContact(Long id);
}


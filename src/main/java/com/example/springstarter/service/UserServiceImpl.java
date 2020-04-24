package com.example.springstarter.service;

import com.example.springstarter.entity.User;
import com.example.springstarter.model.ApiResponse;
import com.example.springstarter.model.UserModel;
import com.example.springstarter.repository.UserRepository;
import com.example.springstarter.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public ApiResponse addUser(UserModel model) {
        User user = this.userRepository.createUser(model);

        ApiResponse response = new ApiResponse();
        if (!user.getUsername().isEmpty()) {
            response.setStatus(Constants.MSG_STATUS_SUC);
            response.setMessage(Constants.MSG_CREATE_USER);
            response.setStatusCode(Constants.ErrorCodes.CODE_CREATE_SUCCESS);
            response.setData(Arrays.asList(user));

        }else {
            response.setStatus(Constants.MSG_STATUS_FAIL);
            response.setMessage(Constants.MSG_CREATE_USER_FAIL);
            response.setStatusCode(Constants.ErrorCodes.CODE_USER_CREATE_FAIL);
            response.setData(Collections.emptyList());
        }
        return response;
    }

    @Override
    public ApiResponse getUser(UserModel model) {
        User user = this.userRepository.findUserByUserName(model.getUsername());
        ApiResponse response =  new ApiResponse();
        if(user.getUsername().isEmpty()){
            response.setStatus(Constants.MSG_STATUS_FAIL);
            response.setMessage(Constants.MSG_AUTH_NO_USER);
            response.setStatusCode(Constants.ErrorCodes.CODE_GET_USER_FAIL);
            response.setData(Collections.emptyList());
        }
        else{
            response.setStatus(Constants.MSG_STATUS_SUC);
            response.setMessage(Constants.MSG_USER_FOUND);
            response.setStatusCode(Constants.ErrorCodes.CODE_SUCCESS);
            response.setData(Arrays.asList(user));
        }

        return response;

    }
}

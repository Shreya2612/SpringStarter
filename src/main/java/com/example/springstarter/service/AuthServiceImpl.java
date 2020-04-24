package com.example.springstarter.service;

import com.example.springstarter.entity.User;
import com.example.springstarter.model.ApiResponse;
import com.example.springstarter.model.LoginModel;
import com.example.springstarter.repository.LoginRepository;
import com.example.springstarter.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private LoginRepository loginRepository;

    @Override
    public ApiResponse login(LoginModel model) {                                 //FOR AUTHENTICATION OF THE LOGIN USERNAME AND PWD WE ARE FETCHING FROM DB(REPOSITORY)
        User user = this.loginRepository.findUser(model.getUsername());
        if (user.getUsername().isEmpty()) {
            ApiResponse response =  new ApiResponse();
            response.setStatus(Constants.MSG_STATUS_FAIL);
            response.setMessage(Constants.MSG_AUTH_NO_USER);
            response.setStatusCode(Constants.ErrorCodes.CODE_AUTH_FAIL);
            response.setData(Collections.emptyList());
            return response;
        }
        ApiResponse response =  new ApiResponse();
        response.setData(Collections.emptyList());

        if(model.getPassword().equals(user.getPassword())){
             response.setStatus(Constants.MSG_STATUS_SUC);
            response.setMessage(Constants.MSG_AUTH_USER);
            response.setStatusCode(Constants.ErrorCodes.CODE_SUCCESS);
        } else {
            response.setStatus(Constants.MSG_STATUS_FAIL);
            response.setMessage(Constants.MSG_PWD_FAIL);
            response.setStatusCode(Constants.ErrorCodes.CODE_FAIL);
        }
        return response;

    }
}

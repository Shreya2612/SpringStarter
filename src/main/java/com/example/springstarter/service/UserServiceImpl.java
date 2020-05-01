package com.example.springstarter.service;

import com.example.springstarter.entity.User;
import com.example.springstarter.model.ApiResponse;
import com.example.springstarter.model.UserModel;
import com.example.springstarter.repository.UserRepository;
import com.example.springstarter.util.Constants;
import com.example.springstarter.util.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import org.apache.commons.*;

import java.util.Arrays;
import java.util.Collections;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public ApiResponse addUser(UserModel model) {

        boolean valid = Utility.userValidation(model);  // user validation part
        if(valid){
            User user = this.userRepository.createUser(model);   //User Entity class being used here to map DB entities into  java class
            ApiResponse response = new ApiResponse();
            if (!user.getUsername().isEmpty()) {
                response.setStatus(Constants.MSG_STATUS_SUC);
                response.setMessage(Constants.MSG_CREATE_USER);
                response.setStatusCode(Constants.ErrorCodes.CODE_CREATE_SUCCESS);
                response.setData(Arrays.asList(user));

            } else {
                response.setStatus(Constants.MSG_STATUS_FAIL);
                response.setMessage(Constants.MSG_CREATE_USER_FAIL);
                response.setStatusCode(Constants.ErrorCodes.CODE_USER_CREATE_FAIL);
                response.setData(Collections.emptyList());
            }
            return response;
        }
        else{
            ApiResponse response = new ApiResponse();
            response.setStatus(Constants.MSG_STATUS_FAIL);
            response.setMessage(Constants.MSG_CREATE_USER_VALID_FAIL);
            response.setStatusCode(Constants.ErrorCodes.CODE_USER_CREATE_FAIL);
            response.setData(Collections.emptyList());
            return response;
        }

    }

    @Override
    public ApiResponse getUser(String username) {
        User user = this.userRepository.findUserByUserName(username);
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

    @Override
    public ApiResponse getUser(Long id) {
        User user = this.userRepository.findUserById(id);
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
 @Override
    public ApiResponse deleteUser(Long id) {
        User user = this.userRepository.deleteUserById(id);
        ApiResponse response =  new ApiResponse();
        if(user==null){
            response.setStatus(Constants.MSG_STATUS_FAIL);
            response.setMessage(Constants.MSG_AUTH_NO_USER);
            response.setStatusCode(Constants.ErrorCodes.CODE_GET_USER_FAIL);
            response.setData(Collections.emptyList());
        }
        else{
            response.setStatus(Constants.MSG_STATUS_SUC);
            response.setMessage(Constants.MSG_USER_DELETE);
            response.setStatusCode(Constants.ErrorCodes.CODE_SUCCESS);
            response.setData(Arrays.asList(user));
        }

        return response;

    }

    @Override
    public ApiResponse updateUser(Long id,UserModel model) {
        User user = this.userRepository.updateUser(id,model);
        ApiResponse response =  new ApiResponse();
        if(user==null){
            response.setStatus(Constants.MSG_STATUS_FAIL);
            response.setMessage(Constants.MSG_AUTH_NO_USER);
            response.setStatusCode(Constants.ErrorCodes.CODE_GET_USER_FAIL);
            response.setData(Collections.emptyList());
        }
        else{
            response.setStatus(Constants.MSG_STATUS_SUC);
            response.setMessage(Constants.MSG_USER_UPDATE);
            response.setStatusCode(Constants.ErrorCodes.CODE_SUCCESS);
            response.setData(Arrays.asList(user));
        }

        return response;

    }



}

package com.example.springstarter.util;

import org.springframework.http.HttpStatus;

public interface Constants {
    String MSG_STATUS_SUC = "Successful";
    String MSG_STATUS_FAIL = "Failure";

    String MSG_AUTH_NO_USER = "User doesn't exist";
    String MSG_AUTH_USER = "Login Successful";
    String MSG_PWD_FAIL = "Your Password is incorrect";
    String MSG_CREATE_USER = "User has been successfully created";
    String MSG_CREATE_USER_FAIL = "Sorry! User already exists";
    String MSG_CREATE_USER_VALID_FAIL = "Username should contain min. 8 characters," +
            "pwd combination incorrect and contact should be of 10 digits";
    String MSG_USER_FOUND = "User Found !";
    String MSG_USER_DELETE = "User Successfully deleted !";
    String MSG_USER_UPDATE = "User Updated Successfully !";


    String MSG_ERR_GENERIC = "Something went wrong. The server is unable to process your request";

    interface ErrorCodes {
        int CODE_SUCCESS = 200;
        int CODE_FAIL = 500;
        int CODE_CREATE_SUCCESS = 201;

        int CODE_AUTH_SUCCESS = 901;
        int CODE_AUTH_FAIL = 902;
        int CODE_USER_CREATE_FAIL= 905;
        int CODE_GET_USER_FAIL= 906;
    }
}

package com.example.springstarter.util;

import org.springframework.http.HttpStatus;

public interface Constants {
    String MSG_STATUS_SUC = "Successful";
    String MSG_STATUS_FAIL = "Failure";

    String MSG_AUTH_NO_USER = "User doesn't exist";
    String MSG_AUTH_USER = "Login Successful";
    String MSG_PWD_FAIL = "Your Password is incorrect";

    String MSG_ERR_GENERIC = "Something went wrong. The server is unable to process your request";

    interface ErrorCodes {
        int CODE_SUCCESS = 200;
        int CODE_FAIL = 500;

        int CODE_AUTH_SUCCESS = 901;
        int CODE_AUTH_FAIL = 902;
    }
}

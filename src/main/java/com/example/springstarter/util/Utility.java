package com.example.springstarter.util;

import com.example.springstarter.model.UserModel;

public interface Utility {
    String AUTH_TOKEN = "S3cretT0k3N@=";

    String REGEX_ALPHANUMERIC = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@=]))";

    static boolean userValidation(UserModel model) {
        return
                model.getFirstName().toString()!= null &&
                model.getPassword().toString().matches("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,10})") &&
                model.getContact().toString().length() == 10 && model.getContact().toString().matches("[0-9]+") ;

    }

} //whatever stays inside utility is always an independent piece of code---confirm?

package com.example.springstarter.util;

import com.example.springstarter.model.UserModel;

public interface Utility {

    static boolean userValidation(UserModel model) {
        return
                model.getFirstName().toString()!= null &&
                model.getPassword().toString().matches("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,10})") &&
                model.getContact().toString().length() == 10 && model.getContact().toString().matches("[0-9]+") ;

    }

} //whatever stays inside utility is always an independent piece of code---confirm?

package com.example.springstarter.util;

import com.example.springstarter.model.UserModel;

public interface Utility {

   static boolean userValidation(UserModel model)
    {
        if(model.getUsername().toString().length()>=8&&model.getUsername().toString().length()<=100&&
                model.getPassword().toString().matches("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,10})")&&
                (model.getContact().toString().length()==10))
            return true;
        else
            return false;
    }

} //whatever stays inside utility is always an independent piece of code---confirm?

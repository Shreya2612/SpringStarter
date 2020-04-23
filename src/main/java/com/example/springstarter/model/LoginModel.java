package com.example.springstarter.model;

public class LoginModel{
    private String password;
    private String username;

    public void setPassword(String password){
        this.password = password;
    }

    public String getPassword(){
        return password;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getUsername(){
        return username;
    }

    @Override
    public String toString(){
        return
                "LoginModel{" +
                        "password = '" + password + '\'' +
                        ",username = '" + username + '\'' +
                        "}";
    }
}

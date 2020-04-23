package com.example.springstarter.repository;

import com.example.springstarter.entity.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
@Repository
public class LoginRepositoryImpl implements LoginRepository {
    private static final Map<String,String> USER_MAP=new HashMap<>();

    public LoginRepositoryImpl() {
        USER_MAP.put("Shreya","Abcd1234");
    }

    @Override
    public User findUser(String username) {
        if(USER_MAP.containsKey(username)) {
            return new User(username,USER_MAP.get(username));
        }
        return new User();
    }

    @Override
    public boolean isValidUser(String username, String password) {
        return false;
    }
}

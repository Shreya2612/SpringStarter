package com.example.springstarter.repository;

import com.example.springstarter.entity.User;
import com.example.springstarter.model.UserModel;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private static final Map<String,String> CREATE_MAP = new HashMap<>();

    private static final TreeMap<Long, User> USER_MAP = new TreeMap<>();

    /*public UserRepositoryImpl() {

        }*/


    @Override
    public User findUserByUserName(String username) {
        if(CREATE_MAP.containsKey(username)) {
            return new User(username,CREATE_MAP.get(username));   //if i want to send username only?
        }
        return new User();

    }

/*    @Override
    public User createUser(String username, String password) {

        if(!CREATE_MAP.containsKey(username)) {    //this condition??
            CREATE_MAP.put(username, password);
            return new User(username,CREATE_MAP.get(username));
        }
        return null;
    }*/

    @Override
    public User createUser(UserModel model) {
        Long lastId = 0L;
        if (USER_MAP.isEmpty()) {
            lastId = 1L;
        } else {
            lastId = USER_MAP.lastKey() +1 ;
        }
        // Check if username already exists
        //{A, B, C, D} => --A--B--C--D-- => --vA--vB--vC--vD--
        boolean isUserExisting = USER_MAP.entrySet().stream() // Entry<Long, User>
                .map(e -> e.getValue()) //User
                .anyMatch(user -> user.getUsername().equals(model.getUsername()));
        if (isUserExisting) {
            return new User();
        }

        User user= new User();
        user.setUsername(model.getUsername());
        user.setPassword(model.getPassword());
        user.setContact(model.getContact());
        user.setId(lastId);
        USER_MAP.put(lastId,user);
        return user;
    }

}

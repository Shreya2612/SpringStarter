package com.example.springstarter.repository;

import com.example.springstarter.entity.User;
import com.example.springstarter.model.UserModel;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private static final Map<String,String> CREATE_MAP = new HashMap<>();

    private static final TreeMap<Long, User> USER_MAP = new TreeMap<>();

    /*public UserRepositoryImpl() {

        }*/


    @Override
    /*public User findUserByUserName(String username) {
        if(CREATE_MAP.containsKey(username)) {
            return new User(username,CREATE_MAP.get(username));   //if i want to send username only?
        }
        return new User();

    }*/
    public User findUserByUserName(String username) {
        Optional<User> user = USER_MAP.entrySet().stream() // in form of Entry<Long, User>
                .map(e -> e.getValue()) //in form of User
                .filter(u -> u.getUsername().equals(username)).findFirst();
                                                           // filter returns more than one value
        return user.orElse(new User());
    }

/*    @Override
    public User createUser(String username, String password) {

        if(!CREATE_MAP.containsKey(username)) {    //this condition??
            CREATE_MAP.put(username, password);
            return new User(username,CREATE_MAP.get(username));
        }
        return null;
    }*/
    public User findUserById(Long id) {
        Optional<User> user = USER_MAP.entrySet().stream() // in form of Entry<Long, User>
                .map(e -> e.getValue()) //in form of User
                .filter(u -> u.getId().equals(id)).findFirst();
        // filter returns more than one value
        return user.orElse(new User());
    }

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
        boolean isUserExisting = USER_MAP.entrySet().stream() // in form of Entry<Long, User>
                .map(e -> e.getValue()) //in form of User
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


    public User deleteUserById(Long id) {
        Optional<User> user = USER_MAP.entrySet().stream() // in form of Entry<Long, User>
                .map(e -> e.getValue()) //in form of User
                .filter(u -> u.getId().equals(id)).findFirst();
        if (user.isPresent()) {
            USER_MAP.remove(id);
            return user.get();
        }
        // filter returns more than one value
        return null;    // Optional always returns either valid value or null in case no match is found
    }

    public User updateUser(Long id, UserModel model) {
        Optional<User> user = USER_MAP.entrySet().stream() // in form of Entry<Long, User>
                .map(e -> e.getValue()) //in form of User
                .filter(u -> u.getId().equals(id)).findFirst();
        if (user.isPresent()) {
            User user1 = user.get();
            user1.setContact(model.getContact());
            user1.setUsername(model.getUsername());
            user1.setPassword(model.getPassword());
            USER_MAP.put(id,user1);
            return user1;
        }
                           // filter returns more than one value
        return null;    // Optional always returns either valid value or null in case no match is found
    }

}

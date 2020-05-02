package com.example.springstarter.service;

import com.example.springstarter.entity.User;
import com.example.springstarter.entity.Users;
import com.example.springstarter.model.ApiResponse;
import com.example.springstarter.model.UserModel;
import com.example.springstarter.repository.UserRepository;
import com.example.springstarter.repository.UsersRepository;
import com.example.springstarter.repository.specs.UserSpecification;
import com.example.springstarter.util.Constants;
import com.example.springstarter.util.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
//import org.apache.commons.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UsersRepository usersRepository;


    @Override
    public ApiResponse addUser(UserModel model) {

        boolean valid = Utility.userValidation(model);  // user validation part
        if (valid) {
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
        } else {
            ApiResponse response = new ApiResponse();
            response.setStatus(Constants.MSG_STATUS_FAIL);
            response.setMessage(Constants.MSG_CREATE_USER_VALID_FAIL);
            response.setStatusCode(Constants.ErrorCodes.CODE_USER_CREATE_FAIL);
            response.setData(Collections.emptyList());
            return response;
        }

    }

    @Override
    public ApiResponse getUser(String firstName, Long id) {
        Optional<Users> userOpt = this.usersRepository.findOne(
                (root, criteriaQuery, criteriaBuilder) -> {
                    Predicate fPred = criteriaBuilder.equal(root.get("firstName"), firstName);
                    Predicate idPred = criteriaBuilder.equal(root.get("id"), id);
                    return criteriaBuilder.and(fPred, idPred);
                }
        );
        return userOpt
                .map(users ->
                        new ApiResponse(
                                Arrays.asList(users),
                                Constants.MSG_USER_FOUND,
                                Constants.MSG_STATUS_SUC,
                                Constants.ErrorCodes.CODE_SUCCESS)
                )
                .orElse(new ApiResponse(
                        Collections.emptyList(),
                        Constants.MSG_AUTH_NO_USER,
                        Constants.MSG_STATUS_FAIL,
                        Constants.ErrorCodes.CODE_GET_USER_FAIL
                ));

        /*User user = this.userRepository.findUserByUserName(username);
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

        return response;*/

    }

    @Override
    public ApiResponse getUser(Long id) {
        Optional<Users> userOpt = this.usersRepository.findById(id);
        return userOpt
                .map(users ->
                        new ApiResponse(
                                Arrays.asList(users),
                                Constants.MSG_USER_FOUND,
                                Constants.MSG_STATUS_SUC,
                                Constants.ErrorCodes.CODE_SUCCESS)
                )
                .orElse(new ApiResponse(
                        Collections.emptyList(),
                        Constants.MSG_AUTH_NO_USER,
                        Constants.MSG_STATUS_FAIL,
                        Constants.ErrorCodes.CODE_GET_USER_FAIL
                ));
        /*User user = this.userRepository.findUserById(id);
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

        return response;*/

    }

    @Override
    public ApiResponse deleteUser(Long id) {

        try {
            Optional<Users> userOpt = this.usersRepository.findById(id);
            if (userOpt.isPresent()) {
                this.usersRepository.deleteById(id);
                return new ApiResponse(
                        Arrays.asList(userOpt.get()),
                        Constants.MSG_USER_DELETE,
                        Constants.MSG_STATUS_SUC,
                        Constants.ErrorCodes.CODE_SUCCESS);
            }
            return new ApiResponse(
                    Collections.emptyList(),
                    Constants.MSG_AUTH_NO_USER,
                    Constants.MSG_STATUS_FAIL,
                    Constants.ErrorCodes.CODE_FAIL
            );
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse(
                    Collections.emptyList(),
                    Constants.MSG_ERR_GENERIC,
                    Constants.MSG_STATUS_FAIL,
                    Constants.ErrorCodes.CODE_FAIL
            );
        }

    }

    @Override
    public ApiResponse updateUser(Long id, UserModel model) {
        User user = this.userRepository.updateUser(id, model);
        ApiResponse response = new ApiResponse();
        if (user == null) {
            response.setStatus(Constants.MSG_STATUS_FAIL);
            response.setMessage(Constants.MSG_AUTH_NO_USER);
            response.setStatusCode(Constants.ErrorCodes.CODE_GET_USER_FAIL);
            response.setData(Collections.emptyList());
        } else {
            response.setStatus(Constants.MSG_STATUS_SUC);
            response.setMessage(Constants.MSG_USER_UPDATE);
            response.setStatusCode(Constants.ErrorCodes.CODE_SUCCESS);
            response.setData(Arrays.asList(user));
        }

        return response;

    }


}

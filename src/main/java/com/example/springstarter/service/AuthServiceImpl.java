package com.example.springstarter.service;

import com.example.springstarter.entity.AuthUser;
import com.example.springstarter.entity.User;
import com.example.springstarter.model.ApiResponse;
import com.example.springstarter.model.LoginModel;
import com.example.springstarter.repository.AuthUserRepository;
import com.example.springstarter.repository.LoginRepository;
import com.example.springstarter.util.Constants;
import com.example.springstarter.util.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.Collections;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthUserRepository authUserRepository;

    @Override
    public ApiResponse login(LoginModel model) {
        //FOR AUTHENTICATION OF THE LOGIN USERNAME AND PWD WE ARE FETCHING FROM DB(REPOSITORY)
        Optional<AuthUser> userOpt = this.authUserRepository.findOne((root, criteriaQuery, criteriaBuilder) -> {
            Predicate fPred = criteriaBuilder.equal(root.get("userName"), model.getUsername());
            return criteriaBuilder.and(fPred);
        });

        if (!userOpt.isPresent()) {
            ApiResponse response = new ApiResponse();
            response.setStatus(Constants.MSG_STATUS_FAIL);
            response.setMessage(Constants.MSG_AUTH_NO_USER);
            response.setStatusCode(Constants.ErrorCodes.CODE_AUTH_FAIL);
            response.setData(Collections.emptyList());
            return response;
        }
        ApiResponse response = new ApiResponse();
        response.setData(Collections.emptyList());

        try {
            AuthUser user = userOpt.get();
            String salt = user.getSalt();
            String hash = Utility.computeHash(model.getPassword(), salt);

            if (hash.equals(user.getHash())) {
                response.setStatus(Constants.MSG_STATUS_SUC);
                response.setMessage(Constants.MSG_AUTH_USER);
                response.setStatusCode(Constants.ErrorCodes.CODE_SUCCESS); //will login successfully
            } else {
                response.setStatus(Constants.MSG_STATUS_FAIL);
                response.setMessage(Constants.MSG_PWD_FAIL);   //else pwd fail
                response.setStatusCode(Constants.ErrorCodes.CODE_FAIL);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(Constants.MSG_STATUS_FAIL);
            response.setMessage(Constants.MSG_ERR_GENERIC);
            response.setStatusCode(Constants.ErrorCodes.CODE_FAIL);
        }
        return response;

    }
}

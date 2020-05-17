package com.example.springstarter.service;

import com.example.springstarter.entity.AuthUser;
import com.example.springstarter.model.ApiResponse;
import com.example.springstarter.model.LoginModel;
import com.example.springstarter.model.UpdatePasswordModel;
import com.example.springstarter.repository.AuthUserRepository;
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

        /*  findOne explained :
            this is similar to query (select * from authuser where user_name = 'Shreya';) as :
            select * from authuser --> this.authUserRepository.findOne((root, criteriaQuery, criteriaBuilder -> {
            where user_name = 'Shreya' --> criteriaBuilder.equal(root.get("userName"), model.getUsername());
            //here root is Entity, ctriteriaQuery is the query we are making and criteriaBuilder builds the criteria
           // and "userName" is coming from our DB and model. is the one which user is presently sending with request body i.e Login Model
           both are matched here for authentication
           and FINALLY UPSEROPT IS RETURNING "THE ENTIRE ROW" OF THAT PARTICULAR USERNAME
            ADDING THAT PREDICATE VALUE DONE WITH THE HELP OF CRITERIA BUILDER.
         */
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
            AuthUser user = userOpt.get(); //fetching the values from userOpt with help of get()
            String salt = user.getSalt(); //getting salt from User Entity
            String hash = Utility.computeHash(model.getPassword(), salt); //computing hash with user's given pwd in model

            if (hash.equals(user.getHash())) { //if computed Hash == hash stored in my DB i.e my AuthUser Entity
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
        return response; //common for all.

    }

    @Override
    public ApiResponse updpwd(String username, UpdatePasswordModel model) {

        Optional<AuthUser> userOpt = this.authUserRepository.findOne((root, criteriaQuery, criteriaBuilder) -> {
            Predicate fPred = criteriaBuilder.equal(root.get("userName"), username);
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
        AuthUser user = userOpt.get();
        ApiResponse response = new ApiResponse();

        try {
            String salt = user.getSalt();
            String hash = Utility.computeHash(model.getOldPwd(), salt);
            boolean ispwdCorrect = hash.equals(user.getHash());
            boolean isNewPwdCorrect = model.getNewPwd().equals(model.getConfirmNewPwd());
            if (ispwdCorrect && isNewPwdCorrect) {
                salt = Utility.generateSalt();
                hash = Utility.computeHash(model.getNewPwd(), salt);
                user.setHash(hash);
                user.setSalt(salt);
                this.authUserRepository.save(user);
                response.setStatus(Constants.MSG_STATUS_SUC);
                response.setMessage(Constants.MSG_PWD_UPDATE_SUC);
                response.setStatusCode(Constants.ErrorCodes.CODE_SUCCESS);
            } else {
                if (!ispwdCorrect) {
                    response.setMessage(Constants.MSG_PWD_FAIL);
                } else {
                    response.setMessage(Constants.MSG_PWD_UPDATE_FAIL2);
                }
                response.setStatus(Constants.MSG_STATUS_FAIL);
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


package com.example.springstarter.service;

import com.example.springstarter.entity.Users;
import com.example.springstarter.model.ApiResponse;
import com.example.springstarter.model.UserModel;
import com.example.springstarter.repository.UsersRepository;
import com.example.springstarter.util.Constants;
import com.example.springstarter.util.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.*;


@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UsersRepository usersRepository;    //this is created for the new JPA Users Repo.


    @Override
    public ApiResponse addUser(UserModel model) {    // extra username is coming
        boolean valid = Utility.userValidation(model);  // user validation part
        if (valid) {
            Users users = new Users();
            users.setFirstName(model.getFirstName());
            users.setLastName(model.getLastName());
            users.setContact(Long.parseLong(model.getContact()));
            users.setMail(model.getMail());
            Users userOpt = this.usersRepository.save(users);
            return new ApiResponse(
                    Arrays.asList(userOpt),
                    Constants.MSG_CREATE_USER,
                    Constants.MSG_STATUS_SUC,
                    Constants.ErrorCodes.CODE_SUCCESS
            );
        }
        ApiResponse response = new ApiResponse();
        response.setStatus(Constants.MSG_STATUS_FAIL);
        response.setMessage(Constants.MSG_CREATE_USER_VALID_FAIL);
        response.setStatusCode(Constants.ErrorCodes.CODE_USER_CREATE_FAIL);
        response.setData(Collections.emptyList());
        return response;
    }

    @Override
    public ApiResponse getUser(String firstName, Long id) {    // which firstName is this ..one which is coming from controller?
        Optional<Users> userOpt = this.usersRepository.findOne(
                (root, criteriaQuery, criteriaBuilder) -> {
                    Predicate fPred = criteriaBuilder.equal(root.get("firstName"), firstName);  //this is the same firstName?
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
        } catch (Exception e) {
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
        Optional<Users> usersOptional = usersRepository.findById(id);
        return usersOptional.map(u -> {                         //converting/mapping Users to ApiResponse
            u.setFirstName(model.getFirstName());
            u.setLastName(model.getLastName());
            u.setContact(Long.parseLong(model.getContact()));
            u.setMail(model.getMail());
            Users saved = usersRepository.save(u);
            return ApiResponse.successResponse(Constants.ErrorCodes.CODE_SUCCESS, Constants.MSG_USER_UPDATE, Arrays.asList(saved));
        }).orElse(ApiResponse.failResponse(Constants.ErrorCodes.CODE_GET_USER_FAIL, Constants.MSG_AUTH_NO_USER));


    }

    // A -> [()] -> B


    @Override
    public ApiResponse getUserList() {

        Iterable<Users> users = this.usersRepository.findAll(Sort.by("id"));
        final List<Users> userList = new ArrayList<>();
        users.forEach(u -> {
            userList.add(u);
        });
        // ArrayList<Users> sortedList = new ArrayList<>();
        /* Another way -
         for(Users u : users) {
         userList.add(u);
         }*/

        if (userList.isEmpty()) {
            return ApiResponse.successResponse(Constants.ErrorCodes.CODE_NO_DATA,
                    Constants.MSG_NO_DATA,
                    Collections.EMPTY_LIST);
        }
        return ApiResponse.successResponse(Constants.ErrorCodes.CODE_SUCCESS,
                Constants.MSG_STATUS_SUC,
                new ArrayList<>(userList));


        //Stream.of(users).collect(Collectors.toList());

        /*List<String> list = Arrays.asList("Shreya", "divisha", "Turior");
        list.stream().collect(Collectors.joining(":"));*/
    }
}


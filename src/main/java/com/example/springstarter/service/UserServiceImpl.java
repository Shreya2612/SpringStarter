package com.example.springstarter.service;

import com.example.springstarter.entity.AuthUser;
import com.example.springstarter.entity.Users;
import com.example.springstarter.model.response.ApiResponse;
import com.example.springstarter.model.request.UserModel;
import com.example.springstarter.model.response.UserResponse;
import com.example.springstarter.repository.AuthUserRepository;
import com.example.springstarter.repository.UsersRepository;
import com.example.springstarter.util.Constants;
import com.example.springstarter.util.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.*;


@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UsersRepository usersRepository;    //this is created for the new JPA Users Repo.
    @Autowired
    private AuthUserRepository authUserRepository;

    @Override
    public ApiResponse addUser(UserModel model) {    // username is coming as well
        boolean valid = Utility.userValidation(model);  // user validation part
        if (valid) {
            Users users = new Users();
            users.setFirstName(model.getFirstName());
            users.setLastName(model.getLastName());
            users.setContact(Long.parseLong(model.getContact()));
            users.setMail(model.getMail());
            Users savedUser = new Users();  // these two limes of code were added just to make sure that if users are not being added to
            AuthUser savedAuthUser = new AuthUser(); //AuthUser Table then they should also not be present in Users Table..done below(**) Eg: entering 2 users with same Username.
            try {
                savedUser = this.usersRepository.save(users);
                AuthUser authUser = new AuthUser();
                authUser.setUserid(savedUser.getId());
                authUser.setUserName(model.getUsername());
                String password = model.getPassword();
                String salt = Utility.generateSalt();
                String hash = Utility.computeHash(password, salt);
                authUser.setHash(hash);
                authUser.setSalt(salt);
                savedAuthUser = this.authUserRepository.save(authUser);

                UserResponse ob = new UserResponse();  // created this because we don't want every field i.e my entire Entity to go in my api response.Mainly done because authname field was going null.
                ob.setId(savedUser.getId()); //by this we just return one id to our user to show that user has been created.
                return new ApiResponse(
                        Arrays.asList(ob), //therefore by ob we have control over which fields are being send as response.
                        Constants.MSG_CREATE_USER,
                        Constants.MSG_STATUS_SUC,
                        Constants.ErrorCodes.CODE_SUCCESS
                );
            } catch (DataIntegrityViolationException e) { //occurs only when my constraint  is violated(here 2 constraints that is id i.e. primary key should not be null which is managed by DB as we are not entering it:auto increment)
                LOG.error(e.getMessage()); // and second is mail should be unique and hence we are handling that

                if (savedUser.getId() == null) {
                    return ApiResponse.failResponse(Constants.ErrorCodes.CODE_FAIL, "Mail id already exists!");
                }
                if (savedUser.getId() != null && savedAuthUser.getId() == null) { //(**)
                    this.usersRepository.delete(savedUser);
                    return ApiResponse.failResponse(Constants.ErrorCodes.CODE_FAIL, "Username is already taken");
                }//here passing username only in msg because that is my only constraint in Auth User Table and this exception occurs only when constraint is violated.

            } catch (Exception e) {
                return ApiResponse.failResponse(Constants.ErrorCodes.CODE_FAIL, Constants.MSG_ERR_GENERIC);
            }

        }
        return ApiResponse.failResponse(Constants.ErrorCodes.CODE_USER_CREATE_FAIL, Constants.MSG_CREATE_USER_VALID_FAIL);
    }

    @Override //Not using this one currently..in Postman  no request made for this
    public ApiResponse getUser(String firstName, Long id) {    // one which is coming from controller-yes
        Optional<Users> userOpt = this.usersRepository.findOne(
                (root, criteriaQuery, criteriaBuilder) -> {
                    Predicate fPred = criteriaBuilder.equal(root.get("firstName"), firstName);  //this green is column of my table.
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


    }

    @Override
    public ApiResponse getUser(Long id) {
        Optional<Users> userOpt = this.usersRepository.findById(id);
        if (userOpt.isPresent()) {
/*
            Optional<AuthUser> authUserOpt = this.authUserRepository.findOne(
                    (root, criteriaQuery, criteriaBuilder) -> {
                        Predicate p = criteriaBuilder.equal(root.get("userid"), id);
                        return criteriaBuilder.and(p);
                    });

            Now using @Query custom method so these JPA methods are not needed
            Doing this becoz we want to get result by single query without hitting DB twice
            as we were initially doing here so using JPQL but
            note: we can't use them for some methods like delete api as delete can't be used with Join.*/

            return userOpt
                    .map(users -> {
//                    Gson gson = new Gson();
//                    String json =  gson.toJson(users);

                                UserResponse ob = new UserResponse();
                                ob.setId(users.getId());
                                ob.setFirstName(users.getFirstName());
                                ob.setLastName(users.getLastName());
                                ob.setMail(users.getMail());
                                ob.setContact(users.getContact());
                                ob.setUserName(users.getAuthUser().getUserName());
                                // Here: users.getAuthUser().getUserName() --> since AuthUsers is mapped in Users Entity as OnetoMany and both tables are now joined so directly users.getAuthUser() can be done.
                                //users (mapped variable), getAuthUser from getter of Users Entity and getUsername from AuthUser Entity as
                                // both table are now joined by JPQL and it works by using Entities only unlike SQL queries working on field names.

                                LOG.info(ob.toString());

                                return new ApiResponse(
                                        Arrays.asList(ob),
                                        Constants.MSG_USER_FOUND,
                                        Constants.MSG_STATUS_SUC,
                                        Constants.ErrorCodes.CODE_SUCCESS);
                            }
                    )


                    .orElse(new ApiResponse(
                            Collections.emptyList(),
                            Constants.MSG_AUTH_NO_USER,
                            Constants.MSG_STATUS_FAIL,
                            Constants.ErrorCodes.CODE_GET_USER_FAIL
                    ));
        }
        return new ApiResponse(
                Collections.emptyList(),
                Constants.MSG_AUTH_NO_USER,
                Constants.MSG_STATUS_FAIL,
                Constants.ErrorCodes.CODE_GET_USER_FAIL
        );
    }
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


    @Override
    public ApiResponse deleteUser(Long id) { //delete can't be used with Joins in query so here JPQL can be used.
        // and even if we use any query that will be complex and here simply we can entire delete data with JPA.
        try {
            Optional<Users> userOpt = this.usersRepository.findById(id);
            if (userOpt.isPresent()) {
                Optional<AuthUser> authUserOpt = this.authUserRepository.findOne((root, criteriaQuery, criteriaBuilder) -> {
                    Predicate pred = criteriaBuilder.equal(root.get("userid"), id);
                    return criteriaBuilder.and(pred);
                });
                UserResponse ob = new UserResponse();
                authUserOpt.ifPresent(user -> {
                    this.authUserRepository.delete(user);
                    this.usersRepository.deleteById(id); //deleteBy doesn't return anything so no ref. var. required here
                    Users users = userOpt.get();
                    AuthUser authUser = authUserOpt.get();
                    ob.setId(users.getId());
                    ob.setFirstName(users.getFirstName());
                    ob.setLastName(users.getLastName());
                    ob.setMail(users.getMail());
                    ob.setContact(users.getContact());
                    ob.setUserName(authUser.getUserName());
                });

                return new ApiResponse(
                        Arrays.asList(ob),
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
        return usersOptional.map(u -> {            //converting/mapping Users to ApiResponse
            u.setFirstName(model.getFirstName()); // here not checking isPresent because .map property works only when returned value is not null if it is then
            u.setLastName(model.getLastName());  // it goes to .orElse block
            u.setContact(Long.parseLong(model.getContact()));
            u.setMail(model.getMail());
            Users saved = usersRepository.save(u);
            UserResponse ob = new UserResponse();
            ob.setId(saved.getId());
            return ApiResponse.successResponse(Constants.ErrorCodes.CODE_SUCCESS, Constants.MSG_USER_UPDATE, Arrays.asList(ob));
        }).orElse(ApiResponse.failResponse(Constants.ErrorCodes.CODE_GET_USER_FAIL, Constants.MSG_AUTH_NO_USER));


    }

    // A -> [()] -> B


    @Override
    public ApiResponse getUserList() {

        Iterable<Object[]> users = this.usersRepository.getUserListByDistinctFirstName(Sort.by("id"));

        //Iterable<Users> users = this.usersRepository.getUserList(Sort.by("id")); //--> JPQL
        final List<UserResponse> userList = new ArrayList<>();

        users.forEach(u -> {
            UserResponse ob = new UserResponse();
            ob.setId((long) u[1]);
            ob.setFirstName((String) u[0]);
            ob.setLastName((String) u[2]);
            ob.setMail((String) u[3]);
            ob.setContact((Long) u[4]);
            ob.setUserName((String) u[5]);
            userList.add(ob);
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


package com.example.springstarter.controller;


import com.example.springstarter.model.ApiResponse;
import com.example.springstarter.model.LoginModel;
import com.example.springstarter.model.UserModel;
import com.example.springstarter.repository.UserRepository;
import com.example.springstarter.repository.UsersRepository;
import com.example.springstarter.service.AuthService;
import com.example.springstarter.service.UserService;
import com.example.springstarter.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/api/user")
public class UserController {


    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.POST)
    public ApiResponse create(@RequestBody UserModel model) {
        return this.userService.addUser(model);     //return response from service
    }

    /*
    * Path Parameter: /user/{id} @PathVariable
    * Request Parameter: /user?id=2&name=Shivam  @RequestParam
    * */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ApiResponse get(@PathVariable Long id) {
        return this.userService.getUser(id);
    }

    @RequestMapping(method = RequestMethod.GET)   //currently not using this..written just to understand the @RequestParam parameter of GET
    public ApiResponse getUserReqParam(@RequestParam("username") String username, @RequestParam("id") Long id) {
        return this.userService.getUser(username, id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ApiResponse delete(@PathVariable Long id) {
        return this.userService.deleteUser(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ApiResponse update(@PathVariable Long id,@RequestBody UserModel model) {                   // update method here is called Handler
        return this.userService.updateUser(id,model);
    }

    @GetMapping(value = "/list")
    public ApiResponse getList() {
        return this.userService.getUserList();
    }
}

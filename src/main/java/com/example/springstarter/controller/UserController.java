package com.example.springstarter.controller;


import com.example.springstarter.model.ApiResponse;
import com.example.springstarter.model.LoginModel;
import com.example.springstarter.model.UserModel;
import com.example.springstarter.service.AuthService;
import com.example.springstarter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {


    @Autowired
    private UserService userService;



    @RequestMapping(method = RequestMethod.POST)
    public ApiResponse create(@RequestBody UserModel model) {
        return this.userService.addUser(model);
    }

    /*
    * Path Parameter: /user/{id} @PathVariable
    * Request Parameter: /user?id=2&name=Shivam  @RequestParam
    * */
    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public ApiResponse get(@PathVariable String username) {
        return this.userService.getUser(new UserModel(username));
    }

    @RequestMapping(method = RequestMethod.GET)
    public ApiResponse getUserReqParam(@RequestParam("username") String username, @RequestParam("id") Integer id) {
        return this.userService.getUser(new UserModel(username));
    }
}

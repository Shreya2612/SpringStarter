package com.example.springstarter.controller;

import com.example.springstarter.model.response.ApiResponse;
import com.example.springstarter.model.request.LoginModel;
import com.example.springstarter.model.request.UpdatePasswordModel;
import com.example.springstarter.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    @Autowired
    private AuthService authService;
    /**
     *
     * { "username": "", "password": "" }
     */

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ApiResponse login(@RequestBody LoginModel model) {
        return this.authService.login(model);
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.PATCH)  //this value should not be verb
    public ApiResponse updpwd(@RequestBody UpdatePasswordModel model, @PathVariable String username) {
        return this.authService.updpwd(username, model);
    }



}

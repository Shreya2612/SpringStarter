package com.example.springstarter.controller;

import com.example.springstarter.model.ApiResponse;
import com.example.springstarter.model.LoginModel;
import com.example.springstarter.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
}

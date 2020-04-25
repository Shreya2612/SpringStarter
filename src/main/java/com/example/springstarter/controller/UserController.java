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
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ApiResponse get(@PathVariable Long id) {
        UserModel model = new UserModel();
        model.setId(id);
        return this.userService.getUser(id);
    }

    @RequestMapping(method = RequestMethod.GET)   //currently not using this..written just to under. the parameters of GET
    public ApiResponse getUserReqParam(@RequestParam("username") String username, @RequestParam("id") Integer id) {
        return this.userService.getUser(username);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ApiResponse delete(@PathVariable Long id) {
        UserModel model = new UserModel();
        model.setId(id);
        return this.userService.deleteUser(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ApiResponse update(@PathVariable Long id,@RequestBody UserModel model) {                   //Handler
        return this.userService.updateUser(id,model);
    }

}

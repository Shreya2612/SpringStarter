package com.example.springstarter.controller;


import com.example.springstarter.model.response.ApiResponse;
import com.example.springstarter.model.request.UserModel;
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
        return this.userService.addUser(model);     //return response from service
    }

    /*
     * Path Parameter: /user/{id} @PathVariable
     * Request Parameter: /user?id=2&name=Shivam  @RequestParam
     * */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)  //get user by id
    public ApiResponse get(@PathVariable Long id) {
        return this.userService.getUser(id);
    }

    @RequestMapping(method = RequestMethod.GET) // get user by username
    //2 get apis written just to understand the @RequestParam parameter of GET.
    public ApiResponse getUserReqParam(@RequestParam("username") String username, @RequestParam("id") Long id) {
        return this.userService.getUser(username, id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ApiResponse delete(@PathVariable("id")Long id) {
        return this.userService.deleteUser(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ApiResponse update(@PathVariable Long id, @RequestBody UserModel model) {  // update method here is called Handler
        return this.userService.updateUser(id, model);
    }

    @GetMapping(value = "/list")
    public ApiResponse getList() {
        return this.userService.getUserList();

    }


}

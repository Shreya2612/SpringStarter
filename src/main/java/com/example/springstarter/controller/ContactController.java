package com.example.springstarter.controller;


import com.example.springstarter.model.request.ContactModel;
import com.example.springstarter.model.response.ApiResponse;
import com.example.springstarter.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contact")

public class ContactController {

    @Autowired
    private ContactService contactService;

    @GetMapping(value = "/list/{userId}")  //here id being passed is id of Users table
    public ApiResponse getContact(@PathVariable("userId") Long userId) {
        return this.contactService.getContactList(userId);
    }

    @PostMapping(value = "/{userId}") // id being passed here is id(PK) of Users table or userid of auth table
    public ApiResponse createContact(@PathVariable("userId") Long userId, @RequestBody List<ContactModel> model) {
        return this.contactService.addContacts(userId, model);
    }


}

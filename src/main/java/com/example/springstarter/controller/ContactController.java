package com.example.springstarter.controller;


import com.example.springstarter.model.request.ContactModel;
import com.example.springstarter.model.response.ApiResponse;
import com.example.springstarter.service.ContactService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contact")

// All the userId being passed in these APIs are id(PK) of Users table or userId of auth table
public class ContactController {

    @Autowired
    private ContactService contactService;


    @GetMapping(value = "/list/{userId}")  //Fetches data from Contact table and shows list of Contacts corresponding to the passed userid.
    public ApiResponse getContact(@PathVariable("userId") Long userId) {
        return this.contactService.getContactList(userId);
    }

    @PostMapping(value = "/{userId}")  //Creates new multiple contacts in ContactList table and userid here is the id corresponding to which contact will be created.
    public ApiResponse createContact(@PathVariable("userId") Long userId, @RequestBody List<ContactModel> model) {
        return this.contactService.addContacts(userId, model);
    }

     @DeleteMapping(value="/{contactId}") //Deletes multiple users of ContactList as well as its corresponding mapping in Contact table by its ContactID.//will first delete from contact table then contact_list because of F.K contactid in contact table
    public ApiResponse delete(@RequestBody List<Long> contactIds) {
        //List<Long> ids = new Gson().fromJson(body, new TypeToken<List<Long>>(){}.getType());
        return this.contactService.deleteContacts(contactIds);
    }

    @PutMapping(value="/{userId}")
    public ApiResponse update(@PathVariable("userId") Long userId , @RequestBody ContactModel model) {
        return this.contactService.updateContactList(userId , model);
    }

    @GetMapping(value="/{userId}")  // to retrieve  single contact from ContactList Table based on id.
    public ApiResponse getSingleContact(@PathVariable Long userId) {
        return this.contactService.getContact(userId);
    }

}

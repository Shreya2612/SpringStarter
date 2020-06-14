package com.example.springstarter.service;

import com.example.springstarter.model.request.ContactModel;
import com.example.springstarter.model.response.ApiResponse;

import java.util.List;

public interface ContactService {

    ApiResponse getContactList(Long userId);

    ApiResponse addContacts(Long userId, List<ContactModel> contactModels);

    //ApiResponse deleteContact(Long userId);   ---- to delete only one Id at a time

    ApiResponse deleteContacts(List<Long> userId); // --- for multiple deletions


    ApiResponse updateContactList(Long userId, ContactModel model);

    ApiResponse getContact(Long userId);
}

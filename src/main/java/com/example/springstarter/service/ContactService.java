package com.example.springstarter.service;

import com.example.springstarter.model.request.ContactModel;
import com.example.springstarter.model.response.ApiResponse;

import java.util.List;

public interface ContactService {

    ApiResponse getContactList(Long userId);

    ApiResponse addContacts(Long userId, List<ContactModel> contactModels);
}

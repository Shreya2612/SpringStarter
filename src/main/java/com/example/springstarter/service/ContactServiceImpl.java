package com.example.springstarter.service;

import com.example.springstarter.entity.Contact;
import com.example.springstarter.entity.ContactList;
import com.example.springstarter.entity.Users;
import com.example.springstarter.model.request.ContactModel;
import com.example.springstarter.model.response.ApiResponse;
import com.example.springstarter.model.response.ContactResponse;
import com.example.springstarter.repository.ContactListRepository;
import com.example.springstarter.repository.ContactRepository;
import com.example.springstarter.repository.UsersRepository;
import com.example.springstarter.util.Constants;
import com.example.springstarter.util.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private UsersRepository usersRepository;    //this is created for the new JPA Users Repo.
    @Autowired
    private ContactListRepository contactListRepository;
    @Autowired
    private ContactRepository contactRepository;


    @Override
    public ApiResponse getContactList(Long userId) {

        try {
            Optional<Users> optUser = this.usersRepository.findById(userId);

            if (!optUser.isPresent()) {
                return ApiResponse.failResponse(
                        Constants.ErrorCodes.CODE_FAIL,
                        Constants.MSG_AUTH_NO_USER
                );
            }

            Iterable<Object[]> list = this.contactRepository.getContact(userId); //--> JPQL
            final List<ContactResponse> userList = new ArrayList<>();

            list.forEach(c -> {
                ContactResponse ob = new ContactResponse();
                ob.setContactId((Long) c[0]);
                ob.setFirstName((String) c[1]);
                ob.setLastName((String) c[2]);
                ob.setMail((String) c[4]);
                ob.setContact((Long) c[3]);

                userList.add(ob);
            });

            if (userList.isEmpty()) {
                return ApiResponse.successResponse(Constants.ErrorCodes.CODE_NO_DATA,
                        Constants.MSG_NO_DATA,
                        Collections.EMPTY_LIST);
            }
            return ApiResponse.successResponse(Constants.ErrorCodes.CODE_SUCCESS,
                    Constants.MSG_STATUS_SUC,
                    new ArrayList<>(userList));
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse(
                    Collections.emptyList(),
                    Constants.MSG_ERR_GENERIC,
                    Constants.MSG_STATUS_FAIL,
                    Constants.ErrorCodes.CODE_FAIL);
        }

    }

    @Override
    public ApiResponse addContacts(Long userId, List<ContactModel> models)   // id coming here is id(PK) of Users table or userid of auth table
    {
        try {
            Optional<Users> optUser = this.usersRepository.findById(userId);

            if (optUser.isPresent()) {
                List<ContactList> contactRows = new ArrayList<>();
                models.forEach(model -> {
                    ContactList contactListTbl = new ContactList();
                    contactListTbl.setFirstName(model.getFirstName());
                    contactListTbl.setLastName(model.getLastName());
                    contactListTbl.setNumber(Long.parseLong(model.getContact()));
                    contactListTbl.setMail(model.getMail());

                    contactRows.add(contactListTbl);
                });


                Iterable<ContactList> savedContactList = this.contactListRepository.saveAll(contactRows);//user created in ContactList table
                //[......] -> [contactId, userId] -> saveContact table
                List<Contact> contactTblList = Utility.stream(savedContactList).map(contact -> {
                    Contact contactTbl = new Contact();
                    contactTbl.setContactid(contact.getId()); //to set contactid in Contact table
                    contactTbl.setUserid(optUser.get().getId()); //to set userid in Contact table
                    return contactTbl;
                }).collect(Collectors.toList());


                Iterable<Contact> savedContacts = this.contactRepository.saveAll(contactTblList);
                List<ContactResponse> res = Utility.stream(savedContacts).map(contact ->{
                    ContactResponse r = new ContactResponse();
                    r.setId(contact.getId());
                    return r;
                }).collect(Collectors.toList());


                return ApiResponse.successResponse(
                        Constants.ErrorCodes.CODE_CREATE_SUCCESS,
                        "Users added in Contact List",
                        new ArrayList<>(res));

            }
            return new ApiResponse(
                    Collections.emptyList(),
                    Constants.MSG_AUTH_NO_USER,
                    Constants.MSG_STATUS_FAIL,
                    Constants.ErrorCodes.CODE_FAIL);

        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse(
                    Collections.emptyList(),
                    Constants.MSG_ERR_GENERIC,
                    Constants.MSG_STATUS_FAIL,
                    Constants.ErrorCodes.CODE_FAIL
            );
        }
    }

}

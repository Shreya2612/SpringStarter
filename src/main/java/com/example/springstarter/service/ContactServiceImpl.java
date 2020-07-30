package com.example.springstarter.service;

import com.example.springstarter.entity.Contact;
import com.example.springstarter.entity.ContactList;
import com.example.springstarter.entity.Users;
import com.example.springstarter.model.request.ContactModel;
import com.example.springstarter.model.response.ApiResponse;
import com.example.springstarter.model.response.ContactResponse;
import com.example.springstarter.model.response.CreateContactResponse;
import com.example.springstarter.model.response.DataItem;
import com.example.springstarter.repository.ContactListRepository;
import com.example.springstarter.repository.ContactRepository;
import com.example.springstarter.repository.UsersRepository;
import com.example.springstarter.util.Constants;
import com.example.springstarter.util.Utility;
import com.google.gson.JsonObject;
import com.sun.org.apache.xerces.internal.xs.StringList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ContactServiceImpl implements ContactService {
    //   Long lastUserId;

    @Autowired
    private UsersRepository usersRepository;    //this is created for the new JPA Users Repo.
    @Autowired
    private ContactListRepository contactListRepository;
    @Autowired
    private ContactRepository contactRepository;


    @Override
    public ApiResponse getContactList(Long userId) {

        try {
            Optional<Users> optUser = this.usersRepository.findById(userId); //to chk whether the coming userid is userid of authorized user or not, so if it is here in users table that means it has to be there in Auth user table as well becoz when we are saving a users info in Users table that time only it is being saved in Auth user table as well(refer UserServiceImpl->create user).

            if (!optUser.isPresent()) {
                return ApiResponse.failResponse(
                        Constants.ErrorCodes.CODE_FAIL,
                        Constants.MSG_AUTH_NO_USER  //user is not Authorized user
                );
            }
            // if Authorized user then
            Iterable<Object[]> list = this.contactRepository.getContact(userId, Sort.by("userid")); //--> JPQL(Query in Repository)
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

    @Override  //creating new multiple/single contact
    public ApiResponse addContacts(Long userId, List<ContactModel> models) // id coming here is id(PK) of Users table or userid of auth table
    {
        try {
            Optional<Users> optUser = this.usersRepository.findById(userId); //if user present in user repo it has to be present in auth_user as well coz we r saving in that way.Ref: UserServiceImpl Class
            if (optUser.isPresent()) {
                List<ContactList> contactRows = new ArrayList<>();
                models.forEach(model -> {
                    // validation for not saving same nos.
                    Iterable<Contact> idChk = this.contactRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
                        Predicate p = criteriaBuilder.equal(root.get("userid"), userId);
                        return criteriaBuilder.and(p);
                    });
                    List<Long> contactId = Utility.stream(idChk).map(i -> {
                        return i.getContactid();
                    }).collect(Collectors.toList());

                    Optional<ContactList> contactChk = this.contactListRepository.findOne((root, criteriaQuery, criteriaBuilder) -> {
                        Predicate p = criteriaBuilder.equal(root.get("number"), model.getContact());
                        CriteriaBuilder.In<Long> inClause = criteriaBuilder.in(root.get("id"));
                        for (Long id : contactId) {
                            inClause.value(id);
                        }
                        //  criteriaQuery.select().where(inClause);
                        return criteriaBuilder.and(p, inClause);
                    });

                    if (!contactChk.isPresent()) { //if contact already does not exist
                        ContactList contactListTbl = new ContactList();
                        contactListTbl.setFirstName(model.getFirstName());
                        contactListTbl.setLastName(model.getLastName());
                        contactListTbl.setNumber(model.getContact());
                        contactListTbl.setMail(model.getMail());

                        contactRows.add(contactListTbl);
                    }
                });
                Iterable<ContactList> savedContactList = this.contactListRepository.saveAll(contactRows);//contacts created in ContactList table
                //[......] -> [contactId, userId] -> saveContact table
                List<Contact> contactTblList = Utility.stream(savedContactList).map(contact -> {
                    Contact contactTbl = new Contact();
                    contactTbl.setContactid(contact.getId()); //to set contactid in Contact table
                    contactTbl.setUserid(optUser.get().getId()); //to set userid in Contact table
                    return contactTbl;
                }).collect(Collectors.toList());
                Iterable<Contact> savedContacts = this.contactRepository.saveAll(contactTblList);
/*
Steps to create response for this without using model class :
                { success: [], failure: [] }
                ArrayList<String> success = new ArrayList<>();
                ArrayList<String> failure = new ArrayList<>();
                String collect = success.stream().map(i -> "\"" + i + "\"").collect(Collectors.joining());
                String collect2 = failure.stream().map(i -> "\"" + i + "\"").collect(Collectors.joining());
                JsonObject ob = Utility.toJson("{\"success\": " + collect + ", \"failure\": " + collect2 + " }");

*/
                int contactSize = models.size() - contactRows.size();
                if (contactSize == 0) {
                    List<Long> successIds = Utility.stream(savedContactList).map(contact -> {
                        return contact.getId();
                    }).collect(Collectors.toList());

                    return ApiResponse.successResponse(
                            Constants.ErrorCodes.CODE_CREATE_SUCCESS,
                            "All users have been added in Contact List",
                            new ArrayList<>(successIds));
                }
                else {
                    int flag=0;
                    List<ContactModel> failNums = new ArrayList<>();
                    for(ContactModel user1 : models) {
                        flag=0;
                        for(ContactList user2 : contactRows) {
                            if(user1.getContact().equals(user2.getNumber())) // how this works ?
                            {
                                flag=1;break;
                            }
                        }
                       if(flag==0) failNums.add(user1);
                    }

                    List<Long> failNumbers = Utility.stream(failNums).map(f -> {
                        return f.getContact();
                    }).collect(Collectors.toList());

                    List<Long> successIds = Utility.stream(contactRows).map(contact -> {
                        return contact.getId();
                    }).collect(Collectors.toList());

                    return ApiResponse.successResponse(
                            Constants.ErrorCodes.CODE_CREATE_SUCCESS,
                            "Only new users have been added in Contact List",
                            new ArrayList<>((List<CreateContactResponse>)new CreateContactResponse((List<DataItem>) new DataItem(successIds,failNumbers)))
                    );
                }
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

    @Override
    public ApiResponse deleteContacts(List<Long> contactIds) { //can deleteAll be used here - No because that is for entire entity.

        try {
            String ids = contactIds.stream().map(i -> String.valueOf(i)).collect(Collectors.joining(","));//contactids coming are of type long so converted to String
            List<Long> contactids = this.contactRepository.deleteByContactIds(ids);
            if (contactids.size() > 0) {
                contactids = this.contactListRepository.deleteByIds(contactIds.stream().map(i -> String.valueOf(i)).collect(Collectors.joining(",")));
                return ApiResponse.successResponse(
                        Constants.ErrorCodes.CODE_SUCCESS,
                        Constants.MSG_USER_DELETE,
                        new ArrayList<>(contactids));
            } else {
                return new ApiResponse(
                        Collections.emptyList(),
                        "Please send valid contact ids",
                        Constants.MSG_STATUS_FAIL,
                        Constants.ErrorCodes.CODE_FAIL);

            }

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

        /*  TO DELETE SINGLE USER WITH USERID IN CONTACTLIST TABLE
        Optional<ContactList> contactListOpt = this.contactListRepository.findById(userId);

        if (contactListOpt.isPresent()) {
           // ContactList c = contactList.get(); any problem if I don't write this line

            Optional<Contact> contactOpt = this.contactRepository.findOne((root, criteriaQuery, criteriaBuilder) -> {
                Predicate p = criteriaBuilder.equal(root.get("contactid"), userId);
                return criteriaBuilder.and(p);
            });
            contactOpt.ifPresent(c -> {  this.contactRepository.delete(c); });
            //not working if don't use Lambda here written below?
            // contactOpt.ifPresent(this.contactRepository.delete(contactOpt.get());---not working??
            this.contactListRepository.deleteById(userId); //can also be deleted with contactListOpt.get()
            ContactList contactList = contactListOpt.get();
            ContactResponse res = new ContactResponse();
            res.setId(contactList.getId());
            res.setFirstName(contactList.getFirstName());
            res.setLastName(contactList.getLastName());
            res.setMail(contactList.getMail());
            res.setContact(contactList.getNumber());

            return ApiResponse.successResponse(
                   Constants.ErrorCodes.CODE_SUCCESS,
                   Constants.MSG_USER_DELETE,
                   Arrays.asList(res));

        }
        return new ApiResponse(
                Collections.EMPTY_LIST,
                Constants.MSG_AUTH_NO_USER,
                Constants.MSG_STATUS_FAIL,
                Constants.ErrorCodes.CODE_FAIL);}*/


    @Override
    public ApiResponse updateContactList(Long userId, ContactModel model) {

        Optional<ContactList> contactListOpt = this.contactListRepository.findById(userId);
        try {
            if (contactListOpt.isPresent()) {
                ContactList contactList = contactListOpt.get();
                contactList.setFirstName(model.getFirstName());
                contactList.setLastName(model.getLastName());
                contactList.setMail(model.getMail());
                contactList.setNumber(model.getContact());

                ContactList savedContactList = this.contactListRepository.save(contactList);
                ContactResponse res = new ContactResponse();
                res.setId(savedContactList.getId());
                return ApiResponse.successResponse(
                        Constants.ErrorCodes.CODE_SUCCESS,
                        Constants.MSG_USER_UPDATE,
                        Arrays.asList(res));

            }
            return ApiResponse.failResponse(
                    Constants.ErrorCodes.CODE_FAIL,
                    Constants.MSG_AUTH_NO_USER);
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
    public ApiResponse getContact(Long userId) {

        Optional<ContactList> contactListOpt = this.contactListRepository.findById(userId);
        if (contactListOpt.isPresent()) {
            ContactList contactList = contactListOpt.get();
            ContactResponse res = new ContactResponse();
            res.setFirstName(contactList.getFirstName());
            res.setLastName(contactList.getLastName());
            res.setMail(contactList.getMail());
            res.setContact(contactList.getNumber());

            return new ApiResponse(
                    Arrays.asList(res),
                    Constants.MSG_USER_FOUND,
                    Constants.MSG_STATUS_SUC,
                    Constants.ErrorCodes.CODE_SUCCESS);
        }
        return new ApiResponse(
                Collections.emptyList(),
                Constants.MSG_AUTH_NO_USER,
                Constants.MSG_STATUS_FAIL,
                Constants.ErrorCodes.CODE_GET_USER_FAIL);
    }


}

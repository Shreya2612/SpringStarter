package com.example.springstarter.repository;

import com.example.springstarter.entity.ContactList;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ContactListRepository extends CrudRepository<ContactList, Long>, JpaSpecificationExecutor<ContactList>, PagingAndSortingRepository<ContactList , Long> {



}



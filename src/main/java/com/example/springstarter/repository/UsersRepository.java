package com.example.springstarter.repository;

import com.example.springstarter.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UsersRepository extends CrudRepository<Users, Long>, JpaSpecificationExecutor<Users>, PagingAndSortingRepository<Users, Long> {
    // Users findOneById(Long id);


}

/*
THESE CRUD REPOSITORIES ARE BEING USED IN ENTIRE PROJ. AFTER INTRO OF SPRING DATA JPA
1.USERSREPOSITORY INTERFACE
2.AUTHUSERREPOSITORY INTERFACE*/

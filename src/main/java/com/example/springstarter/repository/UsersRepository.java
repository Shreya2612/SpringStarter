package com.example.springstarter.repository;

import com.example.springstarter.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<Users, Long>, JpaSpecificationExecutor<Users> {
   // Users findOneById(Long id);


}
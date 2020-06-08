package com.example.springstarter.repository;

import com.example.springstarter.entity.Users;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public interface UsersRepository extends CrudRepository<Users, Long>, JpaSpecificationExecutor<Users>, PagingAndSortingRepository<Users, Long> {
    @Query(
            /* nativeQuery similar to SQL:
             value = "select * from users u join auth_user a on u.id = a.userid where u.id = ?1",
             nativeQuery = true
             */
            //JPQL:
            value = "select u , a from Users u join AuthUser a on u.id = a.userid where u.id = ?1")
    Users getUser(Long id);


    @Query(value = "select u , a from Users u join AuthUser a on u.id = a.userid ")
    List<Users> getUserList(Sort sort);


    @Query("select u.firstName, u.id, u.lastName, u.mail, u.contact, a.userName from Users u join AuthUser a on u.id = a.userid where u.id in (select min(d.id) from Users d group by d.firstName)")
    List<Object[]> getUserListByDistinctFirstName(Sort sort);


}// 2 methods of doing @Query : by nativeQuery and JPQL , JPQL has added adv. like sorting and Pagination over nativeQuery Method.

/*
THESE CRUD REPOSITORIES ARE BEING USED IN ENTIRE PROJ. AFTER INTRO OF SPRING DATA JPA
1.USERSREPOSITORY INTERFACE
2.AUTHUSERREPOSITORY INTERFACE
3.CONTACTLIST REPOSITORY
4.CONTACT REPOSITORY

When earlier used Map as DB then used UserRepository and LoginRepository and its implementations*/


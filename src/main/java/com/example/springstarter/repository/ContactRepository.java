package com.example.springstarter.repository;


import com.example.springstarter.entity.Contact;
import com.example.springstarter.entity.Users;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ContactRepository extends CrudRepository<Contact, Long>, JpaSpecificationExecutor<Contact>, PagingAndSortingRepository<Contact, Long> {

    @Query(value = "select c.contactid , cl.firstName, cl.lastName, cl.number, cl.mail from Contact c join ContactList cl on c.contactid = cl.id where c.userid = ?1")
    List<Object[]> getContact(Long id , Sort sort);

    /*@Query(value = "delete from contact where contactid in (select unnest\\:\\:bigint from unnest(string_to_array(?1, ','))) returning contactid", nativeQuery = true)
    List<Long> deleteByContactIds(String ids);
*/
    @Query(value = "delete from contact where contactid in (select cast(unnest as bigint) from unnest(string_to_array(?1, ','))) returning contactid", nativeQuery = true)
    List<Long> deleteByContactIds(String ids);

}
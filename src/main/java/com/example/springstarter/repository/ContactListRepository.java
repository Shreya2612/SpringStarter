package com.example.springstarter.repository;

import com.example.springstarter.entity.ContactList;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ContactListRepository extends CrudRepository<ContactList, Long>, JpaSpecificationExecutor<ContactList>, PagingAndSortingRepository<ContactList, Long> {
    @Query(value = "delete from contact_list where id in (select unnest\\:\\:bigint from unnest(string_to_array(?1, ','))) returning id", nativeQuery = true)
    List<Long> deleteByIds(String ids);

}



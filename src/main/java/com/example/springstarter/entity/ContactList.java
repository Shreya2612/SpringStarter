package com.example.springstarter.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
@Getter
@Setter
@Data
@Table(name = "contact_list")
@Entity
public class ContactList implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, nullable = false)
    private Long id;

    @OneToOne(mappedBy = "contactList")
    private Contact contact;    // here for ContactList this instance of Contact Entity i.e contact is like a java class field
                               // depicting one-to-one mapping "

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "number")
    private Long number;

    @Column(name = "mail")
    private String mail;

    
}
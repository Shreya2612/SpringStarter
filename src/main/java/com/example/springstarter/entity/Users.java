package com.example.springstarter.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Getter
@Setter
@Table(name = "users")
public class Users implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",insertable = false, nullable = false, unique = true)
    private Long id;

    @OneToOne(mappedBy = "users")
    private AuthUser authUser;

    @OneToMany(mappedBy = "users", fetch = FetchType.LAZY)
    private Set<Contact> contactTable;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "contact")
    private Long contact;

    @Column(name = "mail", nullable = false, unique = true)
    private String mail;

    
}
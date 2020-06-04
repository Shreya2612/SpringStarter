package com.example.springstarter.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Table(name = "contact")
@Entity
@Data
public class Contact implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userid", referencedColumnName = "id", insertable = false, updatable = false)
    private Users users;

    @OneToOne
    @JoinColumn(name = "contactid", referencedColumnName = "id", insertable = false, updatable = false)
    private ContactList contactList;


    @Column(name = "userid")
    private Long userid;

    @Column(name = "contactid")
    private Long contactid;

    
}
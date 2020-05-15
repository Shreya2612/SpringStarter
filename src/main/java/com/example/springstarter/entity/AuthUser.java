package com.example.springstarter.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Table(name = "auth_user")
@Entity
public class AuthUser implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "userid", referencedColumnName = "id", insertable=false,updatable = false)
    private Users users;

    @Column(name = "userid", nullable = false)
    private Long userid;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "hash", nullable = false)
    private String hash;

    @Column(name = "salt", nullable = false)
    private String salt;

    
}
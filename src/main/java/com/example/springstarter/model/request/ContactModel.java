package com.example.springstarter.model.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ContactModel {
    private String firstName;
    private String lastName;
    private String contact;
    private String mail;
}

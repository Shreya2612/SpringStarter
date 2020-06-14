package com.example.springstarter.model.request;

import lombok.*;

@AllArgsConstructor //will generate constructors with all combination of args.
@NoArgsConstructor
@Data //@Data itself includes @Getter and @Setter Annotations

public class ContactModel {
    private String firstName;
    private String lastName;
    private Long contact;
    private String mail;
}

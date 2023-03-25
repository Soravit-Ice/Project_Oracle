package com.project.projectmfec1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterModel {
    private Long id;
    private String firstName;
    private String lastName;
    private String studentId;
    private String email;
    private String userName;
    private String password;
    private String confirmPassword;
    private String image;


}

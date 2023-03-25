package com.project.projectmfec1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordModel {
    private Long id;
    private String password;
    private String confirmPassword;
}

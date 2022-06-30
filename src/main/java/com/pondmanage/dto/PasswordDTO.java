package com.pondmanage.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PasswordDTO {
    String oldPassword;
    String newPassword;
    String confirmPassword;
}

package com.pondmanage.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {
    private String name;
    private String email;
    private String password;
    private String address;
    private String phoneNumber;

}

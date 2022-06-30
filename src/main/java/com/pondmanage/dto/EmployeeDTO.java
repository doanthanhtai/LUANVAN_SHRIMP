package com.pondmanage.dto;

import com.pondmanage.model.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {
    private long id;
    private String name;
    private String phone;
    private Date onboardDate;
    private Float saliry;
    private String bankNo;
    private String bankAccountName;
    private String bankName;
    private String emailAndroidApp;
    private Account account;
}

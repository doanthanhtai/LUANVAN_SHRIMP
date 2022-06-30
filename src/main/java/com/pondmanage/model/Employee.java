package com.pondmanage.model;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "phone")
    private String phone;

    @Column(name = "onboard_date")
    private Date onboardDate;

    @Column(name = "saliry")
    private Float saliry;

    @Column(name = "bank_no")
    private String bankNo;

    @Column(name = "bank_account_name")
    private String bankAccountName;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "is_delete", columnDefinition = "boolean default false")
    private Boolean isDelete = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;
}

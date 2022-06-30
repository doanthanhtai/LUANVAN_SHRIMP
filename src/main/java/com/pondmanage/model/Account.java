package com.pondmanage.model;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "account")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email" , unique = true)
    @NotNull
    private String email;

    @Column(name = "password")
    @NotNull
    private String password;

    @Column(name = "address")
    private String address;

    @Column(name = "account_type")
    private String accountType;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @OneToMany(mappedBy = "account", cascade = CascadeType.DETACH)
    List<Product> productList = new ArrayList<>();

    @OneToMany(mappedBy = "account", cascade = CascadeType.DETACH)
    List<Zone> zoneList = new ArrayList<>();

    @OneToMany(mappedBy = "account", cascade = CascadeType.DETACH)
    List<Shrimp> shrimpList = new ArrayList<>();

    @OneToMany(mappedBy = "account", cascade = CascadeType.DETACH)
    List<Employee> employeeList = new ArrayList<>();
}

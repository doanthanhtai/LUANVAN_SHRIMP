package com.pondmanage.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "supplier")
    private String supplier;

    @Column(name = "enter_price")
    private Float enterPrice;

    @Column(name = "measure")
    private String measure;

    @Column(name = "is_delete")
    private Boolean isDeleted = false;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    List<ProductQuantity> productQuantityList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "diet_id", referencedColumnName = "id")
//    private Diet diet;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    List<Diet> dietList = new ArrayList<>();


//    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
//    List<EnterHistory> enterHistoryList = new ArrayList<>();
}

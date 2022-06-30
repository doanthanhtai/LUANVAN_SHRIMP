package com.pondmanage.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Table(name = "shrimp")
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Shrimp {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "supplier")
    private String supplier;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "price")
    private Float price;

    @Column(name = "create_time")
    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private Date createTime;

    @Column(name = "harvest_time")
    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private Date harvestTime;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pond_id", referencedColumnName = "id", unique = true)
    private Pond pond;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diet_id", referencedColumnName = "id")
    private Diet diet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

    @OneToMany(mappedBy = "shrimp", cascade = CascadeType.ALL)
    List<ProductHistory> productHistoryList = new ArrayList<>();
}

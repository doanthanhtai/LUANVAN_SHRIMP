package com.pondmanage.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product_history")
public class ProductHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shrimp_id", referencedColumnName = "id")
    protected Shrimp shrimp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_quantity_id", referencedColumnName = "id")
    protected ProductQuantity productQuantity;

    @Column(name = "quantity")
    protected Float quantity;

    @Column(name = "is_feed")
    protected Boolean isFeed;

    @Column(name = "result")
    protected String result;

    @Column(name = "used_time")
    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    protected Date createdTime;

    @Column(name = "updated_time")
    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    protected Date updatedTime;

    @Column(name = "last_account_email")
    protected String lastAccountEmail;

    @Column(name = "is_deleted")
    protected Boolean isDeleted = false;



}

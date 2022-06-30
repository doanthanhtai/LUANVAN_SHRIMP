package com.pondmanage.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.time.Instant;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "enter_history")
public class EnterHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "created_time")
    private Date createdTime;

    @Column(name = "updated_time")
    private Date updatedTime;

    @Column(name = "is_delete")
    private Boolean isDeleted = false;

    @Column(name = "quantity")
    private Float quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_quantity_id", referencedColumnName = "id")
    private ProductQuantity productQuantity;

}

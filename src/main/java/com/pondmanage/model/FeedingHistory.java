package com.pondmanage.model;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "feeding_history")
public class FeedingHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "created_time")
    private Instant createdTime;

    @Column(name = "result")
    private String result;

    @Column(name = "is_delete")
    private Boolean isDelete = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pond_id", referencedColumnName = "id")
    private Pond pond;
}

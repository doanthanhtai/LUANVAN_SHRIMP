package com.pondmanage.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "other_history")
@Data
public class OtherHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "cost")
    private Float cost;

    @Column(name = "description")
    private String description;

    @Column(name = "created_time")
    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private Instant createdTime;

    @Column(name = "updated_time")
    @LastModifiedDate
    private Instant updatedTime;

    @Column(name = "is_delete")
    private Boolean isDelete = false;

    @Column(name = "created_by")
    private String createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pond_id", referencedColumnName = "id")
    private Pond pond;
}

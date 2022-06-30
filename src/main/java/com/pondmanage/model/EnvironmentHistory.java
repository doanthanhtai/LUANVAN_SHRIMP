package com.pondmanage.model;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "environment_history")
public class EnvironmentHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "ph")
    private Float ph;

    @Column(name = "oxy")
    private Float oxy;

    @Column(name = "salinity")
    private Integer salinity;

    @Column(name = "created_time")
    private Instant createdTime;

    @Column(name = "updated_time")
    private Instant updatedTime;

    @Column(name = "is_delete")
    private Boolean isDelete = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pond_id", referencedColumnName = "id")
    private Pond pond;
}

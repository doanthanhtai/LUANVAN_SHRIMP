package com.pondmanage.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pond")
public class Pond {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "pond_type")
    private boolean pondType;

    @Column(name = "status")
    private boolean status;

    @Column(name = "area")
    private Float area;

    @Column(name = "water_height")
    private Float waterHeight;

    @Column(name = "create_time")
    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private Date createTime;

    @Column(name = "update_time")
    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private Date updateTime;

    @Column(name = "email_manager")
    private String emailManager;

    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

    @OneToMany(mappedBy = "pond", cascade = CascadeType.ALL)
    private List<EnvironmentHistory> environmentHistoryList = new ArrayList<>();

    @OneToMany(mappedBy = "pond", cascade = CascadeType.ALL)
    private List<OtherHistory> otherHistoryList = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shrimp_id", referencedColumnName = "id")
    private Shrimp shrimp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zone_id", referencedColumnName = "id")
    private Zone zone;
}

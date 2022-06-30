package com.pondmanage.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "ware_house")
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WareHouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "is_deleted")
    private Boolean isDeleted= false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zone_id", referencedColumnName = "id")
    private Zone zone;

//    @OneToMany(mappedBy = "wareHouse", cascade = CascadeType.ALL)
//    List<EnterHistory> enterHistoryList = new ArrayList<>();

    @OneToMany(mappedBy = "wareHouse", cascade = CascadeType.ALL)
    List<ProductQuantity> productQuantityList = new ArrayList<>();
}

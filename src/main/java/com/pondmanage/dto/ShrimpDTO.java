package com.pondmanage.dto;

import com.pondmanage.model.Pond;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ShrimpDTO {
    private Long id;
    private String name;
    private String supplier;
    private Integer quantity;
    private float price;
    private Date createdTime;
    private Date harvestTime;
    private boolean isDelete;
    private Long pondId;
}

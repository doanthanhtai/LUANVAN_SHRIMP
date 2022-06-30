package com.pondmanage.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductHistoryDTO {
    private long id;
    private long productId;
    private String productName;
    private float quantity;
    private Date createTime;
    private Date updateTime;
}

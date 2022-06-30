package com.pondmanage.dto;

import com.pondmanage.model.ProductQuantity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EnterHistoryDTO {
    private Long id;
    private Date createdTime;
    private Date updatedTime;
    private Boolean isDelete;
    private Float quantity;
    private ProductQuantityDTO productQuantityDTO;
}

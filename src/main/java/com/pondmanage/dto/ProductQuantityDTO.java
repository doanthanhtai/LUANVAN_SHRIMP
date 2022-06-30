package com.pondmanage.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.java.Log;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductQuantityDTO {
    private Long id;
    private Long productId;
    private Long wareHouseId;
    private Float quantity;
}

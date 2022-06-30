package com.pondmanage.dto;

import com.pondmanage.model.Pond;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DietDTO {
    private Long id;
    private Long productId;
    private Integer quantity;
    private Integer longTime;
    private boolean status;
    private Long pondId;
}

package com.pondmanage.service;

import com.pondmanage.dto.EnterHistoryDTO;
import com.pondmanage.dto.ProductQuantityDTO;
import com.pondmanage.model.ProductQuantity;

import java.util.List;

public interface ProductQuantityService {
    ProductQuantity insert(EnterHistoryDTO enterHistoryDTO);

    Float totalByWareHouse(Long wareHouseId);

    List<ProductQuantity> getByWareHouse(Long wareHouseId);

    void update(ProductQuantity productQuantity);

    ProductQuantity getById(Long id);
}

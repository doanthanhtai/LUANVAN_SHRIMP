package com.pondmanage.service;

import com.pondmanage.dto.WareHouseDTO;
import com.pondmanage.model.WareHouse;

import java.util.List;

public interface WareHouseService {
    void insertWareHouse(WareHouseDTO wareHouseDTO);
    List<WareHouse> findAll();
    WareHouse getById(Long id);
    void deleteWareHouse(WareHouseDTO wareHouseDTO);

    boolean checkInventory(WareHouseDTO wareHouseDTO);

    void update(WareHouseDTO wareHouseDTO);
}

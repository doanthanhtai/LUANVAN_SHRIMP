package com.pondmanage.service;

import com.pondmanage.dto.ShrimpDTO;
import com.pondmanage.model.Shrimp;

import java.util.List;

public interface ShrimpService {

    List<Shrimp> findAll();

    void insertShrimp(ShrimpDTO shrimpDTO);

    Shrimp getById(Long id);

    void updateShrimp(ShrimpDTO shrimpDTO);
    void updateShrimp(Shrimp shrimp);

    void harvestShrimp(ShrimpDTO shrimpDTO);

    void deleteShrimp(ShrimpDTO shrimpDTO);

    void updateDeleteDiet (Long id);
}

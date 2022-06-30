package com.pondmanage.service;

import com.pondmanage.dto.DietDTO;
import com.pondmanage.model.Diet;
import com.pondmanage.model.Zone;

import java.util.List;

public interface DietService {
    List<Diet> findAll(Zone zone);
    void insert(DietDTO dietDTO);
    void delete(Long id);
    Diet getById(Long id);
    void update(DietDTO dietDTO);
}

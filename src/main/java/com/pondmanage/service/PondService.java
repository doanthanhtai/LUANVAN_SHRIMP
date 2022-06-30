package com.pondmanage.service;

import com.pondmanage.dto.PondDTO;
import com.pondmanage.model.Pond;
import com.pondmanage.model.Zone;

import java.util.List;
import java.util.Optional;

public interface PondService {
    Optional<Pond> findById(long id);
    List<Pond> findAll(Zone zone);
    void insertPond(PondDTO pondDTO);
    void deletePond(PondDTO pondDTO);
    void update(PondDTO pondDTO);
    void update(Pond pond);
    Pond getById(Long id);
    List<Pond> findAll();

}

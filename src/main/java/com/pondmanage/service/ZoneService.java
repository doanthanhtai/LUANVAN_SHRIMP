package com.pondmanage.service;

import com.pondmanage.dto.ZoneDTO;
import com.pondmanage.model.Pond;
import com.pondmanage.model.Zone;

import java.util.List;

public interface ZoneService {

    void insertZone(ZoneDTO zoneDTO);
    List<Zone> findAll();
    void delete(Long id);
    Zone getById(Long id);
    void update(ZoneDTO zoneDTO);
}

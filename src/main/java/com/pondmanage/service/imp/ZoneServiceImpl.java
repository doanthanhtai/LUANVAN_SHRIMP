package com.pondmanage.service.imp;

import com.pondmanage.dto.ZoneDTO;
import com.pondmanage.model.Employee;
import com.pondmanage.model.Pond;
import com.pondmanage.model.Zone;
import com.pondmanage.repository.ZoneRepository;
import com.pondmanage.service.AccountService;
import com.pondmanage.service.ZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ZoneServiceImpl implements ZoneService {

    @Autowired
    ZoneRepository zoneRepository;

    @Autowired
    AccountService accountService;

    @Override
    public void insertZone(ZoneDTO zoneDTO) {
        Zone zone = new Zone();
        zone.setName(zoneDTO.getName());
        zone.setArea(zoneDTO.getArea());
        zone.setCreateTime(zoneDTO.getCreatedTime());
        zone.setAddress(zoneDTO.getAddress());
        zone.setAccount(accountService.getCurrentAccount());
        zoneRepository.save(zone);
    }

    @Override
    public List<Zone> findAll() {
        List<Zone> zones = new ArrayList<>();
        for (Zone zone : zoneRepository.findAll()){
            if (zone.getIsDeleted() || zone.getAccount().getId() == null){
                continue;
            }
            if (Objects.equals(zone.getAccount().getId(), accountService.getCurrentAccount().getId())){
                zones.add(zone);
            }
        }
        return zones;
    }

    @Override
    public void delete(Long id) {
        Zone zone = zoneRepository.getById(id);
        zone.setIsDeleted(true);
        zoneRepository.save(zone);
    }

    @Override
    public Zone getById(Long id) {
        return zoneRepository.getById(id);
    }

    @Override
    public void update(ZoneDTO zoneDTO) {
        Zone zone = zoneRepository.getById(zoneDTO.getId());
        zone.setName(zoneDTO.getName());
        zone.setArea(zoneDTO.getArea());
        zone.setCreateTime(zoneDTO.getCreatedTime());
        zone.setAddress(zoneDTO.getAddress());
        zoneRepository.save(zone);
    }

}

package com.pondmanage.service.imp;

import com.pondmanage.dto.WareHouseDTO;
import com.pondmanage.model.WareHouse;
import com.pondmanage.model.Zone;
import com.pondmanage.repository.WareHouseRepository;
import com.pondmanage.service.ProductQuantityService;
import com.pondmanage.service.WareHouseService;
import com.pondmanage.service.ZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WareHouseServiceImpl implements WareHouseService {

    @Autowired
    WareHouseRepository wareHouseRepository;

    @Autowired
    ZoneService zoneService;

    @Autowired
    ProductQuantityService productQuantityService;

    @Override
    public void insertWareHouse(WareHouseDTO wareHouseDTO) {
        WareHouse wareHouse = new WareHouse();
        wareHouse.setName(wareHouseDTO.getName());
        wareHouse.setZone(zoneService.getById(wareHouseDTO.getZoneId()));
        wareHouseRepository.save(wareHouse);
    }

    @Override
    public List<WareHouse> findAll() {
        List<Zone> zones = zoneService.findAll();
        List<WareHouse> wareHouseList = new ArrayList<>();
        for (WareHouse wareHouse : wareHouseRepository.findAll()){
            if (zones.contains(wareHouse.getZone()) && !wareHouse.getIsDeleted()){
                wareHouseList.add(wareHouse);
            }
        }
        return wareHouseList;
    }

    @Override
    public WareHouse getById(Long id) {
        return wareHouseRepository.getById(id);
    }

    @Override
    public void deleteWareHouse(WareHouseDTO wareHouseDTO) {
        WareHouse wareHouse = wareHouseRepository.getById(wareHouseDTO.getId());
        wareHouse.setIsDeleted(true);
        wareHouseRepository.save(wareHouse);
    }

    @Override
    public boolean checkInventory(WareHouseDTO wareHouseDTO) {
        Float totalQuantity = productQuantityService.totalByWareHouse(wareHouseDTO.getId());
        if (totalQuantity == null) return false;
        return totalQuantity > 0;
    }

    @Override
    public void update(WareHouseDTO wareHouseDTO) {
        WareHouse wareHouse = wareHouseRepository.getById(wareHouseDTO.getId());
        wareHouse.setName(wareHouseDTO.getName());
        wareHouse.setZone(zoneService.getById(wareHouseDTO.getZoneId()));
        wareHouseRepository.save(wareHouse);
    }
}

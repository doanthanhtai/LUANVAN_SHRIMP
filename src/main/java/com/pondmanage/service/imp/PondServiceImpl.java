package com.pondmanage.service.imp;

import com.pondmanage.dto.PondDTO;
import com.pondmanage.model.Pond;
import com.pondmanage.model.Zone;
import com.pondmanage.repository.PondRepository;
import com.pondmanage.service.AccountService;
import com.pondmanage.service.PondService;
import com.pondmanage.service.ZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PondServiceImpl implements PondService {

    @Autowired
    PondRepository pondRepository;

    @Autowired
    ZoneService zoneService;

    @Autowired
    AccountService accountService;

    @Override
    public Optional<Pond> findById(long id) {
        return pondRepository.findById(id);
    }

    @Override
    public List<Pond> findAll(Zone zone) {
        List<Pond> ponds = new ArrayList<>();
        for (Pond pond : pondRepository.findAll()){
            if (pond.getIsDeleted() || pond.getZone().getId() == null){
                continue;
            }
            if (Objects.equals(pond.getZone().getId(), zone.getId())){
                ponds.add(pond);
            }
        }
        return ponds;
    }

    @Override
    public void insertPond(PondDTO pondDTO) {
        Zone zone = zoneService.getById(pondDTO.getZoneId());
        Pond pond = new Pond();
        pond.setName(pondDTO.getName());
        pond.setPondType(pondDTO.getPondType());
        pond.setCreateTime(pondDTO.getCreatedTime());
        pond.setArea(pondDTO.getArea());
        pond.setWaterHeight(pondDTO.getWaterHeight());
        pond.setZone(zone);
        pondRepository.save(pond);
    }

    @Override
    public void deletePond(PondDTO pondDTO) {
        Pond pond = pondRepository.getById(pondDTO.getId());
        pond.setIsDeleted(true);
        pondRepository.save(pond);
    }

    @Override
    public void update(PondDTO pondDTO) {
        Pond pond = pondRepository.getById(pondDTO.getId());
        pond.setName(pondDTO.getName());
        pond.setPondType(pondDTO.getPondType());
        pond.setArea(pondDTO.getArea());
        pond.setCreateTime(pondDTO.getCreatedTime());
        pond.setWaterHeight(pondDTO.getWaterHeight());
        pondRepository.save(pond);
    }

    @Override
    public void update(Pond pond) {
        pondRepository.save(pond);
    }

    @Override
    public Pond getById(Long id) {
        return pondRepository.getById(id);
    }

    @Override
    public List<Pond> findAll() {
        List<Pond> ponds = new ArrayList<>();
        List<Zone> listZone = zoneService.findAll();
        for (Pond pond : pondRepository.findAll()){
            if (pond.getIsDeleted() || pond.getZone() == null){
                continue;
            }
            for (Zone zone : listZone){
                if (Objects.equals(pond.getZone().getId(), zone.getId())){
                    ponds.add(pond);
                    break;
                }
            }
        }
        return ponds;
    }

}

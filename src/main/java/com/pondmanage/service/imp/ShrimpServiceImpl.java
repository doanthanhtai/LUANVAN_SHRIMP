package com.pondmanage.service.imp;

import com.pondmanage.dto.ShrimpDTO;
import com.pondmanage.model.Account;
import com.pondmanage.model.Pond;
import com.pondmanage.model.Shrimp;
import com.pondmanage.model.Zone;
import com.pondmanage.repository.ShrimpRepository;
import com.pondmanage.service.AccountService;
import com.pondmanage.service.PondService;
import com.pondmanage.service.ShrimpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ShrimpServiceImpl implements ShrimpService {

    @Autowired
    ShrimpRepository shrimpRepository;

    @Autowired
    PondService pondService;

    @Autowired
    AccountService accountService;

    @Override
    public List<Shrimp> findAll() {
        List<Shrimp> shrimpList = new ArrayList<>();
        Account account = accountService.getCurrentAccount();
        for (Shrimp shrimp : shrimpRepository.findAll()){
            if (shrimp.getIsDeleted()){
                continue;
            }
            System.out.println(account);
            if(shrimp.getAccount().getId().equals(account.getId())) {
                shrimpList.add(shrimp);
            }
        }
        return shrimpList;
    }

    @Override
    public void insertShrimp(ShrimpDTO shrimpDTO) {
        Pond pond = pondService.getById(shrimpDTO.getPondId());
        Shrimp shrimp = new Shrimp();
        shrimp.setName(shrimpDTO.getName());
        shrimp.setSupplier(shrimpDTO.getSupplier());
        shrimp.setQuantity(shrimpDTO.getQuantity());
        shrimp.setIsDeleted(false);
        shrimp.setPrice(shrimpDTO.getPrice());
        shrimp.setCreateTime(shrimpDTO.getCreatedTime());
        shrimp.setHarvestTime(null);
        shrimp.setPond(pond);
        shrimp.setAccount(accountService.getCurrentAccount());
        shrimpRepository.save(shrimp);
        pond.setShrimp(shrimp);
        pondService.update(pond);
    }

    @Override
    public Shrimp getById(Long id) {
        return shrimpRepository.getById(id);
    }

    @Override
    public void updateShrimp(ShrimpDTO shrimpDTO) {
        Shrimp shrimp = shrimpRepository.getById(shrimpDTO.getId());
        shrimp.setName(shrimpDTO.getName());
        shrimp.setPrice(shrimpDTO.getPrice());
        shrimp.setQuantity(shrimpDTO.getQuantity());
        shrimp.setCreateTime(shrimpDTO.getCreatedTime());
        shrimp.setHarvestTime(shrimpDTO.getHarvestTime());
        shrimp.setSupplier(shrimpDTO.getSupplier());
        shrimp.setPond(pondService.getById(shrimpDTO.getPondId()));
        shrimpRepository.save(shrimp);
    }

    @Override
    public void updateShrimp(Shrimp shrimp) {
        shrimpRepository.save(shrimp);
    }

    @Override
    public void harvestShrimp(ShrimpDTO shrimpDTO) {
        Shrimp shrimp = shrimpRepository.getById(shrimpDTO.getId());
        shrimp.setHarvestTime(shrimpDTO.getHarvestTime());
        Pond pond = pondService.getById(shrimp.getPond().getId());
        pond.setShrimp(null);
        pondService.update(pond);
        shrimp.setPond(null);
        updateDeleteDiet(shrimp.getId());
        shrimpRepository.save(shrimp);
    }

    @Override
    public void deleteShrimp(ShrimpDTO shrimpDTO) {
        Shrimp shrimp = shrimpRepository.getById(shrimpDTO.getId());
        shrimp.setIsDeleted(true);
        shrimpRepository.save(shrimp);
    }

    @Override
    public void updateDeleteDiet(Long id) {
        Shrimp shrimp = shrimpRepository.getById(id);
        shrimp.setDiet(null);
        shrimpRepository.save(shrimp);
    }
}

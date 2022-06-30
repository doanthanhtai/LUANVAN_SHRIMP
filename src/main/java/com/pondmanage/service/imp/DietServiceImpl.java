package com.pondmanage.service.imp;

import com.pondmanage.dto.DietDTO;
import com.pondmanage.model.*;
import com.pondmanage.repository.DietRepository;
import com.pondmanage.service.DietService;
import com.pondmanage.service.PondService;
import com.pondmanage.service.ProductService;
import com.pondmanage.service.ShrimpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DietServiceImpl implements DietService {

    @Autowired
    DietRepository dietRepository;

    @Autowired
    PondService pondService;

    @Autowired
    ProductService productService;

    @Autowired
    ShrimpService shrimpService;

    @Override
    public List<Diet> findAll(Zone zone) {
        List<Diet> diets = new ArrayList<>();
        List<Shrimp> shrimpList = shrimpService.findAll();
        for (Shrimp shrimp : shrimpList) {
            if (shrimp.getIsDeleted() || shrimp.getDiet() == null) continue;
            diets.add(shrimp.getDiet());
        }
        return diets;
    }

    @Override
    public void insert(DietDTO dietDTO) {
        Diet diet = new Diet();
        diet.setProduct(productService.getById(dietDTO.getProductId()));
        diet.setQuantity(dietDTO.getQuantity());
        diet.setStatus(dietDTO.isStatus());
        diet.setLongTime(dietDTO.getLongTime());
        diet.setShrimp(pondService.getById(dietDTO.getPondId()).getShrimp());
        dietRepository.save(diet);
        Shrimp shrimp = pondService.getById(dietDTO.getPondId()).getShrimp();
        shrimp.setDiet(diet);
        shrimpService.updateShrimp(shrimp);
//        Pond pond = pondService.getById(dietDTO.getPondId());
//        pondService.update(pond);
    }

    @Override
    public void delete(Long id) {
        shrimpService.updateDeleteDiet(getById(id).getShrimp().getId());
        dietRepository.delete(getById(id));
    }

    @Override
    public Diet getById(Long id) {
        return dietRepository.getById(id);
    }

    @Override
    public void update(DietDTO dietDTO) {
        Diet diet = dietRepository.getById(dietDTO.getId());
        diet.setProduct(productService.getById(dietDTO.getProductId()));
        diet.setQuantity(dietDTO.getQuantity());
        diet.setLongTime(dietDTO.getLongTime());
        dietRepository.save(diet);
    }
}

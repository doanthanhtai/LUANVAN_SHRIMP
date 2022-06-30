package com.pondmanage.controller;

import com.pondmanage.AjaxRespone;
import com.pondmanage.dto.PondDTO;
import com.pondmanage.dto.ShrimpDTO;
import com.pondmanage.dto.ZoneDTO;
import com.pondmanage.model.Pond;
import com.pondmanage.model.Shrimp;
import com.pondmanage.service.PondService;
import com.pondmanage.service.ShrimpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Arrays;

@RestController
public class ShrimpController {

    @Autowired
    ShrimpService shrimpService;

    @Autowired
    PondService pondService;

    @PostMapping("/shrimps/insert-shrimp")
    public ResponseEntity<?> insertShrimp(@Valid @RequestBody ShrimpDTO shrimpDTO, BindingResult bindingResult) {
        AjaxRespone ajaxRespone = new AjaxRespone();
        if (bindingResult.hasErrors()) {
            ajaxRespone.setMsg("insert-fail");
            return ResponseEntity.badRequest().body(ajaxRespone);
        }
        shrimpService.insertShrimp(shrimpDTO);
        ajaxRespone.setMsg("insert-success");
        return ResponseEntity.ok(ajaxRespone);
    }

    @PostMapping("/shrimps/update-shrimp")
    public ResponseEntity<?> updateShrimp(@Valid @RequestBody ShrimpDTO shrimpDTO, BindingResult bindingResult) {
        AjaxRespone ajaxRespone = new AjaxRespone();
        if (bindingResult.hasErrors()) {
            ajaxRespone.setMsg("update-fail");
            return ResponseEntity.badRequest().body(ajaxRespone);
        }
        shrimpService.updateShrimp(shrimpDTO);
        ajaxRespone.setMsg("update-success");
        return ResponseEntity.ok(ajaxRespone);
    }

    @PostMapping("/shrimps/harvest-shrimp")
    public ResponseEntity<?> harvestShrimp(@Valid @RequestBody ShrimpDTO shrimpDTO, BindingResult bindingResult) {
        AjaxRespone ajaxRespone = new AjaxRespone();
        if (bindingResult.hasErrors()) {
            ajaxRespone.setMsg("harvest-fail");
            return ResponseEntity.badRequest().body(ajaxRespone);
        }
        shrimpService.harvestShrimp(shrimpDTO);
        ajaxRespone.setMsg("harvest-success");
        return ResponseEntity.ok(ajaxRespone);
    }

    @PostMapping("/shrimps/delete-shrimp")
    public ResponseEntity<?> deleteShrimp(@Valid @RequestBody ShrimpDTO shrimpDTO, BindingResult bindingResult) {
        AjaxRespone ajaxRespone = new AjaxRespone();
        if (bindingResult.hasErrors()) {
            ajaxRespone.setMsg("delete-fail");
            return ResponseEntity.badRequest().body(ajaxRespone);
        }
        shrimpService.deleteShrimp(shrimpDTO);
        ajaxRespone.setMsg("delete-success");
        return ResponseEntity.ok(ajaxRespone);
    }

    @PostMapping("/shrimps/get-shrimp-info")
    public AjaxRespone getShrimpInfo(@RequestBody ShrimpDTO shrimpDTO) {
        AjaxRespone ajaxRespone = new AjaxRespone();
        Shrimp shrimp = shrimpService.getById(shrimpDTO.getId());
        shrimpDTO.setName(shrimp.getName());
        shrimpDTO.setPrice(shrimp.getPrice());
        shrimpDTO.setQuantity(shrimp.getQuantity());
        shrimpDTO.setSupplier(shrimp.getSupplier());
        shrimpDTO.setCreatedTime(shrimp.getCreateTime());
        shrimpDTO.setPondId(shrimp.getPond().getId());
        ajaxRespone.setObjects(shrimpDTO);
        return ajaxRespone;
    }
}

package com.pondmanage.controller;

import com.pondmanage.AjaxRespone;
import com.pondmanage.dto.ShrimpDTO;
import com.pondmanage.dto.WareHouseDTO;
import com.pondmanage.dto.ZoneDTO;
import com.pondmanage.model.Shrimp;
import com.pondmanage.model.WareHouse;
import com.pondmanage.service.WareHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class WareHouseController {

    @Autowired
    WareHouseService wareHouseService;

    @PostMapping("/warehouse/insert-warehouse")
    public ResponseEntity<?> insertWareHouse(@Valid @RequestBody WareHouseDTO wareHouseDTO, BindingResult bindingResult) {
        AjaxRespone ajaxRespone = new AjaxRespone();
        if (bindingResult.hasErrors()) {
            ajaxRespone.setMsg("insert-fail");
            return ResponseEntity.badRequest().body(ajaxRespone);
        }
        wareHouseService.insertWareHouse(wareHouseDTO);
        ajaxRespone.setMsg("insert-success");
        return ResponseEntity.ok(ajaxRespone);
    }

    @PostMapping("/warehouse/get-warehouse-info")
    public AjaxRespone getWareHouseInfo(@RequestBody WareHouseDTO wareHouseDTO) {
        AjaxRespone ajaxRespone = new AjaxRespone();
        WareHouse wareHouse = wareHouseService.getById(wareHouseDTO.getId());
        wareHouseDTO.setName(wareHouse.getName());
        wareHouseDTO.setZoneId(wareHouse.getZone().getId());
        ajaxRespone.setObjects(wareHouseDTO);
        return ajaxRespone;
    }

    @PostMapping("/warehouse/delete-warehouse")
    public ResponseEntity<?> deleteWareHouse(@Valid @RequestBody WareHouseDTO wareHouseDTO, BindingResult bindingResult) {
        AjaxRespone ajaxRespone = new AjaxRespone();
        if (bindingResult.hasErrors()) {
            ajaxRespone.setMsg("delete-fail");
            return ResponseEntity.badRequest().body(ajaxRespone);
        }
        if (wareHouseService.checkInventory(wareHouseDTO)) {
            ajaxRespone.setMsg("inventory");
            return ResponseEntity.ok(ajaxRespone);
        }
        wareHouseService.deleteWareHouse(wareHouseDTO);
        ajaxRespone.setMsg("delete-success");
        return ResponseEntity.ok(ajaxRespone);
    }

    @PostMapping("/warehouse/update-warehouse")
    public ResponseEntity<?> updateWareHouse(@Valid @RequestBody WareHouseDTO wareHouseDTO, BindingResult bindingResult) {
        AjaxRespone ajaxRespone = new AjaxRespone();
        if (bindingResult.hasErrors()) {
            ajaxRespone.setMsg("insert-fail");
            return ResponseEntity.badRequest().body(ajaxRespone);
        }
        wareHouseService.update(wareHouseDTO);
        ajaxRespone.setMsg("update-success");
        return ResponseEntity.ok(ajaxRespone);
    }
}

package com.pondmanage.controller;

import com.pondmanage.AjaxRespone;
import com.pondmanage.dto.EmployeeDTO;
import com.pondmanage.dto.PondDTO;
import com.pondmanage.dto.ZoneDTO;
import com.pondmanage.model.Pond;
import com.pondmanage.model.Zone;
import com.pondmanage.service.AccountService;
import com.pondmanage.service.ZoneService;
import org.hibernate.validator.constraints.ModCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
public class ZoneController {

    @Autowired
    ZoneService zoneService;

    @Autowired
    AccountService accountService;

    @PostMapping("/zones/insert-zone")
    public ResponseEntity<?> insertZone(@Valid @RequestBody ZoneDTO zoneDTO, BindingResult bindingResult) {
        AjaxRespone ajaxRespone = new AjaxRespone();
        if (bindingResult.hasErrors()) {
            ajaxRespone.setMsg("insert-fail");
            return ResponseEntity.badRequest().body(ajaxRespone);
        }
        zoneService.insertZone(zoneDTO);
        ajaxRespone.setMsg("insert-success");
        return ResponseEntity.ok(ajaxRespone);
    }

    @PostMapping("/zones/update-zone")
    public ResponseEntity<?> updateZone(@Valid @RequestBody ZoneDTO zoneDTO, BindingResult bindingResult) {
        AjaxRespone ajaxRespone = new AjaxRespone();
        if (bindingResult.hasErrors()) {
            ajaxRespone.setMsg("insert-fail");
            return ResponseEntity.badRequest().body(ajaxRespone);
        }
        zoneService.update(zoneDTO);
        ajaxRespone.setMsg("insert-success");
        return ResponseEntity.ok(ajaxRespone);
    }

    @PostMapping("/zones/delete-zone")
    public ResponseEntity<?> deleteZone(@Valid @RequestBody ZoneDTO zoneDTO, BindingResult bindingResult) {
        AjaxRespone ajaxRespone = new AjaxRespone();
        if (bindingResult.hasErrors()) {
            ajaxRespone.setMsg("delete-fail");
            return ResponseEntity.badRequest().body(ajaxRespone);
        }
        zoneService.delete(zoneDTO.getId());
        ajaxRespone.setMsg("delete-success");
        return ResponseEntity.ok(ajaxRespone);
    }

    @PostMapping("/zones/get-zone-info")
    public AjaxRespone getZoneInfo(@RequestBody ZoneDTO zoneDTO) {
        AjaxRespone ajaxRespone = new AjaxRespone();
        Zone zone = zoneService.getById(zoneDTO.getId());
        zoneDTO.setName(zone.getName());
        zoneDTO.setArea(zone.getArea());
        zoneDTO.setCreatedTime(zone.getCreateTime());
        zoneDTO.setAddress(zone.getAddress());
        ajaxRespone.setObjects(zoneDTO);
        return ajaxRespone;
    }

    @PostMapping("/zones/get-zones")
    public AjaxRespone getzones() {
        AjaxRespone ajaxRespone = new AjaxRespone();
        List<Zone> zones = zoneService.findAll();
        List<ZoneDTO> zoneDTOList = new ArrayList<>();
        for (Zone zone : zones){
            zoneDTOList.add(getZoneDTOByZone(zone));
        }
        ajaxRespone.setObjects(zoneDTOList);
        return ajaxRespone;
    }

    private ZoneDTO getZoneDTOByZone (Zone zone) {
        ZoneDTO zoneDTO = new ZoneDTO();
        zoneDTO.setId(zone.getId());
        zoneDTO.setName(zone.getName());
        return zoneDTO;
    }
}

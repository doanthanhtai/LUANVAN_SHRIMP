package com.pondmanage.controller;

import com.pondmanage.AjaxRespone;
import com.pondmanage.dto.PondDTO;
import com.pondmanage.dto.ShrimpDTO;
import com.pondmanage.dto.ZoneDTO;
import com.pondmanage.model.Pond;
import com.pondmanage.model.Zone;
import com.pondmanage.service.PondService;
import com.pondmanage.service.ZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
public class PondController {

    @Autowired
    PondService pondService;

    @Autowired
    ZoneService zoneService;

    @PostMapping("/ponds/delete-ponds")
    public ResponseEntity<?> delete(@Valid @RequestBody PondDTO pondDTO, BindingResult bindingResult) {
        AjaxRespone ajaxRespone = new AjaxRespone();
        if (bindingResult.hasErrors()){
            ajaxRespone.setMsg("delete-error");
            return ResponseEntity.ok(ajaxRespone);
        }
        pondService.deletePond(pondDTO);
        ajaxRespone.setMsg("delete-success");
        return ResponseEntity.ok(ajaxRespone);
    }

    @PostMapping("/ponds/insert-pond")
    public ResponseEntity<?> insertPond(@Valid @RequestBody PondDTO pondDTO, BindingResult bindingResult) {
        AjaxRespone ajaxRespone = new AjaxRespone();
        if (bindingResult.hasErrors()) {
            ajaxRespone.setMsg("insert-fail");
            return ResponseEntity.badRequest().body(ajaxRespone);
        }
        pondService.insertPond(pondDTO);
        ajaxRespone.setMsg("insert-success");
        return ResponseEntity.ok(ajaxRespone);
    }

    @PostMapping("/ponds/update-pond")
    public ResponseEntity<?> updateZone(@Valid @RequestBody PondDTO pondDTO, BindingResult bindingResult) {
        AjaxRespone ajaxRespone = new AjaxRespone();
        if (bindingResult.hasErrors()) {
            ajaxRespone.setMsg("insert-fail");
            return ResponseEntity.badRequest().body(ajaxRespone);
        }
        pondService.update(pondDTO);
        ajaxRespone.setMsg("insert-success");
        return ResponseEntity.ok(ajaxRespone);
    }


    @PostMapping("/ponds/get-pond-info")
    public AjaxRespone getZoneInfo(@RequestBody PondDTO pondDTO) {
        AjaxRespone ajaxRespone = new AjaxRespone();
        Pond pond = pondService.getById(pondDTO.getId());
        pondDTO.setName(pond.getName());
        pondDTO.setPondType(pond.isPondType());
        pondDTO.setArea(pond.getArea());
        pondDTO.setCreatedTime(pond.getCreateTime());
        pondDTO.setWaterHeight(pond.getWaterHeight());
        ajaxRespone.setObjects(pondDTO);
        return ajaxRespone;
    }

    @PostMapping("/ponds/get-ponds")
    public AjaxRespone getPonds() {
        AjaxRespone ajaxRespone = new AjaxRespone();
        List<Pond> pondList = pondService.findAll();
        List<PondDTO> pondDTOList = new ArrayList<>();
        for (Pond pond : pondList){
            if (pond.getIsDeleted()) continue;
            pondDTOList.add(getPondDTOByPond(pond));
        }
        ajaxRespone.setObjects(pondDTOList);
        return ajaxRespone;
    }

    @PostMapping("/ponds/get-ponds-for-new-diet")
    public AjaxRespone getPondsByZone(@Valid @RequestBody ZoneDTO zoneDTO, BindingResult bindingResult) {
        AjaxRespone ajaxRespone = new AjaxRespone();
        if (bindingResult.hasErrors()) {
            ajaxRespone.setMsg("not-found-pons");
            return ajaxRespone;
        }
        List<Pond> pondList = pondService.findAll(zoneService.getById(zoneDTO.getId()));
        List<PondDTO> pondDTOList = new ArrayList<>();
        for (Pond pond : pondList){
            if (pond.getIsDeleted() || pond.getShrimp() == null || pond.getShrimp().getDiet() != null) continue;
            pondDTOList.add(getPondDTOByPond(pond));
        }
        ajaxRespone.setObjects(pondDTOList);
        return ajaxRespone;
    }

    private PondDTO getPondDTOByPond (Pond pond){
        PondDTO pondDTO = new PondDTO();
        pondDTO.setId(pond.getId());
        pondDTO.setName(pond.getName());
        pondDTO.setCreatedTime(pond.getCreateTime());
        pondDTO.setPondType(pond.isPondType());
        pondDTO.setWaterHeight(pond.getWaterHeight());
        pondDTO.setArea(pond.getArea());
        pondDTO.setEmailManager(pond.getEmailManager());
        //pondDTO.setDiet(pond.getDiet());
        pondDTO.setEnvironmentHistoryList(pond.getEnvironmentHistoryList());
        pondDTO.setOtherHistoryList(pond.getOtherHistoryList());
        pondDTO.setStatus(pond.isStatus());
        pondDTO.setZoneId(pond.getZone().getId());
        return pondDTO;
    }
}

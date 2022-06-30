package com.pondmanage.controller;


import com.pondmanage.AjaxRespone;
import com.pondmanage.dto.DietDTO;
import com.pondmanage.model.Diet;
import com.pondmanage.service.DietService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class DietController {

    @Autowired
    DietService dietService;

    @PostMapping("/diets/delete-diet")
    public ResponseEntity<?> delete(@Valid @RequestBody DietDTO dietDTO, BindingResult bindingResult) {
        AjaxRespone ajaxRespone = new AjaxRespone();
        if (bindingResult.hasErrors()) {
            ajaxRespone.setMsg("delete-error");
            return ResponseEntity.ok(ajaxRespone);
        }
        dietService.delete(dietDTO.getId());
        ajaxRespone.setMsg("delete-success");
        return ResponseEntity.ok(ajaxRespone);
    }

    @PostMapping("/diets/insert-diet")
    public ResponseEntity<?> insert(@Valid @RequestBody DietDTO dietDTO, BindingResult bindingResult) {
        AjaxRespone ajaxRespone = new AjaxRespone();
        if (bindingResult.hasErrors()) {
            ajaxRespone.setMsg("insert-error");
            return ResponseEntity.ok(ajaxRespone);
        }
        dietService.insert(dietDTO);
        ajaxRespone.setMsg("insert-success");
        return ResponseEntity.ok(ajaxRespone);
    }

    @PostMapping("/diets/update-diet")
    public ResponseEntity<?> update(@Valid @RequestBody DietDTO dietDTO, BindingResult bindingResult) {
        AjaxRespone ajaxRespone = new AjaxRespone();
        if (bindingResult.hasErrors()) {
            ajaxRespone.setMsg("update-error");
            return ResponseEntity.ok(ajaxRespone);
        }
        dietService.update(dietDTO);
        ajaxRespone.setMsg("update-success");
        return ResponseEntity.ok(ajaxRespone);
    }

    @PostMapping("/diets/get-diet-info")
    public AjaxRespone getDietInfo(@Valid @RequestBody DietDTO dietDTO, BindingResult bindingResult) {
        AjaxRespone ajaxRespone = new AjaxRespone();
        Diet diet = dietService.getById(dietDTO.getId());
        dietDTO.setPondId(diet.getShrimp().getPond().getId());
        dietDTO.setProductId(diet.getProduct().getId());
        dietDTO.setLongTime(diet.getLongTime());
        dietDTO.setQuantity(diet.getQuantity());
        ajaxRespone.setObjects(dietDTO);
        return ajaxRespone;
    }

}

package com.pondmanage.controller;

import com.pondmanage.AjaxRespone;
import com.pondmanage.dto.EnterHistoryDTO;
import com.pondmanage.dto.ProductQuantityDTO;
import com.pondmanage.model.EnterHistory;
import com.pondmanage.model.ProductQuantity;
import com.pondmanage.service.EnterHistoryService;
import com.pondmanage.service.ProductQuantityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class EnterHistoryController {

    @Autowired
    EnterHistoryService enterHistoryService;

    @PostMapping("/enterHistory/enter-product")
    public ResponseEntity<?> insertShrimp(@Valid @RequestBody EnterHistoryDTO enterHistoryDTO, BindingResult bindingResult) {
        AjaxRespone ajaxRespone = new AjaxRespone();
        if (bindingResult.hasErrors()) {
            ajaxRespone.setMsg("enter-fail");
            return ResponseEntity.badRequest().body(ajaxRespone);
        }
        enterHistoryService.insert(enterHistoryDTO);
        ajaxRespone.setMsg("insert-success");
        return ResponseEntity.ok(ajaxRespone);
    }

    @PostMapping("/enter-histories/get-enter-history-info")
    public ResponseEntity<?> getEnterHistory(@Valid @RequestBody EnterHistoryDTO enterHistoryDTO, BindingResult bindingResult) {
        AjaxRespone ajaxRespone = new AjaxRespone();
        if (bindingResult.hasErrors()) {
            ajaxRespone.setMsg("enter-fail");
            return ResponseEntity.badRequest().body(ajaxRespone);
        }
        EnterHistory enterHistory = enterHistoryService.getById(enterHistoryDTO.getId());
        enterHistoryDTO.setQuantity(enterHistory.getQuantity());
        enterHistoryDTO.setCreatedTime(enterHistory.getCreatedTime());
        enterHistoryDTO.setUpdatedTime(enterHistory.getUpdatedTime());
        enterHistoryDTO.setProductQuantityDTO(convertProQuantityToProQuantityDTO(enterHistory.getProductQuantity()));
        ajaxRespone.setObjects(enterHistoryDTO);
        ajaxRespone.setMsg("insert-success");
        return ResponseEntity.ok(ajaxRespone);
    }

    @PostMapping("/enter-histories/update-enter-history")
    public ResponseEntity<?> updateEnterHistory(@Valid @RequestBody EnterHistoryDTO enterHistoryDTO, BindingResult bindingResult) {
        AjaxRespone ajaxRespone = new AjaxRespone();
        if (bindingResult.hasErrors()) {
            ajaxRespone.setMsg("enter-fail");
            return ResponseEntity.badRequest().body(ajaxRespone);
        }
        enterHistoryService.update(enterHistoryDTO);
        ajaxRespone.setMsg("update-success");
        return ResponseEntity.ok(ajaxRespone);
    }

    @PostMapping("/enter-histories/delete-enter-history")
    public ResponseEntity<?> deleteEnterHistory(@Valid @RequestBody EnterHistoryDTO enterHistoryDTO, BindingResult bindingResult) {
        AjaxRespone ajaxRespone = new AjaxRespone();
        if (bindingResult.hasErrors()) {
            ajaxRespone.setMsg("delete-fail");
            return ResponseEntity.badRequest().body(ajaxRespone);
        }
        enterHistoryService.delete(enterHistoryDTO);
        ajaxRespone.setMsg("delete-success");
        return ResponseEntity.ok(ajaxRespone);
    }

    private ProductQuantityDTO convertProQuantityToProQuantityDTO (ProductQuantity productQuantity) {
        ProductQuantityDTO productQuantityDTO = new ProductQuantityDTO();
        productQuantityDTO.setId(productQuantity.getId());
        productQuantityDTO.setQuantity(productQuantity.getQuantity());
        productQuantityDTO.setProductId(productQuantity.getProduct().getId());
        productQuantityDTO.setWareHouseId(productQuantity.getWareHouse().getId());
        return productQuantityDTO;
    }

}

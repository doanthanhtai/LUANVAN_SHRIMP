package com.pondmanage.controller;

import com.pondmanage.AjaxRespone;
import com.pondmanage.dto.EmployeeDTO;
import com.pondmanage.dto.ShrimpDTO;
import com.pondmanage.model.Employee;
import com.pondmanage.model.Shrimp;
import com.pondmanage.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @PostMapping("/employees/insert-employee")
    public ResponseEntity<?> insertEmployee(@Valid @RequestBody EmployeeDTO employeeDTO, BindingResult bindingResult) {
        AjaxRespone ajaxRespone = new AjaxRespone();
        if (bindingResult.hasErrors()) {
            ajaxRespone.setMsg("insert-fail");
            return ResponseEntity.badRequest().body(ajaxRespone);
        }
        employeeService.insertEmployee(employeeDTO);
        ajaxRespone.setMsg("insert-success");
        return ResponseEntity.ok(ajaxRespone);
    }

    @PostMapping("/employees/delete-employee")
    public ResponseEntity<?> deleteEmployee(@Valid @RequestBody EmployeeDTO employeeDTO, BindingResult bindingResult) {
        AjaxRespone ajaxRespone = new AjaxRespone();
        if (bindingResult.hasErrors()) {
            ajaxRespone.setMsg("insert-fail");
            return ResponseEntity.badRequest().body(ajaxRespone);
        }
        employeeService.delete(employeeDTO.getId());
        ajaxRespone.setMsg("delete-success");
        return ResponseEntity.ok(ajaxRespone);
    }

    @PostMapping("/employees/get-employee-info")
    public AjaxRespone getEmployeeInfo(@RequestBody EmployeeDTO employeeDTO) {
        AjaxRespone ajaxRespone = new AjaxRespone();
        Employee employee = employeeService.getById(employeeDTO.getId());
        employeeDTO.setName(employee.getName());
        employeeDTO.setPhone(employee.getPhone());
        employeeDTO.setBankName(employee.getBankName());
        employeeDTO.setBankNo(employee.getBankNo());
        employeeDTO.setBankAccountName(employee.getBankAccountName());
        employeeDTO.setSaliry(employee.getSaliry());
        employeeDTO.setOnboardDate(employee.getOnboardDate());
        ajaxRespone.setObjects(employeeDTO);
        return ajaxRespone;
    }

    @PostMapping("/employees/update-employee")
    public ResponseEntity<?> updateEmployee(@Valid @RequestBody EmployeeDTO employeeDTO, BindingResult bindingResult) {
        AjaxRespone ajaxRespone = new AjaxRespone();
        if (bindingResult.hasErrors()) {
            ajaxRespone.setMsg("update-fail");
            return ResponseEntity.badRequest().body(ajaxRespone);
        }
        employeeService.updateEmployee(employeeDTO);
        ajaxRespone.setMsg("update-success");
        return ResponseEntity.ok(ajaxRespone);
    }

}

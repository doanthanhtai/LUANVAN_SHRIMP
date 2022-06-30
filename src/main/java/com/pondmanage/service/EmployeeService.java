package com.pondmanage.service;

import com.pondmanage.dto.EmployeeDTO;
import com.pondmanage.dto.ShrimpDTO;
import com.pondmanage.model.Employee;

import java.util.List;

public interface EmployeeService {

    void insertEmployee(EmployeeDTO employeeDTO);

    public List<Employee> findAll();

    void delete(Long id);

    Employee getById(Long id);

    void updateEmployee(EmployeeDTO employeeDTO);
}

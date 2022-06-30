package com.pondmanage.service.imp;

import com.pondmanage.dto.EmployeeDTO;
import com.pondmanage.model.Employee;
import com.pondmanage.repository.AccountRepository;
import com.pondmanage.repository.EmployeeRepository;
import com.pondmanage.service.AccountService;
import com.pondmanage.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    AccountService accountService;

    @Override
    public void insertEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setName(employeeDTO.getName());
        employee.setPhone(employeeDTO.getPhone());
        employee.setSaliry(employeeDTO.getSaliry());
        employee.setOnboardDate(employeeDTO.getOnboardDate());
        employee.setBankNo(employeeDTO.getBankNo());
        employee.setBankAccountName(employeeDTO.getBankAccountName());
        employee.setBankName(employeeDTO.getBankName());
        employee.setAccount(accountService.getCurrentAccount());
        employeeRepository.save(employee);
    }

    @Override
    public List<Employee> findAll() {
        List<Employee> employees = new ArrayList<>();
        for (Employee employee : employeeRepository.findAll()){
            if (employee.getIsDelete() || employee.getAccount().getId() == null){
                continue;
            }
            if (Objects.equals(employee.getAccount().getId(), accountService.getCurrentAccount().getId())){
                employees.add(employee);
            }
        }
        return employees;
    }

    @Override
    public void delete(Long id) {
        Employee employee = employeeRepository.getById(id);
        employee.setIsDelete(true);
        employeeRepository.save(employee);
    }

    @Override
    public Employee getById(Long id) {
        return employeeRepository.getById(id);
    }

    @Override
    public void updateEmployee(EmployeeDTO employeeDTO) {
        Employee employee = employeeRepository.getById(employeeDTO.getId());
        employee.setName(employeeDTO.getName());
        employee.setPhone(employeeDTO.getPhone());
        employee.setSaliry(employeeDTO.getSaliry());
        employee.setOnboardDate(employeeDTO.getOnboardDate());
        employee.setBankName(employeeDTO.getBankName());
        employee.setBankNo(employeeDTO.getBankNo());
        employee.setBankAccountName(employeeDTO.getBankAccountName());
        employeeRepository.save(employee);
    }
}

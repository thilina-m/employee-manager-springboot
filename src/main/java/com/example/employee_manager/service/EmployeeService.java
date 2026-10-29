package com.example.employee_manager.service;

import com.example.employee_manager.model.Employee;
import com.example.employee_manager.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

   
    public Employee getEmployeeById(Long id) {
        Optional<Employee> optional = employeeRepository.findById(id);
        Employee employee = null;
        if (optional.isPresent()) {
            employee = optional.get();
        } else {
            throw new RuntimeException("Employee not found for id :: " + id);
        }
        return employee;
    }

    
    public Employee deleteEmployeeById(Long id) {
        Employee employee = getEmployeeById(id);
        employeeRepository.delete(employee);
        return employee;
    }
}
package com.example.employee_manager.controller;

import com.example.employee_manager.model.Employee;
import com.example.employee_manager.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // 1. View Home Page (Employee List)
    @GetMapping("/")
    public String viewHomePage(Model model) {
        model.addAttribute("listEmployees", employeeService.getAllEmployees());
        return "index"; 
    }

    // 2. Show New Employee Form (Browser)
    @GetMapping("/showNewEmployeeForm")
    public String showNewEmployeeForm(Model model) {
        Employee employee = new Employee();
        model.addAttribute("employee", employee);
        return "new_employee"; 
    }

    // 3. Save Employee (From Web Browser HTML Form)
    @PostMapping("/saveEmployee")
    public String saveEmployee(@ModelAttribute("employee") @Valid Employee employee, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "new_employee"; 
        }
        employeeService.saveEmployee(employee);
        return "redirect:/";
    }

    // 4. REST API Endpoint for Postman to SAVE (JSON Payload)
    @PostMapping("/api/saveEmployee")
    @ResponseBody
    public Employee saveEmployeeApi(@Valid @RequestBody Employee employee) {
        return employeeService.saveEmployee(employee);
    }

    // 5. Show Update Form (Browser)
    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") Long id, Model model) {
        Employee employee = employeeService.getEmployeeById(id);
        model.addAttribute("employee", employee);
        return "update_employee"; 
    }

    // 6. Delete Employee (Browser - GET Request)
    @GetMapping("/deleteEmployee/{id}")
    public String deleteEmployee(@PathVariable(value = "id") Long id) {
        this.employeeService.deleteEmployeeById(id);
        return "redirect:/";
    }

    // 7. REST API Endpoint for Postman to DELETE (DELETE Request)
    @DeleteMapping("/api/deleteEmployee/{id}")
    @ResponseBody
    public String deleteEmployeeApi(@PathVariable(value = "id") Long id) {
        employeeService.deleteEmployeeById(id);
        return "Employee deleted successfully with ID: " + id;
    }
}
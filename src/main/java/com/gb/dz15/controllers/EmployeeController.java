package com.gb.dz15.controllers;

import com.gb.dz15.entities.Employee;
import com.gb.dz15.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class EmployeeController {
    private EmployeeService employeeService;

    @Autowired
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // http://localhost:8189/app/employees/add
    @RequestMapping(path = "employees/add", method = RequestMethod.GET)
    public String addEmployee(Model model) {
        Employee employee = new Employee();
        model.addAttribute("employee", employee);
        return "employee_templates/add_employee";
    }

    @PostMapping(path = "employees/add")
    public String add_employee_result(@ModelAttribute("employee") Employee employee, Model model) {
        employeeService.save(employee);
        return "employee_templates/add_employee_result";
    }

    // http://localhost:8189/app/employees/show
    @RequestMapping(path = "employees/show", method = RequestMethod.GET)
    public String showAllEmployees(Model model) {
        List<Employee> employees = (List<Employee>) employeeService.getAll();
        model.addAttribute("employees", employees);
        return "employee_templates/all_employees";
    }
}

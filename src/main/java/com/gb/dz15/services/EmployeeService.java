package com.gb.dz15.services;


import com.gb.dz15.entities.Employee;
import com.gb.dz15.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    private EmployeeRepository repository;

    public EmployeeService() {
    }

    @Autowired
    public void setProductRepository(EmployeeRepository employeeRepository) {
        this.repository = employeeRepository;
    }

    public Employee save(Employee employee) {
        return repository.save(employee);
    }

    public List<Employee> getAll() {
        return (List<Employee>) repository.findAll();
    }

    public Employee getOneById(Long id) {
        return repository.findById(id).get();
    }

    public Page<Employee> getFilteredEmployees(Specification<Employee> spec, Pageable pageable) {
        return repository.findAll(spec, pageable);
    }

}

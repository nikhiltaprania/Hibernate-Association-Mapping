package com.employeemanagement.service;

import com.employeemanagement.model.Employee;
import com.employeemanagement.model.Project;

import java.util.List;

public interface EmployeeService {
    // Create
    void createEmployee();

    // Read
    Employee getEmployeeById(int employeeId);

    List<Employee> getAllEmployees();

    // Update
    void updateEmployee();

    // Delete
    void deleteEmployee();

    // Additional Methods
    List<Employee> getEmployeesByName(String name);

    List<Employee> getEmployeesByCity(String city);

    List<Employee> getEmployeesByProject(Project project);
}
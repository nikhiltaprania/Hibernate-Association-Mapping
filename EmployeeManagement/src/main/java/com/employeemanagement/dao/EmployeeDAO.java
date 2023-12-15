package com.employeemanagement.dao;

import com.employeemanagement.model.Employee;
import com.employeemanagement.model.Project;

import java.util.List;

public interface EmployeeDAO {
    // Create
    void createEmployee(Employee employee, List<String> phoneNumbers);

    void addPhoneNumbersToEmployee(Employee employee, List<String> phoneNumbers);

    // Read
    Employee getEmployeeById(int employeeId);

    List<Employee> getAllEmployees();

    // Update
    void updateEmployee(Employee employee);

    // Delete
    void deleteEmployee(int employeeId);

    void deletePhoneNumbersFromEmployee(Employee employee, List<Integer> phoneIds);

    // Additional Methods
    List<Employee> getEmployeesByName(String name);

    List<Employee> getEmployeesByCity(String city);

    List<Employee> getEmployeesByProject(Project project);
}